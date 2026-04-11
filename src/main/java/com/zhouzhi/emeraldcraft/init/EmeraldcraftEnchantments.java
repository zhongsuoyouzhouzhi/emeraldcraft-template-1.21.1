package com.zhouzhi.emeraldcraft.init;

import com.zhouzhi.emeraldcraft.EmeraldCraft;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.Tags;

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

    public static final ResourceKey<Enchantment> HEAVY =
            ResourceKey.create(
                    Registries.ENCHANTMENT,
                    ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "heavy")
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
                Enchantment.dynamicCost(5,2),
                Enchantment.dynamicCost(20,4),
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
                Enchantment.dynamicCost(5,5),
                Enchantment.dynamicCost(20,5),
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
                Enchantment.dynamicCost(5,3),
                Enchantment.dynamicCost(30,1),
                4,
                EquipmentSlotGroup.HAND
        );
        Enchantment rebound_enchantment = new Enchantment(
                Component.translatable("enchantment." + EmeraldCraft.MOD_ID + ".rebound"),
                rebound_definition,
                exclusiveSet,
                effects
        );

        Enchantment.EnchantmentDefinition heavy_definition = Enchantment.definition(
                itemGetter.getOrThrow(ItemTags.SWORDS),
                itemGetter.getOrThrow(ItemTags.SWORDS),
                10,
                2,
                Enchantment.dynamicCost(5,5),
                Enchantment.dynamicCost(20,5),
                7,
                EquipmentSlotGroup.MAINHAND
        );

        Enchantment heavy_enchantment = new Enchantment(
                Component.translatable("enchantment." + EmeraldCraft.MOD_ID + ".heavy"),
                heavy_definition,
                exclusiveSet,
                effects
        );

        context.register(VOID_EMERALD_ATTACH, void_emerald_attach_enchantment);
        context.register(LIGHTING, lighting_enchantment);
        context.register(REBOUND, rebound_enchantment);
        context.register(HEAVY, heavy_enchantment);
    }

}
