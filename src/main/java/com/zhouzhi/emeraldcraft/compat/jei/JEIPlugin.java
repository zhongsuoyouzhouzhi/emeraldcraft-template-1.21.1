package com.zhouzhi.emeraldcraft.compat.jei;

import com.zhouzhi.emeraldcraft.init.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModList;

import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.parse("emeraldcraft:jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        // region 在注册前检查 JEI 是否加载
        if (!ModList.get().isLoaded("jei")) return;
        // endregion
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new BlockInteractionCategoryJei(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        // region 在注册前检查 JEI 是否加载
        if (!ModList.get().isLoaded("jei")) return;
        // endregion
        var input = ModItems.REFINED_EMERALD.get().getDefaultInstance();
        var target = ModItems.REFINED_EMERALD_BLOCK.get().getDefaultInstance();
        var output = ModItems.REFINED_EMERALD_BLOCK_2.get().getDefaultInstance();
        var recipe = new BlockInteractionJeiRecipe(input, target, output);
        registration.addRecipes(JeiRecipeTypes.BLOCK_INTERACTION, List.of(recipe));

        input = ModItems.REFINED_EMERALD_T_2.get().getDefaultInstance();
        target = ModItems.REFINED_EMERALD_BLOCK_2.get().getDefaultInstance();
        output = ModItems.REFINED_EMERALD_BLOCK_3.get().getDefaultInstance();
        recipe = new BlockInteractionJeiRecipe(input, target, output);
        registration.addRecipes(JeiRecipeTypes.BLOCK_INTERACTION, List.of(recipe));
    }
}
