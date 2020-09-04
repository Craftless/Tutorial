package net.minecraft.util.registry;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Keyable;
import com.mojang.serialization.Lifecycle;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.schedule.Schedule;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.villager.IVillagerType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootEntryManager;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootPoolEntryType;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IObjectIntIterable;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;
import net.minecraft.world.gen.blockstateprovider.BlockStateProviderType;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSizeType;
import net.minecraft.world.gen.feature.jigsaw.IJigsawDeserializer;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.template.IPosRuleTests;
import net.minecraft.world.gen.feature.template.IRuleTestType;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Attention Modders: This SHOULD NOT be used, you should use ForgeRegistries instead. As it has a cleaner modder facing API.
 * We will be wrapping all of these in our API as necessary for syncing and management.
 */
public abstract class Registry<T> implements Codec<T>, Keyable, IObjectIntIterable<T> {
   protected static final Logger LOGGER = LogManager.getLogger();
   private static final Map<ResourceLocation, Supplier<?>> LOCATION_TO_SUPPLIER = Maps.newLinkedHashMap();
   public static final ResourceLocation ROOT = new ResourceLocation("root");
   protected static final MutableRegistry<MutableRegistry<?>> ROOT_REGISTRY = new SimpleRegistry<>(createKey("root"), Lifecycle.experimental());
   public static final Registry<? extends Registry<?>> REGISTRY = ROOT_REGISTRY;
   public static final RegistryKey<Registry<SoundEvent>> SOUND_EVENT_KEY = createKey("sound_event");
   public static final RegistryKey<Registry<Fluid>> FLUID_KEY = createKey("fluid");
   public static final RegistryKey<Registry<Effect>> MOB_EFFECT_KEY = createKey("mob_effect");
   public static final RegistryKey<Registry<Block>> BLOCK_KEY = createKey("block");
   public static final RegistryKey<Registry<Enchantment>> ENCHANTMENT_KEY = createKey("enchantment");
   public static final RegistryKey<Registry<EntityType<?>>> ENTITY_TYPE_KEY = createKey("entity_type");
   public static final RegistryKey<Registry<Item>> ITEM_KEY = createKey("item");
   public static final RegistryKey<Registry<Potion>> POTION_KEY = createKey("potion");
   public static final RegistryKey<Registry<WorldCarver<?>>> CARVER_KEY = createKey("carver");
   public static final RegistryKey<Registry<SurfaceBuilder<?>>> SURFACE_BUILDER_KEY = createKey("surface_builder");
   public static final RegistryKey<Registry<Feature<?>>> FEATURE_KEY = createKey("feature");
   public static final RegistryKey<Registry<Placement<?>>> DECORATOR_KEY = createKey("decorator");
   public static final RegistryKey<Registry<Biome>> BIOME_KEY = createKey("biome");
   public static final RegistryKey<Registry<BlockStateProviderType<?>>> BLOCK_STATE_PROVIDER_TYPE_KEY = createKey("block_state_provider_type");
   public static final RegistryKey<Registry<BlockPlacerType<?>>> BLOCK_PLACER_TYPE_KEY = createKey("block_placer_type");
   public static final RegistryKey<Registry<FoliagePlacerType<?>>> FOLIAGE_PLACER_TYPE_KEY = createKey("foliage_placer_type");
   public static final RegistryKey<Registry<TrunkPlacerType<?>>> TRUNK_PLACER_TYPE_KEY = createKey("trunk_placer_type");
   public static final RegistryKey<Registry<TreeDecoratorType<?>>> TREE_DECORATOR_TYPE_KEY = createKey("tree_decorator_type");
   public static final RegistryKey<Registry<FeatureSizeType<?>>> FEATURE_SIZE_TYPE_KEY = createKey("feature_size_type");
   public static final RegistryKey<Registry<ParticleType<?>>> PARTICLE_TYPE_KEY = createKey("particle_type");
   public static final RegistryKey<Registry<Codec<? extends BiomeProvider>>> BIOME_SOURCE_KEY = createKey("biome_source");
   public static final RegistryKey<Registry<Codec<? extends ChunkGenerator>>> CHUNK_GENERATOR_KEY = createKey("chunk_generator");
   public static final RegistryKey<Registry<TileEntityType<?>>> BLOCK_ENTITY_TYPE_KEY = createKey("block_entity_type");
   public static final RegistryKey<Registry<PaintingType>> MOTIVE_KEY = createKey("motive");
   public static final RegistryKey<Registry<ResourceLocation>> CUSTOM_STAT_KEY = createKey("custom_stat");
   public static final RegistryKey<Registry<ChunkStatus>> CHUNK_STATUS_KEY = createKey("chunk_status");
   public static final RegistryKey<Registry<Structure<?>>> STRUCTURE_FEATURE_KEY = createKey("structure_feature");
   public static final RegistryKey<Registry<IStructurePieceType>> STRUCTURE_PIECE_KEY = createKey("structure_piece");
   public static final RegistryKey<Registry<IRuleTestType<?>>> RULE_TEST_KEY = createKey("rule_test");
   public static final RegistryKey<Registry<IPosRuleTests<?>>> POS_RULE_TEST_KEY = createKey("pos_rule_test");
   public static final RegistryKey<Registry<IStructureProcessorType<?>>> STRUCTURE_PROCESSOR_KEY = createKey("structure_processor");
   public static final RegistryKey<Registry<IJigsawDeserializer<?>>> STRUCTURE_POOL_ELEMENT_KEY = createKey("structure_pool_element");
   public static final RegistryKey<Registry<ContainerType<?>>> MENU_KEY = createKey("menu");
   public static final RegistryKey<Registry<IRecipeType<?>>> RECIPE_TYPE_KEY = createKey("recipe_type");
   public static final RegistryKey<Registry<IRecipeSerializer<?>>> RECIPE_SERIALIZER_KEY = createKey("recipe_serializer");
   public static final RegistryKey<Registry<Attribute>> ATTRIBUTE_KEY = createKey("attribute");
   public static final RegistryKey<Registry<StatType<?>>> STAT_TYPE_KEY = createKey("stat_type");
   public static final RegistryKey<Registry<IVillagerType>> VILLAGER_TYPE_KEY = createKey("villager_type");
   public static final RegistryKey<Registry<VillagerProfession>> VILLAGER_PROFESSION_KEY = createKey("villager_profession");
   public static final RegistryKey<Registry<PointOfInterestType>> POINT_OF_INTEREST_TYPE_KEY = createKey("point_of_interest_type");
   public static final RegistryKey<Registry<MemoryModuleType<?>>> MEMORY_MODULE_TYPE_KEY = createKey("memory_module_type");
   public static final RegistryKey<Registry<SensorType<?>>> SENSOR_TYPE_KEY = createKey("sensor_type");
   public static final RegistryKey<Registry<Schedule>> SCHEDULE_KEY = createKey("schedule");
   public static final RegistryKey<Registry<Activity>> ACTIVITY_KEY = createKey("activity");
   public static final RegistryKey<Registry<LootPoolEntryType>> LOOT_POOL_ENTRY_TYPE_KEY = createKey("loot_pool_entry_type");
   public static final RegistryKey<Registry<LootFunctionType>> LOOT_FUNCTION_TYPE_KEY = createKey("loot_function_type");
   public static final RegistryKey<Registry<LootConditionType>> LOOT_CONDITION_TYPE_KEY = createKey("loot_condition_type");
   public static final RegistryKey<Registry<DimensionType>> DIMENSION_TYPE_KEY = createKey("dimension_type");
   public static final RegistryKey<Registry<World>> WORLD_KEY = createKey("dimension");
   public static final RegistryKey<Registry<Dimension>> DIMENSION_KEY = createKey("dimension");
   @Deprecated public static final Registry<SoundEvent> SOUND_EVENT = forge(SOUND_EVENT_KEY, SoundEvent.class, () -> {
      return SoundEvents.ENTITY_ITEM_PICKUP;
   });
   @Deprecated public static final DefaultedRegistry<Fluid> FLUID = forgeDefaulted(FLUID_KEY, Fluid.class, () -> {
      return Fluids.EMPTY;
   });
   @Deprecated public static final Registry<Effect> EFFECTS = forge(MOB_EFFECT_KEY, Effect.class, () -> {
      return Effects.LUCK;
   });
   @Deprecated public static final DefaultedRegistry<Block> BLOCK = forgeDefaulted(BLOCK_KEY, Block.class, () -> {
      return Blocks.AIR;
   });
   @Deprecated public static final Registry<Enchantment> ENCHANTMENT = forge(ENCHANTMENT_KEY, Enchantment.class, () -> {
      return Enchantments.FORTUNE;
   });
   @Deprecated public static final DefaultedRegistry<EntityType<?>> ENTITY_TYPE = forgeDefaulted(ENTITY_TYPE_KEY, EntityType.class, () -> {
      return EntityType.PIG;
   });
   @Deprecated public static final DefaultedRegistry<Item> ITEM = forgeDefaulted(ITEM_KEY, Item.class, () -> {
      return Items.AIR;
   });
   @Deprecated public static final DefaultedRegistry<Potion> POTION = forgeDefaulted(POTION_KEY, Potion.class, () -> {
      return Potions.EMPTY;
   });
   @Deprecated public static final Registry<WorldCarver<?>> CARVER = forge(CARVER_KEY, WorldCarver.class, () -> {
      return WorldCarver.CAVE;
   });
   @Deprecated public static final Registry<SurfaceBuilder<?>> SURFACE_BUILDER = forge(SURFACE_BUILDER_KEY, SurfaceBuilder.class, () -> {
      return SurfaceBuilder.DEFAULT;
   });
   @Deprecated public static final Registry<Feature<?>> FEATURE = forge(FEATURE_KEY, Feature.class, () -> {
      return Feature.ORE;
   });
   @Deprecated public static final Registry<Placement<?>> DECORATOR = forge(DECORATOR_KEY, Placement.class, () -> {
      return Placement.NOPE;
   });
   @Deprecated public static final Registry<Biome> BIOME = forge(BIOME_KEY, Biome.class, () -> {
      return Biomes.DEFAULT;
   });
   @Deprecated public static final Registry<BlockStateProviderType<?>> BLOCK_STATE_PROVIDER_TYPE = forge(BLOCK_STATE_PROVIDER_TYPE_KEY, BlockStateProviderType.class, () -> {
      return BlockStateProviderType.SIMPLE_STATE_PROVIDER;
   });
   @Deprecated public static final Registry<BlockPlacerType<?>> BLOCK_PLACER_TYPE = forge(BLOCK_PLACER_TYPE_KEY, BlockPlacerType.class, () -> {
      return BlockPlacerType.SIMPLE_BLOCK;
   });
   @Deprecated public static final Registry<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPE = forge(FOLIAGE_PLACER_TYPE_KEY, FoliagePlacerType.class, () -> {
      return FoliagePlacerType.BLOB;
   });
   public static final Registry<TrunkPlacerType<?>> TRUNK_REPLACER = func_239746_a_(TRUNK_PLACER_TYPE_KEY, () -> {
      return TrunkPlacerType.STRAIGHT_TRUNK_PLACER;
   });
   @Deprecated public static final Registry<TreeDecoratorType<?>> TREE_DECORATOR_TYPE = forge(TREE_DECORATOR_TYPE_KEY, TreeDecoratorType.class, () -> {
      return TreeDecoratorType.LEAVE_VINE;
   });
   public static final Registry<FeatureSizeType<?>> FEATURE_SIZE_TYPE = func_239746_a_(FEATURE_SIZE_TYPE_KEY, () -> {
      return FeatureSizeType.field_236711_a_;
   });
   @Deprecated public static final Registry<ParticleType<?>> PARTICLE_TYPE = forge(PARTICLE_TYPE_KEY, ParticleType.class, () -> {
      return ParticleTypes.BLOCK;
   });
   public static final Registry<Codec<? extends BiomeProvider>> BIOME_PROVIDER_CODEC = func_239742_a_(BIOME_SOURCE_KEY, Lifecycle.stable(), () -> {
      return BiomeProvider.PROVIDER_CODEC;
   });
   public static final Registry<Codec<? extends ChunkGenerator>> CHUNK_GENERATOR_CODEC = func_239742_a_(CHUNK_GENERATOR_KEY, Lifecycle.stable(), () -> {
      return ChunkGenerator.field_235948_a_;
   });
   @Deprecated public static final Registry<TileEntityType<?>> BLOCK_ENTITY_TYPE = forge(BLOCK_ENTITY_TYPE_KEY, TileEntityType.class, () -> {
      return TileEntityType.FURNACE;
   });
   @Deprecated public static final DefaultedRegistry<PaintingType> MOTIVE = forgeDefaulted(MOTIVE_KEY, PaintingType.class, () -> {
      return PaintingType.KEBAB;
   });
   public static final Registry<ResourceLocation> CUSTOM_STAT = func_239746_a_(CUSTOM_STAT_KEY, () -> {
      return Stats.JUMP;
   });
   @Deprecated public static final DefaultedRegistry<ChunkStatus> CHUNK_STATUS = forgeDefaulted(CHUNK_STATUS_KEY, ChunkStatus.class, () -> {
      return ChunkStatus.EMPTY;
   });
   @Deprecated public static final Registry<Structure<?>> STRUCTURE_FEATURE = forge(STRUCTURE_FEATURE_KEY, Structure.class, () -> {
      return Structure.field_236367_c_;
   });
   public static final Registry<IStructurePieceType> STRUCTURE_PIECE = func_239746_a_(STRUCTURE_PIECE_KEY, () -> {
      return IStructurePieceType.MSROOM;
   });
   public static final Registry<IRuleTestType<?>> RULE_TEST = func_239746_a_(RULE_TEST_KEY, () -> {
      return IRuleTestType.ALWAYS_TRUE;
   });
   public static final Registry<IPosRuleTests<?>> POS_RULE_TEST = func_239746_a_(POS_RULE_TEST_KEY, () -> {
      return IPosRuleTests.field_237103_a_;
   });
   public static final Registry<IStructureProcessorType<?>> STRUCTURE_PROCESSOR = func_239746_a_(STRUCTURE_PROCESSOR_KEY, () -> {
      return IStructureProcessorType.BLOCK_IGNORE;
   });
   public static final Registry<IJigsawDeserializer<?>> STRUCTURE_POOL_ELEMENT = func_239746_a_(STRUCTURE_POOL_ELEMENT_KEY, () -> {
      return IJigsawDeserializer.EMPTY_POOL_ELEMENT;
   });
   @Deprecated public static final Registry<ContainerType<?>> MENU = forge(MENU_KEY, ContainerType.class, () -> {
      return ContainerType.ANVIL;
   });
   public static final Registry<IRecipeType<?>> RECIPE_TYPE = func_239746_a_(RECIPE_TYPE_KEY, () -> {
      return IRecipeType.CRAFTING;
   });
   @Deprecated public static final Registry<IRecipeSerializer<?>> RECIPE_SERIALIZER = forge(RECIPE_SERIALIZER_KEY, IRecipeSerializer.class, () -> {
      return IRecipeSerializer.CRAFTING_SHAPELESS;
   });
   @Deprecated public static final Registry<Attribute> ATTRIBUTE = forge(ATTRIBUTE_KEY, Attribute.class, () -> {
      return Attributes.LUCK;
   });
   @Deprecated public static final Registry<StatType<?>> STATS = forge(STAT_TYPE_KEY, StatType.class, () -> {
      return Stats.ITEM_USED;
   });
   public static final DefaultedRegistry<IVillagerType> VILLAGER_TYPE = registerDefaulted(VILLAGER_TYPE_KEY, "plains", () -> {
      return IVillagerType.PLAINS;
   });
   @Deprecated public static final DefaultedRegistry<VillagerProfession> VILLAGER_PROFESSION = forgeDefaulted(VILLAGER_PROFESSION_KEY, VillagerProfession.class, () -> {
      return VillagerProfession.NONE;
   });
   @Deprecated public static final DefaultedRegistry<PointOfInterestType> POINT_OF_INTEREST_TYPE = forgeDefaulted(POINT_OF_INTEREST_TYPE_KEY, PointOfInterestType.class, () -> {
      return PointOfInterestType.UNEMPLOYED;
   });
   @Deprecated public static final DefaultedRegistry<MemoryModuleType<?>> MEMORY_MODULE_TYPE = forgeDefaulted(MEMORY_MODULE_TYPE_KEY, MemoryModuleType.class, () -> {
      return MemoryModuleType.DUMMY;
   });
   @Deprecated public static final DefaultedRegistry<SensorType<?>> SENSOR_TYPE = forgeDefaulted(SENSOR_TYPE_KEY, SensorType.class, () -> {
      return SensorType.DUMMY;
   });
   @Deprecated public static final Registry<Schedule> SCHEDULE = forge(SCHEDULE_KEY, Schedule.class, () -> {
      return Schedule.EMPTY;
   });
   @Deprecated public static final Registry<Activity> ACTIVITY = forge(ACTIVITY_KEY, Activity.class, () -> {
      return Activity.IDLE;
   });
   public static final Registry<LootPoolEntryType> LOOT_POOL_ENTRY_TYPE = func_239746_a_(LOOT_POOL_ENTRY_TYPE_KEY, () -> {
      return LootEntryManager.EMPTY;
   });
   public static final Registry<LootFunctionType> LOOT_FUNCTION_TYPE = func_239746_a_(LOOT_FUNCTION_TYPE_KEY, () -> {
      return LootFunctionManager.SET_COUNT;
   });
   public static final Registry<LootConditionType> LOOT_CONDITION_TYPE = func_239746_a_(LOOT_CONDITION_TYPE_KEY, () -> {
      return LootConditionManager.INVERTED;
   });
   private final RegistryKey<Registry<T>> registryKey;
   private final Lifecycle lifecycle;

