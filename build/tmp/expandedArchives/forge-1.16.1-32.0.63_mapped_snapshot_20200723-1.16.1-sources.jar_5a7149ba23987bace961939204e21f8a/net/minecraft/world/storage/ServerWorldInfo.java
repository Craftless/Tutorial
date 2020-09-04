package net.minecraft.world.storage;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.command.TimerCallbackManager;
import net.minecraft.command.TimerCallbackSerializers;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.nbt.StringNBT;
import net.minecraft.server.IDynamicRegistries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.UUIDCodec;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.DefaultTypeReferences;
import net.minecraft.util.datafix.codec.DatapackCodec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.WorldGenSettingsExport;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerWorldInfo implements IServerWorldInfo, IServerConfiguration {
   private static final Logger LOGGER = LogManager.getLogger();
   private WorldSettings worldSettings;
   private final DimensionGeneratorSettings generatorSettings;
   private final Lifecycle lifecycle;
   private int spawnX;
   private int spawnY;
   private int spawnZ;
   private long gameTime;
   private long dayTime;
   @Nullable
   private final DataFixer dataFixer;
   private final int version;
   private boolean dataFixed;
   @Nullable
   private CompoundNBT loadedPlayerNBT;
   private final int levelStorageVersion;
   private int clearWeatherTime;
   private boolean raining;
   private int rainTime;
   private boolean thundering;
   private int thunderTime;
   private boolean initialized;
   private boolean difficultyLocked;
   private WorldBorder.Serializer borderSerializer;
   private CompoundNBT dragonFightNBT;
   @Nullable
   private CompoundNBT customBossEventNBT;
   private int wanderingTraderSpawnDelay;
   private int wanderingTraderSpawnChance;
   @Nullable
   private UUID wanderingTraderID;
   private final Set<String> serverBrands;
   private boolean wasModded;
   private final TimerCallbackManager<MinecraftServer> schedueledEvents;

   private ServerWorldInfo(@Nullable DataFixer dataFixer, int version, @Nullable CompoundNBT playerNBT, boolean wasModded, int spawnX, int spawnY, int spawnZ, long time, long dayTime, int levelStorageVersion, int clearWeatherTime, int rainTime, boolean raining, int thunderTime, boolean thundering, boolean initialized, boolean difficultyLocked, WorldBorder.Serializer borderSerializer, int wanderingTraderSpawnDelay, int wanderingTraderSpawnChance, @Nullable UUID wanderingTraderID, LinkedHashSet<String> serverBrands, TimerCallbackManager<MinecraftServer> schedueledEvents, @Nullable CompoundNBT customBossEventNBT, CompoundNBT dragonFightNBT, WorldSettings worldSettings, DimensionGeneratorSettings generatorSettings, Lifecycle lifecycle) {
      this.dataFixer = dataFixer;
      this.wasModded = wasModded;
      this.spawnX = spawnX;
      this.spawnY = spawnY;
      this.spawnZ = spawnZ;
      this.gameTime = time;
      this.dayTime = dayTime;
      this.levelStorageVersion = levelStorageVersion;
      this.clearWeatherTime = clearWeatherTime;
      this.rainTime = rainTime;
      this.raining = raining;
      this.thunderTime = thunderTime;
      this.thundering = thundering;
      this.initialized = initialized;
      this.difficultyLocked = difficultyLocked;
      this.borderSerializer = borderSerializer;
      this.wanderingTraderSpawnDelay = wanderingTraderSpawnDelay;
      this.wanderingTraderSpawnChance = wanderingTraderSpawnChance;
      this.wanderingTraderID = wanderingTraderID;
      this.serverBrands = serverBrands;
      this.loadedPlayerNBT = playerNBT;
      this.version = version;
      this.schedueledEvents = schedueledEvents;
      this.customBossEventNBT = customBossEventNBT;
      this.dragonFightNBT = dragonFightNBT;
      this.worldSettings = worldSettings;
      this.generatorSettings = generatorSettings;
      this.lifecycle = lifecycle;
   }

   public ServerWorldInfo(WorldSettings worldSettings, DimensionGeneratorSettings generatorSettings, Lifecycle lifecycle) {
      this((DataFixer)null, SharedConstants.getVersion().getWorldVersion(), (CompoundNBT)null, false, 0, 0, 0, 0L, 0L, 19133, 0, 0, false, 0, false, false, false, WorldBorder.field_235925_b_, 0, 0, (UUID)null, Sets.newLinkedHashSet(), new TimerCallbackManager<>(TimerCallbackSerializers.field_216342_a), (CompoundNBT)null, new CompoundNBT(), worldSettings.clone(), generatorSettings, lifecycle);
   }

   public static ServerWorldInfo func_237369_a_(Dynamic<INBT> dynamic, DataFixer dataFixer, int version, @Nullable CompoundNBT playerNBT, WorldSettings worldSettings, VersionData versionData, DimensionGeneratorSettings generatorSettings, Lifecycle lifecycle) {
      long i = dynamic.get("Time").asLong(0L);
      CompoundNBT compoundnbt = (CompoundNBT)dynamic.get("DragonFight").result().map(Dynamic::getValue).orElseGet(() -> {
         return dynamic.get("DimensionData").get("1").get("DragonFight").orElseEmptyMap().getValue();
      });
      return new ServerWorldInfo(dataFixer, version, playerNBT, dynamic.get("WasModded").asBoolean(false), dynamic.get("SpawnX").asInt(0), dynamic.get("SpawnY").asInt(0), dynamic.get("SpawnZ").asInt(0), i, dynamic.get("DayTime").asLong(i), versionData.func_237323_a_(), dynamic.get("clearWeatherTime").asInt(0), dynamic.get("rainTime").asInt(0), dynamic.get("raining").asBoolean(false), dynamic.get("thunderTime").asInt(0), dynamic.get("thundering").asBoolean(false), dynamic.get("initialized").asBoolean(true), dynamic.get("DifficultyLocked").asBoolean(false), WorldBorder.Serializer.func_235938_a_(dynamic, WorldBorder.field_235925_b_), dynamic.get("WanderingTraderSpawnDelay").asInt(0), dynamic.get("WanderingTraderSpawnChance").asInt(0), dynamic.get("WanderingTraderId").read(UUIDCodec.UUID_CODEC).result().orElse((UUID)null), dynamic.get("ServerBrands").asStream().flatMap((p_237368_0_) -> {
         return Util.streamOptional(p_237368_0_.asString().result());
      }).collect(Collectors.toCollection(Sets::newLinkedHashSet)), new TimerCallbackManager<>(TimerCallbackSerializers.field_216342_a, dynamic.get("ScheduledEvents").asStream()), (CompoundNBT)dynamic.get("CustomBossEvents").orElseEmptyMap().getValue(), compoundnbt, worldSettings, generatorSettings, lifecycle);
   }

   public CompoundNBT func_230411_a_(IDynamicRegistries p_230411_1_, @Nullable CompoundNBT p_230411_2_) {
      this.func_237367_I_();
      if (p_230411_2_ == null) {
         p_230411_2_ = this.loadedPlayerNBT;
      }

      CompoundNBT compoundnbt = new CompoundNBT();
      this.func_237370_a_(p_230411_1_, compoundnbt, p_230411_2_);
      return compoundnbt;
   }

   private void func_237370_a_(IDynamicRegistries p_237370_1_, CompoundNBT p_237370_2_, @Nullable CompoundNBT p_237370_3_) {
      ListNBT listnbt = new ListNBT();
      this.serverBrands.stream().map(StringNBT::valueOf).forEach(listnbt::add);
      p_237370_2_.put("ServerBrands", listnbt);
      p_237370_2_.putBoolean("WasModded", this.wasModded);
      CompoundNBT compoundnbt = new CompoundNBT();
      compoundnbt.putString("Name", SharedConstants.getVersion().getName());
      compoundnbt.putInt("Id", SharedConstants.getVersion().getWorldVersion());
      compoundnbt.putBoolean("Snapshot", !SharedConstants.getVersion().isStable());
      p_237370_2_.put("Version", compoundnbt);
      p_237370_2_.putInt("DataVersion", SharedConstants.getVersion().getWorldVersion());
      WorldGenSettingsExport<INBT> worldgensettingsexport = WorldGenSettingsExport.func_240896_a_(NBTDynamicOps.INSTANCE, p_237370_1_);
      DimensionGeneratorSettings.field_236201_a_.encodeStart(worldgensettingsexport, this.generatorSettings).resultOrPartial(Util.func_240982_a_("WorldGenSettings: ", LOGGER::error)).ifPresent((p_237373_1_) -> {
         p_237370_2_.put("WorldGenSettings", p_237373_1_);
      });
      p_237370_2_.putInt("GameType", this.worldSettings.getGameType().getID());
      p_237370_2_.putInt("SpawnX", this.spawnX);
      p_237370_2_.putInt("SpawnY", this.spawnY);
      p_237370_2_.putInt("SpawnZ", this.spawnZ);
      p_237370_2_.putLong("Time", this.gameTime);
      p_237370_2_.putLong("DayTime", this.dayTime);
      p_237370_2_.putLong("LastPlayed", Util.millisecondsSinceEpoch());
      p_237370_2_.putString("LevelName", this.worldSettings.getWorldName());
      p_237370_2_.putInt("version", 19133);
      p_237370_2_.putInt("clearWeatherTime", this.clearWeatherTime);
      p_237370_2_.putInt("rainTime", this.rainTime);
      p_237370_2_.putBoolean("raining", this.raining);
      p_237370_2_.putInt("thunderTime", this.thunderTime);
      p_237370_2_.putBoolean("thundering", this.thundering);
      p_237370_2_.putBoolean("hardcore", this.worldSettings.isHardcoreEnabled());
      p_237370_2_.putBoolean("allowCommands", this.worldSettings.isCommandsAllowed());
      p_237370_2_.putBoolean("initialized", this.initialized);
      this.borderSerializer.func_235939_a_(p_237370_2_);
      p_237370_2_.putByte("Difficulty", (byte)this.worldSettings.getDifficulty().getId());
      p_237370_2_.putBoolean("DifficultyLocked", this.difficultyLocked);
      p_237370_2_.put("GameRules", this.worldSettings.getGameRules().write());
      p_237370_2_.put("DragonFight", this.dragonFightNBT);
      if (p_237370_3_ != null) {
         p_237370_2_.put("Player", p_237370_3_);
      }

      DatapackCodec.field_234881_b_.encodeStart(NBTDynamicOps.INSTANCE, this.worldSettings.getDatapackCodec()).result().ifPresent((p_237371_1_) -> {
         p_237370_2_.put("DataPacks", p_237371_1_);
      });
      if (this.customBossEventNBT != null) {
         p_237370_2_.put("CustomBossEvents", this.customBossEventNBT);
      }

      p_237370_2_.put("ScheduledEvents", this.schedueledEvents.write());
      p_237370_2_.putInt("WanderingTraderSpawnDelay", this.wanderingTraderSpawnDelay);
      p_237370_2_.putInt("WanderingTraderSpawnChance", this.wanderingTraderSpawnChance);
      if (this.wanderingTraderID != null) {
         p_237370_2_.putUniqueId("WanderingTraderId", this.wanderingTraderID);
      }

   }

   /**
    * Returns the x spawn position
    */
   public int getSpawnX() {
      return this.spawnX;
   }

   /**
    * Return the Y axis spawning point of the player.
    */
   public int getSpawnY() {
      return this.spawnY;
   }

   /**
    * Returns the z spawn position
    */
   public int getSpawnZ() {
      return this.spawnZ;
   }

   public long getGameTime() {
      return this.gameTime;
   }

   /**
    * Get current world time
    */
   public long getDayTime() {
      return this.dayTime;
   }

   private void func_237367_I_() {
      if (!this.dataFixed && this.loadedPlayerNBT != null) {
         if (this.version < SharedConstants.getVersion().getWorldVersion()) {
            if (this.dataFixer == null) {
               throw (NullPointerException)Util.pauseDevMode(new NullPointerException("Fixer Upper not set inside LevelData, and the player tag is not upgraded."));
            }

            this.loadedPlayerNBT = NBTUtil.update(this.dataFixer, DefaultTypeReferences.PLAYER, this.loadedPlayerNBT, this.version);
         }

         this.dataFixed = true;
      }
   }

   public CompoundNBT func_230416_x_() {
      this.func_237367_I_();
      return this.loadedPlayerNBT;
   }

   /**
    * Set the x spawn position to the passed in value
    */
   public void setSpawnX(int x) {
      this.spawnX = x;
   }

   /**
    * Sets the y spawn position
    */
   public void setSpawnY(int y) {
      this.spawnY = y;
   }

   /**
    * Set the z spawn position to the passed in value
    */
   public void setSpawnZ(int z) {
      this.spawnZ = z;
   }

   public void setGameTime(long time) {
      this.gameTime = time;
   }

   /**
    * Set current world time
    */
   public void setDayTime(long time) {
      this.dayTime = time;
   }

   public void setSpawn(BlockPos spawnPoint) {
      this.spawnX = spawnPoint.getX();
      this.spawnY = spawnPoint.getY();
      this.spawnZ = spawnPoint.getZ();
   }

   /**
    * Get current world name
    */
   public String getWorldName() {
      return this.worldSettings.getWorldName();
   }

   public int func_230417_y_() {
      return this.levelStorageVersion;
   }

   public int func_230395_g_() {
      return this.clearWeatherTime;
   }

   public void func_230391_a_(int p_230391_1_) {
      this.clearWeatherTime = p_230391_1_;
   }

   /**
    * Returns true if it is thundering, false otherwise.
    */
   public boolean isThundering() {
      return this.thundering;
   }

   /**
    * Sets whether it is thundering or not.
    */
   public void setThundering(boolean thunderingIn) {
      this.thundering = thunderingIn;
   }

   /**
    * Returns the number of ticks until next thunderbolt.
    */
   public int getThunderTime() {
      return this.thunderTime;
   }

   /**
    * Defines the number of ticks until next thunderbolt.
    */
   public void setThunderTime(int time) {
      this.thunderTime = time;
   }

   /**
    * Returns true if it is raining, false otherwise.
    */
   public boolean isRaining() {
      return this.raining;
   }

   /**
    * Sets whether it is raining or not.
    */
   public void setRaining(boolean isRaining) {
      this.raining = isRaining;
   }

   /**
    * Return the number of ticks until rain.
    */
   public int getRainTime() {
      return this.rainTime;
   }

   /**
    * Sets the number of ticks until rain.
    */
   public void setRainTime(int time) {
      this.rainTime = time;
   }

   /**
    * Gets the GameType.
    */
   public GameType getGameType() {
      return this.worldSettings.getGameType();
   }

   public void setGameType(GameType type) {
      this.worldSettings = this.worldSettings.setGameType(type);
   }

   /**
    * Returns true if hardcore mode is enabled, otherwise false
    */
   public boolean isHardcore() {
      return this.worldSettings.isHardcoreEnabled();
   }

   /**
    * Returns true if commands are allowed on this World.
    */
   public boolean areCommandsAllowed() {
      return this.worldSettings.isCommandsAllowed();
   }

   /**
    * Returns true if the World is initialized.
    */
   public boolean isInitialized() {
      return this.initialized;
   }

   /**
    * Sets the initialization status of the World.
    */
   public void setInitialized(boolean initializedIn) {
      this.initialized = initializedIn;
   }

   /**
    * Gets the GameRules class Instance.
    */
   public GameRules getGameRulesInstance() {
      return this.worldSettings.getGameRules();
   }

   public WorldBorder.Serializer func_230398_q_() {
      return this.borderSerializer;
   }

   public void func_230393_a_(WorldBorder.Serializer p_230393_1_) {
      this.borderSerializer = p_230393_1_;
   }

   public Difficulty getDifficulty() {
      return this.worldSettings.getDifficulty();
   }

   public void setDifficulty(Difficulty difficulty) {
      this.worldSettings = this.worldSettings.setDifficulty(difficulty);
   }

   public boolean isDifficultyLocked() {
      return this.difficultyLocked;
   }

   public void setDifficultyLocked(boolean p_230415_1_) {
      this.difficultyLocked = p_230415_1_;
   }

   public TimerCallbackManager<MinecraftServer> getScheduledEvents() {
      return this.schedueledEvents;
   }

   /**
    * Adds this WorldInfo instance to the crash report.
    */
   public void addToCrashReport(CrashReportCategory category) {
      IServerWorldInfo.super.addToCrashReport(category);
      IServerConfiguration.super.addToCrashReport(category);
   }

   public DimensionGeneratorSettings getDimensionGeneratorSettings() {
      return this.generatorSettings;
   }

   @OnlyIn(Dist.CLIENT)
   public Lifecycle getLifecycle() {
      return this.lifecycle;
   }

   public CompoundNBT getDragonFightData() {
      return this.dragonFightNBT;
   }

   public void setDragonFightData(CompoundNBT nbt) {
      this.dragonFightNBT = nbt;
   }

   public DatapackCodec func_230403_C_() {
      return this.worldSettings.getDatapackCodec();
   }

   public void func_230410_a_(DatapackCodec p_230410_1_) {
      this.worldSettings = this.worldSettings.setDatapackCodec(p_230410_1_);
   }

   @Nullable
   public CompoundNBT getCustomBossEventData() {
      return this.customBossEventNBT;
   }

   public void setCustomBossEventData(@Nullable CompoundNBT nbt) {
      this.customBossEventNBT = nbt;
   }

   public int func_230399_u_() {
      return this.wanderingTraderSpawnDelay;
   }

   public void func_230396_g_(int p_230396_1_) {
      this.wanderingTraderSpawnDelay = p_230396_1_;
   }

   public int func_230400_v_() {
      return this.wanderingTraderSpawnChance;
   }

   public void func_230397_h_(int p_230397_1_) {
      this.wanderingTraderSpawnChance = p_230397_1_;
   }

   public void func_230394_a_(UUID p_230394_1_) {
      this.wanderingTraderID = p_230394_1_;
   }

   public void func_230412_a_(String p_230412_1_, boolean isModded) {
      this.serverBrands.add(p_230412_1_);
      this.wasModded |= isModded;
   }

   public boolean isModded() {
      return this.wasModded;
   }

   public Set<String> func_230406_F_() {
      return ImmutableSet.copyOf(this.serverBrands);
   }

   public IServerWorldInfo func_230407_G_() {
      return this;
   }

   @OnlyIn(Dist.CLIENT)
   public WorldSettings func_230408_H_() {
      return this.worldSettings.clone();
   }
}