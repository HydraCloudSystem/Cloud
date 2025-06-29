package de.cloud.api.event;

import de.cloud.api.event.service.CloudServiceRegisterEvent;
import de.cloud.api.event.service.CloudServiceRemoveEvent;
import de.cloud.api.network.packet.service.ServiceAddPacket;
import de.cloud.api.network.packet.service.ServiceRemovePacket;
import de.cloud.api.CloudAPI;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public final class SimpleEventHandler implements EventHandler {
    private final Map<Class<? extends CloudEvent>, List<Consumer<? extends CloudEvent>>> events = new ConcurrentHashMap<>();

    public SimpleEventHandler() {
        final var packetHandler = CloudAPI.getInstance().getPacketHandler();

        // service register event
        packetHandler.registerPacketListener(ServiceAddPacket.class, (channelHandlerContext, packet) ->
            this.call(new CloudServiceRegisterEvent(packet.getService())));

        // service remove event
        packetHandler.registerPacketListener(ServiceRemovePacket.class, (channelHandlerContext, packet) ->
            this.call(new CloudServiceRemoveEvent(packet.getService())));
    }

    public <T extends CloudEvent> void registerEvent(@NotNull Class<T> clazz, @NotNull Consumer<T> event) {
        events.computeIfAbsent(clazz, k -> new CopyOnWriteArrayList<>()).add(event);
    }

    @SuppressWarnings("unchecked")
    public <T extends CloudEvent> void call(@NotNull T event) {
        List<Consumer<? extends CloudEvent>> consumers = events.get(event.getClass());
        if (consumers != null) {
            for (Consumer<? extends CloudEvent> consumer : consumers) {
                ((Consumer<T>) consumer).accept(event);
            }
        }
    }
}
