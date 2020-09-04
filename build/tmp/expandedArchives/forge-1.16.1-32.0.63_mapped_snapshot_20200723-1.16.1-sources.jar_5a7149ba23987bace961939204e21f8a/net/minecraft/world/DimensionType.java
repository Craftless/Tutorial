package net.minecraft.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.io.File;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.server.IDynamicRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.codec.RangeCodec;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKeyCodec;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.biome.ColumnFuzzedBiomeMagnifier;
import net.minecraft.world.biome.FuzzedBiomeMagnifier;
import net.minecraft.world.biome.IBiomeMagnifier;
import net.minecraft.world.biome.provider.EndBiomeProvider;
import net.minecraft.world.biome.provider.NetherBiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DimensionType {
   public static final MapCodec<DimensionType> DIMENSION_TYPE_CODEC = RecordCodecBuilder.mapCodec((p_236026_0_) -> {
      return p_236026_0_.group(Codec.LONG.optionalFieldOf("fixed_time").xmap((p_236028_0_) -> {
         return p_236028_0_.map(OptionalLong::of).orElseGet(OptionalLong::empty);
      }, (p_236029_0_) -> {
         return p_236029_0_.isPresent() ? Optional.of(p_236029_0_.getAsLong()) : Optional.empty();
      }).forGetter((p_236044_0_) -> {
         return p_236044_0_.fixedTime;
      }), Codec.BOOL.fieldOf("has_skylight").forGetter(DimensionType::hasSkyLight), Codec.BOOL.fieldOf("has_ceiling").forGetter(DimensionType::func_236037_d_), Codec.BOOL.fieldOf("ultrawarm").forGetter(DimensionType::func_236040_e_), Codec.BOOL.fieldOf("natural").forGetter(DimensionType::func_236043_f_), Codec.BOOL.fieldOf("shrunk").forGetter(DimensionType::func_236045_g_), Codec.BOOL.fieldOf("piglin_safe").forGetter(DimensionType::func_241509_i_), Codec.BOOL.fieldOf("bed_works").forGetter(DimensionType::func_241510_j_), Codec.BOOL.fieldOf("respawn_anchor_works").forGetter(DimensionType::func_241511_k_), Codec.BOOL.fieldOf("has_raids").forGetter(DimensionType::func_241512_l_), RangeCodec.func_232989_a_(0, 256).fieldOf("logical_height").forGetter(DimensionType::func_241513_m_), ResourceLocation.RESOURCE_LOCATION_CODEC.fieldOf("infiniburn").forGetter((p_241508_0_) -> {
         return p_241508_0_.field_241504_y_;
      }), Codec.FLOAT.fieldOf("ambient_light").forGetter((p_236042_0_) -> {
         return p_236042_0_.field_236017_x_;
      })).apply(p_236026_0_, DimensionType::new);
   });
   public static final float[] MOON_PHASE_FACTORS = new float[]{1.0F, 0.75F, 0.5F, 0.25F, 0.0F, 0.25F, 0.5F, 0.75F};
   public static final RegistryKey<DimensionType> OVERWORLD = RegistryKey.func_240903_a_(Registry.DIMENSION_TYPE_KEY, new ResourceLocation("overworld"));
   public static final RegistryKey<DimensionType> THE_NETHER = RegistryKey.func_240903_a_(Registry.DIMENSION_TYPE_KEY, new ResourceLocation("the_nether"));
   public static final RegistryKey<DimensionType> THE_END = RegistryKey.func_240903_a_(Registry.DIMENSION_TYPE_KEY, new ResourceLocation("the_end"));
   protected static final DimensionType OVERWORLD_TYPE = new DimensionType(OptionalLong.empty(), true, false, false, true, false, false, false, true, false, true, 256, ColumnFuzzedBiomeMagnifier.INSTANCE, BlockTags.INFINIBURN_OVERWORLD.getName(), 0.0F);
   protected static final DimensionType NETHER_TYPE = new DimensionType(OptionalLong.of(18000L), false, true, true, false, true, false, true, false, true, false, 128, FuzzedBiomeMagnifier.INSTANCE, BlockTags.INFINIBURN_NETHER.getName(), 0.1F);
   protected static final DimensionType END_TYPE = new DimensionType(OptionalLong.of(6000L), false, false, false, false, false, true, false, false, false, true, 256, FuzzedBiomeMagnifier.INSTANCE, BlockTags.INFINIBURN_END.getName(), 0.0F);
   public static final RegistryKey<DimensionType> field_241497_i_ = RegistryKey.func_240903_a_(Registry.DIMENSION_TYPE_KEY, new ResourceLocation("overworld_caves"));
   protected static final DimensionType field_241498_j_ = new DimensionType(OptionalLong.empty(), true, true, false, true, false, false, false, true, false, true, 256, ColumnFuzzedBiomeMagnifier.INSTANCE, BlockTags.INFINIBURN_OVERWORLD.getName(), 0.0F);
   public static final Codec<Supplier<DimensionType>> field_236002_f_ = RegistryKeyCodec.func_241794_a_(Registry.DIMENSION_TYPE_KEY, DIMENSION_TYPE_CODEC);
   private final OptionalLong fixedTime;
   private final boolean hasSkyLight;
   private final boolean hasCeiling;
   private final boolean field_236012_r_;
   private final boolean field_236013_s_;
   private final boolean field_236014_t_;
   private final boolean field_236015_u_;
   private final boolean field_241499_s_;
   private final boolean field_241500_t_;
   private final boolean field_241501_u_;
   private final boolean field_241502_v_;
   private final int field_241503_w_;
   private final IBiomeMagnifier magnifier;
   private final ResourceLocation field_241504_y_;
   private final float field_236017_x_;
   private final transient float[] field_236018_y_;

   public static DimensionType func_236019_a_() {
      return OVERWORLD_TYPE;
   }

   @OnlyIn(Dist.CLIENT)
   public static DimensionType func_241507_b_() {
      return field_241498_j_;
   }

   protected DimensionType(OptionalLong p_i241242_1_, boolean p_i241242_2_, boolean p_i241242_3_, boolean p_i241242_4_, boolean p_i241242_5_, boolean p_i241242_6_, boolean p_i241242_7_, boolean p_i241242_8_, boolean p_i241242_9_, boolean p_i241242_10_, int p_i241242_11_, ResourceLocation p_i241242_12_, float p_i241242_13_) {
      this(p_i241242_1_, p_i241242_2_, p_i241242_3_, p_i241242_4_, p_i241242_5_, p_i241242_6_, false, p_i241242_7_, p_i241242_8_, p_i241242_9_, p_i241242_10_, p_i241242_11_, FuzzedBiomeMagnifier.INSTANCE, p_i241242_12_, p_i241242_13_);
   }

   protected DimensionType(OptionalLong p_i241243_1_, boolean p_i241243_2_, boolean p_i241243_3_, boolean p_i241243_4_, boolean p_i241243_5_, boolean p_i241243_6_, boolean p_i241243_7_, boolean p_i241243_8_, boolean p_i241243_9_, boolean p_i241243_10_, boolean p_i241243_11_, int p_i241243_12_, IBiomeMagnifier p_i241243_13_, ResourceLocation p_i241243_14_, float p_i241243_15_) {
      this.fixedTime = p_i241243_1_;
      this.hasSkyLight = p_i241243_2_;
      this.hasCeiling = p_i241243_3_;
      this.field_236012_r_ = p_i241243_4_;
      this.field_236013_s_ = p_i241243_5_;
      this.field_236014_t_ = p_i241243_6_;
      this.field_236015_u_ = p_i241243_7_;
      this.field_241499_s_ = p_i241243_8_;
      this.field_241500_t_ = p_i241243_9_;
      this.field_241501_u_ = p_i241243_10_;
      this.field_241502_v_ = p_i241243_11_;
      this.field_241503_w_ = p_i241243_12_;
      this.magnifier = p_i241243_13_;
      this.field_241504_y_ = p_i241243_14_;
      this.field_236017_x_ = p_i241243_15_;
      this.field_236018_y_ = func_236020_a_(p_i241243_15_);
   }

   private static float[] func_236020_a_(float p_236020_0_) {
      float[] afloat = new float[16];

      for(int i = 0; i <= 15; ++i) {
         float f = (float)i / 15.0F;
         float f1 = f / (4.0F - 3.0F * f);
         afloat[i] = MathHelper.lerp(p_236020_0_, f1, 1.0F);
      }

      return afloat;
   }

   @Deprecated
   public static DataResult<RegistryKey<World>> func_236025_a_(Dynamic<?> p_236025_0_) {
      Optional<Number> optional = p_236025_0_.asNumber().result();
      if (optional.isPresent()) {
         int i = optional.get().intValue();
         if (i == -1) {
            return DataResult.success(World.field_234919_h_);
         }

         if (i == 0) {
            return DataResult.success(World.field_234918_g_);
         }

         if (i == 1) {
            return DataResult.success(World.field_234920_i_);
         }
      }

      return World.field_234917_f_.parse(p_236025_0_);
   }

   public static IDynamicRegistries.Impl func_236027_a_(IDynamicRegistries.Impl p_236027_0_) {
      p_236027_0_.func_239774_a_(OVERWORLD, OVERWORLD_TYPE);
      p_236027_0_.func_239774_a_(field_241497_i_, field_241498_j_);
      p_236027_0_.func_239774_a_(THE_NETHER, NETHER_TYPE);
      p_236027_0_.func_239774_a_(THE_END, END_TYPE);
      return p_236027_0_;
   }

   private static ChunkGenerator func_236038_d_(long p_236038_0_) {
      return new NoiseChunkGenerator(new EndBiomeProvider(p_236038_0_), p_236038_0_, DimensionSettings.Preset.END.getSettings());
   }

   private static ChunkGenerator func_236041_e_(long p_236041_0_) {
      return new NoiseChunkGenerator(NetherBiomeProvider.Preset.field_235288_b_.func_235292_a_(p_236041_0_), p_236041_0_, DimensionSettings.Preset.NETHER.getSettings());
   }

   public static SimpleRegistry<Dimension> func_236022_a_(long p_236022_0_) {
      SimpleRegistry<Dimension> simpleregistry = new SimpleRegistry<>(Registry.DIMENSION_KEY, Lifecycle.experimental());
      simpleregistry.register(Dimension.THE_NETHER, new Dimension(() -> {
         return NETHER_TYPE;
      }, func_236041_e_(p_236022_0_)));
      simpleregistry.register(Dimension.THE_END, new Dimension(() -> {
         return END_TYPE;
      }, func_236038_d_(p_236022_0_)));
      simpleregistry.func_239662_d_(Dimension.THE_NETHER);
      simpleregistry.func_239662_d_(Dimension.THE_END);
      return simpleregistry;
   }

   @Deprecated
   public String getSuffix() {
      return this == END_TYPE ? "_end" : "";
   }

   public static File func_236031_a_(RegistryKey<World> p_236031_0_, File p_236031_1_) {
      if (p_236031_0_ == World.field_234918_g_) {
         return p_236031_1_;
      } else if (p_236031_0_ == World.field_234920_i_) {
         return new File(p_236031_1_, "DIM1");
      } else {
         return p_236031_0_ == World.field_234919_h_ ? new File(p_236031_1_, "DIM-1") : new File(p_236031_1_, "dimensions/" + p_236031_0_.func_240901_a_().getNamespace() + "/" + p_236031_0_.func_240901_a_().getPath());
      }
   }

   public boolean hasSkyLight() {
      return this.hasSkyLight;
   }

   public boolean func_236037_d_() {
      return this.hasCeiling;
   }

   public boolean func_236040_e_() {
      return this.field_236012_r_;
   }

   public boolean func_236043_f_() {
      return this.field_236013_s_;
   }

   public boolean func_236045_g_() {
      return this.field_236014_t_;
   }

   public boolean func_241509_i_() {
      return this.field_241499_s_;
   }

   public boolean func_241510_j_() {
      return this.field_241500_t_;
   }

   public boolean func_241511_k_() {
      return this.field_241501_u_;
   }

   public boolean func_241512_l_() {
      return this.field_241502_v_;
   }

   public int func_241513_m_() {
      return this.field_241503_w_;
   }

   public boolean func_236046_h_() {
      return this.field_236015_u_;
   }

   public IBiomeMagnifier getMagnifier() {
      return this.magnifier;
   }

   public boolean func_241514_p_() {
      return this.fixedTime.isPresent();
   }

   public float func_236032_b_(long p_236032_1_) {
      double d0 = MathHelper.frac((double)this.fixedTime.orElse(p_236032_1_) / 24000.0D - 0.25D);
      double d1 = 0.5D - Math.cos(d0 * Math.PI) / 2.0D;
      return (float)(d0 * 2.0D + d1) / 3.0F;
   }

   public int func_236035_c_(long p_236035_1_) {
      return (int)(p_236035_1_ / 24000L % 8L + 8L) % 8;
   }

   public float func_236021_a_(int p_236021_1_) {
      return this.field_236018_y_[p_236021_1_];
   }

   public ITag<Block> func_241515_q_() {
      ITag<Block> itag = BlockTags.getCollection().get(this.field_241504_y_);
      return (ITag<Block>)(itag != null ? itag : BlockTags.INFINIBURN_OVERWORLD);
   }
}