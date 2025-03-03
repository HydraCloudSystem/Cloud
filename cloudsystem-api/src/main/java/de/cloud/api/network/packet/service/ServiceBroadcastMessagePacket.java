package de.cloud.api.network.packet.service;

import de.cloud.network.packet.NetworkBuf;
import de.cloud.network.packet.Packet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceBroadcastMessagePacket implements Packet {

    private String message;

    @Override
    public void write(@NotNull NetworkBuf byteBuf) {
        byteBuf
            .writeString(this.message);
    }

    @Override
    public void read(@NotNull NetworkBuf byteBuf) {
        this.message = byteBuf.readString();
    }
}
