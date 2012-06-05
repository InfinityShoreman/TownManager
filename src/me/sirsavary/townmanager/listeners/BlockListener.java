package me.sirsavary.townmanager.listeners;

import me.sirsavary.townmanager.Main;
import me.sirsavary.townmanager.objects.Plot;
import me.sirsavary.townmanager.objects.Town;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

	@EventHandler
	public void onPlayerBlockBreak(BlockBreakEvent event) {
		if (Main.fileManager.isChunkOccupiedByTown(event.getBlock().getChunk())) {

			Player p = event.getPlayer();
			Town playerTown = Main.fileManager.getPlayerTown(p);
			Town town = Main.fileManager.getTownAtChunk(event.getBlock().getChunk());
			if ((playerTown == null) || !playerTown.getID().equalsIgnoreCase(town.getID())) {
				p.sendMessage(town.getColor() + "You are not part of" + town.getID());
				p.sendMessage(town.getColor() + "You cannot build here!");
				event.setCancelled(true);
			}
			else {
				if (town.getMayor().equalsIgnoreCase(p.getName())) return;
				if (!town.isFreeBuildAllowed()) { //if free build is not enabled
					for (Plot plot : Main.fileManager.getPlotsAtChunk(event.getBlock().getChunk())) {
						if (Main.regionHandler.isBlockWithinRegion(event.getBlock(), plot)) { //If the block they are breaking is within a plot
							if (!Main.fileManager.isPlayerMemberOfPlot(plot, p)) {
								p.sendMessage(town.getColor() + "You cannot build here!");
								event.setCancelled(true);
								break;
							} else
								return;
						}
					}
					//The block was not within a plot, and free build is not allowed
					p.sendMessage(town.getColor() + "You cannot build here!");
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerBlockPlace(BlockPlaceEvent event) {
		if (Main.fileManager.isChunkOccupiedByTown(event.getBlock().getChunk())) {

			Player p = event.getPlayer();
			Town playerTown = Main.fileManager.getPlayerTown(p);
			Town town = Main.fileManager.getTownAtChunk(event.getBlock().getChunk());
			if ((playerTown == null) || !playerTown.getID().equalsIgnoreCase(town.getID())) {
				p.sendMessage(town.getColor() + "You are not part of" + town.getID());
				p.sendMessage(town.getColor() + "You cannot build here!");
				event.setCancelled(true);
			}
			else {
				if (town.getMayor().equalsIgnoreCase(p.getName())) return;
				if (!town.isFreeBuildAllowed()) { //if free build is not enabled
					if (Main.fileManager.isChunkOccupiedByPlots(event.getBlock().getChunk())) {
						for (Plot plot : Main.fileManager.getPlotsAtChunk(event.getBlock().getChunk())) {
							if (Main.regionHandler.isBlockWithinRegion(event.getBlock(), plot)) { //If the block they are breaking is within a plot
								if (!Main.fileManager.isPlayerMemberOfPlot(plot, p)) {
									p.sendMessage(town.getColor() + "You cannot build here!");
									event.setCancelled(true);
									break;
								} else
									return;
							}
						}
					}
					else {

						//The block was not within a plot, and free build is not allowed
						p.sendMessage(town.getColor() + "You cannot build here!");
						event.setCancelled(true);
					}
				}
			}
		}
	}
}