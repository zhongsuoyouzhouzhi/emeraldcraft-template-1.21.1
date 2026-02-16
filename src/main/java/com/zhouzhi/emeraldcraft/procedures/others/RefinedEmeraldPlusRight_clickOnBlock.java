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
				_player.displayClientMessage(Component.literal("You are prohibited from doing that."), false);
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
				_player.displayClientMessage(Component.literal("You are not allowed to do that."), false);
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
				_player.displayClientMessage(Component.literal("Maybe we can do something else?"), false);
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
				_player.displayClientMessage(Component.literal("Perhaps you\u2019d better focus on meaningful tasks instead of trivial things like this."), false);
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Blocks.BEDROCK) {
			itemstack.shrink(1);
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack.getItem(), 1800);
			if (world.isClientSide())
				Minecraft.getInstance().gameRenderer.displayItemActivation(itemstack);
			world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
			if (world instanceof Level _level && !_level.isClientSide())
				_level.explode(null, x, y, z, 1024, Level.ExplosionInteraction.BLOCK);
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal(
						(new ItemStack(EmeraldcraftItems.REFINED_EMERALD_PLUS.get()).getDisplayName().getString() + " and " + new ItemStack(Blocks.BEDROCK).getDisplayName().getString() + " bombarded each other, causing a violent explosion.")),
						false);
		}
	}
}