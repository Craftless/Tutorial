package net.minecraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AccessibilityScreen extends SettingsScreen {
   private static final AbstractOption[] OPTIONS = new AbstractOption[]{AbstractOption.NARRATOR, AbstractOption.SHOW_SUBTITLES, AbstractOption.ACCESSIBILITY_TEXT_BACKGROUND_OPACITY, AbstractOption.ACCESSIBILITY_TEXT_BACKGROUND, AbstractOption.CHAT_OPACITY, AbstractOption.field_238235_g_, AbstractOption.field_238236_h_, AbstractOption.AUTO_JUMP, AbstractOption.SNEAK, AbstractOption.SPRINT};
   private Widget field_212989_d;

   public AccessibilityScreen(Screen parentScreen, GameSettings p_i51123_2_) {
      super(parentScreen, p_i51123_2_, new TranslationTextComponent("options.accessibility.title"));
   }

   protected void init() {
      int i = 0;

      for(AbstractOption abstractoption : OPTIONS) {
         int j = this.width / 2 - 155 + i % 2 * 160;
         int k = this.height / 6 + 24 * (i >> 1);
         Widget widget = this.addButton(abstractoption.createWidget(this.minecraft.gameSettings, j, k, 150));
         if (abstractoption == AbstractOption.NARRATOR) {
            this.field_212989_d = widget;
            widget.active = NarratorChatListener.INSTANCE.isActive();
         }

         ++i;
      }

      this.addButton(new Button(this.width / 2 - 100, this.height / 6 + 144, 200, 20, DialogTexts.field_240632_c_, (p_212984_1_) -> {
         this.minecraft.displayGuiScreen(this.parentScreen);
      }));
   }

   public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
      this.renderBackground(matrixStack);
      this.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 20, 16777215);
      super.render(matrixStack, mouseX, mouseY, partialTicks);
   }

   public void func_212985_a() {
      this.field_212989_d.setMessage(AbstractOption.NARRATOR.func_238157_c_(this.gameSettings));
   }
}