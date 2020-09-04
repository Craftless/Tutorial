package net.minecraft.world.gen.settings;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Properties;
import java.util.Random;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DebugChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DimensionGeneratorSettings {
   public static final Codec<DimensionGeneratorSettings> field_236201_a_ = RecordCodecBuilder.<DimensionGeneratorSettings>create((p_236214_0_) -> {
      return p_236214_0_.group(Codec.LONG.fieldOf("seed").stable().forGetter(DimensionGeneratorSettings::getSeed), Codec.BOOL.fieldOf("generate_features").withDefault(true).stable().forGetter(DimensionGeneratorSettings::doesGenerateFeatures), Codec.BOOL.fieldOf("bonus_chest").withDefault(false).stable().forGetter(DimensionGeneratorSettings::hasBonusChest), SimpleRegistry.getSimpleRegistryCodec(Registry.DIMENSION_KEY, Lifecycle.stable(), Dimension.DIMENSION_CODEC).xmap(Dimension::func_236062_a_, Function.identity()).fieldOf("dimensions").forGetter(DimensionGeneratorSettings::func_236224_e_), Codec.STRING.optionalFieldOf("legacy_custom_options").stable().forGetter((p_236213_0_) -> {
         return p_236213_0_.field_236209_i_;
      })).apply(p_236214_0_, p_236214_0_.stable(DimensionGeneratorSettings::new));
   }).comapFlatMap(DimensionGeneratorSettings::func_236233_n_, Function.identity());
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int field_236204_d_ = "North Carolina".hashCode();
   public static final DimensionGeneratorSettings field_236202_b_ = new DimensionGeneratorSettings((long)field_236204_d_, true, true, func_236216_a_(DimensionType.func_236022_a_((long)field_236204_d_), func_236211_a_((long)field_236204_d_)));
   private final long seed;
   private final boolean generateFeatures;
   private final boolean bonusChest;
   private final SimpleRegistry<Dimension> field_236208_h_;
   private final Optional<String> field_236209_i_;

   private DataResult<DimensionGeneratorSettings> func_236233_n_() {
      return this.func_236234_o_() ? DataResult.success(this, Lifecycle.stable()) : DataResult.success(this);
   }

   private boolean func_236234_o_() {
      return Dimension.func_236060_a_(this.seed, this.field_236208_h_);
   }

   public DimensionGeneratorSettings(long seed, boolean generateFeatures, boolean bonusChest, SimpleRegistry<Dimension> p_i231914_5_) {
      this(seed, generateFeatures, bonusChest, p_i231914_5_, Optional.empty());
   }

   private DimensionGeneratorSettings(long seed, boolean generateFeatures, boolean bonusChest, SimpleRegistry<Dimension> p_i231915_5_, Optional<String> p_i231915_6_) {
      this.seed = seed;
      this.generateFeatures = generateFeatures;
      this.bonusChest = bonusChest;
      this.field_236208_h_ = p_i231915_5_;
      this.field_236209_i_ = p_i231915_6_;
   }

   public static DimensionGeneratorSettings func_236210_a_() {
      long i = (new Random()).nextLong();
      return new DimensionGeneratorSettings(i, true, false, func_236216_a_(DimensionType.func_236022_a_(i), func_236211_a_(i)));
   }

   public static NoiseChunkGenerator func_236211_a_(long seed) {
      return new NoiseChunkGenerator(new OverworldBiomeProvider(seed, false, false), seed, DimensionSettings.Preset.OVERWORLD.getSettings());
   }

   public long getSeed() {
      return this.seed;
   }

   public boolean doesGenerateFeatures() {
      return this.generateFeatures;
   }

   public boolean hasBonusChest() {
      return this.bonusChest;
   }

   public static SimpleRegistry<Dimension> func_236216_a_(SimpleRegistry<Dimension> p_236216_0_, ChunkGenerator p_236216_1_) {
      Dimension dimension = p_236216_0_.getValueForKey(Dimension.OVERWORLD);
      Supplier<DimensionType> supplier = () -> {
         return dimension == null ? DimensionType.func_236019_a_() : dimension.getDimensionType();
      };
      return func_241520_a_(p_236216_0_, supplier, p_236216_1_);
   }

   public static SimpleRegistry<Dimension> func_241520_a_(SimpleRegistry<Dimension> p_241520_0_, Supplier<DimensionType> p_241520_1_, ChunkGenerator p_241520_2_) {
      SimpleRegistry<Dimension> simpleregistry = new SimpleRegistry<>(Registry.DIMENSION_KEY, Lifecycle.experimental());
      simpleregistry.register(Dimension.OVERWORLD, new Dimension(p_241520_1_, p_241520_2_));
      simpleregistry.func_239662_d_(Dimension.OVERWORLD);

      for(Entry<RegistryKey<Dimension>, Dimension> entry : p_241520_0_.getEntries()) {
         RegistryKey<Dimension> registrykey = entry.getKey();
         if (registrykey != Dimension.OVERWORLD) {
            simpleregistry.register(registrykey, entry.getValue());
            if (p_241520_0_.func_239660_c_(registrykey)) {
               simpleregistry.func_239662_d_(registrykey);
            }
         }
      }

      return simpleregistry;
   }

   public SimpleRegistry<Dimension> func_236224_e_() {
      return this.field_236208_h_;
   }

   public ChunkGenerator func_236225_f_() {
      Dimension dimension = this.field_236208_h_.getValueForKey(Dimension.OVERWORLD);
      return (ChunkGenerator)(dimension == null ? func_236211_a_((new Random()).nextLong()) : dimension.getChunkGenerator());
   }

   public ImmutableSet<RegistryKey<World>> func_236226_g_() {
      return this.func_236224_e_().getEntries().stream().map((p_236218_0_) -> {
         return RegistryKey.func_240903_a_(Registry.WORLD_KEY, p_236218_0_.getKey().func_240901_a_());
      }).collect(ImmutableSet.toImmutableSet());
   }

   public boolean func_236227_h_() {
      return this.func_236225_f_() instanceof DebugChunkGenerator;
   }

   public boolean func_236228_i_() {
      return this.func_236225_f_() instanceof FlatChunkGenerator;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_236229_j_() {
      return this.field_236209_i_.isPresent();
   }

   public DimensionGeneratorSettings func_236230_k_() {
      return new DimensionGeneratorSettings(this.seed, this.generateFeatures, true, this.field_236208_h_, this.field_236209_i_);
   }

   @OnlyIn(Dist.CLIENT)
   public DimensionGeneratorSettings func_236231_l_() {
      return new DimensionGeneratorSettings(this.seed, !this.generateFeatures, this.bonusChest, this.field_236208_h_);
   }

   @OnlyIn(Dist.CLIENT)
   public DimensionGeneratorSettings func_236232_m_() {
      return new DimensionGeneratorSettings(this.seed, this.generateFeatures, !this.bonusChest, this.field_236208_h_);
   }

   @OnlyIn(Dist.CLIENT)
   public DimensionGeneratorSettings func_236215_a_(SimpleRegistry<Dimension> p_236215_1_) {
      return new DimensionGeneratorSettings(this.seed, this.generateFeatures, this.bonusChest, p_236215_1_);
   }

   public static DimensionGeneratorSettings func_236219_a_(Properties p_236219_0_) {
      String s = MoreObjects.firstNonNull((String)p_236219_0_.get("generator-settings"), "");
      p_236219_0_.put("generator-settings", s);
      String s1 = MoreObjects.firstNonNull((String)p_236219_0_.get("level-seed"), "");
      p_236219_0_.put("level-seed", s1);
      String s2 = (String)p_236219_0_.get("generate-structures");
      boolean flag = s2 == null || Boolean.parseBoolean(s2);
      p_236219_0_.put("generate-structures", Objects.toString(flag));
      String s3 = (String)p_236219_0_.get("level-type");
      String s4 = Optional.ofNullable(s3).map((p_236217_0_) -> {
         return p_236217_0_.toLowerCase(Locale.ROOT);
      }).orElse("default");
      p_236219_0_.put("level-type", s4);
      long i = (new Random()).nextLong();
      if (!s1.isEmpty()) {
         try {
            long j = Long.parseLong(s1);
            if (j != 0L) {
               i = j;
            }
         } catch (NumberFormatException numberformatexception) {
            i = (long)s1.hashCode();
         }
      }

      SimpleRegistry<Dimension> simpleregistry = DimensionType.func_236022_a_(i);
      byte b0 = -1;
      switch(s4.hashCode()) {
      case -1100099890:
         if (s4.equals("largebiomes")) {
            b0 = 3;
         }
         break;
      case 3145593:
         if (s4.equals("flat")) {
            b0 = 0;
         }
         break;
      case 1045526590:
         if (s4.equals("debug_all_block_states")) {
            b0 = 1;
         }
         break;
      case 1271599715:
         if (s4.equals("amplified")) {
            b0 = 2;
         }
      }

      switch(b0) {
      case 0:
         JsonObject jsonobject = !s.isEmpty() ? JSONUtils.fromJson(s) : new JsonObject();
         Dynamic<JsonElement> dynamic = new Dynamic<>(JsonOps.INSTANCE, jsonobject);
         return new DimensionGeneratorSettings(i, flag, false, func_236216_a_(simpleregistry, new FlatChunkGenerator(FlatGenerationSettings.field_236932_a_.parse(dynamic).resultOrPartial(LOGGER::error).orElseGet(FlatGenerationSettings::getDefaultFlatGenerator))));
      case 1:
         return new DimensionGeneratorSettings(i, flag, false, func_236216_a_(simpleregistry, DebugChunkGenerator.field_236065_d_));
      case 2:
         return new DimensionGeneratorSettings(i, flag, false, func_236216_a_(simpleregistry, new NoiseChunkGenerator(new OverworldBiomeProvider(i, false, false), i, DimensionSettings.Preset.AMPLIFIED.getSettings())));
      case 3:
         return new DimensionGeneratorSettings(i, flag, false, func_236216_a_(simpleregistry, new NoiseChunkGenerator(new OverworldBiomeProvider(i, false, true), i, DimensionSettings.Preset.OVERWORLD.getSettings())));
      default:
         return new DimensionGeneratorSettings(i, flag, false, func_236216_a_(simpleregistry, func_236211_a_(i)));
      }
   }

   @OnlyIn(Dist.CLIENT)
   public DimensionGeneratorSettings create(boolean hardcore, OptionalLong worldSeed) {
      long i = worldSeed.orElse(this.seed);
      SimpleRegistry<Dimension> simpleregistry;
      if (worldSeed.isPresent()) {
         simpleregistry = new SimpleRegistry<>(Registry.DIMENSION_KEY, Lifecycle.experimental());
         long j = worldSeed.getAsLong();

         for(Entry<RegistryKey<Dimension>, Dimension> entry : this.field_236208_h_.getEntries()) {
            RegistryKey<Dimension> registrykey = entry.getKey();
            simpleregistry.register(registrykey, new Dimension(entry.getValue().getDimensionTypeSupplier(), entry.getValue().getChunkGenerator().func_230349_a_(j)));
            if (this.field_236208_h_.func_239660_c_(registrykey)) {
               simpleregistry.func_239662_d_(registrykey);
            }
         }
      } else {
         simpleregistry = this.field_236208_h_;
      }

      DimensionGeneratorSettings dimensiongeneratorsettings;
      if (this.func_236227_h_()) {
         dimensiongeneratorsettings = new DimensionGeneratorSettings(i, false, false, simpleregistry);
      } else {
         dimensiongeneratorsettings = new DimensionGeneratorSettings(i, this.doesGenerateFeatures(), this.hasBonusChest() && !hardcore, simpleregistry);
      }

      return dimensiongeneratorsettings;
   }
}