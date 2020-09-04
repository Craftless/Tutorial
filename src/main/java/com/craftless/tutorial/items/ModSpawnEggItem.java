package com.craftless.tutorial.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class ModSpawnEggItem extends SpawnEggItem
{
	protected static final List<ModSpawnEggItem> UNADDED_EGGS = new ArrayList<>();
	private final Lazy<? extends EntityType<?>> entityTypeSupplier;
	
	public ModSpawnEggItem(final RegistryObject<? extends EntityType<?>> entityTypeSupplier, int p_i48465_2_, int p_i48465_3_, Properties p_i48465_4_) {
		super(null, p_i48465_2_, p_i48465_3_, p_i48465_4_);
		this.entityTypeSupplier = Lazy.of(entityTypeSupplier::get);
		UNADDED_EGGS.add(this);
	}

	public static void initSpawnEggs() 
	{
		final Map<EntityType<?>, SpawnEggItem> EGGS = ObfuscationReflectionHelper.getPrivateValue(SpawnEggItem.class, null, "field_195987_b");
		DefaultDispenseItemBehavior dispenseBehavior = new DefaultDispenseItemBehavior()
		{

			@Override
			protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
				Direction direction = source.getBlockState().get(DispenserBlock.FACING);
				EntityType<?> type = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
				type.spawn(source.getWorld(), stack, null, source.getBlockPos(), SpawnReason.DISPENSER, direction != Direction.UP, false);
				stack.shrink(1);
				return stack;
			}
			
		};
		
		for (final SpawnEggItem spawnEgg : UNADDED_EGGS)
		{
			EGGS.put(spawnEgg.getType(null), spawnEgg);
			DispenserBlock.registerDispenseBehavior(spawnEgg, dispenseBehavior);
		}
		UNADDED_EGGS.clear();
	}
	@Override
	public EntityType<?> getType(CompoundNBT nbt) 
	{
		return this.entityTypeSupplier.get();
	}
	
	

}
