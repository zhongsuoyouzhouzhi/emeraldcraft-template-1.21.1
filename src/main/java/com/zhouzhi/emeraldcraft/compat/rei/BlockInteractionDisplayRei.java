package com.zhouzhi.emeraldcraft.compat.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class BlockInteractionDisplayRei implements Display {
    private final List<EntryIngredient> inputs;
    private final List<EntryIngredient> outputs;

    public BlockInteractionDisplayRei(ItemStack input, ItemStack target, ItemStack output) {
        this.inputs = List.of(
                EntryIngredient.of(EntryStacks.of(input)),
                EntryIngredient.of(EntryStacks.of(target))
        );
        this.outputs = List.of(
                EntryIngredient.of(EntryStacks.of(output))
        );
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return inputs;
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return outputs;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return EmeraldcraftReiPlugin.BLOCK_INTERACTION;
    }
}