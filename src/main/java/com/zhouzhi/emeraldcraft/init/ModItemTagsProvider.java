package com.zhouzhi.emeraldcraft.init;

import com.zhouzhi.emeraldcraft.EmeraldCraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;


public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<BlockTagsProvider> blockTagsProvider) {
        super(output, lookupProvider, blockTagsProvider.thenApply(BlockTagsProvider::contentsGetter).join(), EmeraldCraft.MOD_ID, null);
    }
    @Override
    protected void addTags(HolderLookup.@NotNull Provider lookupProvider) {
        this.tag(ModTags.LAVA_EMERALD_TOOLS)
                .add(ModItems.LAVA_EMERALD_SWORD.get())
                .add(ModItems.LAVA_EMERALD_PICKAXE.get())
                .add(ModItems.LAVA_EMERALD_AXE.get())
                .add(ModItems.LAVA_EMERALD_SHOVEL.get())
                .add(ModItems.LAVA_EMERALD_HOE.get());
        this.tag(ModTags.LAVA_EMERALD_T2_TOOLS)
                .add(ModItems.LAVA_EMERALD_SWORD_T2.get())
                .add(ModItems.LAVA_EMERALD_PICKAXE_T2.get())
                .add(ModItems.LAVA_EMERALD_AXE_T2.get())
                .add(ModItems.LAVA_EMERALD_SHOVEL_T2.get())
                .add(ModItems.LAVA_EMERALD_HOE_T2.get());
        this.tag(ModTags.VOID_EMERALD_TOOLS)
                .add(ModItems.VOID_EMERALD_SWORD.get())
                .add(ModItems.VOID_EMERALD_PICKAXE.get())
                .add(ModItems.VOID_EMERALD_AXE.get())
                .add(ModItems.VOID_EMERALD_SHOVEL.get())
                .add(ModItems.VOID_EMERALD_HOE.get())
                .add(ModItems.VOID_EMERALD_SHIELD.get());
        this.tag(ModTags.REFINED_EMERALD_TOOLS)
                .add(ModItems.EMERALD_SWORD.get())
                .add(ModItems.EMERALD_PICKAXE.get())
                .add(ModItems.EMERALD_AXE.get())
                .add(ModItems.EMERALD_SHOVEL.get())
                .add(ModItems.EMERALD_HOE.get());
        this.tag(ModTags.REFINED_EMERALD_TOOLS_T2)
                .add(ModItems.EMERALD_SWORD_T_2.get())
                .add(ModItems.EMERALD_PICKAXE_T_2.get())
                .add(ModItems.EMERALD_AXE_T_2.get())
                .add(ModItems.EMERALD_SHOVEL_T_2.get())
                .add(ModItems.EMERALD_HOE_T_2.get());
        this.tag(ModTags.REFINED_EMERALD_TOOLS_T3)
                .add(ModItems.EMERALD_SWORD_T_3.get())
                .add(ModItems.EMERALD_PICKAXE_T_3.get())
                .add(ModItems.EMERALD_AXE_T_3.get())
                .add(ModItems.EMERALD_SHOVEL_T_3.get())
                .add(ModItems.EMERALD_HOE_T_3.get());
        this.tag(ModTags.SPECIAL_TOOLS)
                .add(ModItems.SKYFILLING_BLADE.get());
        this.tag(ModTags.INLAID_WITH_REFINED_EMERALD)
                .add(ModItems.IRON_SWORD_INLAID_WITH_REFINED_EMERALD.get())
                .add(ModItems.IRON_AXE_INLAID_WITH_REFINED_EMERALD.get())
                .add(ModItems.IRON_PICKAXE_INLAID_WITH_REFINED_EMERALD.get())
                .add(ModItems.IRON_SHOVEL_INLAID_WITH_REFINED_EMERALD.get())
                .add(ModItems.IRON_HOE_INLAID_WITH_REFINED_EMERALD.get());
        this.tag(ModTags.INLAID_WITH_REFINED_EMERALD_T2)
                .add(ModItems.IRON_SWORD_INLAID_WITH_REFINED_EMERALD_T2.get())
                .add(ModItems.IRON_AXE_INLAID_WITH_REFINED_EMERALD_T2.get())
                .add(ModItems.IRON_PICKAXE_INLAID_WITH_REFINED_EMERALD_T2.get())
                .add(ModItems.IRON_SHOVEL_INLAID_WITH_REFINED_EMERALD_T2.get())
                .add(ModItems.IRON_HOE_INLAID_WITH_REFINED_EMERALD_T2.get());
        this.tag(ModTags.GENESIS_EMERALD_TOOLS)
                .add(ModItems.GENESIS_EMERALD_SWORD.get())
                .add(ModItems.GENESIS_EMERALD_PICKAXE.get())
                .add(ModItems.GENESIS_EMERALD_AXE.get())
                .add(ModItems.GENESIS_EMERALD_SHOVEL.get())
                /*.add(ModItems.GENESIS_EMERALD_HOE.get())*/;
        this.tag(ModTags.INFERNO_EMERALD_TOOLS)
                .add(ModItems.INFERNO_EMERALD_SWORD.get())
                .add(ModItems.INFERNO_EMERALD_PICKAXE.get())
                .add(ModItems.INFERNO_EMERALD_AXE.get())
                .add(ModItems.INFERNO_EMERALD_SHOVEL.get())
                /*.add(ModItems.INFERNO_EMERALD_HOE.get())*/;
        this.tag(ModTags.OBLIVION_EMERALD_TOOLS)
                .add(ModItems.OBLIVION_EMERALD_SWORD.get())
                .add(ModItems.OBLIVION_EMERALD_PICKAXE.get())
                .add(ModItems.OBLIVION_EMERALD_AXE.get())
                .add(ModItems.OBLIVION_EMERALD_SHOVEL.get())
                /*.add(ModItems.OBLIVION_EMERALD_HOE.get())*/;

        this.tag(ModTags.EMERALD_TOOLS)
                .addTag(ModTags.REFINED_EMERALD_TOOLS)
                .addTag(ModTags.REFINED_EMERALD_TOOLS_T2)
                .addTag(ModTags.REFINED_EMERALD_TOOLS_T3)
                .addTag(ModTags.VOID_EMERALD_TOOLS)
                .addTag(ModTags.LAVA_EMERALD_TOOLS)
                .addTag(ModTags.LAVA_EMERALD_T2_TOOLS)
                .addTag(ModTags.SPECIAL_TOOLS)
                .addTag(ModTags.INLAID_WITH_REFINED_EMERALD)
                .addTag(ModTags.INLAID_WITH_REFINED_EMERALD_T2)
                .addTag(ModTags.GENESIS_EMERALD_TOOLS)
                .addTag(ModTags.INFERNO_EMERALD_TOOLS)
                .addTag(ModTags.OBLIVION_EMERALD_TOOLS);

        this.tag(ModTags.REFINED_EMERALD_ARMOR)
                .add(ModItems.EMERALD_ARMOR_HELMET.get())
                .add(ModItems.EMERALD_ARMOR_CHESTPLATE.get())
                .add(ModItems.EMERALD_ARMOR_LEGGINGS.get())
                .add(ModItems.EMERALD_ARMOR_BOOTS.get());

        this.tag(ModTags.REFINED_EMERALD_ARMOR_T2)
                .add(ModItems.EMERALD_ARMOR_T_2_HELMET.get())
                .add(ModItems.EMERALD_ARMOR_T_2_CHESTPLATE.get())
                .add(ModItems.EMERALD_ARMOR_T_2_LEGGINGS.get())
                .add(ModItems.EMERALD_ARMOR_T_2_BOOTS.get());

        this.tag(ModTags.REFINED_EMERALD_ARMOR_T3)
                .add(ModItems.EMERALD_ARMOR_T_3_HELMET.get())
                .add(ModItems.EMERALD_ARMOR_T_3_CHESTPLATE.get())
                .add(ModItems.EMERALD_ARMOR_T_3_LEGGINGS.get())
                .add(ModItems.EMERALD_ARMOR_T_3_BOOTS.get());

        this.tag(ModTags.VOID_EMERALD_ARMOR)
                .add(ModItems.VOID_EMERALD_ARMOR_HELMET.get())
                .add(ModItems.VOID_EMERALD_ARMOR_CHESTPLATE.get())
                .add(ModItems.VOID_EMERALD_ARMOR_LEGGINGS.get())
                .add(ModItems.VOID_EMERALD_ARMOR_BOOTS.get());

        this.tag(ModTags.EMERALD_ARMOR)
                .addTag(ModTags.REFINED_EMERALD_ARMOR)
                .addTag(ModTags.REFINED_EMERALD_ARMOR_T2)
                .addTag(ModTags.REFINED_EMERALD_ARMOR_T3)
                .addTag(ModTags.VOID_EMERALD_ARMOR);

        this.tag(Tags.Items.TOOLS_SHIELD)
                .add(ModItems.VOID_EMERALD_SHIELD.get());
    }
}