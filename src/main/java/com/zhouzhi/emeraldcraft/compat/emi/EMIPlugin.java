package com.zhouzhi.emeraldcraft.compat.emi;

import com.zhouzhi.emeraldcraft.init.ModItems;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.resources.ResourceLocation;

@EmiEntrypoint
public class EMIPlugin implements EmiPlugin {

    public static final EmiRecipeCategory BLOCK_INTERACTION_CATEGORY =
            new EmiRecipeCategory(ResourceLocation.parse("emeraldcraft:block_interaction"),
                    EmiStack.of(ModItems.REFINED_EMERALD_BLOCK.get()));

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(BLOCK_INTERACTION_CATEGORY);

        registry.addRecipe(new BlockInteractionEmiRecipe(
                EmiStack.of(ModItems.REFINED_EMERALD.get()),
                EmiStack.of(ModItems.REFINED_EMERALD_BLOCK.get()),
                EmiStack.of(ModItems.REFINED_EMERALD_BLOCK_2.get())
        ));

        registry.addRecipe(new BlockInteractionEmiRecipe(
                EmiStack.of(ModItems.REFINED_EMERALD_T_2.get()),
                EmiStack.of(ModItems.REFINED_EMERALD_BLOCK_2.get()),
                EmiStack.of(ModItems.REFINED_EMERALD_BLOCK_3.get())
        ));
    }
}