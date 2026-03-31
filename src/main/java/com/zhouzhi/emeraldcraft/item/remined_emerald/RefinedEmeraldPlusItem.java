package com.zhouzhi.emeraldcraft.item.remined_emerald;

import com.zhouzhi.emeraldcraft.procedures.others.RefinedEmeraldPlusHitLivingThings;
import com.zhouzhi.emeraldcraft.procedures.others.RefinedEmeraldPlusPlayerFinishedEating;
import com.zhouzhi.emeraldcraft.procedures.others.RefinedEmeraldPlusRight_clickOnBlock;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

public class RefinedEmeraldPlusItem extends Item {
	public RefinedEmeraldPlusItem() {
		super(new Item.Properties().fireResistant().rarity(Rarity.EPIC).food((new FoodProperties.Builder()).nutrition(500).saturationModifier(500f).alwaysEdible().build()));
	}

	@Override
	public int getUseDuration(@ParametersAreNonnullByDefault ItemStack itemstack, @ParametersAreNonnullByDefault LivingEntity livingEntity) {
		return 40;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(@ParametersAreNonnullByDefault ItemStack itemstack) {
		return true;
	}

	@Override
	@MethodsReturnNonnullByDefault
	public ItemStack finishUsingItem(@ParametersAreNonnullByDefault ItemStack itemstack, @ParametersAreNonnullByDefault Level world, @ParametersAreNonnullByDefault LivingEntity entity) {
		ItemStack item = super.finishUsingItem(itemstack, world, entity);
		RefinedEmeraldPlusPlayerFinishedEating.execute(entity);
		return item;
	}

	@Override
	@MethodsReturnNonnullByDefault
	public InteractionResult useOn(@ParametersAreNonnullByDefault UseOnContext context) {
		super.useOn(context);
		RefinedEmeraldPlusRight_clickOnBlock.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getPlayer(), context.getItemInHand());
		return InteractionResult.SUCCESS;
	}

	@Override
	public boolean hurtEnemy(@ParametersAreNonnullByDefault ItemStack itemstack, @ParametersAreNonnullByDefault LivingEntity entity, @ParametersAreNonnullByDefault LivingEntity sourceEntity) {
		boolean r = super.hurtEnemy(itemstack, entity, sourceEntity);
		RefinedEmeraldPlusHitLivingThings.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity, sourceEntity);
		return r;
	}
}
