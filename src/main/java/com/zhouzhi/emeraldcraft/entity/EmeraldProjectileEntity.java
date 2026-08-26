package com.zhouzhi.emeraldcraft.entity;

import com.zhouzhi.emeraldcraft.EmeraldCraft;
import com.zhouzhi.emeraldcraft.init.ModAttributes;
import com.zhouzhi.emeraldcraft.init.ModEntities;
import com.zhouzhi.emeraldcraft.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class EmeraldProjectileEntity extends AbstractArrow implements ItemSupplier {
	public static final ItemStack PROJECTILE_ITEM = new ItemStack(ModItems.REFINED_EMERALD_T_3.get());
	private int knockback = 0;
    private float count;

	public EmeraldProjectileEntity(EntityType<? extends EmeraldProjectileEntity> type, Level world) {
		super(type, world);
		setNoGravity(true);
	}

	public EmeraldProjectileEntity(EntityType<? extends EmeraldProjectileEntity> type, double x, double y, double z, Level world, @Nullable ItemStack firedFromWeapon) {
		super(type, x, y, z, world, PROJECTILE_ITEM, firedFromWeapon);
		setNoGravity(true);
		if (firedFromWeapon != null)
			setKnockback(EnchantmentHelper.getItemEnchantmentLevel(world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.KNOCKBACK), firedFromWeapon));
	}

	public EmeraldProjectileEntity(EntityType<? extends EmeraldProjectileEntity> type, LivingEntity entity, Level world, @Nullable ItemStack firedFromWeapon) {
		super(type, entity, world, PROJECTILE_ITEM, firedFromWeapon);
		setNoGravity(true);
		if (firedFromWeapon != null)
			setKnockback(EnchantmentHelper.getItemEnchantmentLevel(world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.KNOCKBACK), firedFromWeapon));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public @NotNull ItemStack getItem() {
		return PROJECTILE_ITEM;
	}

	@Override
	protected @NotNull ItemStack getDefaultPickupItem() {
		return new ItemStack(ModItems.REFINED_EMERALD_T_3.get());
	}

	@Override
	protected void doPostHurtEffects(@NotNull LivingEntity entity) {
		super.doPostHurtEffects(entity);
		entity.setArrowCount(entity.getArrowCount() - 1);
	}

	public void setKnockback(int knockback) {
		this.knockback = knockback;
	}

	@Override
	protected void doKnockback(@NotNull LivingEntity livingEntity, @NotNull DamageSource damageSource) {
		if (knockback > 0.0) {
			double d1 = Math.max(0.0, 1.0 - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
			Vec3 vec3 = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale(knockback * 0.6 * d1);
			if (vec3.lengthSqr() > 0.0) {
				livingEntity.push(vec3.x, 0.1, vec3.z);
			}
		} else { // knockback might be set by firedFromWeapon passed into constructor
			super.doKnockback(livingEntity, damageSource);
		}
	}

	@Override
	public void onHitEntity(@NotNull EntityHitResult entityHitResult) {
		super.onHitEntity(entityHitResult);
		Level world = this.level();
		Entity entity = entityHitResult.getEntity();
		Entity immediatesourceentity = this;
		Entity sourceentity = this.getOwner();
        if (entity instanceof LivingEntity livingEntity && livingEntity.getAttributes().hasAttribute(ModAttributes.LAUNCHED)) {
            Objects.requireNonNull(livingEntity.getAttribute(ModAttributes.LAUNCHED)).getValue();
        }
        entity.hurt(new DamageSource(world.holderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("emeraldcraft:emerald_radiation")))), 120);
		if (world instanceof ServerLevel _level) {
			LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
			if (entityToSpawn != null) {
				entityToSpawn.moveTo(Vec3.atBottomCenterOf(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ())));
				entityToSpawn.setVisualOnly(true);
				_level.addFreshEntity(entityToSpawn);
			}
		}
		if (entity instanceof Mob _entity)
			_entity.setTarget(null);
		entity.igniteForSeconds(20);
		if (entity instanceof Player _player) {
			_player.getAbilities().invulnerable = true;
			_player.onUpdateAbilities();
		}
		if (!((immediatesourceentity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(ModAttributes.LAUNCHED) ? Objects.requireNonNull(_livingEntity6.getAttribute(ModAttributes.LAUNCHED)).getValue() : 0) == 1)) {
			if (sourceentity instanceof Player _player) {
				_player.getFoodData().setSaturation((entity instanceof Player _plr ? _plr.getFoodData().getSaturationLevel() : 0) + _player.getFoodData().getSaturationLevel());
			}
			if (entity instanceof Player _player)
				_player.getFoodData().setSaturation(0);
			if (sourceentity instanceof Player _player) {
				_player.getFoodData().setFoodLevel((entity instanceof Player _plr ? _plr.getFoodData().getFoodLevel() : 0) + _player.getFoodData().getFoodLevel());
			}
			if (entity instanceof Player _player) {
				_player.causeFoodExhaustion(_player.getFoodData().getFoodLevel());
			}
		}
		for (int index0 = 0; index0 < 32; index0++) {
			{
				entity.setYRot((float) 22.5);
				entity.setXRot(0);
				entity.setYBodyRot(entity.getYRot());
				entity.setYHeadRot(entity.getYRot());
				entity.yRotO = entity.getYRot();
				entity.xRotO = entity.getXRot();
				if (entity instanceof LivingEntity _entity) {
					_entity.yBodyRotO = _entity.getYRot();
					_entity.yHeadRotO = _entity.getYRot();
				}
			}
			EmeraldCraft.queueServerWork(5, () -> entity.makeStuckInBlock(Blocks.AIR.defaultBlockState(), new Vec3(0.25, 0.05, 0.25)));
		}
	}

	@Override
	public void onHitBlock(@NotNull BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.inGround)
			this.discard();
        this.count += 0.05f;
        if (this.count >= 30f) {
            if (!this.level().isClientSide())
                this.discard();
        }
	}

	public static EmeraldProjectileEntity shoot(Level world, LivingEntity entity, RandomSource source) {
		return shoot(world, entity, source, 1.5f, 10, 1);
	}

	public static EmeraldProjectileEntity shoot(Level world, LivingEntity entity, RandomSource source, float pullingPower) {
		return shoot(world, entity, source, pullingPower * 1.5f, 10, 1);
	}

	public static EmeraldProjectileEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
		EmeraldProjectileEntity entityArrow = new EmeraldProjectileEntity(ModEntities.EMERALD_PROJECTILE.get(), entity, world, null);
		entityArrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
		entityArrow.setSilent(true);
		entityArrow.setCritArrow(false);
		entityArrow.setBaseDamage(damage);
		entityArrow.setKnockback(knockback);
		world.addFreshEntity(entityArrow);
		return entityArrow;
	}

	public static EmeraldProjectileEntity shoot(LivingEntity entity, LivingEntity target) {
		EmeraldProjectileEntity entityArrow = new EmeraldProjectileEntity(ModEntities.EMERALD_PROJECTILE.get(), entity, entity.level(), null);
		double dx = target.getX() - entity.getX();
		double dy = target.getY() + target.getEyeHeight() - 1.1;
		double dz = target.getZ() - entity.getZ();
		entityArrow.shoot(dx, dy - entityArrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 1.5f * 2, 12.0F);
		entityArrow.setSilent(true);
		entityArrow.setBaseDamage(10);
		entityArrow.setKnockback(1);
		entityArrow.setCritArrow(false);
		entity.level().addFreshEntity(entityArrow);
		return entityArrow;
	}
}