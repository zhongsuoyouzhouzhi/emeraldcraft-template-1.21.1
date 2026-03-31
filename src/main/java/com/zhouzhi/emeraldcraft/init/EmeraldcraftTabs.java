package com.zhouzhi.emeraldcraft.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import com.zhouzhi.emeraldcraft.EmeraldCraft;

@EventBusSubscriber
public class EmeraldcraftTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EmeraldCraft.MOD_ID);
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EMERALD_CRAFT = REGISTRY.register("emerald_craft",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.emeraldcraft.emerald_craft")).icon(() -> new ItemStack(EmeraldcraftItems.REFINED_EMERALD_T_3.get())).displayItems((parameters, tabData) -> {
				tabData.accept(EmeraldcraftItems.REFINED_EMERALD.get());
				tabData.accept(EmeraldcraftBlocks.REFINED_EMERALD_BLOCK.get().asItem());
				tabData.accept(EmeraldcraftItems.EMERALD_SWORD.get());
				tabData.accept(EmeraldcraftItems.EMERALD_AXE.get());
				tabData.accept(EmeraldcraftItems.EMERALD_PICKAXE.get());
				tabData.accept(EmeraldcraftItems.EMERALD_SHOVEL.get());
				tabData.accept(EmeraldcraftItems.EMERALD_HOE.get());
				tabData.accept(EmeraldcraftItems.EMERALD_ARMOR_HELMET.get());
				tabData.accept(EmeraldcraftItems.EMERALD_ARMOR_CHESTPLATE.get());
				tabData.accept(EmeraldcraftItems.EMERALD_ARMOR_LEGGINGS.get());
				tabData.accept(EmeraldcraftItems.EMERALD_ARMOR_BOOTS.get());
                tabData.accept(EmeraldcraftItems.REFINED_EMERALD_T_2.get());
                tabData.accept(EmeraldcraftBlocks.REFINED_EMERALD_BLOCK_2.get().asItem());
                tabData.accept(EmeraldcraftItems.EMERALD_SWORD_T_2.get());
                tabData.accept(EmeraldcraftItems.EMERALD_AXE_T_2.get());
                tabData.accept(EmeraldcraftItems.EMERALD_PICKAXE_T_2.get());
                tabData.accept(EmeraldcraftItems.EMERALD_SHOVEL_T_2.get());
                tabData.accept(EmeraldcraftItems.EMERALD_HOE_T_2.get());
                tabData.accept(EmeraldcraftItems.EMERALD_ARMOR_T_2_HELMET.get());
                tabData.accept(EmeraldcraftItems.EMERALD_ARMOR_T_2_CHESTPLATE.get());
                tabData.accept(EmeraldcraftItems.EMERALD_ARMOR_T_2_LEGGINGS.get());
                tabData.accept(EmeraldcraftItems.EMERALD_ARMOR_T_2_BOOTS.get());
                tabData.accept(EmeraldcraftItems.REFINED_EMERALD_T_3.get());
                tabData.accept(EmeraldcraftBlocks.REFINED_EMERALD_BLOCK_3.get().asItem());
                tabData.accept(EmeraldcraftItems.EMERALD_SWORD_T_3.get());
                tabData.accept(EmeraldcraftItems.EMERALD_AXE_T_3.get());
                tabData.accept(EmeraldcraftItems.EMERALD_PICKAXE_T_3.get());
                tabData.accept(EmeraldcraftItems.EMERALD_SHOVEL_T_3.get());
                tabData.accept(EmeraldcraftItems.EMERALD_HOE_T_3.get());
                tabData.accept(EmeraldcraftItems.EMERALD_ARMOR_T_3_HELMET.get());
                tabData.accept(EmeraldcraftItems.EMERALD_ARMOR_T_3_CHESTPLATE.get());
                tabData.accept(EmeraldcraftItems.EMERALD_ARMOR_T_3_LEGGINGS.get());
                tabData.accept(EmeraldcraftItems.EMERALD_ARMOR_T_3_BOOTS.get());
                tabData.accept(EmeraldcraftItems.REFINED_EMERALD_PLUS.get());
                tabData.accept(EmeraldcraftItems.SKYFILLING_BLADE.get());
                tabData.accept(EmeraldcraftItems.IRON_SWORD_INLAID_WITH_REFINED_EMERALD.get());
                tabData.accept(EmeraldcraftItems.IRON_AXE_INLAID_WITH_REFINED_EMERALD.get());
                tabData.accept(EmeraldcraftItems.IRON_PICKAXE_INLAID_WITH_REFINED_EMERALD.get());
                tabData.accept(EmeraldcraftItems.IRON_SHOVEL_INLAID_WITH_REFINED_EMERALD.get());
                tabData.accept(EmeraldcraftItems.IRON_HOE_INLAID_WITH_REFINED_EMERALD.get());
                tabData.accept(EmeraldcraftItems.IRON_SWORD_INLAID_WITH_REFINED_EMERALD_T2.get());
                tabData.accept(EmeraldcraftItems.IRON_AXE_INLAID_WITH_REFINED_EMERALD_T2.get());
                tabData.accept(EmeraldcraftItems.IRON_PICKAXE_INLAID_WITH_REFINED_EMERALD_T2.get());
                tabData.accept(EmeraldcraftItems.IRON_SHOVEL_INLAID_WITH_REFINED_EMERALD_T2.get());
                tabData.accept(EmeraldcraftItems.IRON_HOE_INLAID_WITH_REFINED_EMERALD_T2.get());
                tabData.accept(EmeraldcraftItems.VOID_EMERALD.get());
                tabData.accept(EmeraldcraftItems.VOID_EMERALD_SWORD.get());
                tabData.accept(EmeraldcraftItems.VOID_EMERALD_AXE.get());
                tabData.accept(EmeraldcraftItems.VOID_EMERALD_PICKAXE.get());
                tabData.accept(EmeraldcraftItems.VOID_EMERALD_SHOVEL.get());
                tabData.accept(EmeraldcraftItems.VOID_EMERALD_HOE.get());
			}).withSearchBar().build());

	@SubscribeEvent
	public static void buildTabContentsVanilla(BuildCreativeModeTabContentsEvent tabData) {
		if (tabData.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			tabData.accept(EmeraldcraftItems.EMERALD_AXE.get());
			tabData.accept(EmeraldcraftItems.EMERALD_PICKAXE.get());
			tabData.accept(EmeraldcraftItems.EMERALD_SHOVEL.get());
			tabData.accept(EmeraldcraftItems.EMERALD_HOE.get());
            tabData.accept(EmeraldcraftItems.EMERALD_AXE_T_2.get());
            tabData.accept(EmeraldcraftItems.EMERALD_PICKAXE_T_2.get());
            tabData.accept(EmeraldcraftItems.EMERALD_SHOVEL_T_2.get());
            tabData.accept(EmeraldcraftItems.EMERALD_HOE_T_2.get());
            tabData.accept(EmeraldcraftItems.EMERALD_AXE_T_3.get());
            tabData.accept(EmeraldcraftItems.EMERALD_PICKAXE_T_3.get());
            tabData.accept(EmeraldcraftItems.EMERALD_SHOVEL_T_3.get());
            tabData.accept(EmeraldcraftItems.EMERALD_HOE_T_3.get());
            tabData.accept(EmeraldcraftItems.IRON_AXE_INLAID_WITH_REFINED_EMERALD.get());
            tabData.accept(EmeraldcraftItems.IRON_PICKAXE_INLAID_WITH_REFINED_EMERALD.get());
            tabData.accept(EmeraldcraftItems.IRON_SHOVEL_INLAID_WITH_REFINED_EMERALD.get());
            tabData.accept(EmeraldcraftItems.IRON_HOE_INLAID_WITH_REFINED_EMERALD.get());
            tabData.accept(EmeraldcraftItems.IRON_AXE_INLAID_WITH_REFINED_EMERALD_T2.get());
            tabData.accept(EmeraldcraftItems.IRON_PICKAXE_INLAID_WITH_REFINED_EMERALD_T2.get());
            tabData.accept(EmeraldcraftItems.IRON_SHOVEL_INLAID_WITH_REFINED_EMERALD_T2.get());
            tabData.accept(EmeraldcraftItems.IRON_HOE_INLAID_WITH_REFINED_EMERALD_T2.get());
            tabData.accept(EmeraldcraftItems.VOID_EMERALD_AXE.get());
            tabData.accept(EmeraldcraftItems.VOID_EMERALD_PICKAXE.get());
            tabData.accept(EmeraldcraftItems.VOID_EMERALD_SHOVEL.get());
            tabData.accept(EmeraldcraftItems.VOID_EMERALD_HOE.get());
		} else if (tabData.getTabKey() == CreativeModeTabs.COMBAT) {
			tabData.accept(EmeraldcraftItems.EMERALD_SWORD.get());
            tabData.accept(EmeraldcraftItems.EMERALD_AXE.get());
			tabData.accept(EmeraldcraftItems.EMERALD_ARMOR_HELMET.get());
			tabData.accept(EmeraldcraftItems.EMERALD_ARMOR_CHESTPLATE.get());
			tabData.accept(EmeraldcraftItems.EMERALD_ARMOR_LEGGINGS.get());
			tabData.accept(EmeraldcraftItems.EMERALD_ARMOR_BOOTS.get());
            tabData.accept(EmeraldcraftItems.EMERALD_SWORD_T_2.get());
            tabData.accept(EmeraldcraftItems.EMERALD_AXE_T_2.get());
			tabData.accept(EmeraldcraftItems.EMERALD_ARMOR_T_2_HELMET.get());
			tabData.accept(EmeraldcraftItems.EMERALD_ARMOR_T_2_CHESTPLATE.get());
			tabData.accept(EmeraldcraftItems.EMERALD_ARMOR_T_2_LEGGINGS.get());
			tabData.accept(EmeraldcraftItems.EMERALD_ARMOR_T_2_BOOTS.get());
            tabData.accept(EmeraldcraftItems.EMERALD_SWORD_T_3.get());
            tabData.accept(EmeraldcraftItems.EMERALD_AXE_T_3.get());
            tabData.accept(EmeraldcraftItems.EMERALD_ARMOR_T_3_HELMET.get());
            tabData.accept(EmeraldcraftItems.EMERALD_ARMOR_T_3_CHESTPLATE.get());
            tabData.accept(EmeraldcraftItems.EMERALD_ARMOR_T_3_LEGGINGS.get());
            tabData.accept(EmeraldcraftItems.EMERALD_ARMOR_T_3_BOOTS.get());
            tabData.accept(EmeraldcraftItems.SKYFILLING_BLADE.get());
            tabData.accept(EmeraldcraftItems.IRON_SWORD_INLAID_WITH_REFINED_EMERALD.get());
            tabData.accept(EmeraldcraftItems.IRON_AXE_INLAID_WITH_REFINED_EMERALD.get());
            tabData.accept(EmeraldcraftItems.IRON_SWORD_INLAID_WITH_REFINED_EMERALD_T2.get());
            tabData.accept(EmeraldcraftItems.IRON_AXE_INLAID_WITH_REFINED_EMERALD_T2.get());
            tabData.accept(EmeraldcraftItems.VOID_EMERALD_SWORD.get());
            tabData.accept(EmeraldcraftItems.VOID_EMERALD_AXE.get());
		}
	}
}