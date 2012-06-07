package me.sirsavary.townmanager.commands.town;

import me.sirsavary.townmanager.Chatter;
import me.sirsavary.townmanager.Debug;
import me.sirsavary.townmanager.IOManager;
import me.sirsavary.townmanager.Main;
import me.sirsavary.townmanager.commands.AbstractCommand;
import me.sirsavary.townmanager.objects.HousingType;
import me.sirsavary.townmanager.objects.Town;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TownJoin extends AbstractCommand {

	private final Town townToJoin;
	Town t = IOManager.getPlayerTown((Player) sender);
	String townName = null;

	public TownJoin(CommandSender sender, boolean async, Main plugin, String TownToJoin)
			throws Exception {
		super(sender, async, plugin);
		townToJoin = IOManager.getTown(TownToJoin);
		townName = TownToJoin;
	}

	@Override
	public void run() {
		if (t == null) {
			if (townToJoin != null) {

				HousingType ht = townToJoin.getHousingType();

				if (ht == HousingType.OPEN) {
					IOManager.AddCitizen(sender.getName(), townToJoin);
					Main.server.getPlayer(townToJoin.getMayor()).sendMessage(Chatter.Message(sender.getName() + " has joined " + townToJoin.getFormattedID() + "!"));
					sender.sendMessage(Chatter.TagMessage("You have joined " + townToJoin.getFormattedID() + "!"));
					Debug.info(sender.getName() + " join " + townToJoin.getID());
				}
				else if (ht == HousingType.INVITE_ONLY) {
					sender.sendMessage(Chatter.TagMessage(townToJoin.getFormattedID() + " is an invite only town!"));
					Debug.warning(sender.getName() + " tried to join " + townToJoin.getID() + " but it has HousingType.INVITE_ONLY");
				}
				else if (ht == HousingType.CLOSED) {
					sender.sendMessage(Chatter.TagMessage(townToJoin.getFormattedID() + " does not allow players to join!"));
					Debug.warning(sender.getName() + " tried to join " + townToJoin.getID() + " but it has HousingType.CLOSED");
				}
			}
			else {
				sender.sendMessage(Chatter.TagMessage("That town does not exist!"));
				Debug.severe(sender.getName() + " tried to join " + townName + " but it does not exist");
			}
		}
		else {
			sender.sendMessage(Chatter.TagMessage("You are already part of a town!"));
			Debug.warning(sender.getName() + " tried to join " + townToJoin.getID() + " but they are already part of a town");
		}
	}

}
