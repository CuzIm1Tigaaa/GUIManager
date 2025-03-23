package de.cuzim1tigaaa.guimanager;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.profile.PlayerProfile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Getter
public class GuiManager {

    @Getter
    private static final Map<UUID, PlayerProfile> playerProfiles = new HashMap<>();
    private final JavaPlugin plugin;

    public GuiManager(JavaPlugin plugin) {
        this(plugin, true);
    }

    public GuiManager(JavaPlugin plugin, boolean updatePlayerCache) {
        this.plugin = plugin;

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
}