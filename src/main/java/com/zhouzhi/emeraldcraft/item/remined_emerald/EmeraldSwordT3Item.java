package com.zhouzhi.emeraldcraft.item.remined_emerald;

import com.zhouzhi.emeraldcraft.EmeraldCraft;
import com.zhouzhi.emeraldcraft.entity.EmeraldProjectileEntity;
import com.zhouzhi.emeraldcraft.init.ModBlocks;
import com.zhouzhi.emeraldcraft.init.ModEntities;
import com.zhouzhi.emeraldcraft.init.ModItems;
import com.zhouzhi.emeraldcraft.procedures.net.Use;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

public class EmeraldSwordT3Item extends SwordItem {
	private static final Tier TOOL_TIER = new Tier() {
		@Override
		public int getUses() {
			return 10325;
		}

		@Override
		public float getSpeed() {
			return 80f;
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
			return 50;
		}

		@Override
		@MethodsReturnNonnullByDefault
		public Ingredient getRepairIngredient() {
			return Ingredient.of(new ItemStack(ModBlocks.REFINED_EMERALD_BLOCK_3.get()), new ItemStack(ModItems.REFINED_EMERALD_T_3.get()));
		}
	};

	public EmeraldSwordT3Item() {
		super(TOOL_TIER, new Item.Properties().attributes(SwordItem.createAttributes(TOOL_TIER, 69f, -2.4f)).fireResistant().rarity(Rarity.EPIC));
	}

	@Override
	public boolean hurtEnemy(@ParametersAreNonnullByDefault ItemStack itemstack, @ParametersAreNonnullByDefault LivingEntity entity, @ParametersAreNonnullByDefault LivingEntity sourceEntity) {
		boolean r = super.hurtEnemy(itemstack, entity, sourceEntity);
		EmeraldCraft.queueServerWork(10, () -> {
			{
				Level projectileLevel = sourceEntity.level();
				if (!projectileLevel.isClientSide()) {
					Projectile _entityToSpawn = initArrowProjectile(new Arrow(projectileLevel, 0, 0, 0, new Arrow(EntityType.ARROW, projectileLevel).getPickupItemStackOrigin(), createArrowWeaponItemStack(projectileLevel)),
							sourceEntity);
					_entityToSpawn.setPos(sourceEntity.getX(), sourceEntity.getEyeY() - 0.1, sourceEntity.getZ());
					_entityToSpawn.shoot(sourceEntity.getLookAngle().x, sourceEntity.getLookAngle().y, sourceEntity.getLookAngle().z, 2, 0);
					projectileLevel.addFreshEntity(_entityToSpawn);
				}
			}
		});
        Use.EmeraldSwordT3HitLivingThings(entity);
		return r;
	}

	private static AbstractArrow initArrowProjectile(AbstractArrow entityToSpawn, Entity shooter) {
		entityToSpawn.setOwner(shooter);
		entityToSpawn.setBaseDamage((float) 10);
		entityToSpawn.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
		return entityToSpawn;
	}

	private static ItemStack createArrowWeaponItemStack(Level level) {
		ItemStack weapon = new ItemStack(Items.ARROW);
		weapon.enchant(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.KNOCKBACK), 1);
		weapon.enchant(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.PIERCING), (byte) 127);
		return weapon;
	}

	@Override
	@MethodsReturnNonnullByDefault
	public InteractionResultHolder<ItemStack> use(@ParametersAreNonnullByDefault Level world, @ParametersAreNonnullByDefault Player entity, @ParametersAreNonnullByDefault InteractionHand hand) {
		InteractionResultHolder<ItemStack> itemStackInteractionResultHolder = super.use(world, entity, hand);
		ItemStack itemstack = itemStackInteractionResultHolder.getObject();
		if (world instanceof ServerLevel _level) {
			itemstack.hurtAndBreak(40, _level, null, _stkprov -> {
			});
		}
		Level projectileLevel = entity.level();
		if (!projectileLevel.isClientSide()) {
			Projectile _entityToSpawn = initArrowProjectile(new EmeraldProjectileEntity(ModEntities.EMERALD_PROJECTILE.get(), 0, 0, 0, projectileLevel, createArrowWeaponItemStack(projectileLevel)), entity
			);
			_entityToSpawn.setPos(entity.getX(), entity.getEyeY() - 0.1, entity.getZ());
			_entityToSpawn.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, (float) 1.5, 0);
			projectileLevel.addFreshEntity(_entityToSpawn);
		}
		if (entity instanceof Player _player)
			_player.getCooldowns().addCooldown(itemstack.getItem(), 60);
		return itemStackInteractionResultHolder;
	}



    @Override
    public void inventoryTick(@ParametersAreNonnullByDefault ItemStack itemstack, @ParametersAreNonnullByDefault Level world, @ParametersAreNonnullByDefault Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        if (selected)
            Use.RefinedEmeraldT3ToolIsBeingDamagedPerTick(world, entity, itemstack);
    }

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(@ParametersAreNonnullByDefault ItemStack itemstack) {
		return true;
	}
}