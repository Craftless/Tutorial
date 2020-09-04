package net.minecraft.world.biome;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SeaGrassConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class DeepColdOceanBiome extends Biome {
   public DeepColdOceanBiome() {
      super((new Biome.Builder()).surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG).precipitation(Biome.RainType.RAIN).category(Biome.Category.OCEAN).depth(-1.8F).scale(0.1F).temperature(0.5F).downfall(0.5F).func_235097_a_((new BiomeAmbience.Builder()).setWaterColor(4020182).setWaterFogColor(329011).setFogColor(12638463).setMoodSound(MoodSoundAmbience.field_235027_b_).build()).parent((String)null));
      this.func_235063_a_(DefaultBiomeFeatures.OCEAN_MONUMENT_COLD);
      this.func_235063_a_(DefaultBiomeFeatures.MONUMENT);
      DefaultBiomeFeatures.func_235197_c_(this);
      this.func_235063_a_(DefaultBiomeFeatures.RUINED_PORTAL_OCEAN);
      DefaultBiomeFeatures.addOceanCarvers(this);
      DefaultBiomeFeatures.addLakes(this);
      DefaultBiomeFeatures.addMonsterRooms(this);
      DefaultBiomeFeatures.addStoneVariants(this);
      DefaultBiomeFeatures.addOres(this);
      DefaultBiomeFeatures.addSedimentDisks(this);
      DefaultBiomeFeatures.addScatteredOakTrees(this);
      DefaultBiomeFeatures.addDefaultFlowers(this);
      DefaultBiomeFeatures.addSparseGrass(this);
      DefaultBiomeFeatures.addMushrooms(this);
      DefaultBiomeFeatures.addReedsAndPumpkins(this);
      DefaultBiomeFeatures.addSprings(this);
      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SEAGRASS.withConfiguration(new SeaGrassConfig(40, 0.8D)).withPlacement(Placement.TOP_SOLID_HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
      DefaultBiomeFeatures.addSeagrass(this);
      DefaultBiomeFeatures.addExtraKelp(this);
      DefaultBiomeFeatures.addFreezeTopLayer(this);
      this.addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.SQUID, 3, 1, 4));
      this.addSpawn(EntityClassification.WATER_AMBIENT, new Biome.SpawnListEntry(EntityType.COD, 15, 3, 6));
      this.addSpawn(EntityClassification.WATER_AMBIENT, new Biome.SpawnListEntry(EntityType.SALMON, 15, 1, 5));
      this.addSpawn(EntityClassification.AMBIENT, new Biome.SpawnListEntry(EntityType.BAT, 10, 8, 8));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE, 95, 4, 4));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.DROWNED, 5, 1, 1));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SLIME, 100, 4, 4));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.WITCH, 5, 1, 1));
   }
}