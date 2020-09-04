package net.minecraft.client.gui;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface INestedGuiEventHandler extends IGuiEventListener {
   List<? extends IGuiEventListener> getEventListeners();

   /**
    * Returns the first event listener that intersects with the mouse coordinates.
    */
   default Optional<IGuiEventListener> getEventListenerForPos(double mouseX, double mouseY) {
      for(IGuiEventListener iguieventlistener : this.getEventListeners()) {
         if (iguieventlistener.isMouseOver(mouseX, mouseY)) {
            return Optional.of(iguieventlistener);
         }
      }

      return Optional.empty();
   }

   default boolean mouseClicked(double mouseX, double mouseY, int p_231044_5_) {
      for(IGuiEventListener iguieventlistener : this.getEventListeners()) {
         if (iguieventlistener.mouseClicked(mouseX, mouseY, p_231044_5_)) {
            this.setListener(iguieventlistener);
            if (p_231044_5_ == 0) {
               this.setDragging(true);
            }

            return true;
         }
      }

      return false;
   }

   default boolean mouseReleased(double mouseX, double mouseY, int button) {
      this.setDragging(false);
      return this.getEventListenerForPos(mouseX, mouseY).filter((p_212931_5_) -> {
         return p_212931_5_.mouseReleased(mouseX, mouseY, button);
      }).isPresent();
   }

   default boolean mouseDragged(double p_231045_1_, double p_231045_3_, int p_231045_5_, double p_231045_6_, double p_231045_8_) {
      return this.getListener() != null && this.isDragging() && p_231045_5_ == 0 ? this.getListener().mouseDragged(p_231045_1_, p_231045_3_, p_231045_5_, p_231045_6_, p_231045_8_) : false;
   }

   boolean isDragging();

   void setDragging(boolean dragging);

   default boolean mouseScrolled(double mouseX, double mouseY, double p_231043_5_) {
      return this.getEventListenerForPos(mouseX, mouseY).filter((p_212929_6_) -> {
         return p_212929_6_.mouseScrolled(mouseX, mouseY, p_231043_5_);
      }).isPresent();
   }

   default boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      return this.getListener() != null && this.getListener().keyPressed(keyCode, scanCode, modifiers);
   }

   default boolean keyReleased(int keyCode, int scanCode, int modifiers) {
      return this.getListener() != null && this.getListener().keyReleased(keyCode, scanCode, modifiers);
   }

   default boolean charTyped(char p_231042_1_, int p_231042_2_) {
      return this.getListener() != null && this.getListener().charTyped(p_231042_1_, p_231042_2_);
   }

   @Nullable
   IGuiEventListener getListener();

   void setListener(@Nullable IGuiEventListener listener);

   default void setFocusedDefault(@Nullable IGuiEventListener eventListener) {
      this.setListener(eventListener);
      eventListener.changeFocus(true);
   }

   default void setListenerDefault(@Nullable IGuiEventListener eventListener) {
      this.setListener(eventListener);
   }

   default boolean changeFocus(boolean p_231049_1_) {
      IGuiEventListener iguieventlistener = this.getListener();
      boolean flag = iguieventlistener != null;
      if (flag && iguieventlistener.changeFocus(p_231049_1_)) {
         return true;
      } else {
         List<? extends IGuiEventListener> list = this.getEventListeners();
         int j = list.indexOf(iguieventlistener);
         int i;
         if (flag && j >= 0) {
            i = j + (p_231049_1_ ? 1 : 0);
         } else if (p_231049_1_) {
            i = 0;
         } else {
            i = list.size();
         }

         ListIterator<? extends IGuiEventListener> listiterator = list.listIterator(i);
         BooleanSupplier booleansupplier = p_231049_1_ ? listiterator::hasNext : listiterator::hasPrevious;
         Supplier<? extends IGuiEventListener> supplier = p_231049_1_ ? listiterator::next : listiterator::previous;

         while(booleansupplier.getAsBoolean()) {
            IGuiEventListener iguieventlistener1 = supplier.get();
            if (iguieventlistener1.changeFocus(p_231049_1_)) {
               this.setListener(iguieventlistener1);
               return true;
            }
         }

         this.setListener((IGuiEventListener)null);
         return false;
      }
   }
}