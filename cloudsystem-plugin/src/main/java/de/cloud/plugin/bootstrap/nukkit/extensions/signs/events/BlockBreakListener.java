package de.cloud.plugin.bootstrap.nukkit.extensions.signs.events;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockSignPost;
import cn.nukkit.block.BlockWallSign;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import de.cloud.plugin.bootstrap.nukkit.extensions.signs.provider.CloudSignsProvider;
import de.cloud.plugin.bootstrap.nukkit.extensions.signs.utils.CloudSign;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        if (!player.hasPermission("cloud.signs") && block instanceof BlockWallSign) {
            return;
        }

        if (CloudSignsProvider.getRegisterSigns().containsKey(player.getName())) {
            if (block instanceof BlockSignPost) {
                event.setCancelled();
                if (CloudSignsProvider.isCloudSign(block)) {
                    player.sendMessage("§cThis sign is already in use.");
                    return;
                }

                CloudSignsProvider.registerSign(new CloudSign(block.getLocation(), CloudSignsProvider.getRegisterSigns().get(player.getName())));
                CloudSignsProvider.getRegisterSigns().remove(player.getName());

                player.sendMessage("§aCloudSign was registered successfully.");
            }
        } else if (CloudSignsProvider.getUnregisterSigns().contains(player.getName())) {
            if (block instanceof BlockSignPost) {
                event.setCancelled();

                String vectorString = String.format("%d:%d:%d", block.getFloorX(), block.getFloorY(), block.getFloorZ());
                if (!CloudSignsProvider.isCloudSign(block)) {
                    player.sendMessage("§cThis sign is NOT a cloud sign.");
                    return;
                }

                CloudSignsProvider.removeSign(CloudSignsProvider.getCloudSignByPosition(vectorString));
                CloudSignsProvider.getUnregisterSigns().remove(player.getName());
                player.sendMessage("§aYou have removed this cloud sign.");
            }
        } else if (CloudSignsProvider.getInfoSign().contains(player.getName())) {
            if (block instanceof BlockSignPost) {
                event.setCancelled();

                String vectorString = String.format("%d:%d:%d", block.getFloorX(), block.getFloorY(), block.getFloorZ());
                if (!CloudSignsProvider.isCloudSign(block)) {
                    player.sendMessage("§cThis sign is NOT a cloud sign.");
                    return;
                }

                //ToDo
            }
        }
    }
}
