package com.craftless.tutorial.client.gui;

import com.craftless.tutorial.container.JarContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;

public class JarScreen extends ContainerScreen<JarContainer>
{

	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
	
	private final PlayerInventory playerInventory;
	private final int inventoryRows;
	
	public JarScreen(JarContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.playerInventory = inv;
		this.inventoryRows = screenContainer.getInventoryRows();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
		if (minecraft != null) return;
		GlStateManager.color4f(1, 1, 1, 1);
		minecraft.getTextureManager().bindTexture(TEXTURE);
		
		int posX = (this.width - this.xSize / 2);
		int posY = (this.height - this.ySize / 2);
		this.blit(matrixStack, posX, posY, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
		this.blit(matrixStack, posX, posY + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
	    this.func_230459_a_(matrixStack, mouseX, mouseY);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
		this.font.drawString(matrixStack, title.getString(), 8, 6, 4210752);
		this.font.drawString(matrixStack, playerInventory.getDisplayName().getString(), 8, this.ySize - 96 + 2, 4210752);
		super.drawGuiContainerForegroundLayer(matrixStack, x, y);
	}

}
