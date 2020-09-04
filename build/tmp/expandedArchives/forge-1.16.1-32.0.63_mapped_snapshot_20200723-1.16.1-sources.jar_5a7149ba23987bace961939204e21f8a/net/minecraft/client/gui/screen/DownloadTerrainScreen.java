package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DownloadTerrainScreen extends Screen {
   public DownloadTerrainScreen() {
      super(NarratorChatListener.EMPTY);
   }

   public boolean shouldCloseOnEsc() {
      return false;
   }

   public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
      this.renderDirtBackground(0);
      this.drawCenteredString(matrixStack, this.font, I18n.format("multiplayer.downloadingTerrain"), this.width / 2, this.height / 2 - 50, 16777215);
      super.render(matrixStack, mouseX, mouseY, partialTicks);
   }

   public boolean isPauseScreen() {
      return false;
   }
}