package com.zhouzhi.emeraldcraft;

import com.zhouzhi.emeraldcraft.init.*;
import com.zhouzhi.emeraldcraft.listening.AttackListening;
import com.zhouzhi.emeraldcraft.listening.MiningListening;
import com.zhouzhi.emeraldcraft.procedures.enchantment.EnchantmentEffect;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.Tuple;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.util.thread.SidedThreadGroups;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;

@Mod("emeraldcraft")
public class EmeraldCraft {
    public static final Logger LOGGER = LogManager.getLogger(EmeraldCraft.class);
    public static final String MOD_ID = "emeraldcraft";

    public EmeraldCraft(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(new EnchantmentEffect());
        NeoForge.EVENT_BUS.register(new AttackListening());
        NeoForge.EVENT_BUS.register(new MiningListening());
        modEventBus.addListener(this::registerNetworking);
        modEventBus.addListener(this::onGatherData);
        EmeraldcraftBlocks.REGISTRY.register(modEventBus);
        EmeraldcraftItems.REGISTRY.register(modEventBus);
        EmeraldcraftEntities.REGISTRY.register(modEventBus);
        EmeraldcraftTabs.REGISTRY.register(modEventBus);
        EmeraldcraftPotions.REGISTRY.register(modEventBus);
        EmeraldcraftMobEffects.REGISTRY.register(modEventBus);
        EmeraldcraftAttributes.REGISTRY.register(modEventBus);

    }

    private static boolean networkingRegistered = false;
    private static final Map<CustomPacketPayload.Type<?>, NetworkMessage<?>> MESSAGES = new HashMap<>();

    private record NetworkMessage<T extends CustomPacketPayload>(StreamCodec<? extends FriendlyByteBuf, T> reader, IPayloadHandler<T> handler) {
    }

    public static <T extends CustomPacketPayload> void addNetworkMessage(CustomPacketPayload.Type<T> id, StreamCodec<? extends FriendlyByteBuf, T> reader, IPayloadHandler<T> handler) {
        if (networkingRegistered)
            throw new IllegalStateException("Cannot register new network messages after networking has been registered");
        MESSAGES.put(id, new NetworkMessage<>(reader, handler));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void registerNetworking(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(MOD_ID);
        MESSAGES.forEach((id, networkMessage) -> registrar.playBidirectional(id, ((NetworkMessage) networkMessage).reader(), ((NetworkMessage) networkMessage).handler()));
        networkingRegistered = true;
    }

    private static final Collection<Tuple<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

    public static void queueServerWork(int tick, Runnable action) {
        if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER)
            workQueue.add(new Tuple<>(action, tick));
    }

    @SubscribeEvent
    public void tick(ServerTickEvent.Post event) {
        List<Tuple<Runnable, Integer>> actions = new ArrayList<>();
        workQueue.forEach(work -> {
            work.setB(work.getB() - 1);
            if (work.getB() == 0)
                actions.add(work);
        });
        actions.forEach(e -> e.getA().run());
        workQueue.removeAll(actions);
    }

    public void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        RegistrySetBuilder registryBuilder = new RegistrySetBuilder()
                .add(Registries.ENCHANTMENT, EmeraldcraftEnchantments::bootstrap);

        generator.addProvider(
                event.includeServer(),
                new DatapackBuiltinEntriesProvider(
                        output,
                        lookupProvider,
                        registryBuilder,
                        Set.of(MOD_ID)
                )
        );
    }
}