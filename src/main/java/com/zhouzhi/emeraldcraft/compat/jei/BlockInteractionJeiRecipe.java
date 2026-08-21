package com.zhouzhi.emeraldcraft.compat.jei;

import net.minecraft.world.item.ItemStack;

public record BlockInteractionJeiRecipe(
        ItemStack inputItem,
        ItemStack targetBlock,
        ItemStack outputItem
) { }