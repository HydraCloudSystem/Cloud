package de.cloud.api;

import de.cloud.api.event.EventHandler;
import de.cloud.api.event.SimpleEventHandler;
import de.cloud.api.extensions.ExtensionManager;
import de.cloud.api.extensions.ExtenstionConfiguartion;
import de.cloud.api.extensions.signs.CloudSignsConfiguration;
import de.cloud.api.groups.GroupManager;
import de.cloud.api.json.Document;
import de.cloud.api.logger.Logger;
import de.cloud.api.network.packet.CustomPacket;
import de.cloud.api.network.packet.QueryPacket;
import de.cloud.api.network.packet.RedirectPacket;
import de.cloud.api.network.packet.ResponsePacket;
import de.cloud.api.network.packet.group.ServiceGroupCacheUpdatePacket;
import de.cloud.api.network.packet.group.ServiceGroupExecutePacket;
import de.cloud.api.network.packet.group.ServiceGroupUpdatePacket;
import de.cloud.api.network.packet.init.CacheInitPacket;
import de.cloud.api.network.packet.player.*;
import de.cloud.api.network.packet.service.*;
import de.cloud.api.player.PlayerManager;
import de.cloud.api.service.ServiceManager;
import de.cloud.network.packet.PacketHandler;
import de.cloud.network.packet.auth.NodeHandshakeAuthenticationPacket;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
public abstract class CloudAPI {

    @Getter
    protected static CloudAPI instance;
    @Getter
    protected Logger logger;

    private final CloudAPIType cloudAPITypes;
    protected final PacketHandler packetHandler;
    protected final EventHandler eventHandler;

    @Getter public static File cloudSignsFile;
    @Getter public static CloudSignsConfiguration cloudSignsConfiguration;
    @Getter public static ExtenstionConfiguartion extenstionConfiguartion;

    protected CloudAPI(final CloudAPIType cloudAPIType) {
        instance = this;

        this.cloudAPITypes = cloudAPIType;
        this.packetHandler = new PacketHandler(
            NodeHandshakeAuthenticationPacket.class, QueryPacket.class, RedirectPacket.class, CustomPacket.class, ResponsePacket.class, ServiceMemoryRequest.class,
            ServiceGroupCacheUpdatePacket.class, ServiceGroupExecutePacket.class, ServiceGroupUpdatePacket.class,
            CacheInitPacket.class, CloudPlayerDisconnectPacket.class, CloudPlayerKickPacket.class,
            CloudPlayerLoginPacket.class, CloudPlayerMessagePacket.class, CloudPlayerSendServicePacket.class,
            CloudPlayerUpdatePacket.class, ServiceAddPacket.class, ServiceRemovePacket.class,
            ServiceRequestShutdownPacket.class, ServiceUpdatePacket.class, ServiceCopyRequestPacket.class, ServiceStartPacket.class, ServiceBroadcastMessagePacket.class);
        this.eventHandler = new SimpleEventHandler();

        var extensionsFile = resolvePath("extensions.json").toFile();
        cloudSignsFile = resolvePath("cloudsigns.json").toFile();
        loadExtensionsConfig(extensionsFile);
        loadSignsConfig(cloudSignsFile);
        (new ExtensionManager()).loadAllExtensions();
    }

    public static Path getCloudPath() {
        try {
            Path jarPath = Paths.get(CloudAPI.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
            return jarPath.getParent();
        } catch (Exception e) {
            throw new IllegalStateException("Unable to determine main cloud path.", e);
        }
    }

    public static Path resolvePath(String relativePath) {
        return getCloudPath().resolve(relativePath);
    }

    private static void loadExtensionsConfig(@NotNull File file) {
        if (file.exists()) {
            extenstionConfiguartion = new Document(file).get(ExtenstionConfiguartion.class);
            return;
        }
        new Document(extenstionConfiguartion = new ExtenstionConfiguartion()).write(file);
    }

    private static void loadSignsConfig(@NotNull File file) {
        if (file.exists()) {
            cloudSignsConfiguration = new Document(file).get(CloudSignsConfiguration.class);
            return;
        }
        new Document(cloudSignsConfiguration = new CloudSignsConfiguration()).write(file);
    }

    /**
     * @return the group manager
     */
    public abstract @NotNull GroupManager getGroupManager();

    /**
     * @return the service manager
     */
    public abstract @NotNull ServiceManager getServiceManager();

    /**
     * @return the player manager
     */
    public abstract @NotNull PlayerManager getPlayerManager();
}
