package com.zhouzhi.emeraldcraft.init;

import com.zhouzhi.emeraldcraft.EmeraldCraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;


public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, EmeraldCraft.MOD_ID,null);
    }
    @Override
    protected void addTags(HolderLookup.@NotNull Provider lookupProvider) {
        this.tag(ModTags.EMERALD_BLOCKS)
                .add(Blocks.EMERALD_BLOCK)
                .add(ModBlocks.REFINED_EMERALD_BLOCK.get())
                .add(ModBlocks.REFINED_EMERALD_BLOCK_2.get())
                .add(ModBlocks.REFINED_EMERALD_BLOCK_3.get());
    }
}