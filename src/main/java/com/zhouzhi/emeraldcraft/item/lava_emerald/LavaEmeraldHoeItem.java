package com.zhouzhi.emeraldcraft.item.lava_emerald;

import com.zhouzhi.emeraldcraft.init.EmeraldcraftItems;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

public class LavaEmeraldHoeItem extends HoeItem {
	private static final Tier TIER = new Tier() {
		@Override
		public int getUses() {
			return 1521;
		}

		@Override
		public float getSpeed() {
			return 17f;
		}

		@Override
		public float getAttackDamageBonus() {
			return 0;
		}

		@Override
		public @NotNull TagKey<Block> getIncorrectBlocksForDrops() {
			return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
		}

		@Override
		public int getEnchantmentValue() {
			return 20;
		}

		@Override
		public @NotNull Ingredient getRepairIngredient() {
			return Ingredient.of(new ItemStack(EmeraldcraftItems.LAVA_EMERALD.get()));
		}
	};

	public LavaEmeraldHoeItem() {
		super(TIER, new Properties()
				.attributes(DiggerItem.createAttributes(TIER, 0f, 0f))
				.component(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY)
				.fireResistant()
				.rarity(Rarity.EPIC));
	}
	@Override
	@MethodsReturnNonnullByDefault
	public InteractionResult useOn(@ParametersAreNonnullByDefault UseOnContext context) {
		super.useOn(context);
		if (!context.getLevel().isClientSide) {
			if (context.getLevel().getBlockState(context.getClickedPos()) == Blocks.CAULDRON.defaultBlockState()) {
				context.getLevel().setBlockAndUpdate(context.getClickedPos(), Blocks.LAVA_CAULDRON.defaultBlockState());
			}
		}
		return InteractionResult.SUCCESS;
	}
}