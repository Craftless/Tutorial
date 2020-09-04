package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.advancements.AdvancementsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsBridgeScreen;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IngameMenuScreen extends Screen {
   private final boolean isFullMenu;

   public IngameMenuScreen(boolean p_i51519_1_) {
      super(p_i51519_1_ ? new TranslationTextComponent("menu.game") : new TranslationTextComponent("menu.paused"));
      this.isFullMenu = p_i51519_1_;
   }

   protected void init() {
      if (this.isFullMenu) {
         this.addButtons();
      }

   }

   private void addButtons() {
      int i = -16;
      int j = 98;
      this.addButton(new Button(this.width / 2 - 102, this.height / 4 + 24 + -16, 204, 20, new TranslationTextComponent("menu.returnToGame"), (p_213070_1_) -> {
         this.minecraft.displayGuiScreen((Screen)null);
         this.minecraft.mouseHelper.grabMouse();
      }));
      this.addButton(new Button(this.width / 2 - 102, this.height / 4 + 48 + -16, 98, 20, new TranslationTextComponent("gui.advancements"), (p_213065_1_) -> {
         this.minecraft.displayGuiScreen(new AdvancementsScreen(this.minecraft.player.connection.getAdvancementManager()));
      }));
      this.addButton(new Button(this.width / 2 + 4, this.height / 4 + 48 + -16, 98, 20, new TranslationTextComponent("gui.stats"), (p_213066_1_) -> {
         this.minecraft.displayGuiScreen(new StatsScreen(this, this.minecraft.player.getStats()));
      }));
      String s = SharedConstants.getVersion().isStable() ? "https://aka.ms/javafeedback?ref=game" : "https://aka.ms/snapshotfeedback?ref=game";
      this.addButton(new Button(this.width / 2 - 102, this.height / 4 + 72 + -16, 98, 20, new TranslationTextComponent("menu.sendFeedback"), (p_213072_2_) -> {
         this.minecraft.displayGuiScreen(new ConfirmOpenLinkScreen((p_213069_2_) -> {
            if (p_213069_2_) {
               Util.getOSType().openURI(s);
            }

            this.minecraft.displayGuiScreen(this);
         }, s, true));
      }));
      this.addButton(new Button(this.width / 2 + 4, this.height / 4 + 72 + -16, 98, 20, new TranslationTextComponent("menu.reportBugs"), (p_213063_1_) -> {
         this.minecraft.displayGuiScreen(new ConfirmOpenLinkScreen((p_213064_1_) -> {
            if (p_213064_1_) {
               Util.getOSType().openURI("https://aka.ms/snapshotbugs?ref=game");
            }

            this.minecraft.displayGuiScreen(this);
         }, "https://aka.ms/snapshotbugs?ref=game", true));
      }));
      this.addButton(new Button(this.width / 2 - 102, this.height / 4 + 96 + -16, 98, 20, new TranslationTextComponent("menu.options"), (p_213071_1_) -> {
         this.minecraft.displayGuiScreen(new OptionsScreen(this, this.minecraft.gameSettings));
      }));
      Button button = this.addButton(new Button(this.width / 2 + 4, this.height / 4 + 96 + -16, 98, 20, new TranslationTextComponent("menu.shareToLan"), (p_213068_1_) -> {
         this.minecraft.displayGuiScreen(new ShareToLanScreen(this));
      }));
      button.active = this.minecraft.isSingleplayer() && !this.minecraft.getIntegratedServer().getPublic();
      Button button1 = this.addButton(new Button(this.width / 2 - 102, this.height / 4 + 120 + -16, 204, 20, new TranslationTextComponent("menu.returnToMenu"), (p_213067_1_) -> {
         boolean flag = this.minecraft.isIntegratedServerRunning();
         boolean flag1 = this.minecraft.isConnectedToRealms();
         p_213067_1_.active = false;
         this.minecraft.world.sendQuittingDisconnectingPacket();
         if (flag) {
            this.minecraft.unloadWorld(new DirtMessageScreen(new TranslationTextComponent("menu.savingLevel")));
         } else {
            this.minecraft.unloadWorld();
         }

         if (flag) {
            this.minecraft.displayGuiScreen(new MainMenuScreen());
         } else if (flag1) {
            RealmsBridgeScreen realmsbridgescreen = new RealmsBridgeScreen();
            realmsbridgescreen.func_231394_a_(new MainMenuScreen());
         } else {
            this.minecraft.displayGuiScreen(new MultiplayerScreen(new MainMenuScreen()));
         }

      }));
      if (!this.minecraft.isIntegratedServerRunning()) {
         button1.setMessage(new TranslationTextComponent("menu.disconnect"));
      }

   }

   public void tick() {
      super.tick();
   }

   public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
      if (this.isFullMenu) {
         this.renderBackground(matrixStack);
         this.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 40, 16777215);
      } else {
         this.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 10, 16777215);
      }

      super.render(matrixStack, mouseX, mouseY, partialTicks);
   }
}