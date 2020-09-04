package net.minecraft.client.gui;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IGuiEventListener {
   default void mouseMoved(double xPos, double mouseY) {
   }

   default boolean mouseClicked(double mouseX, double mouseY, int p_231044_5_) {
      return false;
   }

   default boolean mouseReleased(double mouseX, double mouseY, int button) {
      return false;
   }

   default boolean mouseDragged(double p_231045_1_, double p_231045_3_, int p_231045_5_, double p_231045_6_, double p_231045_8_) {
      return false;
   }

   default boolean mouseScrolled(double mouseX, double mouseY, double p_231043_5_) {
      return false;
   }

   default boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      return false;
   }

   default boolean keyReleased(int keyCode, int scanCode, int modifiers) {
      return false;
   }

   default boolean charTyped(char p_231042_1_, int p_231042_2_) {
      return false;
   }

   default boolean changeFocus(boolean p_231049_1_) {
      return false;
   }

   default boolean isMouseOver(double mouseX, double mouseY) {
      return false;
   }
}