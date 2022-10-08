package de.cuzim1tigaaa.guimanager;

/**
 * This enum allows you to add custom heads
 */
public class CustomHead {

	public static CustomHead BLACK_ARROW_LEFT = new CustomHead("MzdhZWU5YTc1YmYwZGY3ODk3MTgzMDE1Y2NhMGIyYTdkNzU1YzYzMzg4ZmYwMTc1MmQ1ZjQ0MTlmYzY0NSJ9fX0=");
	public static CustomHead BLACK_ARROW_RIGHT = new CustomHead("NjgyYWQxYjljYjRkZDIxMjU5YzBkNzVhYTMxNWZmMzg5YzNjZWY3NTJiZTM5NDkzMzgxNjRiYWM4NGE5NmUifX19");
	public static CustomHead BLACK_EXCLAMATION = new CustomHead("ZGQyYWNkOWYyZGZjMmUwNWY2OWQ5NDFmZTk5NzBlOGMzZjA1NTI3YTAyYTkzODExNTc4OTFjOGRkYjhjZjMifX19");

	/**
	 * The ID of the Head
	 */
	private final String ID;

	/**
	 * The constructor needs the ID of this head
	 * to generate it. The prefix the constructor provides is always the same
	 * @see <a href="https://minecraft-heads.com">Also look here</a>
	 * @param id The ID of the custom head
	 */
	public CustomHead(String id) {
		String prefix = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv";
		this.ID = prefix + id;
	}

	/**
	 * Getter method for the whole ID
	 * @return Returns the ID of the head as String
	 */
	public String getID() { return ID; }
}