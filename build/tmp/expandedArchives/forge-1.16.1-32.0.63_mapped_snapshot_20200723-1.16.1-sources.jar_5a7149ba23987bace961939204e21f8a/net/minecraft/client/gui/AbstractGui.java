package net.minecraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.function.BiConsumer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractGui {
   public static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation("textures/gui/options_background.png");
   public static final ResourceLocation STATS_ICON_LOCATION = new ResourceLocation("textures/gui/container/stats_icons.png");
   public static final ResourceLocation GUI_ICONS_LOCATION = new ResourceLocation("textures/gui/icons.png");
   private int blitOffset;

   protected void hLine(MatrixStack matrixStack, int p_238465_2_, int p_238465_3_, int p_238465_4_, int color) {
      if (p_238465_3_ < p_238465_2_) {
         int i = p_238465_2_;
         p_238465_2_ = p_238465_3_;
         p_238465_3_ = i;
      }

      fill(matrixStack, p_238465_2_, p_238465_4_, p_238465_3_ + 1, p_238465_4_ + 1, color);
   }

   protected void vLine(MatrixStack matrixStack, int p_238473_2_, int p_238473_3_, int p_238473_4_, int color) {
      if (p_238473_4_ < p_238473_3_) {
         int i = p_238473_3_;
         p_238473_3_ = p_238473_4_;
         p_238473_4_ = i;
      }

      fill(matrixStack, p_238473_2_, p_238473_3_ + 1, p_238473_2_ + 1, p_238473_4_, color);
   }

   public static void fill(MatrixStack matrixStack, int p_238467_1_, int p_238467_2_, int p_238467_3_, int p_238467_4_, int color) {
      fill(matrixStack.getLast().getMatrix(), p_238467_1_, p_238467_2_, p_238467_3_, p_238467_4_, color);
   }

   private static void fill(Matrix4f matrix, int p_238460_1_, int p_238460_2_, int p_238460_3_, int p_238460_4_, int color) {
      if (p_238460_1_ < p_238460_3_) {
         int i = p_238460_1_;
         p_238460_1_ = p_238460_3_;
         p_238460_3_ = i;
      }

      if (p_238460_2_ < p_238460_4_) {
         int j = p_238460_2_;
         p_238460_2_ = p_238460_4_;
         p_238460_4_ = j;
      }

      float f3 = (float)(color >> 24 & 255) / 255.0F;
      float f = (float)(color >> 16 & 255) / 255.0F;
      float f1 = (float)(color >> 8 & 255) / 255.0F;
      float f2 = (float)(color & 255) / 255.0F;
      BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
      RenderSystem.enableBlend();
      RenderSystem.disableTexture();
      RenderSystem.defaultBlendFunc();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
      bufferbuilder.pos(matrix, (float)p_238460_1_, (float)p_238460_4_, 0.0F).color(f, f1, f2, f3).endVertex();
      bufferbuilder.pos(matrix, (float)p_238460_3_, (float)p_238460_4_, 0.0F).color(f, f1, f2, f3).endVertex();
      bufferbuilder.pos(matrix, (float)p_238460_3_, (float)p_238460_2_, 0.0F).color(f, f1, f2, f3).endVertex();
      bufferbuilder.pos(matrix, (float)p_238460_1_, (float)p_238460_2_, 0.0F).color(f, f1, f2, f3).endVertex();
      bufferbuilder.finishDrawing();
      WorldVertexBufferUploader.draw(bufferbuilder);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
   }

   protected void fillGradient(MatrixStack matrixStack, int x1, int y1, int x2, int y2, int p_238468_6_, int p_238468_7_) {
      RenderSystem.disableTexture();
      RenderSystem.enableBlend();
      RenderSystem.disableAlphaTest();
      RenderSystem.defaultBlendFunc();
      RenderSystem.shadeModel(7425);
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
      fillGradient(matrixStack.getLast().getMatrix(), bufferbuilder, x1, y1, x2, y2, this.blitOffset, p_238468_6_, p_238468_7_);
      tessellator.draw();
      RenderSystem.shadeModel(7424);
      RenderSystem.disableBlend();
      RenderSystem.enableAlphaTest();
      RenderSystem.enableTexture();
   }

   protected static void fillGradient(Matrix4f matrix, BufferBuilder builder, int x1, int y1, int x2, int y2, int z, int colorA, int colorB) {
      float f = (float)(colorA >> 24 & 255) / 255.0F;
      float f1 = (float)(colorA >> 16 & 255) / 255.0F;
      float f2 = (float)(colorA >> 8 & 255) / 255.0F;
      float f3 = (float)(colorA & 255) / 255.0F;
      float f4 = (float)(colorB >> 24 & 255) / 255.0F;
      float f5 = (float)(colorB >> 16 & 255) / 255.0F;
      float f6 = (float)(colorB >> 8 & 255) / 255.0F;
      float f7 = (float)(colorB & 255) / 255.0F;
      builder.pos(matrix, (float)x2, (float)y1, (float)z).color(f1, f2, f3, f).endVertex();
      builder.pos(matrix, (float)x1, (float)y1, (float)z).color(f1, f2, f3, f).endVertex();
      builder.pos(matrix, (float)x1, (float)y2, (float)z).color(f5, f6, f7, f4).endVertex();
      builder.pos(matrix, (float)x2, (float)y2, (float)z).color(f5, f6, f7, f4).endVertex();
   }

   public void drawCenteredString(MatrixStack matrixStack, FontRenderer font, String text, int x, int y, int color) {
      font.drawStringWithShadow(matrixStack, text, (float)(x - font.getStringWidth(text) / 2), (float)y, color);
   }

   public void drawCenteredString(MatrixStack matrixStack, FontRenderer font, ITextProperties text, int x, int y, int color) {
      font.func_238407_a_(matrixStack, text, (float)(x - font.func_238414_a_(text) / 2), (float)y, color);
   }

   public void drawString(MatrixStack matrixStack, FontRenderer font, String text, int x, int y, int color) {
      font.drawStringWithShadow(matrixStack, text, (float)x, (float)y, color);
   }

   public void drawString(MatrixStack matrixStack, FontRenderer font, ITextProperties text, int x, int y, int color) {
      font.func_238407_a_(matrixStack, text, (float)x, (float)y, color);
   }

   public void func_238459_a_(int p_238459_1_, int p_238459_2_, BiConsumer<Integer, Integer> p_238459_3_) {
      RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      p_238459_3_.accept(p_238459_1_ + 1, p_238459_2_);
      p_238459_3_.accept(p_238459_1_ - 1, p_238459_2_);
      p_238459_3_.accept(p_238459_1_, p_238459_2_ + 1);
      p_238459_3_.accept(p_238459_1_, p_238459_2_ - 1);
      RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      p_238459_3_.accept(p_238459_1_, p_238459_2_);
   }

   public static void blit(MatrixStack matrixStack, int p_238470_1_, int p_238470_2_, int blitOffset, int p_238470_4_, int p_238470_5_, TextureAtlasSprite p_238470_6_) {
      innerBlit(matrixStack.getLast().getMatrix(), p_238470_1_, p_238470_1_ + p_238470_4_, p_238470_2_, p_238470_2_ + p_238470_5_, blitOffset, p_238470_6_.getMinU(), p_238470_6_.getMaxU(), p_238470_6_.getMinV(), p_238470_6_.getMaxV());
   }

   public void blit(MatrixStack matrixStack, int p_238474_2_, int p_238474_3_, int p_238474_4_, int p_238474_5_, int p_238474_6_, int p_238474_7_) {
      blit(matrixStack, p_238474_2_, p_238474_3_, this.blitOffset, (float)p_238474_4_, (float)p_238474_5_, p_238474_6_, p_238474_7_, 256, 256);
   }

   public static void blit(MatrixStack matrixStack, int p_238464_1_, int p_238464_2_, int p_238464_3_, float p_238464_4_, float p_238464_5_, int p_238464_6_, int p_238464_7_, int p_238464_8_, int p_238464_9_) {
      innerBlit(matrixStack, p_238464_1_, p_238464_1_ + p_238464_6_, p_238464_2_, p_238464_2_ + p_238464_7_, p_238464_3_, p_238464_6_, p_238464_7_, p_238464_4_, p_238464_5_, p_238464_9_, p_238464_8_);
   }

   public static void blit(MatrixStack matrixStack, int p_238466_1_, int p_238466_2_, int p_238466_3_, int p_238466_4_, float p_238466_5_, float p_238466_6_, int p_238466_7_, int p_238466_8_, int p_238466_9_, int p_238466_10_) {
      innerBlit(matrixStack, p_238466_1_, p_238466_1_ + p_238466_3_, p_238466_2_, p_238466_2_ + p_238466_4_, 0, p_238466_7_, p_238466_8_, p_238466_5_, p_238466_6_, p_238466_9_, p_238466_10_);
   }

   public static void blit(MatrixStack matrixStack, int p_238463_1_, int p_238463_2_, float p_238463_3_, float p_238463_4_, int p_238463_5_, int p_238463_6_, int p_238463_7_, int p_238463_8_) {
      blit(matrixStack, p_238463_1_, p_238463_2_, p_238463_5_, p_238463_6_, p_238463_3_, p_238463_4_, p_238463_5_, p_238463_6_, p_238463_7_, p_238463_8_);
   }

   private static void innerBlit(MatrixStack matrixStack, int x1, int x2, int y1, int y2, int blitOffset, int p_238469_6_, int p_238469_7_, float p_238469_8_, float p_238469_9_, int p_238469_10_, int p_238469_11_) {
      innerBlit(matrixStack.getLast().getMatrix(), x1, x2, y1, y2, blitOffset, (p_238469_8_ + 0.0F) / (float)p_238469_10_, (p_238469_8_ + (float)p_238469_6_) / (float)p_238469_10_, (p_238469_9_ + 0.0F) / (float)p_238469_11_, (p_238469_9_ + (float)p_238469_7_) / (float)p_238469_11_);
   }

   private static void innerBlit(Matrix4f matrix, int x1, int x2, int y1, int y2, int blitOffset, float minU, float maxU, float minV, float maxV) {
      BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(matrix, (float)x1, (float)y2, (float)blitOffset).tex(minU, maxV).endVertex();
      bufferbuilder.pos(matrix, (float)x2, (float)y2, (float)blitOffset).tex(maxU, maxV).endVertex();
      bufferbuilder.pos(matrix, (float)x2, (float)y1, (float)blitOffset).tex(maxU, minV).endVertex();
      bufferbuilder.pos(matrix, (float)x1, (float)y1, (float)blitOffset).tex(minU, minV).endVertex();
      bufferbuilder.finishDrawing();
      RenderSystem.enableAlphaTest();
      WorldVertexBufferUploader.draw(bufferbuilder);
   }

   public int getBlitOffset() {
      return this.blitOffset;
   }

   public void setBlitOffset(int value) {
      this.blitOffset = value;
   }
}