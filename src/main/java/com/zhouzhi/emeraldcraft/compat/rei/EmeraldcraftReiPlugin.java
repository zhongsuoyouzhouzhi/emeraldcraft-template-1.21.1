package com.zhouzhi.emeraldcraft.compat.rei;

import com.zhouzhi.emeraldcraft.init.ModItems;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.forge.REIPlugin;

@SuppressWarnings("deprecation")
@REIPlugin
public class EmeraldcraftReiPlugin implements REIClientPlugin {

    public static final CategoryIdentifier<BlockInteractionDisplayRei> BLOCK_INTERACTION =
            CategoryIdentifier.of("emeraldcraft", "block_interaction");

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new BlockInteractionCategoryRei());
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.add(new BlockInteractionDisplayRei(
                ModItems.REFINED_EMERALD.get().getDefaultInstance(),
                ModItems.REFINED_EMERALD_BLOCK.get().getDefaultInstance(),
                ModItems.REFINED_EMERALD_BLOCK_2.get().getDefaultInstance()
        ));

        registry.add(new BlockInteractionDisplayRei(
                ModItems.REFINED_EMERALD_T_2.get().getDefaultInstance(),
                ModItems.REFINED_EMERALD_BLOCK_2.get().getDefaultInstance(),
                ModItems.REFINED_EMERALD_BLOCK_3.get().getDefaultInstance()
        ));
    }
}