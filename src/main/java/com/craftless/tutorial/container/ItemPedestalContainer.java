package com.craftless.tutorial.container;

import java.util.Objects;

import com.craftless.tutorial.init.ModContainerTypes;
import com.craftless.tutorial.tileentities.ItemPedestalTileEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

public class ItemPedestalContainer extends Container
{

	public final ItemPedestalTileEntity tileEntity;
	private final IWorldPosCallable canInteractWithCallable;
	
	public ItemPedestalContainer(final int windowId, final PlayerInventory playerInv, final ItemPedestalTileEntity tileEntityIn) 
	{
		super(ModContainerTypes.ITEM_PEDESTAL.get(), windowId);
		this.tileEntity = tileEntityIn;
		this.canInteractWithCallable = IWorldPosCallable.of(tileEntityIn.getWorld(), tileEntityIn.getPos());
		
		this.addSlot(new Slot(tileEntityIn, 0, 81, 36));
		
		//Main Inventory
		int startX = 8;
		int startY = 84;
		int slotSizePlus2 = 18;
		for (int row = 0; row < 3; row++)
		{
			for (int column = 0; column < 9; column++)
			{
				this.addSlot(new Slot(playerInv, 0 + (row * 9) + column, startX + (column * slotSizePlus2), startY + (row * slotSizePlus2)));
			}
		}
		
		//Hotbar
		int hotbarY = 142;
		for (int column = 0; column < 9; column++)
		{
			this.addSlot(new Slot(playerInv, column, startX + (column * slotSizePlus2), hotbarY));
		}
	}
	
	public ItemPedestalContainer(final int windowId, final PlayerInventory playerInv, final PacketBuffer data)
	{
		this(windowId, playerInv, getTileEntity(playerInv, data));
	}
	
	private static ItemPedestalTileEntity getTileEntity(final PlayerInventory playerInv, final PacketBuffer data)
	{
		Objects.requireNonNull(playerInv, "playerInv cannot be null");
		Objects.requireNonNull(data, "data cannot be null");
		final TileEntity tileAtPos = playerInv.player.world.getTileEntity(data.readBlockPos());
		if (tileAtPos instanceof ItemPedestalTileEntity)
		{
			return (ItemPedestalTileEntity)tileAtPos;
		}
		
		throw new IllegalStateException("TileEntity is not correct " + tileAtPos);
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack is = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);
		if (slot != null & slot.getHasStack())
		{
			ItemStack is1 = slot.getStack();
			is = is1.copy();
			if (index < 1)
			{
				if (!this.mergeItemStack(is1, 1, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(is1, 0, 1, false))
			{
				return ItemStack.EMPTY;
			}
			if (is1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}
		}
		return is;
	}

}
