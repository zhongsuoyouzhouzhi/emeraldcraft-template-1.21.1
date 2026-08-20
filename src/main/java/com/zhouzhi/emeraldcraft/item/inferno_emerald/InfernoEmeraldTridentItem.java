package com.zhouzhi.emeraldcraft.item.inferno_emerald;

import com.zhouzhi.emeraldcraft.entity.ThrownInfernoEmeraldTrident;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.FastColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.NotNull;

public class InfernoEmeraldTridentItem extends TridentItem {
    private static final float ATTACK_DAMAGE = 15.0F;
    private static final float ATTACK_SPEED = -2.8F;
    private static final int BAR_COLOR = FastColor.ARGB32.color(0, 225, 52, 29);
    public InfernoEmeraldTridentItem(Properties  properties) {
        super(properties);
    }

    @Override
    public @NotNull ItemAttributeModifiers getDefaultAttributeModifiers() {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(BASE_ATTACK_DAMAGE_ID, ATTACK_DAMAGE, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID, ATTACK_SPEED, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .build();
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player) {
            int i = this.getUseDuration(stack, entityLiving) - timeLeft;
            if (i >= 10 && !isTooDamagedToUse(stack)) {
                float f = EnchantmentHelper.getTridentSpinAttackStrength(stack, player);
                if (!(f > 0.0F) || player.isInWaterOrRain()) {
                    Holder<SoundEvent> holder = EnchantmentHelper.pickHighestLevel(stack, EnchantmentEffectComponents.TRIDENT_SOUND)
                            .orElse(SoundEvents.TRIDENT_THROW);
                    if (!level.isClientSide) {
                        stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
                        if (f == 0.0F) {
                            ThrownInfernoEmeraldTrident trident = new ThrownInfernoEmeraldTrident(level, player, stack);
                            trident.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
                            trident.setOwner(player);
                            trident.setPickupItemStackFromItem(stack.copy());
                            trident.pickup = AbstractArrow.Pickup.ALLOWED;

                            // 设置忠诚
                            int loyaltyLevel = EnchantmentHelper.getTridentReturnToOwnerAcceleration((ServerLevel) level, stack, trident);
                            if (loyaltyLevel > 0) {
                                trident.setLoyalty(loyaltyLevel);
                            }
                            // 设置附魔光效
                            if (stack.hasFoil()) {
                                trident.setFoil(true);
                            }

                            trident.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);
                            if (player.hasInfiniteMaterials()) {
                                trident.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                            }
                            level.addFreshEntity(trident);
                            level.playSound(null, trident, holder.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
                            if (!player.hasInfiniteMaterials()) {
                                player.getInventory().removeItem(stack);
                            }
                        }
                    }
                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    @Override
    public int getEnchantmentValue() {
        return 80;
    }

    @Override
    public boolean canAttackBlock(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player) {
        return !player.isCreative();
    }

    private static boolean isTooDamagedToUse(ItemStack stack) {
        return stack.getDamageValue() >= stack.getMaxDamage() - 1;
    }

    @Override
    public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ItemAbility itemAbility) {
        return ItemAbilities.DEFAULT_TRIDENT_ACTIONS.contains(itemAbility);
    }

    @Override
    public @NotNull Projectile asProjectile(@NotNull Level level, Position pos, ItemStack stack, @NotNull Direction direction) {
        ThrownInfernoEmeraldTrident thrownInfernoEmeraldTrident = new ThrownInfernoEmeraldTrident(level, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1));
        thrownInfernoEmeraldTrident.pickup = AbstractArrow.Pickup.ALLOWED;

        return thrownInfernoEmeraldTrident;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (isTooDamagedToUse(itemstack)) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return BAR_COLOR;
    }
}
