package com.zhouzhi.emeraldcraft.item.oblivion_emerald;

import com.zhouzhi.emeraldcraft.init.ModItems;
import com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse;
import com.zhouzhi.emeraldcraft.procedures.compress.TagChange;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.zhouzhi.emeraldcraft.procedures.net.Use.OblivionEmeraldToolDestroySingleBlock;

public class OblivionEmeraldShovelItem extends ShovelItem {
	private static final int BAR_COLOR_BASE = FastColor.ARGB32.color(0, 115, 0, 137);
	private static final int BAR_COLOR_SCOPE = FastColor.ARGB32.color(0, 239, 40, 204);
	private static final Tier TOOL_TIER = new Tier() {
		@Override
		public int getUses() {
			return 54213;
		}

		@Override
		public float getSpeed() {
			return 450f;
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
			return 150;
		}

		@Override
		@MethodsReturnNonnullByDefault
		public Ingredient getRepairIngredient() {
			return Ingredient.of(new ItemStack(ModItems.OBLIVION_EMERALD.get()));
		}
	};

	public OblivionEmeraldShovelItem() {
		super(TOOL_TIER, new Properties()
				.attributes(DiggerItem.createAttributes(TOOL_TIER, 9f, -2f))
				.component(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY)
				.fireResistant()
				.rarity(Rarity.EPIC));
	}

    @Override
	public boolean mineBlock(@ParametersAreNonnullByDefault ItemStack stack, Level level, @ParametersAreNonnullByDefault BlockState state, @ParametersAreNonnullByDefault BlockPos pos, @ParametersAreNonnullByDefault LivingEntity miningEntity) {
		if (level.isClientSide()) {
			return true;
		}

		if (!(miningEntity instanceof ServerPlayer player)) {
			return false;
		}
		Tool tool = stack.get(DataComponents.TOOL);
		breakBlockAt(player, pos, stack,state,tool);
		return true;
	}

	private void breakBlockAt(ServerPlayer player, BlockPos pos, ItemStack stack, BlockState state,Tool tool) {
		ServerLevel level = player.serverLevel();
		if (state.getDestroySpeed(level, pos) != 0.0F && tool.damagePerBlock() > 0) {
			stack.hurtAndBreak(tool.damagePerBlock(), player, EquipmentSlot.MAINHAND);
			if (SimpleUse.isDirt(state.getBlock()) && TagChange.getOrCreateComponent(stack, "Scope", false)) {
				level.getServer().execute(() -> SimpleUse.OperateBlock(
						level,
						pos.getX(),
						pos.getY(),
						pos.getZ(),
						2,
						SimpleUse::isDirt,
						(block, x, y, z) -> {
							BlockPos _pos = BlockPos.containing(x, y, z);
							if (_pos.equals(pos)) return;
							BlockState _state = level.getBlockState(_pos);
							OblivionEmeraldToolDestroySingleBlock(level, _pos, _state, stack, player);
						})
				);
			}
		}
	}

	@Override
	public int getBarColor(@NotNull ItemStack stack) {
		return stack.getDamageValue() <= 1000 ? BAR_COLOR_BASE:BAR_COLOR_SCOPE;
	}
}