   private static <T> RegistryKey<Registry<T>> createKey(String name) {
      return RegistryKey.func_240904_a_(new ResourceLocation(name));
   }

   private static <T extends MutableRegistry<?>> void validateMutableRegistry(MutableRegistry<T> registry) {
      registry.forEach((p_239739_1_) -> {
         if (p_239739_1_.keySet().isEmpty()) {
            LOGGER.error("Registry '{}' was empty after loading", (Object)registry.getKey(p_239739_1_));
            if (SharedConstants.developmentMode) {
               throw new IllegalStateException("Registry: '" + registry.getKey(p_239739_1_) + "' is empty, not allowed, fix me!");
            }
         }

         if (p_239739_1_ instanceof DefaultedRegistry) {
            ResourceLocation resourcelocation = ((DefaultedRegistry)p_239739_1_).getDefaultKey();
            Validate.notNull(p_239739_1_.getOrDefault(resourcelocation), "Missing default of DefaultedMappedRegistry: " + resourcelocation);
         }

      });
   }

   private static <T> Registry<T> func_239746_a_(RegistryKey<Registry<T>> registryKey, Supplier<T> supplier) {
      return func_239742_a_(registryKey, Lifecycle.experimental(), supplier);
   }

   private static <T> DefaultedRegistry<T> registerDefaulted(RegistryKey<Registry<T>> registryKey, String defaultedValueKey, Supplier<T> supplier) {
      return registerDefaulted(registryKey, defaultedValueKey, Lifecycle.experimental(), supplier);
   }

