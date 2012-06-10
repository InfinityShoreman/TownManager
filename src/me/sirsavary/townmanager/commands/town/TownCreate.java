package me.sirsavary.townmanager.commands.town;

import me.sirsavary.townmanager.Chatter;
import me.sirsavary.townmanager.Debug;
import me.sirsavary.townmanager.IOManager;
import me.sirsavary.townmanager.Main;
import me.sirsavary.townmanager.commands.AbstractCommand;
import me.sirsavary.townmanager.objects.Plot;
import me.sirsavary.townmanager.objects.PlotType;
import me.sirsavary.townmanager.objects.Selection;
import me.sirsavary.townmanager.objects.Town;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TownCreate extends AbstractCommand
{
	Town t = IOManager.getPlayerTown((Player) sender);

	public TownCreate(CommandSender sender, boolean async, Main plugin) throws Exception {
		super(sender, async, plugin);
	}

	private void CreateTown() {
		Player player = (Player) sender;
		player.sendMessage("");
		player.sendMessage(Chatter.Message("Please head to your town hall"));
		Debug.info(player.getName() + " is creating a new town");
		if (Main.questioner.ask(player, Chatter.Message("When you are there type anything to continue")) != null) {

			player.sendMessage("");
			player.sendMessage(Chatter.Message("Before we can go to the next step,"));
			player.sendMessage(Chatter.Message("Make sure your hand is empty"));
			if (Main.questioner.ask(player, "When it is empty type anything to continue") != null) {
				ItemStack brickStack = new ItemStack(Material.CLAY_BRICK, 1);
				player.setItemInHand(brickStack);
				player.sendMessage("");
				player.sendMessage(Chatter.Message("This is your town brick!"));
				player.sendMessage(Chatter.Message("It is used for town construction functions,"));
				player.sendMessage(Chatter.Message("Including plot management and land claiming!"));
				player.sendMessage(Chatter.Message("With the brick in your hand,"));
				player.sendMessage(Chatter.Message("Left click one corner of your town hall"));
				player.sendMessage(Chatter.Message("Then, right click the other corner"));
				if (Main.questioner.ask(player, "When both corners are selected type anything to continue") != null) {

					Selection sel = Main.regionHandler.getSelection(player);
					boolean townHallTooBig = false;
					if(sel.getSurfaceBlocks().size()>1024){
						townHallTooBig = true;
						player.sendMessage(Chatter.Message("Your town hall is too big!"));
						player.sendMessage(Chatter.Message("Please select a smaller area."));
						player.sendMessage(Chatter.Message("You can claim more land later."));
					}
					while(townHallTooBig = true){
						if (Main.questioner.ask(player, "When both corners are selected type anything to continue") != null) {
							sel = Main.regionHandler.getSelection(player);
							if(sel.getSurfaceBlocks().size()>1024){
								townHallTooBig = true;
								player.sendMessage(Chatter.Message("Your town hall is too big!"));
								player.sendMessage(Chatter.Message("Please select a smaller area."));
								player.sendMessage(Chatter.Message("You can claim more land later."));
							}
							else{townHallTooBig=false;}
						}
					}

					if (sel == null) {
						player.sendMessage("");
						player.sendMessage(Chatter.Message("You did not select your town hall!"));
						player.sendMessage(Chatter.Message("Now we have to do this all over again!"));
						player.sendMessage(Chatter.Message("Type /town create when you'd like to try again."));
					}
					
					else {
						player.sendMessage("");
						player.sendMessage(Chatter.Message("Your town is almost complete!"));
						player.sendMessage(Chatter.Message("The last step is to choose a name for your town!"));
						String townName = Main.questioner.ask(player, "Please choose a name for your town:");
						if (townName.equalsIgnoreCase("timed out")) {
							player.sendMessage(Chatter.TagMessage("You took too long! Operation cancelled!"));
							Debug.warning(player.getName() + " took to long to choose town name, operation cancelled");
						}
						else {
							Debug.info(player.getName() + " is creating new town " + townName);
							Plot region = new Plot("townhall", sel.getMinPoint(), sel.getMaxPoint(), townName, PlotType.GOVERNMENT, player.getName());

							Town newTown = new Town(townName, region, player.getName());
							if (newTown == null) Debug.severe(townName + " is equal to null! Failure to save iminent!");
							IOManager.SaveTown(newTown);
							IOManager.AddCitizen(player.getName(), newTown);
							IOManager.SavePlot(region);
							IOManager.TrackPlotChunks(region);

							player.sendMessage("");
							player.sendMessage(Chatter.Message("Congratulations!"));
							player.sendMessage(Chatter.Message("You survived the town creation process!"));
						}
					}
				}
			}

		}
	}
	@Override
	public void run() {
		sender.sendMessage(Chatter.TagMessage("Welcome to the town create interface!"));
		if (t != null) {
			sender.sendMessage("");
			if (t.getMayor().equalsIgnoreCase(sender.getName())) {
				sender.sendMessage(Chatter.Message("You are currently the mayor of " + t.getColor() + t.getID()));
				sender.sendMessage(Chatter.Message("You must first delete " + t.getColor() + t.getID() + Main.messageColor + " before making a new town"));
				Debug.warning(sender.getName() + " is trying to create a new town but is the mayor of " + t.getID());
			}
			else {
				sender.sendMessage(Chatter.Message("You are currently a resident of " + t.getColor() + t.getID()));
				sender.sendMessage(Chatter.Message("If you leave you will lose your home protection in the process"));
				Debug.warning(sender.getName() + " is trying to create a new town but is already part of a town");
				if (Main.questioner.ask((Player)sender, "Are you sure you want to leave it to create your own town?", "yes", "no").equals("yes")) {
					CreateTown();
				}
			}
		}
		if (t == null) {
			CreateTown();
		}
	}
}