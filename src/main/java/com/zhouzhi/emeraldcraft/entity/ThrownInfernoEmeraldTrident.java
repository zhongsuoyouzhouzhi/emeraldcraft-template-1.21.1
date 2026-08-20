package com.zhouzhi.emeraldcraft.entity;

import com.zhouzhi.emeraldcraft.init.ModEntities;
import com.zhouzhi.emeraldcraft.procedures.compress.TagChange;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

import java.lang.reflect.Field;

public class ThrownInfernoEmeraldTrident extends ThrownTrident {
    private static final EntityDataAccessor<Byte> ID_LOYALTY;
    private static final EntityDataAccessor<Boolean> ID_FOIL;
    public boolean special_skill = false;

    static {
        try {
            Field loyaltyField = ThrownTrident.class.getDeclaredField("ID_LOYALTY");
            loyaltyField.setAccessible(true);
            @SuppressWarnings("unchecked")
            EntityDataAccessor<Byte> loyalty = (EntityDataAccessor<Byte>) loyaltyField.get(null);
            ID_LOYALTY = loyalty;

            Field foilField = ThrownTrident.class.getDeclaredField("ID_FOIL");
            foilField.setAccessible(true);
            @SuppressWarnings("unchecked")
            EntityDataAccessor<Boolean> foil = (EntityDataAccessor<Boolean>) foilField.get(null);
            ID_FOIL = foil;
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize ThrownInfernoEmeraldTrident fields", e);
        }
    }

    public void setLoyalty(int level) {
        if (ID_LOYALTY != null) {
            this.entityData.set(ID_LOYALTY, (byte) Mth.clamp(level, 0, 127));
        }
    }

    public void setFoil(boolean foil) {
        if (ID_FOIL != null) {
            this.entityData.set(ID_FOIL, foil);
        }
    }

    public ThrownInfernoEmeraldTrident(EntityType<? extends ThrownTrident> entityType, Level level) {
        super(entityType, level);
        TagChange.getOrCreateComponent(this,"Inferno",special_skill);
        TagChange.saveComponent(this,"Inferno",special_skill);
    }

    public ThrownInfernoEmeraldTrident(Level level, LivingEntity shooter, ItemStack stack) {
        super(ModEntities.INFERNO_EMERALD_TRIDENT.get(), level);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.setOwner(shooter);
        this.setPickupItemStack(stack.copy());
        try {
            var field = ThrownTrident.class.getDeclaredField("ID_FOIL");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            var dataAccessor = (net.minecraft.network.syncher.EntityDataAccessor<Boolean>) field.get(null);
            this.entityData.set(dataAccessor, stack.hasFoil());
        } catch (Exception ignored) {
            // 反射失败就忽略
        }
        this.pickup = AbstractArrow.Pickup.ALLOWED;
        TagChange.saveComponent(this,"Inferno",TagChange.getOrCreateComponent(stack,"Inferno",special_skill));
        this.special_skill = TagChange.getOrCreateComponent(this,"Inferno",special_skill);
    }

    public ThrownInfernoEmeraldTrident(Level level, double x, double y, double z, ItemStack stack) {
        super(ModEntities.INFERNO_EMERALD_TRIDENT.get(), level);
        this.setPos(x, y, z);
        this.setPickupItemStack(stack.copy());
        this.entityData.set(ID_LOYALTY, this.getLoyaltyFromItem(stack));
        this.entityData.set(ID_FOIL, stack.hasFoil());
        TagChange.saveComponent(this,"Inferno",TagChange.getOrCreateComponent(stack,"Inferno",false));
        this.special_skill = TagChange.getOrCreateComponent(this,"Inferno",special_skill);
    }

    private byte getLoyaltyFromItem(ItemStack stack) {
        Level var3 = this.level();
        byte var10000;
        if (var3 instanceof ServerLevel serverlevel) {
            var10000 = (byte)Mth.clamp(EnchantmentHelper.getTridentReturnToOwnerAcceleration(serverlevel, stack, this), 0, 127);
        } else {
            var10000 = 0;
        }
        return var10000;
    }

    @Override
    public double getBaseDamage() {
        return 22.5;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        float f = 37.5F;
        Entity entity1 = this.getOwner();
        DamageSource damagesource = this.damageSources().trident(this, entity1 == null ? this : entity1);
        Level var7 = this.level();
        if (var7 instanceof ServerLevel serverlevel) {
            f = EnchantmentHelper.modifyDamage(serverlevel, this.getWeaponItem(), entity, damagesource, f);
        }

        try {
            java.lang.reflect.Field field = ThrownTrident.class.getDeclaredField("dealtDamage");
            field.setAccessible(true);
            field.setBoolean(this, true);
        } catch (Exception ignored) {}

        if (entity.hurt(damagesource, f)) {
            entity.lavaHurt();
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            var7 = this.level();
            if (var7 instanceof ServerLevel serverlevel) {

                ItemStack weapon = this.getWeaponItem();
                RegistryAccess registryAccess = serverlevel.registryAccess();
                Holder<Enchantment> EnchantmentHolder = registryAccess
                        .lookupOrThrow(Registries.ENCHANTMENT)
                        .getOrThrow(Enchantments.CHANNELING);
                if (weapon.getEnchantmentLevel(EnchantmentHolder) > 0) {
                    if (serverlevel.isThundering() && serverlevel.isRainingAt(entity.blockPosition())) {
                        LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(serverlevel);
                        if (lightning != null) {
                            lightning.moveTo(entity.position());
                            serverlevel.addFreshEntity(lightning);
                        }
                    }
                }

                EnchantmentHelper.doPostAttackEffectsWithItemSource(serverlevel, entity, damagesource, this.getWeaponItem());
            }

            if (entity instanceof LivingEntity livingentity) {
                this.doKnockback(livingentity, damagesource);
                this.doPostHurtEffects(livingentity);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
        this.playSound(SoundEvents.TRIDENT_HIT, 1.0F, 1.0F);
    }

    public void setPickupItemStackFromItem(ItemStack stack) {
        this.setPickupItemStack(stack);
    }
}