   private static <T> Registry<T> func_239742_a_(RegistryKey<Registry<T>> registryKey, Lifecycle lifecycle, Supplier<T> supplier) {
      return func_239743_a_(registryKey, new SimpleRegistry<>(registryKey, lifecycle), supplier);
   }

   private static <T> DefaultedRegistry<T> registerDefaulted(RegistryKey<Registry<T>> registryKey, String defaultedValueKey, Lifecycle lifecycle, Supplier<T> supplier) {
      return func_239743_a_(registryKey, new DefaultedRegistry<>(defaultedValueKey, registryKey, lifecycle), supplier);
   }

   private static <T, R extends MutableRegistry<T>> R func_239743_a_(RegistryKey<Registry<T>> registryKey, R instance, Supplier<T> objectSupplier) {
      ResourceLocation resourcelocation = registryKey.func_240901_a_();
      LOCATION_TO_SUPPLIER.put(resourcelocation, objectSupplier);
      return (R)((MutableRegistry)ROOT_REGISTRY).register((RegistryKey)registryKey, (Object)instance);
   }

   protected Registry(RegistryKey<Registry<T>> registryKey, Lifecycle lifecycle) {
      this.registryKey = registryKey;
      this.lifecycle = lifecycle;
   }

   public String toString() {
      return "Registry[" + this.registryKey + " (" + this.lifecycle + ")]";
   }

