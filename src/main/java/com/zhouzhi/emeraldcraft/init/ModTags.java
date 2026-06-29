package com.zhouzhi.emeraldcraft.init;

import com.zhouzhi.emeraldcraft.EmeraldCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static final TagKey<Item> LAVA_EMERALD_TOOLS = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "lava_emerald_tools")
    );
    public static final TagKey<Item> VOID_EMERALD_TOOLS = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "void_emerald_tools")
    );
    public static final TagKey<Item> REFINED_EMERALD_TOOLS = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "refined_emerald_tools")
    );
    public static final TagKey<Item> REFINED_EMERALD_TOOLS_T2 = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "refined_emerald_tools_t2")
    );
    public static final TagKey<Item> REFINED_EMERALD_TOOLS_T3 = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "refined_emerald_tools_t3")
    );
    public static final TagKey<Item> SPECIAL_TOOLS = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "special_tools")
    );
    public static final TagKey<Item> INLAID_WITH_REFINED_EMERALD = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "inlaid_with_refined_emerald")
    );
    public static final TagKey<Item> INLAID_WITH_REFINED_EMERALD_T2 = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "inlaid_with_refined_emerald_t2")
    );
    public static final TagKey<Item> EMERALD_TOOLS = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "emerald_tools")
    );

    public static final TagKey<Block> EMERALD_BLOCKS = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "emerald_blocks")
    );

    public static final TagKey<Item> REFINED_EMERALD_ARMOR = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "refined_emerald_armor")
    );

    public static final TagKey<Item> REFINED_EMERALD_ARMOR_T2 = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "refined_emerald_armor_t2")
    );

    public static final TagKey<Item> REFINED_EMERALD_ARMOR_T3 = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "refined_emerald_armor_t3")
    );

    public static final TagKey<Item> VOID_EMERALD_ARMOR = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "void_emerald_armor")
    );

    public static final TagKey<Item> EMERALD_ARMOR = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "emerald_armor")
    );
}
