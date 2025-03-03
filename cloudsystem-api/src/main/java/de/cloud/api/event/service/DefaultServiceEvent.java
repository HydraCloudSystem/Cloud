package de.cloud.api.event.service;

import de.cloud.api.service.CloudService;
import de.cloud.api.event.CloudEvent;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class DefaultServiceEvent implements CloudEvent {

    private final CloudService service;

    public DefaultServiceEvent(final @NotNull CloudService service) {
        this.service = service;
    }

}
