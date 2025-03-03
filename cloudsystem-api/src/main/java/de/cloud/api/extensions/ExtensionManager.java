package de.cloud.api.extensions;

import de.cloud.api.CloudAPI;
import de.cloud.api.cache.InGameExtension;
import de.cloud.api.json.Document;

import java.io.File;

public class ExtensionManager {

    public void setModuleState(String module, boolean enabled) {
        CloudAPI.getExtenstionConfiguartion().getExtensions().put(module, enabled);
        saveConfig();
    }

    public boolean getModuleState(String module) {
        return InGameExtension.getModuleState(module);
    }

    private void saveConfig() {
        File file = new File("extensions.json");
        new Document(CloudAPI.getExtenstionConfiguartion()).write(file);
    }

    public void loadAllExtensions() {
        CloudAPI.getExtenstionConfiguartion().getExtensions().forEach(InGameExtension::setModuleState);
    }
}
