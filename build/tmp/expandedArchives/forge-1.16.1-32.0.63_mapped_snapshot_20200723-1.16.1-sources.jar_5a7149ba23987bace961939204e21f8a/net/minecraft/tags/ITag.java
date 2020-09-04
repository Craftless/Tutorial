package net.minecraft.tags;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public interface ITag<T> {
   static <T> Codec<ITag<T>> getTagCodec(Supplier<TagCollection<T>> p_232947_0_) {
      return ResourceLocation.RESOURCE_LOCATION_CODEC.flatXmap((p_232949_1_) -> {
         return Optional.ofNullable(p_232947_0_.get().get(p_232949_1_)).map(DataResult::success).orElseGet(() -> {
            return DataResult.error("Unknown tag: " + p_232949_1_);
         });
      }, (p_232948_1_) -> {
         return Optional.ofNullable(p_232947_0_.get().func_232973_a_(p_232948_1_)).map(DataResult::success).orElseGet(() -> {
            return DataResult.error("Unknown tag: " + p_232948_1_);
         });
      });
   }

   boolean contains(T element);

   List<T> getAllElements();

   default T getRandomElement(Random random) {
      List<T> list = this.getAllElements();
      return list.get(random.nextInt(list.size()));
   }

   static <T> ITag<T> getTagOf(Set<T> elements) {
      return Tag.func_241286_a_(elements);
   }

   public static class Builder {
      private final List<ITag.Proxy> proxyTags = Lists.newArrayList();
      private boolean replace = false;

      public static ITag.Builder create() {
         return new ITag.Builder();
      }

      public ITag.Builder addProxyTag(ITag.Proxy proxyTag) {
         this.proxyTags.add(proxyTag);
         return this;
      }

      public ITag.Builder addTag(ITag.ITagEntry tagEntry, String identifier) {
         return this.addProxyTag(new ITag.Proxy(tagEntry, identifier));
      }

      public ITag.Builder addItemEntry(ResourceLocation registryName, String identifier) {
         return this.addTag(new ITag.ItemEntry(registryName), identifier);
      }

      public ITag.Builder addTagEntry(ResourceLocation tag, String identifier) {
         return this.addTag(new ITag.TagEntry(tag), identifier);
      }

      public ITag.Builder replace(boolean value) {
         this.replace = value;
         return this;
      }

      public ITag.Builder replace() {
         return replace(true);
      }

      public <T> Optional<ITag<T>> func_232959_a_(Function<ResourceLocation, ITag<T>> p_232959_1_, Function<ResourceLocation, T> p_232959_2_) {
         ImmutableSet.Builder<T> builder = ImmutableSet.builder();

         for(ITag.Proxy itag$proxy : this.proxyTags) {
            if (!itag$proxy.getEntry().matches(p_232959_1_, p_232959_2_, builder::add)) {
               return Optional.empty();
            }
         }

         return Optional.of(ITag.getTagOf(builder.build()));
      }

      public Stream<ITag.Proxy> getProxyStream() {
         return this.proxyTags.stream();
      }

      public <T> Stream<ITag.Proxy> func_232963_b_(Function<ResourceLocation, ITag<T>> p_232963_1_, Function<ResourceLocation, T> p_232963_2_) {
         return this.getProxyStream().filter((p_232960_2_) -> {
            return !p_232960_2_.getEntry().matches(p_232963_1_, p_232963_2_, (p_232957_0_) -> {
            });
         });
      }

      public ITag.Builder deserialize(JsonObject json, String identifier) {
         JsonArray jsonarray = JSONUtils.getJsonArray(json, "values");
         List<ITag.ITagEntry> list = Lists.newArrayList();

         for(JsonElement jsonelement : jsonarray) {
            String s = JSONUtils.getString(jsonelement, "value");
            if (s.startsWith("#")) {
               list.add(new ITag.TagEntry(new ResourceLocation(s.substring(1))));
            } else {
               list.add(new ITag.ItemEntry(new ResourceLocation(s)));
            }
         }

         if (JSONUtils.getBoolean(json, "replace", false)) {
            this.proxyTags.clear();
         }

         net.minecraftforge.common.ForgeHooks.deserializeTagAdditions(list, json, proxyTags);
         list.forEach((p_232958_2_) -> {
            this.proxyTags.add(new ITag.Proxy(p_232958_2_, identifier));
         });
         return this;
      }

      public JsonObject serialize() {
         JsonObject jsonobject = new JsonObject();
         JsonArray jsonarray = new JsonArray();

         for(ITag.Proxy itag$proxy : this.proxyTags) {
            if(!(itag$proxy.entry instanceof net.minecraftforge.common.data.IOptionalTagEntry))
            itag$proxy.getEntry().addAdditionalData(jsonarray);
         }

         JsonArray optopnals = new JsonArray();
          getProxyStream()
                 .map(e -> e.entry)
                 .filter(e -> e instanceof net.minecraftforge.common.data.IOptionalTagEntry)
                 .forEach(e -> e.addAdditionalData(optopnals));

         jsonobject.addProperty("replace", replace);
         jsonobject.add("values", jsonarray);
         if (optopnals.size() > 0)
             jsonobject.add("optional", optopnals);
         return jsonobject;
      }
   }

   public interface INamedTag<T> extends ITag<T> {
      ResourceLocation getName();
   }

   public interface ITagEntry {
      <T> boolean matches(Function<ResourceLocation, ITag<T>> resourceTagFunction, Function<ResourceLocation, T> resourceElementFunction, Consumer<T> elementConsumer);

      void addAdditionalData(JsonArray jsonArray);
   }

   public static class ItemEntry implements ITag.ITagEntry {
      private final ResourceLocation identifier;

      public ItemEntry(ResourceLocation identifier) {
         this.identifier = identifier;
      }

      public <T> boolean matches(Function<ResourceLocation, ITag<T>> resourceTagFunction, Function<ResourceLocation, T> resourceElementFunction, Consumer<T> elementConsumer) {
         T t = resourceElementFunction.apply(this.identifier);
         if (t == null) {
            return false;
         } else {
            elementConsumer.accept(t);
            return true;
         }
      }

      public void addAdditionalData(JsonArray jsonArray) {
         jsonArray.add(this.identifier.toString());
      }

      public String toString() {
         return this.identifier.toString();
      }
      @Override public boolean equals(Object o) { return o == this || (o instanceof ITag.ItemEntry && java.util.Objects.equals(this.identifier, ((ITag.ItemEntry) o).identifier)); }
   }

   public static class Proxy {
      private final ITag.ITagEntry entry;
      private final String identifier;

      private Proxy(ITag.ITagEntry entry, String identifier) {
         this.entry = entry;
         this.identifier = identifier;
      }

      public ITag.ITagEntry getEntry() {
         return this.entry;
      }

      public String toString() {
         return this.entry.toString() + " (from " + this.identifier + ")";
      }
   }

   public static class TagEntry implements ITag.ITagEntry {
      private final ResourceLocation id;

      public TagEntry(ResourceLocation resourceLocationIn) {
         this.id = resourceLocationIn;
      }

      public <T> boolean matches(Function<ResourceLocation, ITag<T>> resourceTagFunction, Function<ResourceLocation, T> resourceElementFunction, Consumer<T> elementConsumer) {
         ITag<T> itag = resourceTagFunction.apply(this.id);
         if (itag == null) {
            return false;
         } else {
            itag.getAllElements().forEach(elementConsumer);
            return true;
         }
      }

      public void addAdditionalData(JsonArray jsonArray) {
         jsonArray.add("#" + this.id);
      }

      public String toString() {
         return "#" + this.id;
      }
      @Override public boolean equals(Object o) { return o == this || (o instanceof ITag.TagEntry && java.util.Objects.equals(this.id, ((ITag.TagEntry) o).id)); }
   }
}