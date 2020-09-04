package com.craftless.tutorial.client.renderer;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.client.model.HogModel;
import com.craftless.tutorial.entities.HogEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class HogRenderer extends MobRenderer<HogEntity, HogModel<HogEntity>>
{

	public HogRenderer(EntityRendererManager p_i50961_1_) {
		super(p_i50961_1_, new HogModel<>(), 0.7F);
	}



	protected static final ResourceLocation TEXTURE = new ResourceLocation(Tutorial.MOD_ID, "textures/entity/hog.png");



	@Override
	public ResourceLocation getEntityTexture(HogEntity entity) 
	{
		return TEXTURE;
	}
	
	

	

	
	
	

}
