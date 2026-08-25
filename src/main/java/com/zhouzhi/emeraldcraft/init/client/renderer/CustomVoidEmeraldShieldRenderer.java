package com.zhouzhi.emeraldcraft.init.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.zhouzhi.emeraldcraft.init.ModItems;
import com.zhouzhi.emeraldcraft.procedures.net.Use;
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
public class CustomVoidEmeraldShieldRenderer extends BlockEntityWithoutLevelRenderer {
    private static final Minecraft mc = Minecraft.getInstance();
    private final ShieldModel shieldModel;
    private static final ResourceLocation SHIELD_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("emeraldcraft", "textures/entity/void_emerald_shield.png");

    public CustomVoidEmeraldShieldRenderer() {
        super(mc.getBlockEntityRenderDispatcher(), mc.getEntityModels());
        this.shieldModel = new ShieldModel(mc.getEntityModels().bakeLayer(ModelLayers.SHIELD));
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack poseStack,
                             MultiBufferSource buffer, int packedLight, int packedOverlay) {
        poseStack.pushPose();

        poseStack.scale(1.0F, -1.0F, -1.0F);

        boolean blocking = false;
        var player = mc.player;
        if (player != null) {
            blocking = Use.Render.isBlocking(player, stack,ModItems.VOID_EMERALD_SHIELD);
        }

        if (blocking) {
            Use.Render.render(context, poseStack);
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