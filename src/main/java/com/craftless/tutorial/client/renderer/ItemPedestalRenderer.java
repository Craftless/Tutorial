package com.craftless.tutorial.client.renderer;

import com.craftless.tutorial.tileentities.ItemPedestalTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemPedestalRenderer extends TileEntityRenderer<ItemPedestalTileEntity>
{

	private float degrees;
	private float height;

	public ItemPedestalRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
		degrees = 0;
		height = 0;
	}
	
	@Override
	public void render(ItemPedestalTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		NonNullList<ItemStack> items = tileEntityIn.getItems();
		float prevHeight = height;
		for (ItemStack is : items)
		{
			if (!is.isEmpty())
			{
				matrixStackIn.push();
				matrixStackIn.translate(0.5f, 1.5f, 0.5f);
				float currentTime = tileEntityIn.getWorld().getGameTime() + partialTicks;
				matrixStackIn.translate(0D, (Math.sin(Math.PI * currentTime / 16) / 4) + 0.1D, 0D);
				matrixStackIn.rotate(Vector3f.YP.rotationDegrees(degrees++ / 2));
				renderItem(is, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
				matrixStackIn.pop();

			}
		}
	}
	
	private void renderItem(ItemStack is, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		Minecraft.getInstance().getItemRenderer().renderItem(is, TransformType.GROUND, combinedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
	}

}
