package com.zhouzhi.emeraldcraft.listening;

import com.zhouzhi.emeraldcraft.item.lava_emerald.LavaEmeraldAxeItem;
import com.zhouzhi.emeraldcraft.item.lava_emerald.LavaEmeraldHoeItem;
import com.zhouzhi.emeraldcraft.item.lava_emerald.LavaEmeraldPickaxeItem;
import com.zhouzhi.emeraldcraft.item.lava_emerald.LavaEmeraldShovelItem;
import com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

public class MiningListening {
    @SubscribeEvent
    public void LavaEmeraldToolMining(BlockEvent.BreakEvent event){
        Player entity = event.getPlayer();
        if (SimpleUse.getEntityGameType(entity) != GameType.SURVIVAL) {
            return;
        }
        Level level = entity.getCommandSenderWorld();
        if (level.isClientSide()) {
            return;
        }
        ItemStack weapon = entity.getMainHandItem();
        BlockPos pos = event.getPos();
        BlockState state = event.getState();
        int type = 0;
        if (weapon.getItem() instanceof LavaEmeraldAxeItem)
            type = 1;
        else if (weapon.getItem() instanceof LavaEmeraldPickaxeItem)
            type = 2;
        else if (weapon.getItem() instanceof LavaEmeraldShovelItem)
            type = 3;
        else if (weapon.getItem() instanceof LavaEmeraldHoeItem)
            type = 4;
        if (type != 0) {
            if (level instanceof ServerLevel serverLevel) {
                ItemStack item = SimpleUse.getSmeltedResult(serverLevel, state.getBlock().asItem().getDefaultInstance());
                if (item.isEmpty()) {
                    return;
                }
                switch (type) {
                    case 1:
                        if (!state.is(BlockTags.MINEABLE_WITH_AXE)) return;
                        break;
                    case 2:
                        if (!state.is(BlockTags.MINEABLE_WITH_PICKAXE)) return;
                        break;
                    case 3:
                        if (!state.is(BlockTags.MINEABLE_WITH_SHOVEL)) return;
                        break;
                    case 4:
                        if (!state.is(BlockTags.MINEABLE_WITH_HOE)) return;
                        break;
                }
                event.setCanceled(true);
                level.destroyBlock(event.getPos(), false, entity);
                weapon.hurtAndBreak(1, serverLevel, entity, a -> {
                });
                double x = pos.getX() + 0.5;
                double y = pos.getY() + 0.5;
                double z = pos.getZ() + 0.5;
                ItemEntity itemEntity = new ItemEntity(serverLevel, x, y, z, item);
                itemEntity.setDefaultPickUpDelay();
                serverLevel.addFreshEntity(itemEntity);
                serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, x, y, z, 24, 0.2, 0.2, 0.2, 0.05);
            }
        }
    }
}
