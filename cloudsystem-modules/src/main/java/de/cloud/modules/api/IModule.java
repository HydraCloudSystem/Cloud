package de.cloud.modules.api;

public interface IModule {

    /**
     * Called when the module will be enabled.
     */
    void onEnable();

    /**
     * Called when the module will be disabled.
     */
    void onDisable();
}
