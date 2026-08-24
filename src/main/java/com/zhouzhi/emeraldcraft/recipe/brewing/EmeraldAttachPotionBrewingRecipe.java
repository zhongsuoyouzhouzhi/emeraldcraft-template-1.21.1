package com.zhouzhi.emeraldcraft.recipe.brewing;

import com.zhouzhi.emeraldcraft.init.ModItems;
import com.zhouzhi.emeraldcraft.init.ModPotions;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

@EventBusSubscriber
public class EmeraldAttachPotionBrewingRecipe {
	@SubscribeEvent
	public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
		PotionBrewing.Builder builder = event.getBuilder();
		builder.addMix(
				Potions.AWKWARD,
				ModItems.REFINED_EMERALD_T_2.get(),
				ModPotions.EMERALD_ATTACH_POTION
		);
	}
}