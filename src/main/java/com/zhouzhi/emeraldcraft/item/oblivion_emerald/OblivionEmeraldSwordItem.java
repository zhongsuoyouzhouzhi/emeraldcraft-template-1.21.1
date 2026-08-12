package com.zhouzhi.emeraldcraft.item.oblivion_emerald;

import com.zhouzhi.emeraldcraft.init.ModItems;
import com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse;
import com.zhouzhi.emeraldcraft.procedures.net.Use;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.FastColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.zhouzhi.emeraldcraft.procedures.net.Use.killEntity;

public class OblivionEmeraldSwordItem extends SwordItem {
	private static final int BAR_COLOR = FastColor.ARGB32.color(0, 91, 1, 117);
	private static final Tier TOOL_TIER = new Tier() {
		@Override
		public int getUses() {
			return 48990;
		}

		@Override
		public float getSpeed() {
			return 200f;
		}

		@Override
		public float getAttackDamageBonus() {
			return 9f;
		}

		@Override
		@MethodsReturnNonnullByDefault
		public TagKey<Block> getIncorrectBlocksForDrops() {
			return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
		}

		@Override
		public int getEnchantmentValue() {
			return 150;
		}

		@Override
		@MethodsReturnNonnullByDefault
		public Ingredient getRepairIngredient() {
			return Ingredient.of(new ItemStack(ModItems.OBLIVION_EMERALD.get()));
		}
	};


	public OblivionEmeraldSwordItem() {
		super(TOOL_TIER,
				new Properties()
						.attributes(SwordItem.createAttributes(TOOL_TIER, 15f, -2.0f))
						.component(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY)
						.fireResistant()
						.rarity(Rarity.EPIC)
			);
	}

	@Override
	public boolean hurtEnemy(@ParametersAreNonnullByDefault ItemStack itemstack, @ParametersAreNonnullByDefault LivingEntity entity,@ParametersAreNonnullByDefault LivingEntity sourceEntity) {
		boolean r = super.hurtEnemy(itemstack, entity, sourceEntity);
        if (entity.getHealth() <= entity.getMaxHealth() * 0.2f) {
			entity.setSilent(true);
			killEntity(entity,sourceEntity);
		}
		return r;
	}

	@Override
	@MethodsReturnNonnullByDefault
	public InteractionResultHolder<ItemStack> use(@ParametersAreNonnullByDefault Level world,@ParametersAreNonnullByDefault Player entity,@ParametersAreNonnullByDefault InteractionHand hand) {
		InteractionResultHolder<ItemStack> itemStackInteractionResultHolder = super.use(world, entity, hand);
        entity.getCooldowns().addCooldown(this, 80);
		ItemStack itemStack = entity.getItemInHand(hand);
		if (Use.OblivionEmeraldSwordRight_click(entity) != 0) {
			if (!SimpleUse.GameTypeGetter.isCreativeOrSpectator(entity) && entity.level() instanceof ServerLevel serverLevel) {
				itemStack.hurtAndBreak(250, serverLevel, entity, item -> {
				});
			}
		}
		return itemStackInteractionResultHolder;
	}

	@Override
	public int getBarColor(@NotNull ItemStack stack) {
		return BAR_COLOR;
	}
}
