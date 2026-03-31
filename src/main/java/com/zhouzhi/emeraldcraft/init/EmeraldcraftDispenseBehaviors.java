package com.zhouzhi.emeraldcraft.init;

import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.BlockSource;

import com.zhouzhi.emeraldcraft.procedures.others.RefinedEmeraldT3DLCDispenserLaunch;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber
public class EmeraldcraftDispenseBehaviors {
	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(() ->
			DispenserBlock.registerBehavior(EmeraldcraftItems.REFINED_EMERALD_T_3.get(), new DefaultDispenseItemBehavior() {
				public @NotNull ItemStack execute(@NotNull BlockSource blockSource, @NotNull ItemStack itemstack) {
					RefinedEmeraldT3DLCDispenserLaunch.execute(blockSource.level(), blockSource.pos().getX(), blockSource.pos().getY(), blockSource.pos().getZ(), blockSource.state().getValue(DispenserBlock.FACING));
					itemstack.shrink(1);
					return itemstack;
				}
			})
		);
	}
}