package net.minecraft.util.math;

public class CubeCoordinateIterator {
   private int field_239623_a_;
   private int field_239624_b_;
   private int field_239625_c_;
   private int field_239626_d_;
   private int field_239627_e_;
   private int field_239628_f_;
   private int field_239629_g_;
   private int field_239630_h_;
   private int x;
   private int y;
   private int z;

   public CubeCoordinateIterator(int startX, int startY, int startZ, int p_i50798_4_, int endY, int p_i50798_6_) {
      this.field_239623_a_ = startX;
      this.field_239624_b_ = startY;
      this.field_239625_c_ = startZ;
      this.field_239626_d_ = p_i50798_4_ - startX + 1;
      this.field_239627_e_ = endY - startY + 1;
      this.field_239628_f_ = p_i50798_6_ - startZ + 1;
      this.field_239629_g_ = this.field_239626_d_ * this.field_239627_e_ * this.field_239628_f_;
   }

   public boolean hasNext() {
      if (this.field_239630_h_ == this.field_239629_g_) {
         return false;
      } else {
         this.x = this.field_239630_h_ % this.field_239626_d_;
         int i = this.field_239630_h_ / this.field_239626_d_;
         this.y = i % this.field_239627_e_;
         this.z = i / this.field_239627_e_;
         ++this.field_239630_h_;
         return true;
      }
   }

   public int getX() {
      return this.field_239623_a_ + this.x;
   }

   public int getY() {
      return this.field_239624_b_ + this.y;
   }

   public int getZ() {
      return this.field_239625_c_ + this.z;
   }

   public int numBoundariesTouched() {
      int i = 0;
      if (this.x == 0 || this.x == this.field_239626_d_ - 1) {
         ++i;
      }

      if (this.y == 0 || this.y == this.field_239627_e_ - 1) {
         ++i;
      }

      if (this.z == 0 || this.z == this.field_239628_f_ - 1) {
         ++i;
      }

      return i;
   }
}