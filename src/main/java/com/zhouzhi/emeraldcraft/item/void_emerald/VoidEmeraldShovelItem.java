package com.zhouzhi.emeraldcraft.item.void_emerald;

import com.zhouzhi.emeraldcraft.init.EmeraldcraftItems;
import com.zhouzhi.emeraldcraft.procedures.compress.TagChange;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse.destroyDirt;
import static com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse.isDirt;

public class VoidEmeraldShovelItem extends ShovelItem {
	private static final Tier TOOL_TIER = new Tier() {
		@Override
		public int getUses() {
			return 11243;
		}

		@Override
		public float getSpeed() {
			return 320f;
		}

		@Override
		public float getAttackDamageBonus() {
			return 1;
		}

		@Override
		@MethodsReturnNonnullByDefault
		public TagKey<Block> getIncorrectBlocksForDrops() {
			return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
		}

		@Override
		public int getEnchantmentValue() {
			return 60;
		}

		@Override
		@MethodsReturnNonnullByDefault
		public Ingredient getRepairIngredient() {
			return Ingredient.of(new ItemStack(EmeraldcraftItems.VOID_EMERALD.get()));
		}
	};

	public VoidEmeraldShovelItem() {
		super(TOOL_TIER, new Item.Properties()
				.attributes(DiggerItem.createAttributes(TOOL_TIER, 7.2f, -2f))
				.component(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY)
				.fireResistant()
				.rarity(Rarity.EPIC));
	}

    @Override
    public boolean mineBlock(@ParametersAreNonnullByDefault ItemStack stack,@ParametersAreNonnullByDefault Level level, BlockState state,@ParametersAreNonnullByDefault BlockPos pos,@ParametersAreNonnullByDefault LivingEntity miningEntity) {
        if (isDirt(state.getBlock()) && !TagChange.getOrCreateComponent(stack,"Scope",false))
            destroyDirt(level,pos.getX(),pos.getY(),pos.getZ(),1,false);
        return super.mineBlock(stack, level, state, pos, miningEntity);
    }

}