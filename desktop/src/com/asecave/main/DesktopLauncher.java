package com.asecave.main;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.useVsync(true);
//		config.setForegroundFPS(20);
		config.setBackBufferConfig(8, 8, 8, 8, 16, 0, 4);
		config.setWindowedMode(960, 640);
		config.setTitle("Physics Engine 2");
		new Lwjgl3Application(new Main(), config);
	}
}
