package com.zhouzhi.emeraldcraft.item.remined_emerald;

import com.zhouzhi.emeraldcraft.init.EmeraldcraftItems;
import com.zhouzhi.emeraldcraft.procedures.others.RefinedEmeraldT2ToolIsBeingDamagedPerTick;
import com.zhouzhi.emeraldcraft.procedures.others.EmeraldSwordT2ItemHasBeenSynthesis_or_Smelted;
import com.zhouzhi.emeraldcraft.procedures.net.Use;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

public class EmeraldSwordT2Item extends SwordItem {
	private static final Tier TOOL_TIER = new Tier() {
		@Override
		public int getUses() {
			return 2420;
		}

		@Override
		public float getSpeed() {
			return 20f;
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
			return 15;
		}

		@Override
		@MethodsReturnNonnullByDefault
		public Ingredient getRepairIngredient() {
			return Ingredient.of(new ItemStack(EmeraldcraftItems.REFINED_EMERALD_T_2.get()));
		}
	};

	public EmeraldSwordT2Item() {
		super(TOOL_TIER, new Item.Properties().attributes(SwordItem.createAttributes(TOOL_TIER, 9f, -2.4f)));
	}

    @Override
    public boolean hurtEnemy(@ParametersAreNonnullByDefault ItemStack itemstack, @ParametersAreNonnullByDefault LivingEntity entity, @ParametersAreNonnullByDefault LivingEntity sourceEntity) {
        boolean r = super.hurtEnemy(itemstack, entity, sourceEntity);
        Use.EmeraldSwordT2HitLivingThings(entity);
        return r;
    }

	@Override
	public void onCraftedBy(@ParametersAreNonnullByDefault ItemStack itemstack, @ParametersAreNonnullByDefault Level world, @ParametersAreNonnullByDefault Player entity) {
		super.onCraftedBy(itemstack, world, entity);
		EmeraldSwordT2ItemHasBeenSynthesis_or_Smelted.execute(entity);
	}

	@Override
	public void inventoryTick(@ParametersAreNonnullByDefault ItemStack itemstack, @ParametersAreNonnullByDefault Level world, @ParametersAreNonnullByDefault Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		if (selected)
			RefinedEmeraldT2ToolIsBeingDamagedPerTick.execute(world, entity, itemstack);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(@ParametersAreNonnullByDefault ItemStack itemstack) {
		return true;
	}
}