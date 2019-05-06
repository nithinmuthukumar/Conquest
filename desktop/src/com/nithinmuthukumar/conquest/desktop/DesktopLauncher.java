package com.nithinmuthukumar.conquest.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nithinmuthukumar.conquest.Conquest;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.height=720;
		config.width=960;
		config.resizable=false;
		new LwjglApplication(new Conquest(), config);
	}
}
