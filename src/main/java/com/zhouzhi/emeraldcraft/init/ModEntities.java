package com.zhouzhi.emeraldcraft.init;

import com.zhouzhi.emeraldcraft.EmeraldCraft;
import com.zhouzhi.emeraldcraft.entity.EmeraldProjectileEntity;
import com.zhouzhi.emeraldcraft.entity.ThrownInfernoEmeraldTrident;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, EmeraldCraft.MOD_ID);
	public static final DeferredHolder<EntityType<?>, EntityType<EmeraldProjectileEntity>> EMERALD_PROJECTILE = register("emerald_projectile",
			EntityType.Builder.<EmeraldProjectileEntity>of(EmeraldProjectileEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final DeferredHolder<EntityType<?>, EntityType<ThrownInfernoEmeraldTrident>> INFERNO_EMERALD_TRIDENT =
			REGISTRY.register("inferno_emerald_trident",
					() -> EntityType.Builder.<ThrownInfernoEmeraldTrident>of(
									ThrownInfernoEmeraldTrident::new, MobCategory.MISC)
							.sized(0.5F, 0.5F)
							.clientTrackingRange(4)
							.updateInterval(20)
							.build("inferno_emerald_trident")
			);

	// Start of user code block custom entities
	// End of user code block custom entities
	private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> entityTypeBuilder.build(registryname));
	}
}