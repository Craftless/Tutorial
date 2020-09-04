package net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.SmithingTableContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SmithingTableScreen extends AbstractRepairScreen<SmithingTableContainer> {
   private static final ResourceLocation field_238852_A_ = new ResourceLocation("textures/gui/container/smithing.png");

   public SmithingTableScreen(SmithingTableContainer p_i232294_1_, PlayerInventory p_i232294_2_, ITextComponent p_i232294_3_) {
      super(p_i232294_1_, p_i232294_2_, p_i232294_3_, field_238852_A_);
      this.titleX = 60;
      this.titleY = 18;
   }

   protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
      RenderSystem.disableBlend();
      super.drawGuiContainerForegroundLayer(matrixStack, x, y);
   }
}