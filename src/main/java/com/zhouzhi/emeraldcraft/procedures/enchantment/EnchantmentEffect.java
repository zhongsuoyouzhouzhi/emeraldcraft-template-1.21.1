package com.zhouzhi.emeraldcraft.procedures.enchantment;

import com.zhouzhi.emeraldcraft.init.EmeraldcraftEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class EnchantmentEffect {
    @SubscribeEvent
    public void onLivingDamage(LivingDamageEvent.Pre event) {
        LivingEntity target = event.getEntity();
        Level level = event.getEntity().getCommandSenderWorld();
        if (level.isClientSide()) {
            return;
        }
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            ItemStack weapon = attacker.getMainHandItem();

            RegistryAccess registryAccess = level.registryAccess();

            float newDamage = event.getOriginalDamage();
            //Lighting
            {
                Holder<Enchantment> EnchantmentHolder = registryAccess
                        .lookupOrThrow(Registries.ENCHANTMENT)
                        .getOrThrow(EmeraldcraftEnchantments.LIGHTING);

                int enchantmentLevel = weapon.getEnchantmentLevel(EnchantmentHolder);
                if (enchantmentLevel > 0) {
                    float Brightness = level.getBrightness(
                            LightLayer.BLOCK,
                            target.blockPosition()
                    );

                    float bonusDamage = 0.1f * newDamage + enchantmentLevel * Brightness * 0.15f;
                    if (Brightness >= 14f)
                        bonusDamage *= 2.4f;
                    newDamage += bonusDamage;
                    event.setNewDamage(newDamage);
                    if (level instanceof ServerLevel serverLevel) {
                        Vec3 vec3 = getPointAtDistance(
                                target.getX(),target.getY() + 0.25,target.getZ(),
                                attacker.getX(),attacker.getY(),attacker.getZ(),
                                0.2
                        );

                        serverLevel.sendParticles(
                                ParticleTypes.GLOW,
                                vec3.x,
                                vec3.y,
                                vec3.z,
                                128 * (int) Brightness,
                                0.5, 0.75, 0.5,
                                0.075
                        );
                    }
                }
            }

            //Void Emerald Attach
            {
                Holder<Enchantment> EnchantmentHolder = registryAccess
                        .lookupOrThrow(Registries.ENCHANTMENT)
                        .getOrThrow(EmeraldcraftEnchantments.VOID_EMERALD_ATTACH);

                int enchantmentLevel = weapon.getEnchantmentLevel(EnchantmentHolder);

                if (enchantmentLevel > 0) {
                    float bonusDamage = enchantmentLevel * newDamage * 0.25f;
                    newDamage += bonusDamage;
                    event.setNewDamage(newDamage);
                }
            }
        }
    }
    public static Vec3 getPointAtDistance(
            double x1, double y1, double z1,
            double x2, double y2, double z2,
            double distance) {

        double dx = x2 - x1;
        double dy = y2 - y1;
        double dz = z2 - z1;
        double totalDistance = Math.sqrt(dx * dx + dy * dy + dz * dz);

        if (totalDistance == 0) {
            return new Vec3(x1, y1, z1);
        }

        double ratio = distance / totalDistance;

        double targetX = x1 + dx * ratio;
        double targetY = y1 + dy * ratio;
        double targetZ = z1 + dz * ratio;

        return new Vec3(targetX, targetY, targetZ);
    }

    @SubscribeEvent
    public void right_clickOn(PlayerInteractEvent.RightClickItem event){
        Player player = event.getEntity();
        Level level = player.getCommandSenderWorld();
        if (level.isClientSide()) {
            return;
        }
        ItemStack weapon = player.getMainHandItem();

        RegistryAccess registryAccess = level.registryAccess();
        //Lighting
        {
            Holder<Enchantment> EnchantmentHolder = registryAccess
                    .lookupOrThrow(Registries.ENCHANTMENT)
                    .getOrThrow(EmeraldcraftEnchantments.LIGHTING);

            int enchantmentLevel = weapon.getEnchantmentLevel(EnchantmentHolder);
            if (enchantmentLevel > 0) {
                float Brightness = level.getBrightness(
                        LightLayer.BLOCK,
                        player.getOnPos().above()
                );
                if (Brightness < 5f && level.getBlockState(player.getOnPos().above()).equals(Blocks.AIR.defaultBlockState()) && !level.getBlockState(player.getOnPos()).equals(Blocks.AIR.defaultBlockState())) {
                    double damage = enchantmentLevel == 1 ? 0.0075 : 0.0025;
                    damage *= weapon.getMaxDamage();
                    if (level instanceof ServerLevel _level)
                        weapon.hurtAndBreak(damage < 1 ? 1 : (int) damage , _level, player,a -> {
                        });
                    level.setBlockAndUpdate(player.getOnPos().above(), Blocks.TORCH.defaultBlockState());
                }
            }
        }
    }

    @SubscribeEvent
    public void shield(LivingShieldBlockEvent event) {
        if (event.isCanceled()) return;

        LivingEntity target = event.getEntity();
        Level level = event.getEntity().getCommandSenderWorld();
        if (level.isClientSide()) {
            return;
        }

        ItemStack weapon = target.getUseItem();

        RegistryAccess registryAccess = level.registryAccess();
        //Rebound
        {
            Holder<Enchantment> EnchantmentHolder = registryAccess
                    .lookupOrThrow(Registries.ENCHANTMENT)
                    .getOrThrow(EmeraldcraftEnchantments.REBOUND);

            int enchantmentLevel = weapon.getEnchantmentLevel(EnchantmentHolder);



            if (enchantmentLevel > 0 && event.getDamageSource().getEntity() instanceof LivingEntity entity) {
                entity.hurt(event.getDamageSource(), enchantmentLevel * 0.25f * event.getOriginalBlockedDamage());
                event.setShieldDamage((1 - enchantmentLevel * 0.25f) * event.getOriginalBlockedDamage());
                if (level instanceof ServerLevel serverLevel)
                    weapon.hurtAndBreak(enchantmentLevel < 4 ? 2 : 1, serverLevel, target, a -> {});
            }
        }
    }
}
