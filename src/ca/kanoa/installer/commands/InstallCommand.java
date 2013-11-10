package ca.kanoa.installer.commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import ca.kanoa.installer.Installer;
import ca.kanoa.installer.ftp.FTP;
import ca.kanoa.installer.installation.FileInstallation;
import ca.kanoa.installer.installation.VersionFile;

public class InstallCommand extends SimpleCommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		FTP.download("plugins/Installer/VERSIONS");
		VersionFile files = new VersionFile(
				new File(Installer.getInstance().getDataFolder(), "VERSIONS"));
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
