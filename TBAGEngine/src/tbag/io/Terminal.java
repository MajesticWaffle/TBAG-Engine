package tbag.io;

import java.awt.Color;
import java.util.ArrayList;

import tbag.location.Location;
import tbag.management.Trade;

/**
 * Contains methods for outputting to the console
 * @author Trevor Skupien
 */
public class Terminal {
	
	public Location lastLocation = null;
	
	/**
	 * Uses Wad's optimum 16 color palette
	 */
	public Color[] colors = {
		new Color(0,0,0),
		new Color(0,0,170),
		new Color(0,170,0),
		new Color(0,170,170),
		new Color(170,0,0),
		new Color(170,0,170),
		new Color(255,170,0),
		new Color(170,170,170),
		new Color(85,85,85),
		new Color(85,85,255),
		new Color(85,255,85),
		new Color(85,255,255),
		new Color(255,85,85),
		new Color(255,85,255),
		new Color(255,255,85),
		new Color(255,255,255)
	};

	/**
	 * Displays the player's current location's name and description.
	 * Depending on the locations display mode, will display the name of adjacent locations.
	 * @param gi.player The player instance used
	 */
	public void displayLocation(GameInstance gi) {
		if(lastLocation != gi.player.currentLocation) {
			gi.player.availableTrades = new ArrayList<Trade>();
			StringBuilder sb = new StringBuilder();
			sb.append("\n&e> " + gi.player.currentLocation.dspName + " <");
			sb.append("\n&f" + gi.player.currentLocation.dspDesc);
			sb.append("\n");
			for(int i = 0; i < gi.player.currentLocation.dspDesc.length(); i++)
				sb.append("-");
			//display tiles according to displayAdj mode
			int tempDisp = gi.player.currentLocation.displayAdj;
			if(tempDisp >= 16) {
				sb.append("\nTo the north lies: " + gi.player.currentLocation.northLocation.dspName);
				tempDisp -= 16;
			}
			if(tempDisp >= 8) {
				sb.append("\nTo the east lies: " + gi.player.currentLocation.eastLocation.dspName);
				tempDisp -= 8;
			}
			if(tempDisp >= 4) {
				sb.append("\nTo the south lies: " + gi.player.currentLocation.southLocation.dspName);
				tempDisp -= 4;
			}
			if(tempDisp >= 2) {
				sb.append("\nTo the west lies: " + gi.player.currentLocation.westLocation.dspName);
				tempDisp -= 2;
			}
			gi.terminal.display(sb.toString(), gi);
			lastLocation = gi.player.currentLocation;
		}
	}
	
	/**
	 * Basic output command
	 * @param o Object displayed
	 * @param gi Current GameInstance
	 */
	public void display(Object o, GameInstance gi) {
		if(o != null) {
			String[] sArray = o.toString().trim().split("(?=&)");
			gi.window.append("\n");
			Color currentColor = Color.white;
			for(String s : sArray) {
				if(s.length() > 1) {
					try {
						if(s.substring(0,1).equals("&")) {
							currentColor = colors[Integer.parseInt(s.substring(1,2), 16)];
							s = s.substring(2);
						}
					}catch(Exception e) {
						currentColor = Color.white;
					}
				}
				gi.window.appendColor(s , currentColor);
			}
		}
	}

	/**
	 * Outputs <code>o</code> as a red, bold error.
	 * @param o Object displayed
	 * @param gi Current GameInstance
	 */
	public void displayError(Object o, GameInstance gi) {
		if(o != null) {
			gi.window.appendError("\n");
			gi.window.appendError(o);
		}
	}
	
}
