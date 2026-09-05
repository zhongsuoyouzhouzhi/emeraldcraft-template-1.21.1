package com.zhouzhi.emeraldcraft.init;

import com.zhouzhi.emeraldcraft.EmeraldCraft;
import com.zhouzhi.emeraldcraft.init.client.renderer.CustomTridentRenderer;
import com.zhouzhi.emeraldcraft.init.client.renderer.EmeraldGuardianRenderer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(Dist.CLIENT)
public class ModEntityRenderers {
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ModEntities.EMERALD_PROJECTILE.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(ModEntities.EMERALD_GUARDIAN.get(), EmeraldGuardianRenderer::new);
	}

	@SubscribeEvent
	public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(
				ModEntities.INFERNO_EMERALD_TRIDENT.get(),
				context -> new CustomTridentRenderer(
						context,
						new ModelLayerLocation(
								ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "inferno_emerald_trident"),
								"main"
						),
						ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "textures/entity/inferno_emerald_trident.png")
				)
		);
	}
}