package net.minecraft.world.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.OptionalInt;
import net.minecraft.client.audio.BackgroundMusicSelector;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BiomeAmbience {
   public static final Codec<BiomeAmbience> field_235204_a_ = RecordCodecBuilder.create((p_235215_0_) -> {
      return p_235215_0_.group(Codec.INT.fieldOf("fog_color").forGetter((p_235229_0_) -> {
         return p_235229_0_.fogColor;
      }), Codec.INT.fieldOf("water_color").forGetter((p_235227_0_) -> {
         return p_235227_0_.waterColor;
      }), Codec.INT.fieldOf("water_fog_color").forGetter((p_235225_0_) -> {
         return p_235225_0_.waterFogColor;
      }), ParticleEffectAmbience.field_235041_a_.optionalFieldOf("particle").forGetter((p_235223_0_) -> {
         return p_235223_0_.particle;
      }), SoundEvent.SOUND_EVENT_CODEC.optionalFieldOf("ambient_sound").forGetter((p_235221_0_) -> {
         return p_235221_0_.ambientSound;
      }), MoodSoundAmbience.field_235026_a_.optionalFieldOf("mood_sound").forGetter((p_235219_0_) -> {
         return p_235219_0_.moodSound;
      }), SoundAdditionsAmbience.field_235018_a_.optionalFieldOf("additions_sound").forGetter((p_235217_0_) -> {
         return p_235217_0_.additionsSound;
      }), BackgroundMusicSelector.field_232656_a_.optionalFieldOf("music").forGetter((p_235214_0_) -> {
         return p_235214_0_.music;
      })).apply(p_235215_0_, BiomeAmbience::new);
   });
   private final int fogColor;
   private final int waterColor;
   private final int waterFogColor;
   private final Optional<ParticleEffectAmbience> particle;
   private final Optional<SoundEvent> ambientSound;
   private final Optional<MoodSoundAmbience> moodSound;
   private final Optional<SoundAdditionsAmbience> additionsSound;
   private final Optional<BackgroundMusicSelector> music;

   private BiomeAmbience(int fogColor, int waterColor, int waterFogColor, Optional<ParticleEffectAmbience> particle, Optional<SoundEvent> ambientSound, Optional<MoodSoundAmbience> moodSound, Optional<SoundAdditionsAmbience> additionsSound, Optional<BackgroundMusicSelector> music) {
      this.fogColor = fogColor;
      this.waterColor = waterColor;
      this.waterFogColor = waterFogColor;
      this.particle = particle;
      this.ambientSound = ambientSound;
      this.moodSound = moodSound;
      this.additionsSound = additionsSound;
      this.music = music;
   }

   @OnlyIn(Dist.CLIENT)
   public int getFogColor() {
      return this.fogColor;
   }

   @OnlyIn(Dist.CLIENT)
   public int getWaterColor() {
      return this.waterColor;
   }

   @OnlyIn(Dist.CLIENT)
   public int getWaterFogColor() {
      return this.waterFogColor;
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<ParticleEffectAmbience> getParticle() {
      return this.particle;
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<SoundEvent> getAmbientSound() {
      return this.ambientSound;
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<MoodSoundAmbience> getMoodSound() {
      return this.moodSound;
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<SoundAdditionsAmbience> getAdditionsSound() {
      return this.additionsSound;
   }

   @OnlyIn(Dist.CLIENT)
   public Optional<BackgroundMusicSelector> getMusic() {
      return this.music;
   }

   public static class Builder {
      private OptionalInt fogColor = OptionalInt.empty();
      private OptionalInt waterColor = OptionalInt.empty();
      private OptionalInt waterFogColor = OptionalInt.empty();
      private Optional<ParticleEffectAmbience> particle = Optional.empty();
      private Optional<SoundEvent> ambientSound = Optional.empty();
      private Optional<MoodSoundAmbience> moodSound = Optional.empty();
      private Optional<SoundAdditionsAmbience> additionsSound = Optional.empty();
      private Optional<BackgroundMusicSelector> music = Optional.empty();

      public BiomeAmbience.Builder setFogColor(int p_235239_1_) {
         this.fogColor = OptionalInt.of(p_235239_1_);
         return this;
      }

      public BiomeAmbience.Builder setWaterColor(int p_235246_1_) {
         this.waterColor = OptionalInt.of(p_235246_1_);
         return this;
      }

      public BiomeAmbience.Builder setWaterFogColor(int p_235248_1_) {
         this.waterFogColor = OptionalInt.of(p_235248_1_);
         return this;
      }

      public BiomeAmbience.Builder setParticle(ParticleEffectAmbience p_235244_1_) {
         this.particle = Optional.of(p_235244_1_);
         return this;
      }

      public BiomeAmbience.Builder setAmbientSound(SoundEvent p_235241_1_) {
         this.ambientSound = Optional.of(p_235241_1_);
         return this;
      }

      public BiomeAmbience.Builder setMoodSound(MoodSoundAmbience p_235243_1_) {
         this.moodSound = Optional.of(p_235243_1_);
         return this;
      }

      public BiomeAmbience.Builder setAdditionsSound(SoundAdditionsAmbience p_235242_1_) {
         this.additionsSound = Optional.of(p_235242_1_);
         return this;
      }

      public BiomeAmbience.Builder setMusic(BackgroundMusicSelector p_235240_1_) {
         this.music = Optional.of(p_235240_1_);
         return this;
      }

      public BiomeAmbience build() {
         return new BiomeAmbience(this.fogColor.orElseThrow(() -> {
            return new IllegalStateException("Missing 'fog' color.");
         }), this.waterColor.orElseThrow(() -> {
            return new IllegalStateException("Missing 'water' color.");
         }), this.waterFogColor.orElseThrow(() -> {
            return new IllegalStateException("Missing 'water fog' color.");
         }), this.particle, this.ambientSound, this.moodSound, this.additionsSound, this.music);
      }
   }
}