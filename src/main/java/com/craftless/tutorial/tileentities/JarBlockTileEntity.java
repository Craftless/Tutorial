package com.craftless.tutorial.tileentities;

import com.craftless.tutorial.init.ModItems;
import com.craftless.tutorial.init.ModTileEntityTypes;

import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class JarBlockTileEntity extends TileEntity implements ITickable
{

	private int tick = 0;
	public JarBlockTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
		// TODO Auto-generated constructor stub
	}
	
	public JarBlockTileEntity()
	{
		this(ModTileEntityTypes.JAR_BLOCK.get());
	}

	@Override
	public void tick() {
		if (tick > 69 * 20)
		{
			ItemEntity itemEntity = new ItemEntity(world, (double)this.pos.getX(), (double)this.pos.getY(), (double)this.pos.getZ(), ModItems.JAR.get().getDefaultInstance());
			world.addEntity(itemEntity);
		}
	}

}
