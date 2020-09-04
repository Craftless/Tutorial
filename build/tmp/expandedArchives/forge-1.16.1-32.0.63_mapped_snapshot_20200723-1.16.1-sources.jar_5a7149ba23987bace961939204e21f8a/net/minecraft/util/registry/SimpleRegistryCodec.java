package net.minecraft.util.registry;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import net.minecraft.util.RegistryKey;

public final class SimpleRegistryCodec<E> implements Codec<SimpleRegistry<E>> {
   private final Codec<SimpleRegistry<E>> registryCodec;
   private final RegistryKey<Registry<E>> registryKey;
   private final MapCodec<E> field_240860_c_;

   public static <E> SimpleRegistryCodec<E> func_241793_a_(RegistryKey<Registry<E>> registryKey, Lifecycle lifecycle, MapCodec<E> p_241793_2_) {
      return new SimpleRegistryCodec<>(registryKey, lifecycle, p_241793_2_);
   }

   private SimpleRegistryCodec(RegistryKey<Registry<E>> registryKey, Lifecycle lifecycle, MapCodec<E> p_i241274_3_) {
      this.registryCodec = SimpleRegistry.func_241745_c_(registryKey, lifecycle, p_i241274_3_);
      this.registryKey = registryKey;
      this.field_240860_c_ = p_i241274_3_;
   }

   public <T> DataResult<T> encode(SimpleRegistry<E> p_encode_1_, DynamicOps<T> p_encode_2_, T p_encode_3_) {
      return this.registryCodec.encode(p_encode_1_, p_encode_2_, p_encode_3_);
   }

   public <T> DataResult<Pair<SimpleRegistry<E>, T>> decode(DynamicOps<T> p_decode_1_, T p_decode_2_) {
      DataResult<Pair<SimpleRegistry<E>, T>> dataresult = this.registryCodec.decode(p_decode_1_, p_decode_2_);
      return p_decode_1_ instanceof WorldSettingsImport ? dataresult.flatMap((p_240862_2_) -> {
         return ((WorldSettingsImport)p_decode_1_).func_241797_a_(p_240862_2_.getFirst(), this.registryKey, this.field_240860_c_).map((p_240861_1_) -> {
            return Pair.of(p_240861_1_, (T)p_240862_2_.getSecond());
         });
      }) : dataresult;
   }

   public String toString() {
      return "RegistryDapaPackCodec[" + this.registryCodec + " " + this.registryKey + " " + this.field_240860_c_ + "]";
   }
}