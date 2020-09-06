package com.craftless.tutorial.container;

import java.util.Objects;

import com.craftless.tutorial.init.ModBlocks;
import com.craftless.tutorial.init.ModContainerTypes;
import com.craftless.tutorial.tileentities.RubyChestTileEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

public class RubyChestContainer extends Container
{

	public final RubyChestTileEntity tileEntity;
	private final IWorldPosCallable canInteractWithCallable;

	
	public RubyChestContainer(final int windowId, final PlayerInventory playerInventory, final RubyChestTileEntity tileEntity)
	{
		super(ModContainerTypes.RUBY_CHEST.get(), windowId);
		this.tileEntity = tileEntity;
		this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());
		
		

		//Main Inventory
		int rows = 4;
		int columns = 9;
		int startX = 8;
		int startY = 18;
		int slotSizePlus2 = 18;

		for (int row = 0; row < rows; ++row)
		{
			for (int column = 0; column < columns; ++column)
			{
				this.addSlot(new Slot(tileEntity, (row * 9) + column, startX + (column * slotSizePlus2), startY + (row * slotSizePlus2)));
			}
		}
		
		//Main Player Inventory
		int startPlayerInvY = startY * 5 + 12;
		
		for (int row = 0; row < 3; ++row)
		{
			for (int column = 0; column < columns; ++column)
			{
				this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, startX + (column * slotSizePlus2), startPlayerInvY + (row * slotSizePlus2)));
			}
		}
		
		//Hotbar
		int hotbarY = startPlayerInvY + (startPlayerInvY / 2) + 7;
		
		for (int column = 0; column < columns; ++column)
		{
			this.addSlot(new Slot(playerInventory, column, startX + (column * slotSizePlus2), hotbarY));
		}
		
	}

	private static RubyChestTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data)
	{
		Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
		Objects.requireNonNull(data, "data cannot be null");
		final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());
		if (tileAtPos instanceof RubyChestTileEntity)
		{
			return (RubyChestTileEntity) tileAtPos;
		}
		throw new IllegalStateException("Tile entity is not correct" + tileAtPos);
	}
	
	public RubyChestContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data)
	{
		this(windowId, playerInventory, getTileEntity(playerInventory, data));
	}
	
	
	@Override
	public boolean canInteractWith(PlayerEntity playerIn) 
	{
		return isWithinUsableDistance(canInteractWithCallable, playerIn, ModBlocks.RUBY_CHEST.get());
	}
	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack is = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack())
		{
			ItemStack is1 = slot.getStack();
			is = is1.copy();
			if (index < 36)
			{
				if (!this.mergeItemStack(is1, 36, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(is1,  0,  36,  false))
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