   public <U> DataResult<Pair<T, U>> decode(DynamicOps<U> p_decode_1_, U p_decode_2_) {
      return p_decode_1_.compressMaps() ? p_decode_1_.getNumberValue(p_decode_2_).flatMap((p_239740_1_) -> {
         int i = p_239740_1_.intValue();
         if (!this.hasRegistryID(i)) {
            return DataResult.error("Unknown registry id: " + p_239740_1_);
         } else {
            T t = this.getByValue(i);
            return DataResult.success(t, this.lifecycle);
         }
      }).map((p_239736_1_) -> {
         return Pair.of(p_239736_1_, p_decode_1_.empty());
      }) : ResourceLocation.RESOURCE_LOCATION_CODEC.decode(p_decode_1_, p_decode_2_).addLifecycle(this.lifecycle).flatMap((p_239735_1_) -> {
         return !this.containsKey(p_239735_1_.getFirst()) ? DataResult.error("Unknown registry key: " + p_239735_1_.getFirst()) : DataResult.success(p_239735_1_.mapFirst(this::getOrDefault), this.lifecycle);
      });
   }

   public <U> DataResult<U> encode(T p_encode_1_, DynamicOps<U> p_encode_2_, U p_encode_3_) {
      ResourceLocation resourcelocation = this.getKey(p_encode_1_);
      if (resourcelocation == null) {
         return DataResult.error("Unknown registry element " + p_encode_1_);
      } else {
         return p_encode_2_.compressMaps() ? p_encode_2_.mergeToPrimitive(p_encode_3_, p_encode_2_.createInt(this.getId(p_encode_1_))).setLifecycle(this.lifecycle) : p_encode_2_.mergeToPrimitive(p_encode_3_, p_encode_2_.createString(resourcelocation.toString())).setLifecycle(this.lifecycle);
      }
   }

