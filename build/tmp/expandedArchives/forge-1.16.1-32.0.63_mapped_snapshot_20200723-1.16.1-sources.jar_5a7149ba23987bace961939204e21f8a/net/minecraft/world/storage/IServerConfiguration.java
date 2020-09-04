package net.minecraft.world.storage;

import com.mojang.serialization.Lifecycle;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.IDynamicRegistries;
import net.minecraft.util.datafix.codec.DatapackCodec;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IServerConfiguration {
   DatapackCodec func_230403_C_();

   void func_230410_a_(DatapackCodec p_230410_1_);

   boolean isModded();

   Set<String> func_230406_F_();

   void func_230412_a_(String p_230412_1_, boolean isModded);

   /**
    * Adds this WorldInfo instance to the crash report.
    */
   default void addToCrashReport(CrashReportCategory category) {
      category.addDetail("Known server brands", () -> {
         return String.join(", ", this.func_230406_F_());
      });
      category.addDetail("Level was modded", () -> {
         return Boolean.toString(this.isModded());
      });
      category.addDetail("Level storage version", () -> {
         int i = this.func_230417_y_();
         return String.format("0x%05X - %s", i, this.func_237379_i_(i));
      });
   }

   default String func_237379_i_(int p_237379_1_) {
      switch(p_237379_1_) {
      case 19132:
         return "McRegion";
      case 19133:
         return "Anvil";
      default:
         return "Unknown?";
      }
   }

   @Nullable
   CompoundNBT getCustomBossEventData();

   void setCustomBossEventData(@Nullable CompoundNBT nbt);

   IServerWorldInfo func_230407_G_();

   @OnlyIn(Dist.CLIENT)
   WorldSettings func_230408_H_();

   CompoundNBT func_230411_a_(IDynamicRegistries p_230411_1_, @Nullable CompoundNBT p_230411_2_);

   /**
    * Returns true if hardcore mode is enabled, otherwise false
    */
   boolean isHardcore();

   int func_230417_y_();

   /**
    * Get current world name
    */
   String getWorldName();

   /**
    * Gets the GameType.
    */
   GameType getGameType();

   void setGameType(GameType type);

   /**
    * Returns true if commands are allowed on this World.
    */
   boolean areCommandsAllowed();

   Difficulty getDifficulty();

   void setDifficulty(Difficulty difficulty);

   boolean isDifficultyLocked();

   void setDifficultyLocked(boolean p_230415_1_);

   /**
    * Gets the GameRules class Instance.
    */
   GameRules getGameRulesInstance();

   CompoundNBT func_230416_x_();

   CompoundNBT getDragonFightData();

   void setDragonFightData(CompoundNBT nbt);

   DimensionGeneratorSettings getDimensionGeneratorSettings();

   @OnlyIn(Dist.CLIENT)
   Lifecycle getLifecycle();
}