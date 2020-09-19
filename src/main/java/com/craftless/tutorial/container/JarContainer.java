package com.craftless.tutorial.container;

import com.craftless.tutorial.init.ModContainerTypes;
import com.craftless.tutorial.items.JarItem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class JarContainer extends Container
{
	private final ItemStack is;
	private final IItemHandler itemHandler;
	private int blocked = -1;

	public JarContainer(final int windowId, final PlayerInventory playerInventory, PacketBuffer packetBuffer) {
		super(ModContainerTypes.JAR.get(), windowId);
		this.is = getHeldItem(playerInventory.player);
		this.itemHandler = ((JarItem) this.is.getItem()).getInventory(this.is);

		for (int i = 0; i < this.itemHandler.getSlots(); ++i)
		{
			int x = 8 + 18 * (i % 9);
			int y = 18 + 18 * (i / 9);
			addSlot(new SlotItemHandler(itemHandler, i, x, y));
		}

		//Main Inventory
		final int columns = 9;
		final int rows = itemHandler.getSlots() / columns;
		final int startY = (rows - 4) * 18;

		//PlayerInventory
		for (int y = 0; y < 3; ++y)
		{
			for (int x = 0; x < 9; ++x)
			{
				this.addSlot(new Slot(playerInventory, 9 + y * 9 + 9, 8 + x * 18, 103 + y * 18 + startY));
			}
		}

		// Hotbar
		for (int x = 0; x < 9; ++x) {
			Slot slot = addSlot(new Slot(playerInventory, x, 8 + x * 18, 161 + startY) {
				@Override
				public boolean canTakeStack(PlayerEntity playerIn) {
					return slotNumber != blocked;
				}
			});
			if (x == playerInventory.currentItem
					&& ItemStack.areItemStacksEqual(playerInventory.getCurrentItem(), this.is)) {
				blocked = slot.slotNumber;
			}
		}
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn)
	{
		return true;
	}

	private static ItemStack getHeldItem(PlayerEntity player)
	{
		if ((player.getHeldItemMainhand().getItem() instanceof JarItem) || (player.getHeldItemOffhand().getItem() instanceof JarItem))
		{
			return player.getHeldItemMainhand();
		}
		return ItemStack.EMPTY;
	}
	
	public int getInventoryRows() 
	{
		return itemHandler.getSlots() / 9;
	}
	
	@Override
	public void onContainerClosed(PlayerEntity playerIn) {
		super.onContainerClosed(playerIn);
		((JarItem) this.is.getItem()).saveInventory(this.is, this.itemHandler);
	}
	

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        // This method handles shift-clicking to transfer items quickly. This can easily crash the game if not coded
        // correctly. The first slots (index 0 to whatever) are usually the inventory block/item, while player slots
        // start after those.
        Slot slot = this.getSlot(index);

        if (!slot.canTakeStack(playerIn)) {
            return slot.getStack();
        }

        if (index == blocked || !slot.getHasStack()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getStack();
        ItemStack newStack = stack.copy();

        int containerSlots = itemHandler.getSlots();
        if (index < containerSlots) {
            if (!this.mergeItemStack(stack, containerSlots, this.inventorySlots.size(), true)) {
                return ItemStack.EMPTY;
            }
            slot.onSlotChanged();
        } else if (!this.mergeItemStack(stack, 0, containerSlots, false)) {
            return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) {
            slot.putStack(ItemStack.EMPTY);
        } else {
            slot.onSlotChanged();
        }

        return slot.onTake(playerIn, newStack);
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        if (slotId < 0 || slotId > inventorySlots.size()) {
            return super.slotClick(slotId, dragType, clickTypeIn, player);
        }

        Slot slot = inventorySlots.get(slotId);
        if (!canTake(slotId, slot, dragType, player, clickTypeIn)) {
            return slot.getStack();
        }

        return super.slotClick(slotId, dragType, clickTypeIn, player);
    }

    private static boolean isJar(ItemStack is)
    {
    	return is.getItem() instanceof JarItem;
    }
    
    private boolean canTake(int slotId, Slot slot, int button, PlayerEntity player, ClickType clickType) {
        if (slotId == blocked || slotId <= itemHandler.getSlots() - 1 && isJar(player.inventory.getItemStack())) {
            return false;
        }

        // Hotbar swapping via number keys
        if (clickType == ClickType.SWAP) {
            int hotbarId = itemHandler.getSlots() + 27 + button;
            // Block swapping with container
            if (blocked == hotbarId) {
                return false;
            }

            Slot hotbarSlot = getSlot(hotbarId);
            if (slotId <= itemHandler.getSlots() - 1) {
                return !isJar(slot.getStack()) && !isJar(hotbarSlot.getStack());
            }
        }

        return true;
    }

}
