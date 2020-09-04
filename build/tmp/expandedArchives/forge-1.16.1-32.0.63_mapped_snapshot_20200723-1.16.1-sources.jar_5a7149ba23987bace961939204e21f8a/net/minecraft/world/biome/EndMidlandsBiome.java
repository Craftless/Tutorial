package net.minecraft.world.biome;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EndMidlandsBiome extends Biome {
   public EndMidlandsBiome() {
      super((new Biome.Builder()).surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.END_STONE_CONFIG).precipitation(Biome.RainType.NONE).category(Biome.Category.THEEND).depth(0.1F).scale(0.2F).temperature(0.5F).downfall(0.5F).func_235097_a_((new BiomeAmbience.Builder()).setWaterColor(4159204).setWaterFogColor(329011).setFogColor(10518688).setMoodSound(MoodSoundAmbience.field_235027_b_).build()).parent((String)null));
      this.func_235063_a_(DefaultBiomeFeatures.END_CITY);
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 4, 4));
   }

   @OnlyIn(Dist.CLIENT)
   public int getSkyColor() {
      return 0;
   }
}