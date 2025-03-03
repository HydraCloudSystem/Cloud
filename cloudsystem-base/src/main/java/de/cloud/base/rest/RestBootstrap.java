package de.cloud.base.rest;

import de.cloud.api.CloudAPI;
import de.cloud.api.logger.LogType;
import de.cloud.base.rest.routes.DefaultRoute;
import de.cloud.base.rest.routes.group.GroupListRoute;
import de.cloud.base.rest.routes.handler.CustomExceptionHandler;
import de.cloud.base.rest.routes.player.PlayerKickRoute;
import de.cloud.base.rest.routes.player.PlayerListRoute;
import de.cloud.base.rest.routes.player.PlayerTransferRoute;
import de.cloud.base.rest.routes.service.ServiceCreateRoute;
import de.cloud.base.rest.routes.service.ServiceListRoute;
import de.cloud.base.rest.routes.service.ServiceStartRoute;
import de.cloud.base.rest.routes.service.ServiceStopRoute;
import spark.Spark;

import java.io.IOException;
import java.net.ServerSocket;

public class RestBootstrap {
    private static String PASSWORD = null;

    public RestBootstrap(String restPassword) {
        PASSWORD = restPassword;

        if (isPortAvailable()) {
            Spark.port(8080);
        } else {
            throw new RuntimeException("Port is not available");
        }
        Spark.before((req, res) -> {
            String password = req.headers("X-Password");
            if (password == null || !password.equals(PASSWORD)) {
                Spark.halt(401, "Unauthorized");
            }
        });

        registerRoutes();

        Spark.exception(Exception.class, new CustomExceptionHandler());

        CloudAPI.getInstance().getLogger().log("§aRestAPI loaded successfully.", LogType.INFO);
    }

    private boolean isPortAvailable() {
        try (var serverSocket = new ServerSocket(8080)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    protected void registerRoutes() {
        Spark.get("/", new DefaultRoute());

        //Service
        Spark.get("/service/create/", new ServiceCreateRoute());
        Spark.get("/service/start/", new ServiceStartRoute());
        Spark.get("/service/stop/", new ServiceStopRoute());
        Spark.get("/service/list/", new ServiceListRoute());

        //Player
        Spark.get("/player/kick/", new PlayerKickRoute());
        Spark.get("/player/list/", new PlayerListRoute());
        Spark.get("/player/transfer/", new PlayerTransferRoute());

        //Group
        Spark.get("/group/list/", new GroupListRoute());
    }
}
