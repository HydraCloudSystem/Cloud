package de.cloud.api.event.player;

import de.cloud.api.player.CloudPlayer;
import de.cloud.api.event.CloudEvent;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class DefaultPlayerEvent implements CloudEvent {

    private final CloudPlayer player;

    public DefaultPlayerEvent(final @NotNull CloudPlayer player) {
        this.player = player;
    }

}
