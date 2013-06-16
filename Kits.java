package com.lunarddosing;
import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Kits extends JavaPlugin {

  	public void onEnable() {
			try {
				saveConfig();
				setupConfig(getConfig());
				saveConfig();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@SuppressWarnings("deprecation")
		public boolean onCommand(CommandSender sender, Command command,
				String commandLabel, String[] args) {
			if (commandLabel.equalsIgnoreCase("kitb")){
				if (sender instanceof Player){
					Player player = (Player) sender;
					if (args.length == 0) {
						String[] classes = getConfig().getString("kitb.Names")
								.split(",");
						for (String s : classes) {
							if (s != null) {
								player.sendMessage("[" + ChatColor.AQUA + s 
										+ ChatColor.WHITE + "] " + ChatColor.GRAY 
										+ ": " + ChatColor.DARK_GRAY 
										+ "Adds You To The " + s + " Class!");
							}
						}
					} else {
						for (String s : getConfig().getConfigurationSection("kitb")
								.getKeys(false)) {
							if (args[0].equalsIgnoreCase(s)) {
									player.getInventory().clear();
									try {
										String items = getConfig().getString(
												"kitb." + s + ".Items");
										
										String[] indiItems = items.split(",");
										
										for (String s1 : indiItems) {
											String[] itemAmounts = s1.split("-");
											ItemStack item = new ItemStack(
													Integer.valueOf(itemAmounts[0]),
													Integer.valueOf(itemAmounts[1]));
											player.getInventory().addItem(item);
										}
										player.updateInventory();
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
						}
					}
			}
			return false;
}

private void setupConfig(FileConfiguration config) throws IOException {
	if (!new File(getDataFolder(), "RESET.FILE").exists()) {
		config.set("kitb.Warrior.Items",  "276-1,306-1,307-1,308-1,309-1");
		config.set("kitb.Archer.Items", "261-1,262-64,306-1,307-1,308-1,309-1");
		
		config.set("kitb.Names",  "Warrior,Archer");
		
		new File(getDataFolder(), "RESET.FILE").createNewFile();
		}
	}
}
