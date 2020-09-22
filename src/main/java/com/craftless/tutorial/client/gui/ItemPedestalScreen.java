package com.craftless.tutorial.client.gui;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.container.ItemPedestalContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ItemPedestalScreen extends ContainerScreen<ItemPedestalContainer> implements IHasContainer<ItemPedestalContainer>
{
	
	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Tutorial.MOD_ID, "textures/gui/item_pedestal.png");

	public ItemPedestalScreen(ItemPedestalContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.guiLeft = 0;
		this.guiTop = 0;
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	    this.func_230459_a_(matrixStack, mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
		super.drawGuiContainerForegroundLayer(matrixStack, x, y);
		this.font.drawString(matrixStack, this.title.getString(), 8.0f, 8.0f, 4210752);
		this.font.drawString(matrixStack, this.playerInventory.getDisplayName().getString(), 8.0f, (float) (this.ySize - 96 + 2), 4210752);
	}
}
