package de.cloud.modules.api;

import java.io.Serializable;

public interface ILoadedModule extends Serializable {

    String getName();

    String getFileName();

    String getAuthor();

    String getVersion();

    String getDescription();

    String getMainClass();

    boolean isReloadable();
}
