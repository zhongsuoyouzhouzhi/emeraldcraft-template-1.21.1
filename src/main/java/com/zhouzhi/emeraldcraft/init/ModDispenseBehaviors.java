package com.zhouzhi.emeraldcraft.init;

import com.zhouzhi.emeraldcraft.procedures.others.RefinedEmeraldT3DLCDispenserLaunch;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber
public class ModDispenseBehaviors {
	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(() ->
			DispenserBlock.registerBehavior(ModItems.REFINED_EMERALD_T_3.get(), new DefaultDispenseItemBehavior() {
				public @NotNull ItemStack execute(@NotNull BlockSource blockSource, @NotNull ItemStack itemstack) {
					RefinedEmeraldT3DLCDispenserLaunch.execute(blockSource.level(), blockSource.pos().getX(), blockSource.pos().getY(), blockSource.pos().getZ(), blockSource.state().getValue(DispenserBlock.FACING));
					itemstack.shrink(1);
					return itemstack;
				}
			})
		);
	}
}