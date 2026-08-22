package com.zhouzhi.emeraldcraft.compat.emi;

import com.zhouzhi.emeraldcraft.init.ModItems;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.resources.ResourceLocation;

@EmiEntrypoint
public class EmeraldcraftEmiPlugin implements EmiPlugin {

    public static final EmiRecipeCategory BLOCK_INTERACTION_CATEGORY =
            new EmiRecipeCategory(ResourceLocation.parse("emeraldcraft:block_interaction"),
                    EmiStack.of(ModItems.REFINED_EMERALD_BLOCK.get()));

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(BLOCK_INTERACTION_CATEGORY);

        BlockInteractionEmiRecipe REFINED_EMERALD_BLOCK_2_recipe = new BlockInteractionEmiRecipe(
                EmiStack.of(ModItems.REFINED_EMERALD.get()),
                EmiStack.of(ModItems.REFINED_EMERALD_BLOCK.get()),
                EmiStack.of(ModItems.REFINED_EMERALD_BLOCK_2.get())
        );
        registry.addRecipe(REFINED_EMERALD_BLOCK_2_recipe);

        BlockInteractionEmiRecipe REFINED_EMERALD_BLOCK_3_recipe = new BlockInteractionEmiRecipe(
                EmiStack.of(ModItems.REFINED_EMERALD_T_2.get()),
                EmiStack.of(ModItems.REFINED_EMERALD_BLOCK_2.get()),
                EmiStack.of(ModItems.REFINED_EMERALD_BLOCK_3.get())
        );
        registry.addRecipe(REFINED_EMERALD_BLOCK_3_recipe);
    }
}