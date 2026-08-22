package com.zhouzhi.emeraldcraft.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class BlockInteractionEmiRecipe implements EmiRecipe {
    private final ResourceLocation id = ResourceLocation.parse("emeraldcraft:block_interaction");
    private final EmiIngredient input1;
    private final EmiIngredient input2;
    private final EmiStack output;

    public BlockInteractionEmiRecipe(EmiIngredient input1, EmiIngredient input2, EmiStack output) {
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EmeraldcraftEmiPlugin.BLOCK_INTERACTION_CATEGORY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(input1, input2);
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(output);
    }

    @Override
    public int getDisplayWidth() {
        return 100;
    }

    @Override
    public int getDisplayHeight() {
        return 50;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addSlot(input1, 5, 5);
        widgets.addSlot(input2, 5, 25);
        widgets.addSlot(output, 70, 15).recipeContext(this);
        widgets.addTexture(ResourceLocation.parse("emi:textures/gui/widgets.png"), 45, 15, 24, 17, 0, 0);
    }
}