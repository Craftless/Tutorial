package net.minecraft.util.registry;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import java.util.function.Supplier;
import net.minecraft.util.RegistryKey;

public final class RegistryKeyCodec<E> implements Codec<Supplier<E>> {
   private final RegistryKey<Registry<E>> registryKey;
   private final MapCodec<E> registryCodec;

   public static <E> RegistryKeyCodec<E> func_241794_a_(RegistryKey<Registry<E>> registryKey, MapCodec<E> codec) {
      return new RegistryKeyCodec<>(registryKey, codec);
   }

   private RegistryKeyCodec(RegistryKey<Registry<E>> registryKey, MapCodec<E> codec) {
      this.registryKey = registryKey;
      this.registryCodec = codec;
   }

   public <T> DataResult<T> encode(Supplier<E> p_encode_1_, DynamicOps<T> p_encode_2_, T p_encode_3_) {
      return p_encode_2_ instanceof WorldGenSettingsExport ? ((WorldGenSettingsExport)p_encode_2_).func_241811_a_(p_encode_1_.get(), p_encode_3_, this.registryKey, this.registryCodec) : this.registryCodec.codec().encode(p_encode_1_.get(), p_encode_2_, p_encode_3_);
   }

   public <T> DataResult<Pair<Supplier<E>, T>> decode(DynamicOps<T> p_decode_1_, T p_decode_2_) {
      return p_decode_1_ instanceof WorldSettingsImport ? ((WorldSettingsImport)p_decode_1_).func_241802_a_(p_decode_2_, this.registryKey, this.registryCodec) : this.registryCodec.codec().decode(p_decode_1_, p_decode_2_).map((p_240866_0_) -> {
         return p_240866_0_.mapFirst((p_240867_0_) -> {
            return () -> {
               return p_240867_0_;
            };
         });
      });
   }

   public String toString() {
      return "RegistryFileCodec[" + this.registryKey + " " + this.registryCodec + "]";
   }
}