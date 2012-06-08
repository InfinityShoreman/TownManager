package me.sirsavary.townmanager.commands;

import me.sirsavary.townmanager.Chatter;
import me.sirsavary.townmanager.IOManager;
import me.sirsavary.townmanager.LanguageConstants;
import me.sirsavary.townmanager.objects.Plot;
import me.sirsavary.townmanager.objects.Town;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlotCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Town t = IOManager.getPlayerTown((Player) sender);
		Player p = (Player) sender;

		if (t == null) { //If player is not part of a town
			p.sendMessage(Chatter.TagMessage(LanguageConstants.NotPartOfTown()));
			return true;
		}

		if (args.length == 0) { //No args, just /plot
			Location pLoc = p.getLocation();
			for (@SuppressWarnings("unused") Plot plot : IOManager.getPlotsAtChunk(pLoc.getChunk())) {

			}
			return true;
		}
		else if (args.length == 1)
			return true;
		else { //Too many args, invalid command
			p.sendMessage(Chatter.TagMessage(LanguageConstants.InvalidCommand()));
			return true;
		}
	}
}