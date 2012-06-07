package me.sirsavary.townmanager.commands.town;

import me.sirsavary.townmanager.Chatter;
import me.sirsavary.townmanager.IOManager;
import me.sirsavary.townmanager.objects.Town;
import me.sirsavary.townmanager.objects.TownChunk;

import org.bukkit.entity.Player;

public class TownUnclaim {

	public TownUnclaim(Player player) {
		Player p = player;
		Town t = IOManager.getPlayerTown(p);
		if (t == null) {
			p.sendMessage(Chatter.TagMessage("You are not part of a town!"));
		}
		else if (!t.getMayor().equalsIgnoreCase(p.getName())) {
			p.sendMessage(Chatter.TagMessage("You are not the mayor of " + t.getFormattedID() + "!"));
		}
		else {
			TownChunk tc = IOManager.getTownChunkAtChunk(p.getLocation().getChunk()); //TownChunk(p.getLocation().getChunk(), null, t.getCountry(), t.getID());

			if (tc == null) {
				p.sendMessage(Chatter.Message("This chunk has not been claimed!"));
			}
			else {
				if (IOManager.getTownAtChunk(p.getLocation().getChunk()).getID().equalsIgnoreCase(t.getID())) {
					if (tc.getPlots() != null) {
						p.sendMessage(Chatter.Message("This chunk has plots and cannot be unclaimed!"));
					}
					else {
						IOManager.UnTrackChunk(tc);
						p.sendMessage("Chunk unclaimed!");
					}
				}
				else {
					p.sendMessage(IOManager.getTownAtChunk(p.getLocation().getChunk()).getFormattedID() + " owns this chunk!");
				}
			}
		}
	}
}
