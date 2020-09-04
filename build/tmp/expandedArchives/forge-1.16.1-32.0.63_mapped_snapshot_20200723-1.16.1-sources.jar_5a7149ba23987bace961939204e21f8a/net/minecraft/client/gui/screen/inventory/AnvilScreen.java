package net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.RepairContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CRenameItemPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AnvilScreen extends AbstractRepairScreen<RepairContainer> {
   private static final ResourceLocation ANVIL_RESOURCE = new ResourceLocation("textures/gui/container/anvil.png");
   private TextFieldWidget nameField;

   public AnvilScreen(RepairContainer p_i51103_1_, PlayerInventory p_i51103_2_, ITextComponent p_i51103_3_) {
      super(p_i51103_1_, p_i51103_2_, p_i51103_3_, ANVIL_RESOURCE);
      this.titleX = 60;
   }

   protected void func_230453_j_() {
      this.minecraft.keyboardListener.enableRepeatEvents(true);
      int i = (this.width - this.xSize) / 2;
      int j = (this.height - this.ySize) / 2;
      this.nameField = new TextFieldWidget(this.font, i + 62, j + 24, 103, 12, new TranslationTextComponent("container.repair"));
      this.nameField.setCanLoseFocus(false);
      this.nameField.setTextColor(-1);
      this.nameField.setDisabledTextColour(-1);
      this.nameField.setEnableBackgroundDrawing(false);
      this.nameField.setMaxStringLength(35);
      this.nameField.setResponder(this::func_214075_a);
      this.children.add(this.nameField);
      this.setFocusedDefault(this.nameField);
   }

   public void resize(Minecraft minecraft, int width, int height) {
      String s = this.nameField.getText();
      this.init(minecraft, width, height);
      this.nameField.setText(s);
   }

   public void onClose() {
      super.onClose();
      this.minecraft.keyboardListener.enableRepeatEvents(false);
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256) {
         this.minecraft.player.closeScreen();
      }

      return !this.nameField.keyPressed(keyCode, scanCode, modifiers) && !this.nameField.canWrite() ? super.keyPressed(keyCode, scanCode, modifiers) : true;
   }

   private void func_214075_a(String p_214075_1_) {
      if (!p_214075_1_.isEmpty()) {
         String s = p_214075_1_;
         Slot slot = this.container.getSlot(0);
         if (slot != null && slot.getHasStack() && !slot.getStack().hasDisplayName() && p_214075_1_.equals(slot.getStack().getDisplayName().getString())) {
            s = "";
         }

         this.container.updateItemName(s);
         this.minecraft.player.connection.sendPacket(new CRenameItemPacket(s));
      }
   }

   protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
      RenderSystem.disableBlend();
      super.drawGuiContainerForegroundLayer(matrixStack, x, y);
      int i = this.container.getMaximumCost();
      if (i > 0) {
         int j = 8453920;
         boolean flag = true;
         String s = I18n.format("container.repair.cost", i);
         if (i >= 40 && !this.minecraft.player.abilities.isCreativeMode) {
            s = I18n.format("container.repair.expensive");
            j = 16736352;
         } else if (!this.container.getSlot(2).getHasStack()) {
            flag = false;
         } else if (!this.container.getSlot(2).canTakeStack(this.playerInventory.player)) {
            j = 16736352;
         }

         if (flag) {
            int k = this.xSize - 8 - this.font.getStringWidth(s) - 2;
            int l = 69;
            fill(matrixStack, k - 2, 67, this.xSize - 8, 79, 1325400064);
            this.font.drawStringWithShadow(matrixStack, s, (float)k, 69.0F, j);
         }
      }

   }

   public void func_230452_b_(MatrixStack p_230452_1_, int p_230452_2_, int p_230452_3_, float p_230452_4_) {
      this.nameField.render(p_230452_1_, p_230452_2_, p_230452_3_, p_230452_4_);
   }

   /**
    * Sends the contents of an inventory slot to the client-side Container. This doesn't have to match the actual
    * contents of that slot.
    */
   public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack) {
      if (slotInd == 0) {
         this.nameField.setText(stack.isEmpty() ? "" : stack.getDisplayName().getString());
         this.nameField.setEnabled(!stack.isEmpty());
         this.setListener(this.nameField);
      }

   }
}