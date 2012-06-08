package me.sirsavary.townmanager;

import java.util.HashMap;

public class LanguageConstants {

	public static String TownNotFound;
	public static String InvalidCommand;
	public static String NotPartOfTown;
	public static String NotMayor;
	public static String AlreadyPartOfTown;
	public static String AlreadyMayorOfTown;
	public static String ChunkAlreadyClaimed;
	public static String NotEnoughCitizens;
	public static String NotEnoughMoney;
	public static String PVPIsDisabled;
	public static String NoBuildRights;
	
	public static void Setup(HashMap<String, String> langMap) {
		TownNotFound = langMap.get("TownNotFound");
		InvalidCommand = langMap.get("InvalidCommand");
		NotPartOfTown = langMap.get("NotPartOfTown");
	}
}
