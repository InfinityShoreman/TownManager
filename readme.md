TownManager is a lightweight yet powerful town management plugin for the Bukkit Mincraft SMP server wrapper

Instructions for Kirk:

As I have not setup any milestones yet, or any tickets to outline serious bugs there is not much you will be able to do to help me as you do not know TownManager inside and out (yet). Instead, you can help me collect debug info from the plugin to aid in stabilizing the current release. Once this is done, you can help me with all sorts of fun stuff (Plots, countries, taxes, wars, etc). So, how do you help me debug? Simple, look through commonly used functions/code (Just about everything in the IOManager class file is used very often) and place one of the following lines:

Debug.info("Debug Message"); //Basic info
Debug.warning("Debug Message"); //Warning (Something happened, not always a bad thing)
Debug.severe("Debug Message"); //Severe (When the plugin blows up)

Including as many variables as possible is nice! Example:

Debug.info("New town created");
Versus
Debug.info("New town " + townName + " created by player " + mayorName);

Hope this helps Kirk! Happy coding!