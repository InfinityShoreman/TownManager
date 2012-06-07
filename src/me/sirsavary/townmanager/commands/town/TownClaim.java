package me.sirsavary.townmanager.commands.town;

import me.sirsavary.townmanager.Chatter;
import me.sirsavary.townmanager.Debug;
import me.sirsavary.townmanager.IOManager;
import me.sirsavary.townmanager.objects.Town;
import me.sirsavary.townmanager.objects.TownChunk;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TownClaim {

	public TownClaim(Player player) {
		Player p = player;
		Location l = p.getLocation();
		Town t = IOManager.getPlayerTown(p);
		if (t == null) {
			p.sendMessage(Chatter.TagMessage("You are not part of a town!"));
			Debug.warning(p.getName() + " attemped to claim a chunk at " + l.getChunk().getX() + "," + l.getChunk().getZ() + " but was not part of a town");
		}
		else if (!t.getMayor().equalsIgnoreCase(p.getName())) {
			p.sendMessage(Chatter.TagMessage("You are not the mayor of " + t.getFormattedID() + "!"));
			Debug.warning(p.getName() + " attemped to claim a chunk at " + l.getChunk().getX() + "," + l.getChunk().getZ() + " but was not the mayor of " + t.getID());
		}
		else {
			TownChunk tc = IOManager.getTownChunkAtChunk(l.getChunk());

			if (tc == null) {
				if (t.getSize() < ((t.getCitizens().size() * 5) + 5)) {
					tc = new TownChunk(l.getChunk(), null, t.getCountry(), t.getID());
					IOManager.TrackChunk(tc);
					p.sendMessage(Chatter.Message("Chunk claimed!"));
					Debug.info(p.getName() + " claimed the chunk at " + l.getChunk().getX() + "," + l.getChunk().getZ() + " for " + t.getID());
				}
				else {
					p.sendMessage(t.getFormattedID() + " cannot claim anymore land!");
					p.sendMessage("More citizens are required to claim more land!");
					Debug.warning(p.getName() + " attemped to claim a chunk at " + l.getChunk().getX() + "," + l.getChunk().getZ() + " but " + t.getID() + " does not have enough citizens");
				}
			}
			else {
				if (IOManager.getTownAtChunk(l.getChunk()).getID().equalsIgnoreCase(t.getID())) {
					p.sendMessage(t.getFormattedID() + " already owns this chunk!");
					Debug.warning(p.getName() + " attemped to claim a chunk at " + l.getChunk().getX() + "," + l.getChunk().getZ() + " but it already belongs to " + t.getID());
				}
				else {
					p.sendMessage(IOManager.getTownAtChunk(l.getChunk()).getFormattedID() + " already owns this chunk!");
					Debug.warning(p.getName() + " attemped to claim a chunk at " + l.getChunk().getX() + "," + l.getChunk().getZ() + " but " + IOManager.getTownAtChunk(l.getChunk()) + " has already claimed it");
				}
			}
		}
	}
}