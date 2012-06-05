package me.sirsavary.townmanager.commands.town;

import me.sirsavary.townmanager.Chatter;
import me.sirsavary.townmanager.Main;
import me.sirsavary.townmanager.objects.Town;

import org.bukkit.entity.Player;

public class TownTeleport {

	public TownTeleport(Player player) {
		Player p = player;
		Town t = Main.fileManager.getPlayerTown(p);
		if (t == null) {
			p.sendMessage(Chatter.TagMessage("You are not part of a town!"));
		}
		else {
			p.teleport(t.getSpawnLocation());
			p.sendMessage(Chatter.TagMessage("Teleporting to town spawn!"));
		}
	}
}