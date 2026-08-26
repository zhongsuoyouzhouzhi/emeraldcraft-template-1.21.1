package com.zhouzhi.emeraldcraft.item.remined_emerald;

import com.zhouzhi.emeraldcraft.entity.EmeraldProjectileEntity;
import com.zhouzhi.emeraldcraft.init.ModEntities;
import com.zhouzhi.emeraldcraft.init.ModItems;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

public class SkyFillingBladeItem extends SwordItem {
	private static final Tier TOOL_TIER = new Tier() {
		@Override
		public int getUses() {
			return 262144;
		}

		@Override
		public float getSpeed() {
			return 150f;
		}

		@Override
		public float getAttackDamageBonus() {
			return 0;
		}

		@Override
		@MethodsReturnNonnullByDefault
		public TagKey<Block> getIncorrectBlocksForDrops() {
			return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
		}

		@Override
		public int getEnchantmentValue() {
			return 100;
		}

		@Override
		@MethodsReturnNonnullByDefault
		public Ingredient getRepairIngredient() {
			return Ingredient.of(new ItemStack(ModItems.REFINED_EMERALD_PLUS.get()));
		}
	};

	public SkyFillingBladeItem() {
		super(TOOL_TIER, new Item.Properties().attributes(SwordItem.createAttributes(TOOL_TIER, 149f, -2.4f)).fireResistant().rarity(Rarity.EPIC));
	}

	@Override
	public boolean hurtEnemy(@ParametersAreNonnullByDefault ItemStack itemstack, @ParametersAreNonnullByDefault LivingEntity entity, @ParametersAreNonnullByDefault LivingEntity sourceEntity) {
		boolean r = super.hurtEnemy(itemstack, entity, sourceEntity);
		Level world = entity.getCommandSenderWorld();
		if (itemstack.getEnchantmentLevel(world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FIRE_ASPECT)) != 0) {
			entity.igniteForSeconds(15 + itemstack.getEnchantmentLevel(world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FIRE_ASPECT)) * 5);
			entity.hurt(new DamageSource(world.holderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("emeraldcraft:emerald_radiation")))), 20);
		} else {
			entity.igniteForSeconds(10);
		}
		if (entity instanceof Player _player) {
			_player.getFoodData().setFoodLevel(0);
			_player.getFoodData().setSaturation(0);
		}
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 4, false, false));
		return r;
	}

	@Override
	@MethodsReturnNonnullByDefault
	public InteractionResultHolder<ItemStack> use(@ParametersAreNonnullByDefault Level world, @ParametersAreNonnullByDefault Player entity, @ParametersAreNonnullByDefault InteractionHand hand) {
		InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
		ItemStack itemstack = ar.getObject();
		if (world instanceof ServerLevel _level) {
			itemstack.hurtAndBreak(9, _level, null, _stkprov -> {
			});
		}
		Level projectileLevel = entity.level();
		if (!projectileLevel.isClientSide()) {
			Projectile _entityToSpawn = initArrowProjectile(new EmeraldProjectileEntity(ModEntities.EMERALD_PROJECTILE.get(), 0, 0, 0, projectileLevel, createArrowWeaponItemStack(projectileLevel)), entity
			);
			_entityToSpawn.setPos(entity.getX(), entity.getEyeY() - 0.1, entity.getZ());
			_entityToSpawn.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, (float) 4, 0);
			projectileLevel.addFreshEntity(_entityToSpawn);
		}
		if (entity instanceof Player _player)
			_player.getCooldowns().addCooldown(itemstack.getItem(), 20);
		return ar;
	}

	private static AbstractArrow initArrowProjectile(AbstractArrow entityToSpawn, Entity shooter) {
		entityToSpawn.setOwner(shooter);
		entityToSpawn.setBaseDamage((float) 0);
		entityToSpawn.setSilent(true);
		entityToSpawn.pickup = AbstractArrow.Pickup.DISALLOWED;
		return entityToSpawn;
	}

	private static ItemStack createArrowWeaponItemStack(Level level) {
		ItemStack weapon = new ItemStack(Items.ARROW);
		weapon.enchant(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.KNOCKBACK), 1);
		weapon.enchant(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.PIERCING), (byte) 127);
		return weapon;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(@ParametersAreNonnullByDefault ItemStack itemstack) {
		return true;
	}
}