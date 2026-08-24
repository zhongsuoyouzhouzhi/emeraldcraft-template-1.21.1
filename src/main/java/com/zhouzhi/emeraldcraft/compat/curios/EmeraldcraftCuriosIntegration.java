package com.zhouzhi.emeraldcraft.compat.curios;

import com.zhouzhi.emeraldcraft.init.ModItems;
import com.zhouzhi.emeraldcraft.init.ModMobEffects;
import com.zhouzhi.emeraldcraft.procedures.net.Use;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

@SuppressWarnings("removal")
@EventBusSubscriber(modid = "emeraldcraft")
public class EmeraldcraftCuriosIntegration {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(
                CuriosCapability.ITEM,
                (stack, context) -> new ICurio() {
                    @Override
                    public ItemStack getStack() {
                        return stack;
                    }

                    @Override
                    public void curioTick(SlotContext slotContext) {
                        if (slotContext.entity() instanceof LivingEntity living) {
                            living.addEffect(new MobEffectInstance(ModMobEffects.EMERALD_ATTACH,40,39,false,false));
                            living.addEffect(new MobEffectInstance(ModMobEffects.EMERALD_BONUS,20,19,false,false));
                            living.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.FIRE_RESISTANCE,40,0,false,false));
                            if (living instanceof Player player) {
                                if (player.getFoodData().getFoodLevel() < 20) {
                                    player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel() + 1);
                                }
                                if (player.getFoodData().getSaturationLevel() < 20) {
                                    player.getFoodData().setSaturation(player.getFoodData().getSaturationLevel() + 1);
                                }
                            }
                            if (living.isUnderWater()) {
                                if (living.getAirSupply() <= 10) {
                                    living.setAirSupply(300);
                                }
                            }
                        }
                    }

                    @Override
                    public boolean canEquip(SlotContext slotContext) {
                        return true;
                    }
                },
                ModItems.GENESIS_EMERALD.get()
        );

        event.registerItem(
                CuriosCapability.ITEM,
                (stack, context) -> new ICurio() {
                    @Override
                    public ItemStack getStack() {
                        return stack;
                    }
                    @Override
                    public void curioTick(SlotContext slotContext) {
                        if (slotContext.entity() instanceof LivingEntity living) {
                            if (living.isOnFire() || living.isInLava() || living.level().getBlockState(living.getOnPos()).is(Blocks.MAGMA_BLOCK)) {
                                living.extinguishFire();
                                living.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 39,false,false));
                                living.addEffect(new MobEffectInstance(ModMobEffects.EMERALD_ATTACH,200,59,false,false));
                                living.addEffect(new MobEffectInstance(ModMobEffects.EMERALD_BONUS,200,49,false,false));
                                living.addEffect(new MobEffectInstance(MobEffects.HEAL,1,4,false,false));
                            }
                            living.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20, 0));
                        }
                    }
                    @Override
                    public boolean canEquip(SlotContext slotContext) {
                        return true;
                    }
                },
                ModItems.INFERNO_EMERALD.get()
        );

        event.registerItem(
                CuriosCapability.ITEM,
                (stack, context) -> new ICurio() {
                    @Override
                    public ItemStack getStack() {
                        return stack;
                    }
                    @Override
                    public void curioTick(SlotContext slotContext) {
                        if (slotContext.entity() instanceof LivingEntity living) {
                            living.addEffect(new MobEffectInstance(ModMobEffects.VOID, 10, 8,false,false));
                            if (living.isInWaterRainOrBubble()) {
                                Use.subDamageValue(stack,1);
                            }
                        }
                    }
                    @Override
                    public boolean canEquip(SlotContext slotContext) {
                        return true;
                    }
                },
                ModItems.OBLIVION_EMERALD.get()
        );
    }
}
