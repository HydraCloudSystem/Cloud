package de.cloud.api.network.packet.player;

import de.cloud.network.packet.NetworkBuf;
import de.cloud.network.packet.Packet;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class CloudPlayerDisconnectPacket implements Packet {

    private UUID uuid;

    @Override
    public void write(@NotNull NetworkBuf byteBuf) {
        byteBuf.writeUUID(this.uuid);
    }

    @Override
    public void read(@NotNull NetworkBuf byteBuf) {
        this.uuid = byteBuf.readUUID();
    }

    public UUID getUniqueId() {
        return this.uuid;
    }

}
