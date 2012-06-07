package me.sirsavary.townmanager.listeners;

import java.util.ArrayList;
import java.util.HashMap;

import me.sirsavary.townmanager.IOManager;
import me.sirsavary.townmanager.Main;
import me.sirsavary.townmanager.objects.ResourceListType;
import me.sirsavary.townmanager.objects.TaxType;
import me.sirsavary.townmanager.objects.Town;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class ResourceTaxListener {
	
	private HashMap<String, HashMap<Integer, Integer>> resourceMap = new HashMap<String, HashMap<Integer, Integer>>();
	
	/** 
	 * Event listener to handle resource taxing
	 * 
	 * @param e - The BlockBreakEvent
	 */
	@EventHandler
	public void onPlayerCollectBlock(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Town playerTown = IOManager.getPlayerTown(p); //Get the player's town
		if (playerTown == null) return; //If player does not have a town, return
		if (playerTown.getTaxType() != TaxType.RESOURCE) return; //If the player's town does not collect resource taxes, return
		
		ArrayList<ItemStack> dropList = (ArrayList<ItemStack>) e.getBlock().getDrops(); //Get the drops from the broken block
		if (dropList.isEmpty()) return; //If there are no drops, return
		Integer blockID = dropList.get(0).getTypeId(); //Get the ID of the drops
		
		if (playerTown.getResourceListType() == ResourceListType.WHITE_LIST) {
			if (!playerTown.getResourceList().contains(blockID)) return; //If the town is not looking to collect this resource
			
			HashMap<Integer, Integer> playerMap = resourceMap.get(p.getName()); //Get the players's resource map
			Integer dropAmount = dropList.get(0).getAmount(); //How much was dropped
			Integer currentResourceCount;
			
			if (!playerMap.containsKey(blockID)) { //If the broken block is not being tracked, track it
				currentResourceCount = dropAmount;
				playerMap.put(blockID, currentResourceCount); 
			}
			else {
				currentResourceCount = playerMap.get(p) + dropAmount; //Update the player's current resource count
				playerMap.put(blockID, currentResourceCount);
			}
		}
		else if (playerTown.getResourceListType() == ResourceListType.BLACK_LIST) {
			if (playerTown.getResourceList().contains(blockID)) return; //If the town is not looking to collect this resource
			
			HashMap<Integer, Integer> playerMap = resourceMap.get(p.getName()); //Get the players's resource map
			Integer dropAmount = dropList.get(0).getAmount(); //How much was dropped
			Integer currentResourceCount;
			
			if (!playerMap.containsKey(blockID)) { //If the broken block is not being tracked, track it
				currentResourceCount = dropAmount;
				playerMap.put(blockID, currentResourceCount); 
			}
			else {
				currentResourceCount = playerMap.get(p) + dropAmount; //Update the player's current resource count
				playerMap.put(blockID, currentResourceCount);
			}
		}

	
	}

	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		
		Town playerTown = IOManager.getPlayerTown(p); //Get the player's town
		if (playerTown == null) return; //If player does not have a town, return
		if (playerTown.getTaxType() != TaxType.RESOURCE) return; //If the player's town does not collect resource taxes, return
		
		Integer taxAmount = playerTown.getTax();
		HashMap<Integer, Integer> playerMap = resourceMap.get(p.getName()); //Get the players's resource map
		
		for (Integer ID : playerMap.keySet()) {
			Integer amount = playerMap.get(ID);
			
			Integer resourceAmount = amount % taxAmount;
			
			Main.server.getPlayerExact(playerTown.getMayor()).getInventory().addItem(new ItemStack(ID, resourceAmount));
		}
		
		resourceMap.remove(p.getName());
	}
}
