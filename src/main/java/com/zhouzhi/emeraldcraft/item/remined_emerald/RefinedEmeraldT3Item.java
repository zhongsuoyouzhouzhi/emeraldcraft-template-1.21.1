package com.zhouzhi.emeraldcraft.item.remined_emerald;

import com.zhouzhi.emeraldcraft.init.ModBlocks;
import com.zhouzhi.emeraldcraft.init.ModItems;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

public class RefinedEmeraldT3Item extends Item {
	public RefinedEmeraldT3Item() {
		super(new Item.Properties().fireResistant().rarity(Rarity.RARE));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(@ParametersAreNonnullByDefault ItemStack itemstack) {
		return true;
	}

	@Override
	@MethodsReturnNonnullByDefault
	public InteractionResult useOn(@ParametersAreNonnullByDefault UseOnContext context) {
		super.useOn(context);
		Level world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		double x = pos.getX();
		double y = pos.getY();
		double z = pos.getZ();
		ItemStack itemstack = context.getItemInHand();
		Player player = context.getPlayer();
		if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == ModBlocks.REFINED_EMERALD_BLOCK_3.get()) {
			itemstack.shrink(1);
			world.setBlock(BlockPos.containing(x, y, z), net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 3);
			if (player != null) {
				player.getCooldowns().addCooldown(itemstack.getItem(), 150);
				if (!world.isClientSide) {
					world.explode(null, x, y, z, 384, Level.ExplosionInteraction.BLOCK);
					send(1, player);
				}
			}
            if (world.isClientSide())
				Minecraft.getInstance().gameRenderer.displayItemActivation(itemstack);
        } else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == ModBlocks.REFINED_EMERALD_BLOCK_2.get()) {
			itemstack.shrink(1);
			if (player instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack.getItem(), 150);
			if (world.isClientSide())
				Minecraft.getInstance().gameRenderer.displayItemActivation(itemstack);
			world.setBlock(BlockPos.containing(x, y, z), net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 3);
			if (world instanceof Level _level && !_level.isClientSide())
				_level.explode(null, x, y, z, 192, Level.ExplosionInteraction.BLOCK);
			if (player instanceof Player _player && !_player.level().isClientSide())
				send(2, _player);
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == ModBlocks.REFINED_EMERALD_BLOCK.get()) {
			itemstack.shrink(1);
			if (player instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack.getItem(), 150);
			if (world.isClientSide())
				Minecraft.getInstance().gameRenderer.displayItemActivation(itemstack);
			world.setBlock(BlockPos.containing(x, y, z), net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 3);
			if (world instanceof Level _level && !_level.isClientSide())
				_level.explode(null, x, y, z, 96, Level.ExplosionInteraction.BLOCK);
			if (player instanceof Player _player && !_player.level().isClientSide())
				send(3, _player);
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == net.minecraft.world.level.block.Blocks.EMERALD_BLOCK) {
			itemstack.shrink(1);
			if (player instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack.getItem(), 150);
			if (world.isClientSide())
				Minecraft.getInstance().gameRenderer.displayItemActivation(itemstack);
			world.setBlock(BlockPos.containing(x, y, z), net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 3);
			if (world instanceof Level _level && !_level.isClientSide())
				_level.explode(null, x, y, z, 32, Level.ExplosionInteraction.BLOCK);
			if (player instanceof Player _player && !_player.level().isClientSide())
				send(4, _player);
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == net.minecraft.world.level.block.Blocks.BEDROCK) {
			itemstack.shrink(1);
			if (player instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack.getItem(), 1600);
			if (world.isClientSide())
				Minecraft.getInstance().gameRenderer.displayItemActivation(itemstack);
			world.setBlock(BlockPos.containing(x, y, z), net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 3);
			if (world instanceof Level _level && !_level.isClientSide())
				_level.explode(null, x, y, z, 768, Level.ExplosionInteraction.BLOCK);
			if (player instanceof Player _player && !_player.level().isClientSide()) {
				Component name1 = Component.translatable("message.emeraldcraft.warning_5_1");
				Component name2 = Component.translatable("message.emeraldcraft.warning_5_2");
				_player.displayClientMessage(Component.literal(
								(new ItemStack(ModItems.REFINED_EMERALD_T_3.get()).getDisplayName().getString() + name1.getString() + new ItemStack(net.minecraft.world.level.block.Blocks.BEDROCK).getDisplayName().getString() + name2.getString())),
						false);
			}
			world.setBlock(BlockPos.containing(x, y, z), net.minecraft.world.level.block.Blocks.BEDROCK.defaultBlockState(), 3);
			if (!(player instanceof ServerPlayer _plr58 && _plr58.level() instanceof ServerLevel && _plr58.getAdvancements().getOrStartProgress(Objects.requireNonNull(_plr58.server.getAdvancements().get(ResourceLocation.parse("emeraldcraft:ultimate_boom")))).isDone())) {
				if (player instanceof ServerPlayer _player) {
					AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("emeraldcraft:ultimate_boom"));
					if (_adv != null) {
						AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
						if (!_ap.isDone()) {
							for (String criteria : _ap.getRemainingCriteria())
								_player.getAdvancements().award(_adv, criteria);
						}
					}
				}
			}
		}
		return InteractionResult.SUCCESS;
	}
	private static void send(int n ,Player _player) {
		Component name = Component.translatable("message.emeraldcraft.warning_" + n);
		_player.displayClientMessage(name, false);
	}
}