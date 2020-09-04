package net.minecraft.world.gen.carver;

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;

public class ConfiguredCarver<WC extends ICarverConfig> {
   public static final Codec<ConfiguredCarver<?>> field_236235_a_ = Registry.CARVER.dispatch("name", (p_236236_0_) -> {
      return p_236236_0_.carver;
   }, WorldCarver::func_236244_c_);
   public final WorldCarver<WC> carver;
   public final WC config;

   public ConfiguredCarver(WorldCarver<WC> carver, WC config) {
      this.carver = carver;
      this.config = config;
   }

   public boolean shouldCarve(Random rand, int chunkX, int chunkZ) {
      return this.carver.shouldCarve(rand, chunkX, chunkZ, this.config);
   }

   public boolean carveRegion(IChunk chunk, Function<BlockPos, Biome> biomePos, Random rand, int seaLevel, int chunkXOffset, int chunkZOffset, int chunkX, int chunkZ, BitSet carvingMask) {
      return this.carver.carveRegion(chunk, biomePos, rand, seaLevel, chunkXOffset, chunkZOffset, chunkX, chunkZ, carvingMask, this.config);
   }
}