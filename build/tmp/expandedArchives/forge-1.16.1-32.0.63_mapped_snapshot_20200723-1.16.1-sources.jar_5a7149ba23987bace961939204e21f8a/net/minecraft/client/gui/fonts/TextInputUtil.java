package net.minecraft.client.gui.fonts;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.CharacterManager;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TextInputUtil {
   private final Supplier<String> textSupplier;
   private final Consumer<String> textConsumer;
   private final Supplier<String> field_238564_c_;
   private final Consumer<String> field_238565_d_;
   private final Predicate<String> field_238566_e_;
   private int endIndex;
   private int startIndex;

   public TextInputUtil(Supplier<String> p_i232265_1_, Consumer<String> p_i232265_2_, Supplier<String> p_i232265_3_, Consumer<String> p_i232265_4_, Predicate<String> p_i232265_5_) {
      this.textSupplier = p_i232265_1_;
      this.textConsumer = p_i232265_2_;
      this.field_238564_c_ = p_i232265_3_;
      this.field_238565_d_ = p_i232265_4_;
      this.field_238566_e_ = p_i232265_5_;
      this.func_238588_f_();
   }

   public static Supplier<String> func_238570_a_(Minecraft p_238570_0_) {
      return () -> {
         return func_238576_b_(p_238570_0_);
      };
   }

   public static String func_238576_b_(Minecraft p_238576_0_) {
      return TextFormatting.getTextWithoutFormattingCodes(p_238576_0_.keyboardListener.getClipboardString().replaceAll("\\r", ""));
   }

   public static Consumer<String> func_238582_c_(Minecraft p_238582_0_) {
      return (p_238577_1_) -> {
         func_238571_a_(p_238582_0_, p_238577_1_);
      };
   }

   public static void func_238571_a_(Minecraft p_238571_0_, String p_238571_1_) {
      p_238571_0_.keyboardListener.setClipboardString(p_238571_1_);
   }

   public boolean putChar(char character) {
      if (SharedConstants.isAllowedCharacter(character)) {
         this.func_238572_a_(this.textSupplier.get(), Character.toString(character));
      }

      return true;
   }

   public boolean specialKeyPressed(int key) {
      if (Screen.isSelectAll(key)) {
         this.func_238585_d_();
         return true;
      } else if (Screen.isCopy(key)) {
         this.func_238580_c_();
         return true;
      } else if (Screen.isPaste(key)) {
         this.func_238574_b_();
         return true;
      } else if (Screen.isCut(key)) {
         this.func_238567_a_();
         return true;
      } else if (key == 259) {
         this.func_238586_d_(-1);
         return true;
      } else {
         if (key == 261) {
            this.func_238586_d_(1);
         } else {
            if (key == 263) {
               if (Screen.hasControlDown()) {
                  this.func_238575_b_(-1, Screen.hasShiftDown());
               } else {
                  this.func_238569_a_(-1, Screen.hasShiftDown());
               }

               return true;
            }

            if (key == 262) {
               if (Screen.hasControlDown()) {
                  this.func_238575_b_(1, Screen.hasShiftDown());
               } else {
                  this.func_238569_a_(1, Screen.hasShiftDown());
               }

               return true;
            }

            if (key == 268) {
               this.func_238579_b_(Screen.hasShiftDown());
               return true;
            }

            if (key == 269) {
               this.func_238584_c_(Screen.hasShiftDown());
               return true;
            }
         }

         return false;
      }
   }

   private int func_238589_g_(int p_238589_1_) {
      return MathHelper.clamp(p_238589_1_, 0, this.textSupplier.get().length());
   }

   private void func_238572_a_(String p_238572_1_, String p_238572_2_) {
      if (this.startIndex != this.endIndex) {
         p_238572_1_ = this.func_238583_c_(p_238572_1_);
      }

      this.endIndex = MathHelper.clamp(this.endIndex, 0, p_238572_1_.length());
      String s = (new StringBuilder(p_238572_1_)).insert(this.endIndex, p_238572_2_).toString();
      if (this.field_238566_e_.test(s)) {
         this.textConsumer.accept(s);
         this.startIndex = this.endIndex = Math.min(s.length(), this.endIndex + p_238572_2_.length());
      }

   }

   public void putText(String text) {
      this.func_238572_a_(this.textSupplier.get(), text);
   }

   private void func_238573_a_(boolean p_238573_1_) {
      if (!p_238573_1_) {
         this.startIndex = this.endIndex;
      }

   }

   public void func_238569_a_(int p_238569_1_, boolean p_238569_2_) {
      this.endIndex = Util.func_240980_a_(this.textSupplier.get(), this.endIndex, p_238569_1_);
      this.func_238573_a_(p_238569_2_);
   }

   public void func_238575_b_(int p_238575_1_, boolean p_238575_2_) {
      this.endIndex = CharacterManager.func_238351_a_(this.textSupplier.get(), p_238575_1_, this.endIndex, true);
      this.func_238573_a_(p_238575_2_);
   }

   public void func_238586_d_(int p_238586_1_) {
      String s = this.textSupplier.get();
      if (!s.isEmpty()) {
         String s1;
         if (this.startIndex != this.endIndex) {
            s1 = this.func_238583_c_(s);
         } else {
            int i = Util.func_240980_a_(s, this.endIndex, p_238586_1_);
            int j = Math.min(i, this.endIndex);
            int k = Math.max(i, this.endIndex);
            s1 = (new StringBuilder(s)).delete(j, k).toString();
            if (p_238586_1_ < 0) {
               this.startIndex = this.endIndex = j;
            }
         }

         this.textConsumer.accept(s1);
      }

   }

   public void func_238567_a_() {
      String s = this.textSupplier.get();
      this.field_238565_d_.accept(this.func_238578_b_(s));
      this.textConsumer.accept(this.func_238583_c_(s));
   }

   public void func_238574_b_() {
      this.func_238572_a_(this.textSupplier.get(), this.field_238564_c_.get());
      this.startIndex = this.endIndex;
   }

   public void func_238580_c_() {
      this.field_238565_d_.accept(this.func_238578_b_(this.textSupplier.get()));
   }

   public void func_238585_d_() {
      this.startIndex = 0;
      this.endIndex = this.textSupplier.get().length();
   }

   private String func_238578_b_(String p_238578_1_) {
      int i = Math.min(this.endIndex, this.startIndex);
      int j = Math.max(this.endIndex, this.startIndex);
      return p_238578_1_.substring(i, j);
   }

   private String func_238583_c_(String p_238583_1_) {
      if (this.startIndex == this.endIndex) {
         return p_238583_1_;
      } else {
         int i = Math.min(this.endIndex, this.startIndex);
         int j = Math.max(this.endIndex, this.startIndex);
         String s = p_238583_1_.substring(0, i) + p_238583_1_.substring(j);
         this.startIndex = this.endIndex = i;
         return s;
      }
   }

   private void func_238579_b_(boolean p_238579_1_) {
      this.endIndex = 0;
      this.func_238573_a_(p_238579_1_);
   }

   public void func_238588_f_() {
      this.func_238584_c_(false);
   }

   private void func_238584_c_(boolean p_238584_1_) {
      this.endIndex = this.textSupplier.get().length();
      this.func_238573_a_(p_238584_1_);
   }

   public int getEndIndex() {
      return this.endIndex;
   }

   public void func_238581_c_(int p_238581_1_, boolean p_238581_2_) {
      this.endIndex = this.func_238589_g_(p_238581_1_);
      this.func_238573_a_(p_238581_2_);
   }

   public int getStartIndex() {
      return this.startIndex;
   }

   public void func_238568_a_(int p_238568_1_, int p_238568_2_) {
      int i = this.textSupplier.get().length();
      this.endIndex = MathHelper.clamp(p_238568_1_, 0, i);
      this.startIndex = MathHelper.clamp(p_238568_2_, 0, i);
   }

   public boolean func_238590_i_() {
      return this.endIndex != this.startIndex;
   }
}