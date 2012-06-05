package me.sirsavary.townmanager;

public class LanguageHolder {

	private static String TownNotFound;
	private static String InvalidCommand;
	private static String NotPartOfTown;

	public static String TownNotFound() {
		return TownNotFound;
	}

	@SuppressWarnings("unused")
	private void setTownNotFound(String townNotFound) {
		TownNotFound = townNotFound;
	}

	public static String InvalidCommand() {
		return InvalidCommand;
	}

	@SuppressWarnings("unused")
	private static void setInvalidCommand(String invalidCommand) {
		InvalidCommand = invalidCommand;
	}

	public static String NotPartOfTown() {
		return NotPartOfTown;
	}

	@SuppressWarnings("unused")
	private static void setNotPartOfTown(String notPartOfTown) {
		NotPartOfTown = notPartOfTown;
	}

}
