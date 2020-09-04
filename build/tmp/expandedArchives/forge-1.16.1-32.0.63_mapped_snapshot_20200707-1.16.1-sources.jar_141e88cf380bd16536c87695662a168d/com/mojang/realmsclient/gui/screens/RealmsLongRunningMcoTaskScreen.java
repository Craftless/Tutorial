package com.mojang.realmsclient.gui.screens;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.realmsclient.exception.RealmsDefaultUncaughtExceptionHandler;
import com.mojang.realmsclient.gui.LongRunningTask;
import java.util.Set;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.IErrorConsumer;
import net.minecraft.realms.RealmsNarratorHelper;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsLongRunningMcoTaskScreen extends RealmsScreen implements IErrorConsumer {
   private static final Logger field_224238_b = LogManager.getLogger();
   private final Screen field_224241_e;
   private volatile String field_224243_g = "";
   private volatile boolean field_224244_h;
   private volatile ITextComponent field_224245_i;
   private volatile boolean field_224246_j;
   private int field_224247_k;
   private final LongRunningTask field_224248_l;
   private final int field_224249_m = 212;
   public static final String[] field_224237_a = new String[]{"\u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583", "_ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584", "_ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585", "_ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586", "_ _ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587", "_ _ _ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588", "_ _ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587", "_ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586", "_ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585", "_ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584", "\u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583", "\u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _", "\u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _", "\u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _", "\u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _ _", "\u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _ _ _", "\u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _ _", "\u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _", "\u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _", "\u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _"};

   public RealmsLongRunningMcoTaskScreen(Screen p_i232209_1_, LongRunningTask p_i232209_2_) {
      this.field_224241_e = p_i232209_1_;
      this.field_224248_l = p_i232209_2_;
      p_i232209_2_.func_224987_a(this);
      Thread thread = new Thread(p_i232209_2_, "Realms-long-running-task");
      thread.setUncaughtExceptionHandler(new RealmsDefaultUncaughtExceptionHandler(field_224238_b));
      thread.start();
   }

   public void tick() {
      super.tick();
      RealmsNarratorHelper.func_239553_b_(this.field_224243_g);
      ++this.field_224247_k;
      this.field_224248_l.func_224990_b();
   }

   public boolean keyPressed(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
      if (p_231046_1_ == 256) {
         this.func_224236_c();
         return true;
      } else {
         return super.keyPressed(p_231046_1_, p_231046_2_, p_231046_3_);
      }
   }

   public void init() {
      this.field_224248_l.func_224991_c();
      this.addButton(new Button(this.width / 2 - 106, func_239562_k_(12), 212, 20, DialogTexts.field_240633_d_, (p_237852_1_) -> {
         this.func_224236_c();
      }));
   }

   private void func_224236_c() {
      this.field_224246_j = true;
      this.field_224248_l.func_224992_d();
      this.minecraft.displayGuiScreen(this.field_224241_e);
   }

   public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      this.renderBackground(p_230430_1_);
      this.drawCenteredString(p_230430_1_, this.font, this.field_224243_g, this.width / 2, func_239562_k_(3), 16777215);
      if (!this.field_224244_h) {
         this.drawCenteredString(p_230430_1_, this.font, field_224237_a[this.field_224247_k % field_224237_a.length], this.width / 2, func_239562_k_(8), 8421504);
      }

      if (this.field_224244_h) {
         this.drawCenteredString(p_230430_1_, this.font, this.field_224245_i, this.width / 2, func_239562_k_(8), 16711680);
      }

      super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   public void func_230434_a_(ITextComponent p_230434_1_) {
      this.field_224244_h = true;
      this.field_224245_i = p_230434_1_;
      RealmsNarratorHelper.func_239550_a_(p_230434_1_.getString());
      this.func_237850_a_();
      this.addButton(new Button(this.width / 2 - 106, this.height / 4 + 120 + 12, 200, 20, DialogTexts.field_240637_h_, (p_237851_1_) -> {
         this.func_224236_c();
      }));
   }

   public void func_237850_a_() {
      Set<IGuiEventListener> set = Sets.newHashSet(this.buttons);
      this.children.removeIf(set::contains);
      this.buttons.clear();
   }

   public void func_224234_b(String p_224234_1_) {
      this.field_224243_g = p_224234_1_;
   }

   public boolean func_224235_b() {
      return this.field_224246_j;
   }
}