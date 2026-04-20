package com.zhouzhi.emeraldcraft.procedures.enchantment;

import com.zhouzhi.emeraldcraft.init.ModEnchantments;
import com.zhouzhi.emeraldcraft.init.ModItems;
import com.zhouzhi.emeraldcraft.init.ModTags;
import com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import static com.zhouzhi.emeraldcraft.procedures.compress.TagChange.getOrCreateComponent;

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
            
            boolean isLavaEmerald = weapon.is(ModItems.LAVA_EMERALD) ||
                    weapon.is(ModTags.LAVA_EMERALD_TOOLS);
            
            //Heavy
            {
                Holder<Enchantment> EnchantmentHolder = registryAccess
                        .lookupOrThrow(Registries.ENCHANTMENT)
                        .getOrThrow(ModEnchantments.HEAVY);

                int enchantmentLevel = weapon.getEnchantmentLevel(EnchantmentHolder);
                
                if (isLavaEmerald) {
                    enchantmentLevel += 3;
                }

                if (enchantmentLevel > 0 && attacker.fallDistance > 3f) {
                    float bonusDamage;
                    if (enchantmentLevel == 1) {
                        bonusDamage = 2.2f * (attacker.fallDistance - 2.0f);
                    } else if (enchantmentLevel == 2) {
                        bonusDamage = 3.2f * (attacker.fallDistance - 2.2f);
                    } else if (enchantmentLevel == 3) {
                        bonusDamage = 4.0f * (attacker.fallDistance - 2.2f);
                    } else {
                        bonusDamage = (4f + 0.1f * enchantmentLevel) * (attacker.fallDistance - 2.2f);
                    }
                    newDamage += bonusDamage;
                    event.setNewDamage(newDamage);
                    attacker.resetFallDistance();
                }
            }
            
            //Lighting
            {
                Holder<Enchantment> EnchantmentHolder = registryAccess
                        .lookupOrThrow(Registries.ENCHANTMENT)
                        .getOrThrow(ModEnchantments.LIGHTING);

                int enchantmentLevel = weapon.getEnchantmentLevel(EnchantmentHolder);

                if (isLavaEmerald && enchantmentLevel == 0) {
                    enchantmentLevel = 2;
                }

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
                        Vec3 vec3 = SimpleUse.getPointAtDistance(
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
                        .getOrThrow(ModEnchantments.VOID_EMERALD_ATTACH);

                int enchantmentLevel = weapon.getEnchantmentLevel(EnchantmentHolder);

                boolean isVoidEmerald = weapon.is(ModItems.VOID_EMERALD) ||
                        weapon.is(ModTags.VOID_EMERALD_TOOLS);

                if (isVoidEmerald) {
                    enchantmentLevel += 1;
                    if (!getOrCreateComponent(weapon, "Void_Open", true)) {
                        enchantmentLevel += 3;
                    }
                }

                if (enchantmentLevel > 0) {
                    float bonusDamage = enchantmentLevel * newDamage * 0.25f;
                    newDamage += bonusDamage;
                    event.setNewDamage(newDamage);
                }
            }
        }
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

        boolean isLavaEmerald = weapon.is(ModItems.LAVA_EMERALD) ||
                weapon.is(ModTags.LAVA_EMERALD_TOOLS);
        
        //Lighting
        {
            Holder<Enchantment> EnchantmentHolder = registryAccess
                    .lookupOrThrow(Registries.ENCHANTMENT)
                    .getOrThrow(ModEnchantments.LIGHTING);

            int enchantmentLevel = weapon.getEnchantmentLevel(EnchantmentHolder);
            
            if (isLavaEmerald) {
                enchantmentLevel += 2;
            }
            
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
                    .getOrThrow(ModEnchantments.REBOUND);

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
