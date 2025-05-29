package de.cuzim1tigaaa.guimanager;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.*;
import java.util.UUID;

/**
 * This enum allows you to add custom heads
 */
@Getter
public class CustomHead {

	public static CustomHead BLACK_ARROW_LEFT = new CustomHead(
			"MzdhZWU5YTc1YmYwZGY3ODk3MTgzMDE1Y2NhMGIyYTdkNzU1YzYzMzg4ZmYwMTc1MmQ1ZjQ0MTlmYzY0NSJ9fX0=",
			"37aee9a75bf0df7897183015cca0b2a7d755c63388ff01752d5f4419fc645");
	public static CustomHead BLACK_ARROW_RIGHT = new CustomHead(
			"NjgyYWQxYjljYjRkZDIxMjU5YzBkNzVhYTMxNWZmMzg5YzNjZWY3NTJiZTM5NDkzMzgxNjRiYWM4NGE5NmUifX19",
			"682ad1b9cb4dd21259c0d75aa315ff389c3cef752be3949338164bac84a96e");
	public static CustomHead BLACK_EXCLAMATION = new CustomHead(
			"ZGQyYWNkOWYyZGZjMmUwNWY2OWQ5NDFmZTk5NzBlOGMzZjA1NTI3YTAyYTkzODExNTc4OTFjOGRkYjhjZjMifX19",
			"dd2acd9f2dfc2e05f69d941fe9970e8c3f05527a02a9381157891c8ddb8cf3");

	/**
	 * The ID of the Head
     * -- GETTER --
     *  Getter method for the whole ID
     *
     * @return Returns the ID of the head as String

     */
	private final String ID;
    /**
     * -- GETTER --
     *  Getter method for the URL
     *
     * @return Returns the URL of the head as String
     */
    private final String URL;

	/**
	 * The constructor needs the ID of this head
	 * to generate it. The prefix the constructor provides is always the same
	 *
	 * @param id  The ID of the custom head
	 * @param url The URL of the custom head
	 * @see <a href="https://minecraft-heads.com">Also look here</a>
	 */
	public CustomHead(String id, String url) {
		this.ID = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv" + id;
		this.URL = "http://textures.minecraft.net/texture/" + url;
	}

    /**
	 * This method returns the playerprofile of a head
	 * @param head The head you want to get
	 * @return Returns the playerprofile of the head
	 */
	public static PlayerProfile getHead(CustomHead head) {
		try {
			URL url = URI.create(head.getURL()).toURL();
			PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID());
			PlayerTextures textures = profile.getTextures();
			textures.setSkin(url);
			profile.setTextures(textures);
			return profile;
		}catch(MalformedURLException exception) {
			return null;
		}
	}
}