package de.cloud.api.version;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
public final class GameServerVersion {

    public static final Map<String, GameServerVersion> VERSIONS = new HashMap<>();

    public static final GameServerVersion WATERDOG = new GameServerVersion(
        "waterdog", "latest", true, "https://github.com/WaterdogPE/WaterdogPE/releases/download/latest/Waterdog.jar"
    );

    public static final GameServerVersion NUKKIT = new GameServerVersion(
        "nukkit", "latest", false, "https://repo.opencollab.dev/api/maven/latest/file/maven-snapshots/cn/nukkit/nukkit/1.0-SNAPSHOT?extension=jar"
    );

    private final String url;
    private final String title;
    private final String version;
    @Getter
    private final boolean proxy;

    private GameServerVersion(final @NotNull String title, final @NotNull String version, final boolean proxy, final @NotNull String url) {
        this.url = url;
        this.title = title;
        this.version = version;
        this.proxy = proxy;
        VERSIONS.put(this.getName(), this);
    }

    public static GameServerVersion getVersionByName(final @NotNull String value) {
        return VERSIONS.get(value);
    }

    public @NotNull String getName() {
        return String.format("%s%s", this.title, !Objects.equals(this.version, "latest") ? "-" + this.version : "");
    }

    public String getJar() {
        return String.format("%s%s.jar", this.title, !Objects.equals(this.version, "latest") ? "-" + this.version : "");
    }
}
