package com.craftless.tutorial.client.gui;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.container.RubyChestContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RubyChestScreen extends ContainerScreen<RubyChestContainer> 
{

	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Tutorial.MOD_ID, "textures/gui/ruby_chest.png");
	
	public RubyChestScreen(RubyChestContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.guiLeft = 0;
		this.guiTop = 0;
		this.xSize = 175;
		this.ySize = 183;
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);		
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	    this.func_230459_a_(matrixStack, mouseX, mouseY);
	}
	
	
	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) 
	{
		super.drawGuiContainerForegroundLayer(matrixStack, x, y);
		this.font.drawString(matrixStack, this.title.getString(), 8.0f, 6.0f, 4210752);
		this.font.drawString(matrixStack, this.playerInventory.getDisplayName().getString(), 8.0f, 90.0f, 4210752);
		
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);	
		RenderSystem.color3f(1.0f, 1.0f, 1.0f);
		
		this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
		int x = (this.width - this.xSize / 2);
		int y = (this.height - this.ySize / 2);
		this.blit(matrixStack, x, y, 0, 0, this.xSize, this.ySize);
		
	}
	
	
	
	
	
	
}

