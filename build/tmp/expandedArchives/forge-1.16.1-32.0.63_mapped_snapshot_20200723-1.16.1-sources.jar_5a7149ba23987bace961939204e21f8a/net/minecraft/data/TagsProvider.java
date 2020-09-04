package net.minecraft.data;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.tags.ITag;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class TagsProvider<T> implements IDataProvider {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
   protected final DataGenerator generator;
   protected final Registry<T> registry;
   protected final Map<ResourceLocation, ITag.Builder> tagToBuilder = Maps.newLinkedHashMap();
   protected String modId;

   @Deprecated//Forge, Use ModID version.
   protected TagsProvider(DataGenerator generatorIn, Registry<T> registryIn) {
       this(generatorIn, registryIn, "vanilla");
   }
   protected TagsProvider(DataGenerator generatorIn, Registry<T> registryIn, String modId) {
      this.generator = generatorIn;
      this.registry = registryIn;
      this.modId = modId;
   }

   protected abstract void registerTags();

   /**
    * Performs this provider's action.
    */
   public void act(DirectoryCache cache) {
      this.tagToBuilder.clear();
      this.registerTags();
      ITag<T> itag = Tag.func_241284_a_();
      Function<ResourceLocation, ITag<T>> function = (p_240523_2_) -> {
         return this.tagToBuilder.containsKey(p_240523_2_) ? itag : null;
      };
      Function<ResourceLocation, T> function1 = (p_240527_1_) -> {
         return this.registry.getValue(p_240527_1_).orElse((T)null);
      };
      this.tagToBuilder.forEach((p_240524_4_, p_240524_5_) -> {
         List<ITag.Proxy> list = p_240524_5_.func_232963_b_(function, function1).collect(Collectors.toList());
         if (!list.isEmpty()) {
            throw new IllegalArgumentException(String.format("Couldn't define tag %s as it is missing following references: %s", p_240524_4_, list.stream().map(Objects::toString).collect(Collectors.joining(","))));
         } else {
            JsonObject jsonobject = p_240524_5_.serialize();
            Path path = this.makePath(p_240524_4_);
            if (path == null) return; //Forge: Allow running this data provider without writing it. Recipe provider needs valid tags.

            try {
               String s = GSON.toJson((JsonElement)jsonobject);
               String s1 = HASH_FUNCTION.hashUnencodedChars(s).toString();
               if (!Objects.equals(cache.getPreviousHash(path), s1) || !Files.exists(path)) {
                  Files.createDirectories(path.getParent());

                  try (BufferedWriter bufferedwriter = Files.newBufferedWriter(path)) {
                     bufferedwriter.write(s);
                  }
               }

               cache.recordHash(path, s1);
            } catch (IOException ioexception) {
               LOGGER.error("Couldn't save tags to {}", path, ioexception);
            }

         }
      });
   }

   /**
    * Resolves a Path for the location to save the given tag.
    */
   protected abstract Path makePath(ResourceLocation id);

   protected TagsProvider.Builder<T> getOrCreateBuilder(ITag.INamedTag<T> tag) {
      ITag.Builder itag$builder = this.createBuilderIfAbsent(tag);
      return new TagsProvider.Builder<>(itag$builder, this.registry, modId);
   }

   protected ITag.Builder createBuilderIfAbsent(ITag.INamedTag<T> tag) {
      return this.tagToBuilder.computeIfAbsent(tag.getName(), (p_240526_0_) -> {
         return new ITag.Builder();
      });
   }

   public static class Builder<T> implements net.minecraftforge.common.extensions.IForgeTagBuilder<T> {
      private final ITag.Builder builder;
      private final Registry<T> registry;
      private final String id;

      private Builder(ITag.Builder builder, Registry<T> registry, String id) {
         this.builder = builder;
         this.registry = registry;
         this.id = id;
      }

      public TagsProvider.Builder<T> addItemEntry(T item) {
         this.builder.addItemEntry(this.registry.getKey(item), this.id);
         return this;
      }

      public TagsProvider.Builder<T> addTag(ITag.INamedTag<T> tag) {
         this.builder.addTagEntry(tag.getName(), this.id);
         return this;
      }

      @SafeVarargs
      public final TagsProvider.Builder<T> add(T... toAdd) {
         Stream.<T>of(toAdd).map(this.registry::getKey).forEach((p_240533_1_) -> {
            this.builder.addItemEntry(p_240533_1_, this.id);
         });
         return this;
      }

      public TagsProvider.Builder<T> add(ITag.ITagEntry tag) {
          builder.addTag(tag, id);
          return this;
      }

      public ITag.Builder getInternalBuilder() {
          return builder;
      }

      public String getModID() {
          return id;
      }
   }
}