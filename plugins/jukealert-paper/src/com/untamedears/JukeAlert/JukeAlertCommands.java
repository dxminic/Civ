package com.untamedears.JukeAlert;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JukeAlertCommands implements CommandExecutor {
	private JukeAlert plugin;
	
	public JukeAlertCommands(JukeAlert plugin) {
		this.plugin = plugin;
	}
 
	@SuppressWarnings("null")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player= null;
		if(label.equalsIgnoreCase("basic")){
			player.sendMessage("Works");
			return false;
		}
		return false;
	}
	
}
