package de.cuzim1tigaaa.guimanager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.profile.PlayerProfile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class GuiManager {

    private final JavaPlugin plugin;
    private final GuiUtils guiUtils;
    private final ItemUtils itemUtils;

    private final Map<UUID, PlayerProfile> playerProfiles = new HashMap<>();

    public GuiManager(JavaPlugin plugin) {
        this(plugin, true);
    }

    public GuiManager(JavaPlugin plugin, boolean updatePlayerCache) {
        this.plugin = plugin;
        this.guiUtils = new GuiUtils(this);
        this.itemUtils = new ItemUtils(this);

        if(updatePlayerCache)
            updatePlayerCache();
    }

    public void updatePlayerCache() {
        Arrays.stream(Bukkit.getOfflinePlayers()).forEach(player -> {
            if(!player.isOnline() && !player.hasPlayedBefore())
                return;

            UUID uuid = player.getUniqueId();
            PlayerProfile profile = Bukkit.createPlayerProfile(uuid, player.getName());
            if(profile.getTextures().isEmpty()) {
                try {
                    profile = profile.update().get();
                } catch(InterruptedException | ExecutionException e) {
                    plugin.getLogger().warning("Failed to update player profile for " + player.getName());
                    return;
                }
            }
            playerProfiles.put(uuid, profile);
        });
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public GuiUtils getGuiUtils() {
        return guiUtils;
    }

    public ItemUtils getItemUtils() {
        return itemUtils;
    }

    public Map<UUID, PlayerProfile> getPlayerProfiles() {
        return playerProfiles;
    }
}
