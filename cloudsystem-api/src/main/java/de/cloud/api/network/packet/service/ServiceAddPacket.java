package de.cloud.api.network.packet.service;

import de.cloud.api.service.CloudService;
import de.cloud.network.packet.Packet;
import de.cloud.network.packet.NetworkBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ServiceAddPacket implements Packet {

    private CloudService service;

    @Override
    public void write(@NotNull NetworkBuf byteBuf) {
        this.service.write(byteBuf);
    }

    @Override
    public void read(@NotNull NetworkBuf byteBuf) {
        this.service = CloudService.read(byteBuf);
    }

}
