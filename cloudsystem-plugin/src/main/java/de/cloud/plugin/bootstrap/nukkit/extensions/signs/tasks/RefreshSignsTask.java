package de.cloud.plugin.bootstrap.nukkit.extensions.signs.tasks;

import cn.nukkit.scheduler.Task;
import de.cloud.plugin.bootstrap.nukkit.extensions.signs.provider.CloudSignsProvider;
import de.cloud.plugin.bootstrap.nukkit.extensions.signs.utils.CloudSign;

public class RefreshSignsTask extends Task {

    @Override
    public void onRun(int i) {
        for (CloudSign cloudSign : CloudSignsProvider.getSigns().values()) {
            if (cloudSign != null && cloudSign.isNearPlayers()) cloudSign.refreshSign();
        }
    }
}
