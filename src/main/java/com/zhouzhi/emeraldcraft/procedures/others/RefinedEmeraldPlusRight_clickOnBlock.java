package com.zhouzhi.emeraldcraft.procedures.others;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

import com.zhouzhi.emeraldcraft.init.EmeraldcraftItems;
import com.zhouzhi.emeraldcraft.init.EmeraldcraftBlocks;

public class RefinedEmeraldPlusRight_clickOnBlock {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == EmeraldcraftBlocks.REFINEDEMERALD_BLOCK_3.get()) {
			itemstack.shrink(1);
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack.getItem(), 150);
			if (world.isClientSide())
				Minecraft.getInstance().gameRenderer.displayItemActivation(itemstack);
			world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
			if (world instanceof Level _level && !_level.isClientSide())
				_level.explode(null, x, y, z, 512, Level.ExplosionInteraction.BLOCK);
			if (entity instanceof Player _player && !_player.level().isClientSide())
				send(1,_player);
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == EmeraldcraftBlocks.REFINEDEMERALD_BLOCK_2.get()) {
			itemstack.shrink(1);
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack.getItem(), 150);
			if (world.isClientSide())
				Minecraft.getInstance().gameRenderer.displayItemActivation(itemstack);
			world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
			if (world instanceof Level _level && !_level.isClientSide())
				_level.explode(null, x, y, z, 256, Level.ExplosionInteraction.BLOCK);
			if (entity instanceof Player _player && !_player.level().isClientSide())
				send(2,_player);
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == EmeraldcraftBlocks.REFINEDEMERALD_BLOCK.get()) {
			itemstack.shrink(1);
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack.getItem(), 150);
			if (world.isClientSide())
				Minecraft.getInstance().gameRenderer.displayItemActivation(itemstack);
			world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
			if (world instanceof Level _level && !_level.isClientSide())
				_level.explode(null, x, y, z, 128, Level.ExplosionInteraction.BLOCK);
			if (entity instanceof Player _player && !_player.level().isClientSide())
				send(3,_player);
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Blocks.EMERALD_BLOCK) {
			itemstack.shrink(1);
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack.getItem(), 150);
			if (world.isClientSide())
				Minecraft.getInstance().gameRenderer.displayItemActivation(itemstack);
			world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
			if (world instanceof Level _level && !_level.isClientSide())
				_level.explode(null, x, y, z, 96, Level.ExplosionInteraction.BLOCK);
			if (entity instanceof Player _player && !_player.level().isClientSide())
				send(4,_player);
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Blocks.BEDROCK) {
			itemstack.shrink(1);
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack.getItem(), 1800);
			if (world.isClientSide())
				Minecraft.getInstance().gameRenderer.displayItemActivation(itemstack);
			world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
			if (world instanceof Level _level && !_level.isClientSide())
				_level.explode(null, x, y, z, 1024, Level.ExplosionInteraction.BLOCK);
			if (entity instanceof Player _player && !_player.level().isClientSide()) {
				Component name1 = Component.translatable("message.emeraldcraft.warning_5_1");
				Component name2 = Component.translatable("message.emeraldcraft.warning_5_2");
				_player.displayClientMessage(Component.literal(
								(new ItemStack(EmeraldcraftItems.REFINED_EMERALD_PLUS.get()).getDisplayName().getString() + name1.getString() + new ItemStack(Blocks.BEDROCK).getDisplayName().getString() + name2.getString())),
						false);
			}
		}
	}

	private static void send(int n ,Player _player) {
		Component name = Component.translatable("message.emeraldcraft.warning_" + n);
		_player.displayClientMessage(name, false);
	}
}