package net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChestScreen extends ContainerScreen<ChestContainer> implements IHasContainer<ChestContainer> {
   /** The ResourceLocation containing the chest GUI texture. */
   private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
   /** Window height is calculated with these values; the more rows, the higher */
   private final int inventoryRows;

   public ChestScreen(ChestContainer p_i51095_1_, PlayerInventory p_i51095_2_, ITextComponent p_i51095_3_) {
      super(p_i51095_1_, p_i51095_2_, p_i51095_3_);
      this.passEvents = false;
      int i = 222;
      int j = 114;
      this.inventoryRows = p_i51095_1_.getNumRows();
      this.ySize = 114 + this.inventoryRows * 18;
      this.playerInventoryTitleY = this.ySize - 94;
   }

   public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
      this.renderBackground(matrixStack);
      super.render(matrixStack, mouseX, mouseY, partialTicks);
      this.func_230459_a_(matrixStack, mouseX, mouseY);
   }

   protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.minecraft.getTextureManager().bindTexture(CHEST_GUI_TEXTURE);
      int i = (this.width - this.xSize) / 2;
      int j = (this.height - this.ySize) / 2;
      this.blit(matrixStack, i, j, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
      this.blit(matrixStack, i, j + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
   }
}