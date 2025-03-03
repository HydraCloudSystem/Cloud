package de.cloud.api.event.group;

import de.cloud.api.groups.ServiceGroup;
import de.cloud.api.event.CloudEvent;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class DefaultServiceGroupEvent implements CloudEvent {

    private final ServiceGroup serviceGroup;

    public DefaultServiceGroupEvent(final @NotNull ServiceGroup serviceGroup) {
        this.serviceGroup = serviceGroup;
    }

}
