package com.zhouzhi.emeraldcraft.network;

import com.zhouzhi.emeraldcraft.EmeraldCraft;
import com.zhouzhi.emeraldcraft.procedures.SpecialSkillPressed;
import net.minecraft.core.SectionPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber
public record SpecialSkillMessage(int eventType, int pressed_ms) implements CustomPacketPayload {
	public static final Type<SpecialSkillMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "key_special_skill"));
	public static final StreamCodec<RegistryFriendlyByteBuf, SpecialSkillMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, SpecialSkillMessage message) -> {
		buffer.writeInt(message.eventType);
		buffer.writeInt(message.pressed_ms);
	}, (RegistryFriendlyByteBuf buffer) -> new SpecialSkillMessage(buffer.readInt(), buffer.readInt()));

	@Override
	public @NotNull Type<SpecialSkillMessage> type() {
		return TYPE;
	}

	public static void handleData(final SpecialSkillMessage message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> pressAction(context.player(), message.eventType, message.pressed_ms)).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void pressAction(Player entity, int type, int pressed_ms) {
		Level world = entity.level();
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		// security measure to prevent arbitrary chunk generation
		if (!world.getChunkSource().hasChunk(SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z)))
			return;
		if (type == 0) {
			SpecialSkillPressed.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		EmeraldCraft.addNetworkMessage(SpecialSkillMessage.TYPE, SpecialSkillMessage.STREAM_CODEC, SpecialSkillMessage::handleData);
	}
}