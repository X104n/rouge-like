package inf101.rogue101.game;

import inf101.rogue101.objects.IItem;

public class EmojiFactory {

	/**
	 * Set to true if using emojis – you must download and add the TrueType font
	 * file manually.
	 * <p>
	 * The font can be downloaded from
	 * <a href="http://vedlegg.uib.no/?id=13b7e208ea8ee38e5de34570a527e80c">http://vedlegg.uib.no/?id=13b7e208ea8ee38e5de34570a527e80c</a>
	 * (originally from http://users.teilar.gr/~g1951d/)
	 * <p>
	 * (Put the Symbola.ttf file in src/main/java/inf101/v20/gfx/fonts/ – do
	 * 'Refresh' on your project after adding the file)
	 */
	public static boolean USE_EMOJI = false;
	
	public String getEmoji(IItem item) {
		if (USE_EMOJI) {
			return item.getEmoji();
		} else {
			return item.getGraphicTextSymbol();
		}

	}
}
