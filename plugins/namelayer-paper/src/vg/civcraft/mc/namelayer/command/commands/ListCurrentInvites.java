package vg.civcraft.mc.namelayer.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vg.civcraft.mc.namelayer.NameAPI;
import vg.civcraft.mc.namelayer.command.PlayerCommand;
import vg.civcraft.mc.namelayer.listeners.PlayerListener;

public class ListCurrentInvites extends PlayerCommand{

	public ListCurrentInvites(String name) {
		super(name);
		setDescription("This command is used to list your current invites.");
		setUsage("/nllci <group>");
		setIdentifier("nllci");
		setArguments(1,1);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (!(sender instanceof Player)){
			sender.sendMessage("back off console man");
			return true;
		}
		Player p = (Player) sender;
		p.sendMessage(PlayerListener.getNotificationsInStringForm(NameAPI.getUUID(p.getName())));
		return true;
	}

}
