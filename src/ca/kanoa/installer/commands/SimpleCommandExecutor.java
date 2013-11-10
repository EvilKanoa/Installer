package ca.kanoa.installer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class SimpleCommandExecutor implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String label, String[] args) {
		if (args.length != 0) {
			return false;
		} else {
			return onCommand(sender, args);
		}
	}
	
	public abstract boolean onCommand(CommandSender sender, String[] args);

}
