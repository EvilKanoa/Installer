package ca.kanoa.installer.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import ca.kanoa.installer.FTPInfo;
import ca.kanoa.installer.Installer;
import ca.kanoa.installer.ftp.FTP;
import ca.kanoa.installer.installation.FileInstallation;
import ca.kanoa.installer.installation.Sync;

public class InstallCommand extends SimpleCommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		Installer.downloadSyncFiles();

		Sync files = new Sync();
		files.add(FTPInfo.SYNC_FILES);
		
		for (FileInstallation file : files.getFiles()) {
			if (!FTP.download(file.getLocalLocation())) {
				sender.sendMessage(ChatColor.RED + 
						"Warning: Could not download " + 
						file.getLocalLocation() + "...");
			}
		}
		sender.sendMessage(ChatColor.GREEN + "Install complete!");
		return true;
	}

}
