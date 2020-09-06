package com.craftless.tutorial.tileentities;

import javax.annotation.Nonnull;

import com.craftless.tutorial.blocks.RubyChestBlock;
import com.craftless.tutorial.container.RubyChestContainer;
import com.craftless.tutorial.init.ModTileEntityTypes;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

public class RubyChestTileEntity extends LockableLootTileEntity
{

	private NonNullList<ItemStack> chestContents = NonNullList.withSize(36, ItemStack.EMPTY);
	protected int numPlayersUsing;
	private IItemHandlerModifiable items = createHandler();
	private LazyOptional<IItemHandlerModifiable> itemHandler = LazyOptional.of(() -> items);
	
	public RubyChestTileEntity(TileEntityType<?> typeIn) {
		super(typeIn);
		// TODO Auto-generated constructor stub
	}
	

	public RubyChestTileEntity()
	{
		this(ModTileEntityTypes.RUBY_CHEST.get());
	}

	@Override
	public int getSizeInventory() {
		return 36;
	}

	@Override
	public NonNullList<ItemStack> getItems() {
		return this.chestContents;
	}

	@Override
	public void setItems(NonNullList<ItemStack> itemsIn) {
		this.chestContents = itemsIn;
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container.ruby_chest");
	}

	@Override
	protected RubyChestContainer createMenu(int id, PlayerInventory player) 
	{
		return new RubyChestContainer(id, player, this);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		if (!this.checkLootAndWrite(compound))
		{
			ItemStackHelper.saveAllItems(compound, this.chestContents);
		}
		return compound;
	}
	
	@Override
	public void read(BlockState state, CompoundNBT compound) {
		super.read(state, compound);
		this.chestContents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		if (!this.checkLootAndRead(compound))
		{
			ItemStackHelper.loadAllItems(compound, this.chestContents);
		}
	}
		
	private void playSound(SoundEvent sound)
	{
		double dx = (double) this.pos.getX() + 0.5d;
		double dy = (double) this.pos.getY() + 0.5d;
		double dz = (double) this.pos.getZ() + 0.5d;
		this.world. playSound((PlayerEntity) null, dx, dy, dz, sound, SoundCategory.BLOCKS, 0.5f, this.world.rand.nextFloat() * 0.1f + 0.9f);
		
		
	}
	
	@Override
	public boolean receiveClientEvent(int id, int type) {
		if (id == 1)
		{
			this.numPlayersUsing = type;
			return true;
			
		}
		else
		{
			return super.receiveClientEvent(id, type);
		}
	}
	
	@Override
	public void openInventory(PlayerEntity player) {
		if (!player.isSpectator())
		{
			if (this.numPlayersUsing < 0)
			{
				this.numPlayersUsing = 0;
			}
			
			++numPlayersUsing;
			this.onOpenOrClose();
		}
	}
	
	@Override
	public void closeInventory(PlayerEntity player) {
		if (!player.isSpectator())
		{
			--this.numPlayersUsing;
			this.onOpenOrClose();
		}
	}
	
	protected void onOpenOrClose()
	{
		Block block = this.getBlockState().getBlock();
		if (block instanceof RubyChestBlock)
		{
			this.world.addBlockEvent(pos, block, 1, this.numPlayersUsing);
			this.world.notifyNeighborsOfStateChange(this.pos, block);
		}
	}

	
	public static int getPlayersUsing(IBlockReader reader, BlockPos pos)
	{
		BlockState blockstate = reader.getBlockState(pos);
		TileEntity te = reader.getTileEntity(pos);
		if (te instanceof RubyChestTileEntity)
		{
			return ((RubyChestTileEntity)te).numPlayersUsing;
		}
		return 0;
	}
	
	public static void swapContents(RubyChestTileEntity te, RubyChestTileEntity otherTe)
	{
		NonNullList<ItemStack> list = te.getItems();
		te.setItems(otherTe.getItems());
		otherTe.setItems(list);
	}
	
	@Override
	public void updateContainingBlockInfo() {
		super.updateContainingBlockInfo();
		if (this.itemHandler != null)
		{
			this.itemHandler.invalidate();
			this.itemHandler = null;
		}
	};
	
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nonnull Direction side) 
	{
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return itemHandler.cast();
		}
		return super.getCapability(cap, side);
	}
	
	private IItemHandlerModifiable createHandler()
	{
		return new InvWrapper(this);
	}
	
	
	@Override
	public void remove() {
		super.remove();
		if (itemHandler != null)
		{
			this.itemHandler.invalidate();
			this.itemHandler = null;
		}
	}



}
