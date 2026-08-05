package com.zhouzhi.emeraldcraft.listening;

import com.zhouzhi.emeraldcraft.procedures.net.Use;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

public class TickListening {
    @SubscribeEvent
    public void serverTick(ServerTickEvent.Post event){
        if (!event.hasTime()){
            return;
        }
        event.getServer().getAllLevels().forEach(level -> {
            Use.EntityPause.tickPausedEntities(level,10);
        });
    }
}
