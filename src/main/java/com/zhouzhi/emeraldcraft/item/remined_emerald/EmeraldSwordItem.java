package com.zhouzhi.emeraldcraft.item.remined_emerald;

import com.zhouzhi.emeraldcraft.init.ModItems;
import com.zhouzhi.emeraldcraft.procedures.net.Use;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

public class EmeraldSwordItem extends SwordItem {
	private static final Tier TOOL_TIER = new Tier() {
		@Override
		public int getUses() {
			return 1415;
		}

		@Override
		public float getSpeed() {
			return 12f;
		}

		@Override
		public float getAttackDamageBonus() {
			return 0;
		}

		@Override
		@MethodsReturnNonnullByDefault
		public TagKey<Block> getIncorrectBlocksForDrops() {
			return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
		}

		@Override
		public int getEnchantmentValue() {
			return 12;
		}

		@Override
		@MethodsReturnNonnullByDefault
		public Ingredient getRepairIngredient() {
			return Ingredient.of(new ItemStack(ModItems.REFINED_EMERALD.get()));
		}
	};

    public EmeraldSwordItem() {
        super(TOOL_TIER, new Item.Properties()
                .attributes(SwordItem.createAttributes(TOOL_TIER, 7f, -2.4f))
				.rarity(Rarity.UNCOMMON)
        );
    }

    @Override
    public boolean hurtEnemy(@ParametersAreNonnullByDefault ItemStack itemstack, @ParametersAreNonnullByDefault LivingEntity entity, @ParametersAreNonnullByDefault LivingEntity sourceEntity) {
        boolean r = super.hurtEnemy(itemstack, entity, sourceEntity);
        Use.EmeraldSwordHitLivingThings(entity);
        return r;
    }

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(@ParametersAreNonnullByDefault ItemStack itemstack) {
		return true;
	}
}