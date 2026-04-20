package com.zhouzhi.emeraldcraft.init;

import com.zhouzhi.emeraldcraft.EmeraldCraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
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
        this.tag(ModTags.VOID_EMERALD_TOOLS)
                .add(ModItems.VOID_EMERALD_SWORD.get())
                .add(ModItems.VOID_EMERALD_PICKAXE.get())
                .add(ModItems.VOID_EMERALD_AXE.get())
                .add(ModItems.VOID_EMERALD_SHOVEL.get())
                .add(ModItems.VOID_EMERALD_HOE.get());
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

        this.tag(ModTags.EMERALD_TOOLS)
                .addTag(ModTags.REFINED_EMERALD_TOOLS)
                .addTag(ModTags.REFINED_EMERALD_TOOLS_T2)
                .addTag(ModTags.REFINED_EMERALD_TOOLS_T3)
                .addTag(ModTags.VOID_EMERALD_TOOLS)
                .addTag(ModTags.LAVA_EMERALD_TOOLS)
                .addTag(ModTags.SPECIAL_TOOLS)
                .addTag(ModTags.INLAID_WITH_REFINED_EMERALD)
                .addTag(ModTags.INLAID_WITH_REFINED_EMERALD_T2);
    }
}