   public <U> Stream<U> keys(DynamicOps<U> p_keys_1_) {
      return this.keySet().stream().map((p_239737_1_) -> {
         return p_keys_1_.createString(p_239737_1_.toString());
      });
   }

   /**
    * Gets the name we use to identify the given object.
    */
   @Nullable
   public abstract ResourceLocation getKey(T value);

   public abstract Optional<RegistryKey<T>> func_230519_c_(T value);

   /**
    * Gets the integer ID we use to identify the given object.
    */
   public abstract int getId(@Nullable T value);

   @Nullable
   public abstract T getValueForKey(@Nullable RegistryKey<T> key);

   @Nullable
   public abstract T getOrDefault(@Nullable ResourceLocation name);

   /**
    * Gets the value assosiated by the key. Returns an optional and never throw exceptions.
    */
   public abstract Optional<T> getValue(@Nullable ResourceLocation key);

   /**
    * Gets all the keys recognized by this registry.
    */
   public abstract Set<ResourceLocation> keySet();

   public Stream<T> stream() {
      return StreamSupport.stream(this.spliterator(), false);
   }

   public abstract boolean containsKey(ResourceLocation name);

   public abstract boolean func_239660_c_(RegistryKey<T> p_239660_1_);

   public abstract boolean hasRegistryID(int id);

