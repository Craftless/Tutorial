package com.craftless.tutorial.particles;

import java.util.Locale;

import com.craftless.tutorial.init.ModParticles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ColouredParticle extends SpriteTexturedParticle
{
	
	private double posX, posY, posZ;

	
	public ColouredParticle(ClientWorld worldIn, double xCoordIn, double yCoordIn,
			double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, ColouredParticleData data, IAnimatedSprite sprite) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);

		this.motionX = xSpeedIn;
		this.motionY = ySpeedIn;
		this.motionZ = zSpeedIn;
		this.posX = xCoordIn;
		this.posY = yCoordIn;
		this.posZ = zCoordIn;
		this.particleScale = 0.1f * (this.rand.nextFloat() * 0.2f + 1.7f);
		float f = (float) Math.random() * 0.4F + 0.6F;
		this.particleRed = ((float) (Math.random() * (double) 0.2F) + 0.8F) * data.getRed() * f;
		this.particleGreen = ((float) (Math.random() * (double) 0.2F) + 0.8F) * data.getGreen() * f;
		this.particleBlue = ((float) (Math.random() * (double) 0.2F) + 0.8F) * data.getBlue() * f;
		this.maxAge = (int) (Math.random() * 10.0D) + 40;
		
		
	}

	@Override
	public void tick() {
		this.prevPosX = posX;
		this.prevPosY = posY;
		this.prevPosZ = posZ;
		if (this.age++ >= this.maxAge)
		{
			this.setExpired();
		}
		else
		{
			this.move(this.motionX, this.motionY, this.motionZ);
	         this.particleGreen = (float)((double)this.particleGreen * 0.96D);
	         this.particleBlue = (float)((double)this.particleBlue * 0.9D);
	         this.motionX *= (double)0.7F;
	         this.motionY *= (double)0.7F;
	         this.motionZ *= (double)0.7F;
	         this.motionY -= (double)0.02F;
	         if (this.onGround) {
	            this.motionX *= (double)0.7F;
	            this.motionZ *= (double)0.7F;
	         }
		}
	}
	
	@Override
	public IParticleRenderType getRenderType() {
		return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<ColouredParticleData>
	{
		private final IAnimatedSprite spriteSet;

		public Factory(IAnimatedSprite spriteIn)
		{
			this.spriteSet = spriteIn;
		}
		
		
		@Override
		public Particle makeParticle(ColouredParticleData typeIn, ClientWorld worldIn, double x, double y, double z,
				double xSpeed, double ySpeed, double zSpeed) {
			ColouredParticle particle = new ColouredParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, typeIn, spriteSet);
			particle.selectSpriteRandomly(spriteSet);
			return particle;
		}

	}
	
	
	
	
	public static class ColouredParticleData extends ParticleType<ColouredParticleData> implements IParticleData
	{


		public static final IParticleData.IDeserializer<ColouredParticleData> DESERIALIZER = new IParticleData.IDeserializer<ColouredParticleData>() 
		{

			@Override
			public ColouredParticleData deserialize(ParticleType<ColouredParticleData> particleTypeIn,
					StringReader reader) throws CommandSyntaxException {
				reader.expect(' ');
				float red = (float) reader.readDouble();
				reader.expect(' ');
				float green = (float) reader.readDouble();
				reader.expect(' ');
				float blue = (float) reader.readDouble();
				reader.expect(' ');
				float alpha = (float) reader.readDouble();

				return new ColouredParticleData(red, green, blue, alpha);
			}

			public ColouredParticleData read(ParticleType<ColouredParticleData> particleTypeIn, PacketBuffer buffer) {
				return new ColouredParticleData(buffer.readFloat(), buffer.readFloat(), buffer.readFloat(),
						buffer.readFloat());
			}
		};

		private float red;
		private float green;
		private float blue;
		private float alpha;

		public ColouredParticleData(float redIn, float greenIn, float blueIn, float alphaIn) {
			this(false, DESERIALIZER);
			this.red = redIn;
			this.green = greenIn;
			this.blue = blueIn;
			this.alpha = MathHelper.clamp(alphaIn, 0.01f, 4.0f);		
		}
		
		public ColouredParticleData(boolean alwaysShow, IDeserializer<ColouredParticleData> deserializer)
		{
			super(alwaysShow, deserializer);
		}
		
		
		
		
		
		/*
		public ColouredParticleData(float redIn, float greenIn, float blueIn, float alphaIn) {
			this.red = redIn;
			this.green = greenIn;
			this.blue = blueIn;
			this.alpha = MathHelper.clamp(alphaIn, 0.01f, 4.0f);
		}
		 */

		@Override
		public void write(PacketBuffer buffer) {
			buffer.writeFloat(this.red);
			buffer.writeFloat(this.green);
			buffer.writeFloat(this.blue);
			buffer.writeFloat(this.alpha);
		}

		@SuppressWarnings("deprecation")
		@Override
		public String getParameters() {
			return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f", Registry.PARTICLE_TYPE.getKey(this.getType()),
					this.red, this.green, this.blue, this.alpha);
		}

		@Override
		public ParticleType<ColouredParticleData> getType() {
			return ModParticles.COLOURED_PARTICLE.get();
		}

		@OnlyIn(Dist.CLIENT)
		public float getRed() {
			return this.red;
		}

		@OnlyIn(Dist.CLIENT)
		public float getGreen() {
			return this.green;
		}

		@OnlyIn(Dist.CLIENT)
		public float getBlue() {
			return this.blue;
		}

		@OnlyIn(Dist.CLIENT)
		public float getAlpha() {
			return this.alpha;
		}


		@Override
		public Codec<ColouredParticleData> func_230522_e_() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
}
