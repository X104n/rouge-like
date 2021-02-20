package inf101.v20.rogue101;

import java.util.Arrays;
import java.util.List;

public class AppInfo {
	/**
	 * Your application name.
	 */
	public static final String APP_NAME = "Rogue-101";
	/**
	 * Your name.
	 */
	public static final String APP_DEVELOPER = "INF101-student";
	/**
	 * A short description.
	 */
	public static final String APP_DESCRIPTION = "Implementasjon av inf101.v21.sem1";
	/**
	 * List of extra credits (e.g. for media sources)
	 */
	public static final List<String> APP_EXTRA_CREDITS = Arrays.asList(//
	/* "Graphics by Foo Bar" */
	);
	/**
	 * Help text. Could be used for an in-game help page, perhaps.
	 * <p>
	 * Use <code>\n</code> for new lines, <code>\f<code> between pages (if
	 * multi-page).
	 */
	public static final String APP_HELP = "";
	
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
}