   public static <T> T register(Registry<? super T> registry, String identifier, T value) {
      return register(registry, new ResourceLocation(identifier), value);
   }

   public static <V, T extends V> T register(Registry<V> registry, ResourceLocation identifier, T value) {
      return ((MutableRegistry<V>)registry).register(RegistryKey.func_240903_a_(registry.registryKey, identifier), value);
   }

   public static <V, T extends V> T register(Registry<V> registry, int id, String identifier, T value) {
      return ((MutableRegistry<V>)registry).register(id, RegistryKey.func_240903_a_(registry.registryKey, new ResourceLocation(identifier)), value);
   }

   private static <T extends net.minecraftforge.registries.IForgeRegistryEntry<T>> Registry<T> forge(RegistryKey<Registry<T>> key, Class<? super T> cls, Supplier<T> def) {
      return func_239743_a_(key, net.minecraftforge.registries.GameData.<T>getWrapper(cls), def);
   }

   private static <T extends net.minecraftforge.registries.IForgeRegistryEntry<T>> DefaultedRegistry<T> forgeDefaulted(RegistryKey<Registry<T>> key, Class<? super T> cls, Supplier<T> def) {
      return Registry.func_239743_a_(key, net.minecraftforge.registries.GameData.<T>getWrapperDefaulted(cls), def);
   }

   public final RegistryKey<Registry<T>> getRegistryKey() {
      return registryKey;
   }

   static {
      LOCATION_TO_SUPPLIER.forEach((registry, registrySupplier) -> {
         if (registrySupplier.get() == null) {
            LOGGER.error("Unable to bootstrap registry '{}'", (Object)registry);
         }

      });
      validateMutableRegistry(ROOT_REGISTRY);
   }
}