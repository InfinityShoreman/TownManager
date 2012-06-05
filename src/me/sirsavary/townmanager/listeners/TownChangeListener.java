package me.sirsavary.townmanager.listeners;

import me.sirsavary.townmanager.Chatter;
import me.sirsavary.townmanager.Main;
import me.sirsavary.townmanager.objects.Town;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class TownChangeListener implements Listener {

	@EventHandler
	public void onPlayerTownChange(PlayerMoveEvent event) {
		Chunk c = event.getPlayer().getLocation().getChunk();
		Player p = event.getPlayer();

		Town newTown;
		Town oldTown;

		if (Main.fileManager.isChunkOccupiedByTown(c)) {
			newTown = Main.fileManager.getTownAtChunk(c);
		} else {
			newTown = null;
		}

		if (!Main.lastTownMap.containsKey(p)) {
			oldTown = null;
		} else {
			oldTown = Main.lastTownMap.get(p);
		}

		//Wilderness --> Wilderness
		if ((oldTown == null) && (newTown == null)) {
			//Don't do anything!
		}
		//Town --> Wilderness and Wilderness --> Town
		else if (((oldTown != null) && (newTown == null)) || ((oldTown == null) && (newTown != null))) {
			//If player is heading into wilderness
			if (newTown == null) {
				//Send the player a message and update lastTownMap
				p.sendMessage(Chatter.Message("~Wilderness~"));
				Main.lastTownMap.put(p, newTown);
				p.performCommand("dynmap hide");
			}
			//If player is heading into town
			else if (newTown != null) {
				//Send the player a message using the new town's color and name and update lastTownMap
				p.sendMessage(newTown.getColor() + newTown.getID() + " - " + newTown.getMOTD());
				Main.lastTownMap.put(p, newTown);
				p.performCommand("dynmap show");
			}
		}
		//Town --> Town
		else if ((oldTown != null) && (newTown != null)) {
			//If the towns are different
			if (!oldTown.getID().equalsIgnoreCase(newTown.getID())) {
				//Send the player a message using the new town's color and name and update lastTownMap
				p.sendMessage(newTown.getColor() + newTown.getID() + " - " + newTown.getMOTD());
				Main.lastTownMap.put(p, newTown);
			}
		}
	}
}
