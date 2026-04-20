package com.zhouzhi.emeraldcraft.recipe.brewing;

import com.zhouzhi.emeraldcraft.init.ModItems;
import com.zhouzhi.emeraldcraft.init.ModPotions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.brewing.IBrewingRecipe;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber
public class EmeraldAttachPotionBrewingRecipe implements IBrewingRecipe {
	@SubscribeEvent
	public static void init(RegisterBrewingRecipesEvent event) {
		event.getBuilder().addRecipe(new EmeraldAttachPotionBrewingRecipe());
	}

	@Override
	public boolean isInput(@NotNull ItemStack input) {
		return Ingredient.of(new ItemStack(net.minecraft.world.item.Items.POTION)).test(input);
	}

	@Override
	public boolean isIngredient(@NotNull ItemStack ingredient) {
		return Ingredient.of(new ItemStack(ModItems.REFINED_EMERALD.get())).test(ingredient);
	}

	@Override
	public @NotNull ItemStack getOutput(@NotNull ItemStack input, @NotNull ItemStack ingredient) {
		if (isInput(input) && isIngredient(ingredient)) {
			return PotionContents.createItemStack(net.minecraft.world.item.Items.POTION, ModPotions.EMERALD_ATTACH_POTION);
		}
		return ItemStack.EMPTY;
	}
}