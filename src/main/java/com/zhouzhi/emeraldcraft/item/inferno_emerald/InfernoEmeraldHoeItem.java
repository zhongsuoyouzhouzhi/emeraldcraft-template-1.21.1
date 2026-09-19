package com.zhouzhi.emeraldcraft.item.inferno_emerald;

import com.zhouzhi.emeraldcraft.init.ModItems;
import com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.FastColor;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

public class InfernoEmeraldHoeItem extends HoeItem {
	private static final int BAR_COLOR = FastColor.ARGB32.color(0, 255, 200, 0);
	private static final Tier TIER = new Tier() {
		@Override
		public int getUses() {
			return 47644;
		}

		@Override
		public float getSpeed() {
			return 65f;
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
			return 80;
		}

		@Override
		public @NotNull Ingredient getRepairIngredient() {
			return Ingredient.of(new ItemStack(ModItems.INFERNO_EMERALD.get()));
		}
	};

	public InfernoEmeraldHoeItem() {
		super(TIER, new Properties()
				.attributes(DiggerItem.createAttributes(TIER, 0f, 0f))
				.component(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY)
				.fireResistant()
				.rarity(Rarity.EPIC));
	}
	@Override
	@MethodsReturnNonnullByDefault
	public InteractionResult useOn(@ParametersAreNonnullByDefault UseOnContext context) {
		var result = super.useOn(context);
		if (result == InteractionResult.PASS) {
			var level = context.getLevel();
			var pos = context.getClickedPos();
			var state = level.getBlockState(pos);
			if (state.is(Blocks.CAULDRON)) {
				level.setBlockAndUpdate(pos, Blocks.LAVA_CAULDRON.defaultBlockState());
			} else if (state.is(Blocks.SNOW)) {
				level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			} else if (level.getBlockState(pos.above()).is(BlockTags.AIR) && (state.is(Blocks.FARMLAND) || state.is(Blocks.SOUL_SAND))) {
				Vec3 aimCenter = new Vec3(0.1, 0.5, 0);
				if (!state.is(Blocks.SOUL_SAND)) {
					level.setBlockAndUpdate(pos, Blocks.SOUL_SAND.defaultBlockState());
					if (level instanceof ServerLevel serverLevel) {
					Vec3 center = new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
						SimpleUse.Effect.round_plane(serverLevel, ParticleTypes.SMALL_FLAME, center, 0.5, 30, aimCenter, 0.2, true);
					}
				}
				pos = pos.above();
				level.setBlockAndUpdate(pos, Blocks.NETHER_WART.defaultBlockState().setValue(NetherWartBlock.AGE, NetherWartBlock.MAX_AGE));
				if (level instanceof ServerLevel serverLevel) {
					Vec3 center = new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
					SimpleUse.Effect.round_plane(serverLevel, ParticleTypes.SMALL_FLAME, center, 0.5, 30, aimCenter, 0.2, true);
				}

			} else if (state.is(Blocks.NETHER_WART) && state.getValue(NetherWartBlock.AGE) < NetherWartBlock.MAX_AGE) {
				level.setBlockAndUpdate(pos, Blocks.NETHER_WART.defaultBlockState().setValue(NetherWartBlock.AGE, NetherWartBlock.MAX_AGE));
				if (level instanceof ServerLevel serverLevel) {
					Vec3 aimCenter = new Vec3(0.1, 0.5, 0);
					Vec3 center = new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
					SimpleUse.Effect.round_plane(serverLevel, ParticleTypes.SMALL_FLAME, center, 0.5, 30, aimCenter, 0.2, true);
				}
			} else return result;
			Player player = context.getPlayer();
			level.playSound(player, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
			if (!level.isClientSide) {
				if (player != null) {
					context.getItemInHand().hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
				}
			}
			return InteractionResult.SUCCESS;
		}
		return result;
	}

	@Override
	public int getBarColor(@NotNull ItemStack stack) {
		return BAR_COLOR;
	}
}