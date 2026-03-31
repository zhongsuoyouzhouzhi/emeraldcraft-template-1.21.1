package com.zhouzhi.emeraldcraft.item.void_emerald;

import com.zhouzhi.emeraldcraft.init.EmeraldcraftItems;
import com.zhouzhi.emeraldcraft.procedures.net.Use;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.ParametersAreNonnullByDefault;

public class VoidEmeraldSwordItem extends SwordItem {
	private static final Tier TOOL_TIER = new Tier() {
		@Override
		public int getUses() {
			return 16492;
		}

		@Override
		public float getSpeed() {
			return 120f;
		}

		@Override
		public float getAttackDamageBonus() {
			return 5f;
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


	public VoidEmeraldSwordItem() {
		super(TOOL_TIER,
				new Item.Properties()
						.attributes(SwordItem.createAttributes(TOOL_TIER, 4f, -2.0f))
						.component(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY)
						.fireResistant()
			);
	}

	@Override
	public boolean hurtEnemy(@ParametersAreNonnullByDefault ItemStack itemstack, @ParametersAreNonnullByDefault LivingEntity entity,@ParametersAreNonnullByDefault LivingEntity sourceEntity) {
		boolean r = super.hurtEnemy(itemstack, entity, sourceEntity);
        Use.VoidEmeraldSwordHitLivingThings(itemstack, entity, sourceEntity);
		return r;
	}

	@Override
	@MethodsReturnNonnullByDefault
	public InteractionResultHolder<ItemStack> use(@ParametersAreNonnullByDefault Level world,@ParametersAreNonnullByDefault Player entity,@ParametersAreNonnullByDefault InteractionHand hand) {
		InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        Use.VoidEmeraldSwordRight_clickOnAir(ar.getObject(),entity,world);
		return ar;
	}
}
