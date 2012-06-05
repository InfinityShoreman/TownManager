package me.sirsavary.townmanager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Debug {
	static File debugLogFile = new File(Main.pluginFolder, "Debug.log");
	static FileWriter writer;
	static Boolean b = Main.debugEnabled;

	public static void SetupDebug() {
		try {
			writer = new FileWriter(debugLogFile);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void info(String string) {
		if (b) {
			try {
				writer.write(string);
				writer.write(0);
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void warning(String string) {
		if (b) {
			try {
				writer.write("[Warning]" + string);
				writer.write(0);
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void severe(String string) {
		if (b) {
			try {
				writer.write("[Severe]" + string);
				writer.write(0);
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
