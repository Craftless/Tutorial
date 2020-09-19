package com.craftless.tutorial.items;

import com.craftless.tutorial.Tutorial;
import com.craftless.tutorial.container.JarContainer;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class JarItem extends Item
{

	private static final String NBT_COLOR = "JarColor";
	
	public JarItem() {
		super(new Item.Properties().group(Tutorial.TAB).maxStackSize(1));
	}
	
	public int getInventorySize(ItemStack is)
	{
		return 27;
	}
	
	public IItemHandler getInventory(ItemStack is)
	{
		ItemStackHandler isHandler = new ItemStackHandler(getInventorySize(is));
		isHandler.deserializeNBT(is.getOrCreateTag().getCompound("Inventory"));
		return isHandler;
	}
	
	public void saveInventory(ItemStack is, IItemHandler itemHandler)
	{
		if (itemHandler instanceof ItemStackHandler)
		{
			is.getOrCreateTag().put("Inventory", ((ItemStackHandler) itemHandler).serializeNBT());
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if (!worldIn.isRemote)
		{
			playerIn.openContainer(new SimpleNamedContainerProvider((id, playerInventory, player) -> {
				return new JarContainer(id, playerInventory, null);
			}, new TranslationTextComponent("container.tutorial.jar")));
		}
		return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
	}

	public static int getJarColor(ItemStack is)
	{
		return is.getOrCreateTag().getInt(NBT_COLOR);
	}
	
	public static void setJarColor(ItemStack is, int color)
	{
		is.getOrCreateTag().putInt(NBT_COLOR, color);
	}
	
	public static int getItemColor(ItemStack is, int tintIndex)
	{
		if (tintIndex == 0)
		{
			return getJarColor(is);
		}
		return 0xFFFFFF;
	}
	
	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		if (isInGroup(group))
		{
			for (DyeColor color : DyeColor.values())
			{
				ItemStack is = new ItemStack(this);
				setJarColor(is, color.getFireworkColor());
				items.add(is);
			}
		}
		super.fillItemGroup(group, items);
	}
	
}
