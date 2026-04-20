package com.zhouzhi.emeraldcraft.init;

import com.zhouzhi.emeraldcraft.EmeraldCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber
public class ModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EmeraldCraft.MOD_ID);
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EMERALDCRAFT = REGISTRY.register("emeraldcraft",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.emeraldcraft.emeraldcraft")).icon(() -> new ItemStack(ModItems.REFINED_EMERALD_T_3.get())).displayItems((parameters, tabData) -> {
				tabData.accept(ModItems.REFINED_EMERALD.get());
				tabData.accept(ModBlocks.REFINED_EMERALD_BLOCK.get().asItem());
				tabData.accept(ModItems.EMERALD_SWORD.get());
				tabData.accept(ModItems.EMERALD_AXE.get());
				tabData.accept(ModItems.EMERALD_PICKAXE.get());
				tabData.accept(ModItems.EMERALD_SHOVEL.get());
				tabData.accept(ModItems.EMERALD_HOE.get());
				tabData.accept(ModItems.EMERALD_ARMOR_HELMET.get());
				tabData.accept(ModItems.EMERALD_ARMOR_CHESTPLATE.get());
				tabData.accept(ModItems.EMERALD_ARMOR_LEGGINGS.get());
				tabData.accept(ModItems.EMERALD_ARMOR_BOOTS.get());
                tabData.accept(ModItems.REFINED_EMERALD_T_2.get());
                tabData.accept(ModBlocks.REFINED_EMERALD_BLOCK_2.get().asItem());
                tabData.accept(ModItems.EMERALD_SWORD_T_2.get());
                tabData.accept(ModItems.EMERALD_AXE_T_2.get());
                tabData.accept(ModItems.EMERALD_PICKAXE_T_2.get());
                tabData.accept(ModItems.EMERALD_SHOVEL_T_2.get());
                tabData.accept(ModItems.EMERALD_HOE_T_2.get());
                tabData.accept(ModItems.EMERALD_ARMOR_T_2_HELMET.get());
                tabData.accept(ModItems.EMERALD_ARMOR_T_2_CHESTPLATE.get());
                tabData.accept(ModItems.EMERALD_ARMOR_T_2_LEGGINGS.get());
                tabData.accept(ModItems.EMERALD_ARMOR_T_2_BOOTS.get());
                tabData.accept(ModItems.REFINED_EMERALD_T_3.get());
                tabData.accept(ModBlocks.REFINED_EMERALD_BLOCK_3.get().asItem());
                tabData.accept(ModItems.EMERALD_SWORD_T_3.get());
                tabData.accept(ModItems.EMERALD_AXE_T_3.get());
                tabData.accept(ModItems.EMERALD_PICKAXE_T_3.get());
                tabData.accept(ModItems.EMERALD_SHOVEL_T_3.get());
                tabData.accept(ModItems.EMERALD_HOE_T_3.get());
                tabData.accept(ModItems.EMERALD_ARMOR_T_3_HELMET.get());
                tabData.accept(ModItems.EMERALD_ARMOR_T_3_CHESTPLATE.get());
                tabData.accept(ModItems.EMERALD_ARMOR_T_3_LEGGINGS.get());
                tabData.accept(ModItems.EMERALD_ARMOR_T_3_BOOTS.get());
                tabData.accept(ModItems.REFINED_EMERALD_PLUS.get());
                tabData.accept(ModItems.SKYFILLING_BLADE.get());
                tabData.accept(ModItems.IRON_SWORD_INLAID_WITH_REFINED_EMERALD.get());
                tabData.accept(ModItems.IRON_AXE_INLAID_WITH_REFINED_EMERALD.get());
                tabData.accept(ModItems.IRON_PICKAXE_INLAID_WITH_REFINED_EMERALD.get());
                tabData.accept(ModItems.IRON_SHOVEL_INLAID_WITH_REFINED_EMERALD.get());
                tabData.accept(ModItems.IRON_HOE_INLAID_WITH_REFINED_EMERALD.get());
                tabData.accept(ModItems.IRON_SWORD_INLAID_WITH_REFINED_EMERALD_T2.get());
                tabData.accept(ModItems.IRON_AXE_INLAID_WITH_REFINED_EMERALD_T2.get());
                tabData.accept(ModItems.IRON_PICKAXE_INLAID_WITH_REFINED_EMERALD_T2.get());
                tabData.accept(ModItems.IRON_SHOVEL_INLAID_WITH_REFINED_EMERALD_T2.get());
                tabData.accept(ModItems.IRON_HOE_INLAID_WITH_REFINED_EMERALD_T2.get());
                tabData.accept(ModItems.VOID_EMERALD.get());
                tabData.accept(ModItems.VOID_EMERALD_SWORD.get());
                tabData.accept(ModItems.VOID_EMERALD_AXE.get());
                tabData.accept(ModItems.VOID_EMERALD_PICKAXE.get());
                tabData.accept(ModItems.VOID_EMERALD_SHOVEL.get());
                tabData.accept(ModItems.VOID_EMERALD_HOE.get());
                tabData.accept(ModItems.LAVA_EMERALD.get());
                tabData.accept(ModItems.LAVA_EMERALD_SWORD.get());
                tabData.accept(ModItems.LAVA_EMERALD_AXE.get());
                tabData.accept(ModItems.LAVA_EMERALD_PICKAXE.get());
                tabData.accept(ModItems.LAVA_EMERALD_SHOVEL.get());
                tabData.accept(ModItems.LAVA_EMERALD_HOE.get());
			}).withSearchBar().build());

	@SubscribeEvent
	public static void buildTabContentsVanilla(BuildCreativeModeTabContentsEvent tabData) {
		if (tabData.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			tabData.accept(ModItems.EMERALD_AXE.get());
			tabData.accept(ModItems.EMERALD_PICKAXE.get());
			tabData.accept(ModItems.EMERALD_SHOVEL.get());
			tabData.accept(ModItems.EMERALD_HOE.get());
            tabData.accept(ModItems.EMERALD_AXE_T_2.get());
            tabData.accept(ModItems.EMERALD_PICKAXE_T_2.get());
            tabData.accept(ModItems.EMERALD_SHOVEL_T_2.get());
            tabData.accept(ModItems.EMERALD_HOE_T_2.get());
            tabData.accept(ModItems.EMERALD_AXE_T_3.get());
            tabData.accept(ModItems.EMERALD_PICKAXE_T_3.get());
            tabData.accept(ModItems.EMERALD_SHOVEL_T_3.get());
            tabData.accept(ModItems.EMERALD_HOE_T_3.get());
            tabData.accept(ModItems.IRON_AXE_INLAID_WITH_REFINED_EMERALD.get());
            tabData.accept(ModItems.IRON_PICKAXE_INLAID_WITH_REFINED_EMERALD.get());
            tabData.accept(ModItems.IRON_SHOVEL_INLAID_WITH_REFINED_EMERALD.get());
            tabData.accept(ModItems.IRON_HOE_INLAID_WITH_REFINED_EMERALD.get());
            tabData.accept(ModItems.IRON_AXE_INLAID_WITH_REFINED_EMERALD_T2.get());
            tabData.accept(ModItems.IRON_PICKAXE_INLAID_WITH_REFINED_EMERALD_T2.get());
            tabData.accept(ModItems.IRON_SHOVEL_INLAID_WITH_REFINED_EMERALD_T2.get());
            tabData.accept(ModItems.IRON_HOE_INLAID_WITH_REFINED_EMERALD_T2.get());
            tabData.accept(ModItems.VOID_EMERALD_AXE.get());
            tabData.accept(ModItems.VOID_EMERALD_PICKAXE.get());
            tabData.accept(ModItems.VOID_EMERALD_SHOVEL.get());
            tabData.accept(ModItems.VOID_EMERALD_HOE.get());
            tabData.accept(ModItems.LAVA_EMERALD_AXE.get());
            tabData.accept(ModItems.LAVA_EMERALD_PICKAXE.get());
            tabData.accept(ModItems.LAVA_EMERALD_SHOVEL.get());
            tabData.accept(ModItems.LAVA_EMERALD_HOE.get());
		} else if (tabData.getTabKey() == CreativeModeTabs.COMBAT) {
			tabData.accept(ModItems.EMERALD_SWORD.get());
            tabData.accept(ModItems.EMERALD_AXE.get());
			tabData.accept(ModItems.EMERALD_ARMOR_HELMET.get());
			tabData.accept(ModItems.EMERALD_ARMOR_CHESTPLATE.get());
			tabData.accept(ModItems.EMERALD_ARMOR_LEGGINGS.get());
			tabData.accept(ModItems.EMERALD_ARMOR_BOOTS.get());
            tabData.accept(ModItems.EMERALD_SWORD_T_2.get());
            tabData.accept(ModItems.EMERALD_AXE_T_2.get());
			tabData.accept(ModItems.EMERALD_ARMOR_T_2_HELMET.get());
			tabData.accept(ModItems.EMERALD_ARMOR_T_2_CHESTPLATE.get());
			tabData.accept(ModItems.EMERALD_ARMOR_T_2_LEGGINGS.get());
			tabData.accept(ModItems.EMERALD_ARMOR_T_2_BOOTS.get());
            tabData.accept(ModItems.EMERALD_SWORD_T_3.get());
            tabData.accept(ModItems.EMERALD_AXE_T_3.get());
            tabData.accept(ModItems.EMERALD_ARMOR_T_3_HELMET.get());
            tabData.accept(ModItems.EMERALD_ARMOR_T_3_CHESTPLATE.get());
            tabData.accept(ModItems.EMERALD_ARMOR_T_3_LEGGINGS.get());
            tabData.accept(ModItems.EMERALD_ARMOR_T_3_BOOTS.get());
            tabData.accept(ModItems.SKYFILLING_BLADE.get());
            tabData.accept(ModItems.IRON_SWORD_INLAID_WITH_REFINED_EMERALD.get());
            tabData.accept(ModItems.IRON_AXE_INLAID_WITH_REFINED_EMERALD.get());
            tabData.accept(ModItems.IRON_SWORD_INLAID_WITH_REFINED_EMERALD_T2.get());
            tabData.accept(ModItems.IRON_AXE_INLAID_WITH_REFINED_EMERALD_T2.get());
            tabData.accept(ModItems.VOID_EMERALD_SWORD.get());
            tabData.accept(ModItems.VOID_EMERALD_AXE.get());
            tabData.accept(ModItems.LAVA_EMERALD_SWORD.get());
            tabData.accept(ModItems.LAVA_EMERALD_AXE.get());
		} else if (tabData.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            tabData.accept(ModItems.REFINED_EMERALD.get());
            tabData.accept(ModItems.REFINED_EMERALD_T_2.get());
            tabData.accept(ModItems.REFINED_EMERALD_T_3.get());
            tabData.accept(ModItems.REFINED_EMERALD_PLUS.get());
            tabData.accept(ModItems.VOID_EMERALD.get());
            tabData.accept(ModItems.LAVA_EMERALD.get());
		}
	}
}