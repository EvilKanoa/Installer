package ca.kanoa.installer.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import ca.kanoa.installer.FTPInfo;
import ca.kanoa.installer.Installer;
import ca.kanoa.installer.installation.FileInstallation;
import ca.kanoa.installer.installation.Sync;

public class UninstallCommand extends SimpleCommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		Installer.downloadSyncFiles();

		Sync sync = new Sync();
		sync.add(FTPInfo.SYNC_FILES);
		
		sender.sendMessage(ChatColor.YELLOW + 
				"Warning: Bukkit may have some files in memory, they'll"
				+ " have to be manually deleted.");
		
		for (FileInstallation file : sync.getFiles()) {
			sender.sendMessage(ChatColor.RED + "Deleting " + file.
					getLocalLocation());
			file.getFile().delete();
		}
		for (String s : FTPInfo.SYNC_FILES) {
			sender.sendMessage(ChatColor.RED + "Deleting " + s);
			Installer.getRootFile(s).delete();
		}
		return true;
	}

}
