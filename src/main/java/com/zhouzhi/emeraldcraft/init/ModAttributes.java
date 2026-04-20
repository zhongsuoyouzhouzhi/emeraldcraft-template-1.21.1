package com.zhouzhi.emeraldcraft.init;

import com.zhouzhi.emeraldcraft.EmeraldCraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber
public class ModAttributes {
	public static final DeferredRegister<Attribute> REGISTRY = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, EmeraldCraft.MOD_ID);
	public static final DeferredHolder<Attribute, Attribute> LAUNCHED = REGISTRY.register("launched", () -> new RangedAttribute("attribute.emeraldcraft.launched", 0, 0, 1).setSyncable(true).setSentiment(Attribute.Sentiment.NEUTRAL));
	public static final DeferredHolder<Attribute, Attribute> EVOLVED = REGISTRY.register("evolved", () -> new RangedAttribute("attribute.emeraldcraft.evolved", 1, 1, 5).setSyncable(true));

	@SubscribeEvent
	public static void addAttributes(EntityAttributeModificationEvent event) {
		event.getTypes().forEach(entity -> event.add(entity, LAUNCHED));
		event.add(EntityType.PLAYER, EVOLVED);
	}
}