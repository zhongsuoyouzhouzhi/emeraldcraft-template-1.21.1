package com.zhouzhi.emeraldcraft.init.client.renderer;

import com.zhouzhi.emeraldcraft.EmeraldCraft;
import com.zhouzhi.emeraldcraft.entity.EmeraldGuardianEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class EmeraldGuardianRenderer extends GeoEntityRenderer<EmeraldGuardianEntity> {
    public EmeraldGuardianRenderer(EntityRendererProvider.Context context) {
        super(context, new GeoModel<>() {
            @Override
            public ResourceLocation getModelResource(EmeraldGuardianEntity emeraldGuardianEntity) {
                return ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "geo/emerald_guardian.geo.json");
            }

            @Override
            public ResourceLocation getTextureResource(EmeraldGuardianEntity emeraldGuardianEntity) {
                return ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "textures/entity/emerald_guardian.png");
            }

            @Override
            public ResourceLocation getAnimationResource(EmeraldGuardianEntity emeraldGuardianEntity) {
                return ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "animations/emerald_guardian.json");
            }
        });
    }
}