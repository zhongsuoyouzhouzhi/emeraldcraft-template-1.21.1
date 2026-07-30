package com.zhouzhi.emeraldcraft.init.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CustomShieldRenderer extends BlockEntityWithoutLevelRenderer {
    private final ShieldModel shieldModel;
    private static final ResourceLocation SHIELD_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("emeraldcraft", "textures/entity/void_emerald_shield.png");

    public CustomShieldRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        this.shieldModel = new ShieldModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.SHIELD));
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack poseStack,
                             MultiBufferSource buffer, int packedLight, int packedOverlay) {
        poseStack.pushPose();

        poseStack.scale(1.0F, -1.0F, -1.0F);

        boolean blocking = false;
        var player = Minecraft.getInstance().player;
        if (player != null && player.isBlocking()) {
            ItemStack activeItem = player.getUseItem();
            if (activeItem == stack) {
                blocking = true;
            }
        }

        if (blocking) {
            if (context == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND ||
                    context == ItemDisplayContext.FIRST_PERSON_LEFT_HAND) {
                poseStack.translate(0.0F, -0.2F, 0.3F);
                poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(-10.0F));
            } else if (context == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND ||
                    context == ItemDisplayContext.THIRD_PERSON_LEFT_HAND) {
                poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(-90.0F));
                poseStack.translate(0.0F, -0.1F, 0.0F);
            }
        }

        VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(
                buffer,
                RenderType.entitySolid(SHIELD_TEXTURE),
                false,
                stack.hasFoil()
        );

        this.shieldModel.handle().render(poseStack, vertexConsumer, packedLight, packedOverlay);
        this.shieldModel.plate().render(poseStack, vertexConsumer, packedLight, packedOverlay);

        poseStack.popPose();
    }
}