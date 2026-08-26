package com.zhouzhi.emeraldcraft.item.remined_emerald;

import com.zhouzhi.emeraldcraft.init.ModAttributes;
import com.zhouzhi.emeraldcraft.init.ModBlocks;
import com.zhouzhi.emeraldcraft.init.ModItems;
import com.zhouzhi.emeraldcraft.init.ModMobEffects;
import com.zhouzhi.emeraldcraft.procedures.compress.DamageALL;
import com.zhouzhi.emeraldcraft.procedures.compress.MobEffectALL;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

public class RefinedEmeraldPlusItem extends Item {
	public RefinedEmeraldPlusItem() {
		super(new Item.Properties().fireResistant().rarity(Rarity.EPIC).food((new FoodProperties.Builder()).nutrition(500).saturationModifier(500f).alwaysEdible().build()));
	}

	@Override
	public int getUseDuration(@ParametersAreNonnullByDefault ItemStack itemstack, @ParametersAreNonnullByDefault LivingEntity livingEntity) {
		return 40;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(@ParametersAreNonnullByDefault ItemStack itemstack) {
		return true;
	}

	@Override
	@MethodsReturnNonnullByDefault
	public ItemStack finishUsingItem(@ParametersAreNonnullByDefault ItemStack itemstack, @ParametersAreNonnullByDefault Level world, @ParametersAreNonnullByDefault LivingEntity entity) {
		ItemStack item = super.finishUsingItem(itemstack, world, entity);
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide() && _entity.getAttributes().hasAttribute(ModAttributes.EVOLVED)) {
			double effectlevel = Objects.requireNonNull(_entity.getAttribute(ModAttributes.EVOLVED)).getValue();
			if (effectlevel  < 5){
				Objects.requireNonNull(_entity.getAttribute(ModAttributes.EVOLVED)).setBaseValue(effectlevel + 1);
			}
			else if (entity instanceof ServerPlayer _player) {
				AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("emeraldcraft:strengthen_body"));
				if (_adv != null) {
					AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
					if (!_ap.isDone()) {
						for (String criteria : _ap.getRemainingCriteria())
							_player.getAdvancements().award(_adv, criteria);
					}
				}
			}
			effectlevel++;
			effectlevel = effectlevel / 2;
			MobEffectInstance[] b = {
					new MobEffectInstance(ModMobEffects.EMERALD_ATTACH, (int)(effectlevel * 14400), 50, false, false),
					new MobEffectInstance(ModMobEffects.EMERALD_BONUS, (int)(effectlevel * 14400), 50, false, false),
					new MobEffectInstance(net.minecraft.world.effect.MobEffects.ABSORPTION, (int)(effectlevel * 7200), 10, false, false),
					new MobEffectInstance(net.minecraft.world.effect.MobEffects.FIRE_RESISTANCE, (int)(effectlevel * 7200), 0, false, false),
					new MobEffectInstance(net.minecraft.world.effect.MobEffects.DAMAGE_BOOST, (int)(effectlevel * 7200), 5, false, false),
					new MobEffectInstance(net.minecraft.world.effect.MobEffects.HEAL, 1, 20, false, false),
					new MobEffectInstance(net.minecraft.world.effect.MobEffects.REGENERATION, (int)(effectlevel * 10800), 5, false, false),
					new MobEffectInstance(net.minecraft.world.effect.MobEffects.LUCK, (int)(effectlevel * 7200), 5, false, false),
					new MobEffectInstance(net.minecraft.world.effect.MobEffects.NIGHT_VISION, (int)(effectlevel * 14400), 0, false, false)};
			for (MobEffectInstance mobEffectInstance : b) {
				_entity.addEffect(mobEffectInstance);
			}
		}
		if (entity instanceof Player player) {
			ItemStack[] inventory = player.getInventory().items.toArray(new ItemStack[0]);
			for (ItemStack itemStack : inventory) {
				if (itemStack != null && itemStack.isDamageableItem()) {
					itemStack.setDamageValue(0);
				}
			}
		}
		return item;
	}

	@Override
	@MethodsReturnNonnullByDefault
	public InteractionResult useOn(@ParametersAreNonnullByDefault UseOnContext context) {
		super.useOn(context);
		Level world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		double x = pos.getX();
		double y = pos.getY();
		double z = pos.getZ();
		Entity entity = context.getPlayer();
		ItemStack itemstack = context.getItemInHand();
		if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == ModBlocks.REFINED_EMERALD_BLOCK_3.get()) {
			itemstack.shrink(1);
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack.getItem(), 150);
			if (world.isClientSide())
				Minecraft.getInstance().gameRenderer.displayItemActivation(itemstack);
			world.setBlock(BlockPos.containing(x, y, z), net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 3);
			if (world instanceof Level _level && !_level.isClientSide())
				_level.explode(null, x, y, z, 512, Level.ExplosionInteraction.BLOCK);
			if (entity instanceof Player _player && !_player.level().isClientSide())
				send(1,_player);
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == ModBlocks.REFINED_EMERALD_BLOCK_2.get()) {
			itemstack.shrink(1);
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack.getItem(), 150);
			if (world.isClientSide())
				Minecraft.getInstance().gameRenderer.displayItemActivation(itemstack);
			world.setBlock(BlockPos.containing(x, y, z), net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 3);
			if (world instanceof Level _level && !_level.isClientSide())
				_level.explode(null, x, y, z, 256, Level.ExplosionInteraction.BLOCK);
			if (entity instanceof Player _player && !_player.level().isClientSide())
				send(2,_player);
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == ModBlocks.REFINED_EMERALD_BLOCK.get()) {
			itemstack.shrink(1);
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack.getItem(), 150);
			if (world.isClientSide())
				Minecraft.getInstance().gameRenderer.displayItemActivation(itemstack);
			world.setBlock(BlockPos.containing(x, y, z), net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 3);
			if (world instanceof Level _level && !_level.isClientSide())
				_level.explode(null, x, y, z, 128, Level.ExplosionInteraction.BLOCK);
			if (entity instanceof Player _player && !_player.level().isClientSide())
				send(3,_player);
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == net.minecraft.world.level.block.Blocks.EMERALD_BLOCK) {
			itemstack.shrink(1);
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack.getItem(), 150);
			if (world.isClientSide())
				Minecraft.getInstance().gameRenderer.displayItemActivation(itemstack);
			world.setBlock(BlockPos.containing(x, y, z), net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 3);
			if (world instanceof Level _level && !_level.isClientSide())
				_level.explode(null, x, y, z, 96, Level.ExplosionInteraction.BLOCK);
			if (entity instanceof Player _player && !_player.level().isClientSide())
				send(4,_player);
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == net.minecraft.world.level.block.Blocks.BEDROCK) {
			itemstack.shrink(1);
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack.getItem(), 1800);
			if (world.isClientSide())
				Minecraft.getInstance().gameRenderer.displayItemActivation(itemstack);
			world.setBlock(BlockPos.containing(x, y, z), net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 3);
			if (world instanceof Level _level && !_level.isClientSide())
				_level.explode(null, x, y, z, 1024, Level.ExplosionInteraction.BLOCK);
			if (entity instanceof Player _player && !_player.level().isClientSide()) {
				Component name1 = Component.translatable("message.emeraldcraft.warning_5_1");
				Component name2 = Component.translatable("message.emeraldcraft.warning_5_2");
				_player.displayClientMessage(Component.literal(
								(new ItemStack(ModItems.REFINED_EMERALD_PLUS.get()).getDisplayName().getString() + name1.getString() + new ItemStack(net.minecraft.world.level.block.Blocks.BEDROCK).getDisplayName().getString() + name2.getString())),
						false);
			}
		}
		return InteractionResult.SUCCESS;
	}

	private static void send(int n ,Player _player) {
		Component name = Component.translatable("message.emeraldcraft.warning_" + n);
		_player.displayClientMessage(name, false);
	}

	@Override
	public boolean hurtEnemy(@ParametersAreNonnullByDefault ItemStack itemstack, @ParametersAreNonnullByDefault LivingEntity entity, @ParametersAreNonnullByDefault LivingEntity sourceEntity) {
		boolean r = super.hurtEnemy(itemstack, entity, sourceEntity);
		Level world = entity.level();
		DamageALL.execute(world, sourceEntity, 22.5f, 10, 10, 10, false);
		MobEffectInstance[] effects = new MobEffectInstance[]{
				new MobEffectInstance(MobEffects.POISON, 300, 5, false, false),
				new MobEffectInstance(MobEffects.BLINDNESS, 360, 3, false, false),
				new MobEffectInstance(ModMobEffects.SUPPRESS, 360, 5, false, false)
		};
		MobEffectALL.execute(world, entity.getX(), entity.getY(), entity.getZ(), effects, 15 ,15 ,15, sourceEntity);
		return r;
	}
}
