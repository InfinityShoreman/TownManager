package me.sirsavary.townmanager.commands.town;

import me.sirsavary.townmanager.Chatter;
import me.sirsavary.townmanager.IOManager;
import me.sirsavary.townmanager.objects.Town;
import me.sirsavary.townmanager.objects.TownChunk;

import org.bukkit.entity.Player;

public class TownClaim {

	public TownClaim(Player player) {
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
				if (t.getSize() < ((t.getCitizens().size() * 5) + 5)) {
					tc = new TownChunk(p.getLocation().getChunk(), null, t.getCountry(), t.getID());
					IOManager.TrackChunk(tc);
					p.sendMessage(Chatter.Message("Chunk claimed!"));
				}
				else {
					p.sendMessage(t.getFormattedID() + " cannot claim anymore land!");
					p.sendMessage("More citizens are required to claim more land!");
				}
			}
			else {
				if (IOManager.getTownAtChunk(p.getLocation().getChunk()).getID().equalsIgnoreCase(t.getID())) {
					p.sendMessage(t.getFormattedID() + " already owns this chunk!");
				}
				else {
					p.sendMessage(IOManager.getTownAtChunk(p.getLocation().getChunk()).getFormattedID() + " already owns this chunk!");
				}
			}
		}
	}
}
