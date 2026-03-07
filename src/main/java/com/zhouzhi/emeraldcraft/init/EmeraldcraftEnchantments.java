package com.zhouzhi.emeraldcraft.init;

import com.zhouzhi.emeraldcraft.EmeraldCraft;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class EmeraldcraftEnchantments {
    public static final ResourceKey<Enchantment> VOID_EMERALD_ATTACH =
            ResourceKey.create(
                    Registries.ENCHANTMENT,
                    ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "void_emerald_attach")
            );

    public static final ResourceKey<Enchantment> LIGHTING =
            ResourceKey.create(
                    Registries.ENCHANTMENT,
                    ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "lighting")
            );

    public static final ResourceKey<Enchantment> REBOUND =
            ResourceKey.create(
                    Registries.ENCHANTMENT,
                    ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "rebound")
            );

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> itemGetter = context.lookup(Registries.ITEM);
        HolderSet<Enchantment> exclusiveSet = HolderSet.empty();
        DataComponentMap effects = DataComponentMap.EMPTY;

        Enchantment.EnchantmentDefinition void_emerald_attach_definition = Enchantment.definition(
                itemGetter.getOrThrow(ItemTags.SWORDS),
                itemGetter.getOrThrow(ItemTags.SWORDS),
                4,
                5,
                Enchantment.constantCost(5),
                Enchantment.constantCost(30),
                6,
                EquipmentSlotGroup.MAINHAND
        );
        Enchantment void_emerald_attach_enchantment = new Enchantment(
                Component.translatable("enchantment." + EmeraldCraft.MOD_ID + ".void_emerald_attach"),
                void_emerald_attach_definition,
                exclusiveSet,
                effects
        );


        Enchantment.EnchantmentDefinition lighting_definition = Enchantment.definition(
                itemGetter.getOrThrow(ItemTags.SWORDS),
                itemGetter.getOrThrow(ItemTags.SWORDS),
                10,
                2,
                Enchantment.constantCost(10),
                Enchantment.constantCost(25),
                2,
                EquipmentSlotGroup.MAINHAND
        );
        Enchantment lighting_enchantment = new Enchantment(
                Component.translatable("enchantment." + EmeraldCraft.MOD_ID + ".lighting"),
                lighting_definition,
                exclusiveSet,
                effects
        );

        Enchantment.EnchantmentDefinition rebound_definition = Enchantment.definition(
                itemGetter.getOrThrow(Tags.Items.TOOLS_SHIELD),
                itemGetter.getOrThrow(Tags.Items.TOOLS_SHIELD),
                15,
                6,
                Enchantment.constantCost(5),
                Enchantment.constantCost(30),
                4,
                EquipmentSlotGroup.HAND
        );
        Enchantment rebound_enchantment = new Enchantment(
                Component.translatable("enchantment." + EmeraldCraft.MOD_ID + ".rebound"),
                rebound_definition,
                exclusiveSet,
                effects
        );

        context.register(VOID_EMERALD_ATTACH, void_emerald_attach_enchantment);
        context.register(LIGHTING, lighting_enchantment);
        context.register(REBOUND, rebound_enchantment);
    }

}
