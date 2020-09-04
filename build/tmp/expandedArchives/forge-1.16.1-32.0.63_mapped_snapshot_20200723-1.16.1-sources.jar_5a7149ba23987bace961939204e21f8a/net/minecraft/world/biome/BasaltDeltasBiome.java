package net.minecraft.world.biome;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.client.audio.BackgroundMusicTracks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.placement.ChanceRangeConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class BasaltDeltasBiome extends Biome {
   protected BasaltDeltasBiome() {
      super((new Biome.Builder()).surfaceBuilder(SurfaceBuilder.field_237191_af_, SurfaceBuilder.field_237187_R_).precipitation(Biome.RainType.NONE).category(Biome.Category.NETHER).depth(0.1F).scale(0.2F).temperature(2.0F).downfall(0.0F).func_235097_a_((new BiomeAmbience.Builder()).setWaterColor(4159204).setWaterFogColor(4341314).setFogColor(6840176).setParticle(new ParticleEffectAmbience(ParticleTypes.WHITE_ASH, 0.118093334F)).setAmbientSound(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP).setMoodSound(new MoodSoundAmbience(SoundEvents.AMBIENT_BASALT_DELTAS_MOOD, 6000, 8, 2.0D)).setAdditionsSound(new SoundAdditionsAmbience(SoundEvents.AMBIENT_BASALT_DELTAS_ADDITIONS, 0.0111D)).setMusic(BackgroundMusicTracks.func_232677_a_(SoundEvents.MUSIC_NETHER_BASALT_DELTAS)).build()).parent((String)null).func_235098_a_(ImmutableList.of(new Biome.Attributes(-0.5F, 0.0F, 0.0F, 0.0F, 0.175F))));
      this.func_235063_a_(DefaultBiomeFeatures.RUINED_PORTAL_NETHER);
      this.addCarver(GenerationStage.Carving.AIR, createCarver(WorldCarver.field_236240_b_, new ProbabilityConfig(0.2F)));
      this.func_235063_a_(DefaultBiomeFeatures.FORTRESS);
      this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.field_236286_Q_.withConfiguration(DefaultBiomeFeatures.BASALT_DELTA).withPlacement(Placement.COUNT_HEIGHTMAP.configure(new FrequencyConfig(40))));
      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SPRING_FEATURE.withConfiguration(DefaultBiomeFeatures.LAVA_SPRING_CONFIG).withPlacement(Placement.COUNT_VERY_BIASED_RANGE.configure(new CountRangeConfig(40, 8, 16, 256))));
      this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.field_236285_P_.withConfiguration(DefaultBiomeFeatures.field_235140_aR_).withPlacement(Placement.COUNT_HEIGHTMAP.configure(new FrequencyConfig(4))));
      this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.field_236285_P_.withConfiguration(DefaultBiomeFeatures.field_235141_aS_).withPlacement(Placement.COUNT_HEIGHTMAP.configure(new FrequencyConfig(2))));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.field_236287_R_.withConfiguration(DefaultBiomeFeatures.field_235142_aT_).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(75, 0, 0, 128))));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.field_236287_R_.withConfiguration(DefaultBiomeFeatures.field_235143_aU_).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(25, 0, 0, 128))));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.withConfiguration(DefaultBiomeFeatures.NETHER_LAVA).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(16, 4, 8, 128))));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.NETHER_FIRE).withPlacement(Placement.field_236960_A_.configure(new FrequencyConfig(10))));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.NETHER_SOUL_FIRE).withPlacement(Placement.field_236960_A_.configure(new FrequencyConfig(10))));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.GLOWSTONE_BLOB.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.LIGHT_GEM_CHANCE.configure(new FrequencyConfig(10))));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.GLOWSTONE_BLOB.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 128))));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.BROWN_MUSHROOM_CONFIG).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(0.5F, 0, 0, 128))));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.RED_MUSHROOM_CONFIG).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(0.5F, 0, 0, 128))));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, Blocks.MAGMA_BLOCK.getDefaultState(), 33)).withPlacement(Placement.MAGMA.configure(new FrequencyConfig(4))));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.withConfiguration(DefaultBiomeFeatures.ENCLOSED_NETHER_SPRING_CONFIG).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(32, 10, 20, 128))));
      DefaultBiomeFeatures.func_235190_a_(this, 20, 32);
      DefaultBiomeFeatures.func_235193_at_(this);
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.GHAST, 40, 1, 1));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.MAGMA_CUBE, 100, 2, 5));
      this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.STRIDER, 60, 1, 2));
   }
}