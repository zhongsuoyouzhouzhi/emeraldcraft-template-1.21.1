package com.zhouzhi.emeraldcraft.compat.jei;

import mezz.jei.api.recipe.RecipeType;

public class JeiRecipeTypes {
    public static final RecipeType<BlockInteractionJeiRecipe> BLOCK_INTERACTION =
            RecipeType.create("emeraldcraft", "block_interaction", BlockInteractionJeiRecipe.class);
}