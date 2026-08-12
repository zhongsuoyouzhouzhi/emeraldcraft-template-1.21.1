package com.zhouzhi.emeraldcraft.item.oblivion_emerald;

import com.zhouzhi.emeraldcraft.init.ModItems;
import com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse;
import com.zhouzhi.emeraldcraft.procedures.compress.TagChange;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class OblivionEmeraldPickaxeItem extends PickaxeItem {
	private static final int BAR_COLOR = FastColor.ARGB32.color(0, 157, 0, 165);
	private static final Tier TOOL_TIER = new Tier() {
		@Override
		public int getUses() {
			return 50411;
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
            return Ingredient.of(new ItemStack(ModItems.VOID_EMERALD.get()));
			}
	};

	public OblivionEmeraldPickaxeItem() {
		super(TOOL_TIER, new Properties()
				.attributes(DiggerItem.createAttributes(TOOL_TIER, 6f, -2.4f))
				.component(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY)
				.fireResistant()
				.rarity(Rarity.EPIC));
	}

    @Override
	public boolean mineBlock(@ParametersAreNonnullByDefault ItemStack stack, Level level,@ParametersAreNonnullByDefault BlockState state,@ParametersAreNonnullByDefault BlockPos pos,@ParametersAreNonnullByDefault LivingEntity miningEntity) {
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
			if ((SimpleUse.isStone(state.getBlock()) || state.getBlock() == Blocks.EMERALD_ORE || state.getBlock() == Blocks.DEEPSLATE_EMERALD_ORE) && TagChange.getOrCreateComponent(stack,"Scope",false)) {
				level.getServer().execute(() -> SimpleUse.OperateBlock(
						level,
						pos.getX(),
						pos.getY(),
						pos.getZ(),
						2,
						(block) -> SimpleUse.isStone(block) || block == Blocks.EMERALD_ORE || block == Blocks.DEEPSLATE_EMERALD_ORE,
						(block,x,y,z) -> {
							BlockPos _pos = BlockPos.containing(x, y, z);
							BlockState _state = level.getBlockState(_pos);

							List<ItemStack> drops = Block.getDrops(_state , level, _pos, null, null, stack);

							for (ItemStack drop : drops) {
								Block.popResource(level, _pos, drop);
							}

							int exp = _state.getExpDrop(level, _pos, null, player, stack);
							if (exp > 0) {
								ExperienceOrb.award(level, Vec3.atCenterOf(_pos), exp);
							}

							level.removeBlock(_pos, false);

							level.playSound(null, _pos, _state.getSoundType().getBreakSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
							level.levelEvent(
									null,
									LevelEvent.PARTICLES_DESTROY_BLOCK,
									_pos,
									Block.getId(_state)
							);
						})
				);
			}
		}
	}

	@Override
	public int getBarColor(@NotNull ItemStack stack) {
		return BAR_COLOR;
	}
}