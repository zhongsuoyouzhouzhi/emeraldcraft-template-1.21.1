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

public class OblivionEmeraldAxeItem extends AxeItem {
	private static final int BAR_COLOR_BASE = FastColor.ARGB32.color(0, 249, 93, 255);
	private static final int BAR_COLOR_SCOPE = FastColor.ARGB32.color(0, 255, 155, 255);
	private static final Tier TOOL_TIER = new Tier() {
		@Override
		public int getUses() {
			return 47251;
		}

		@Override
		public float getSpeed() {
			return 500f;
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

	public OblivionEmeraldAxeItem() {
		super(TOOL_TIER, new Properties()
				.attributes(DiggerItem.createAttributes(TOOL_TIER, 18f, -3f))
				.component(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY)
				.fireResistant()
				.rarity(Rarity.EPIC));
	}

    @Override
    public boolean mineBlock(@ParametersAreNonnullByDefault ItemStack stack,@ParametersAreNonnullByDefault Level level, @ParametersAreNonnullByDefault BlockState state, @ParametersAreNonnullByDefault BlockPos pos, @ParametersAreNonnullByDefault LivingEntity miningEntity) {
		if (level.isClientSide()) {
			return false;
		}
		if (!(miningEntity instanceof ServerPlayer player)) {
			return false;
		}
		Tool tool = stack.get(DataComponents.TOOL);
		if (tool != null) {
			breakBlockAt(player, pos, stack, state, tool);
		}
		return true;
	}

	private void breakBlockAt(ServerPlayer player, BlockPos pos, ItemStack stack, BlockState state, Tool tool) {
		ServerLevel level = player.serverLevel();
		if (state.getDestroySpeed(level, pos) <= 0.0F) {
			return;
		}
		if (tool.damagePerBlock() > 0) {
			stack.hurtAndBreak(tool.damagePerBlock(), player, EquipmentSlot.MAINHAND);
		}
		OblivionEmeraldToolDestroySingleBlock(level, pos, state, stack, player);
		if (SimpleUse.isLog(state.getBlock()) && TagChange.getOrCreateComponent(stack, "Scope", false)) {
			level.getServer().execute(()-> SimpleUse.OperateBlock(
					level,
					pos.getX(),
					pos.getY(),
					pos.getZ(),
					2,
					SimpleUse::isLog,
					(block, x, y, z) -> {
						BlockPos _pos = BlockPos.containing(x, y, z);
						if (_pos.equals(pos)) return;
						BlockState _state = level.getBlockState(_pos);
						OblivionEmeraldToolDestroySingleBlock(level, _pos, _state, stack, player);
					}
			));
		}
	}

	@Override
	public int getBarColor(@NotNull ItemStack stack) {
		return stack.getDamageValue() <= 1000 ? BAR_COLOR_BASE:BAR_COLOR_SCOPE;
	}
}