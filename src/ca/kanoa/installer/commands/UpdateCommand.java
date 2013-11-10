package ca.kanoa.installer.commands;

import java.io.File;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import ca.kanoa.installer.Installer;
import ca.kanoa.installer.ftp.FTP;
import ca.kanoa.installer.installation.FileInstallation;
import ca.kanoa.installer.installation.FileInstallation.FileStatus;
import ca.kanoa.installer.installation.VersionFile;

public class UpdateCommand extends SimpleCommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		File localVersions = new File(Installer.getInstance().getDataFolder(), 
				"VERSIONS");
		if (!localVersions.exists()) {
			sender.sendMessage(ChatColor.RED + "No install yet, use /install");
			return true;
		}
		FTP.downloadTo("plugins/Installer/VERSIONS", "plugins/Installer/VERSIONS.temp");
		
		VersionFile local = new VersionFile(new File(Installer.getInstance()
				.getDataFolder(), "VERSIONS"));
		VersionFile remote = new VersionFile(new File(Installer.getInstance()
				.getDataFolder(), "VERSIONS.temp"));

		for (FileInstallation file : remote.getFiles()) {
			FileStatus status = getStatus(file, local);
			if (status == FileStatus.OUTDATED) {
				sender.sendMessage("Updating " + file.getLocalLocation() + "...");
				file.getFile().delete();
				FTP.download(file.getLocalLocation());
			} else if (status == FileStatus.NOT_INSTALLED) {
				sender.sendMessage("Installing " + file.getLocalLocation() + "...");
				FTP.download(file.getLocalLocation());
			}
		}
		new File(Installer.getInstance().getDataFolder(), 
				"VERSIONS.temp").delete();
		localVersions.delete();
		FTP.download("plugins/Installer/VERSIONS");
		sender.sendMessage(ChatColor.GREEN + "Update complete!");
		return true;
	}

	private FileStatus getStatus(FileInstallation installation, 
			VersionFile local) {
		for (FileInstallation file : local.getFiles()) {
			if (file.getLocalLocation().equalsIgnoreCase(installation
					.getLocalLocation())) {
				if (!file.getFile().exists()) {
					return FileStatus.NOT_INSTALLED;
				} else if (file.getVersion() == installation.getVersion()) {
					return FileStatus.INSTALLED;
				} else {
					return FileStatus.OUTDATED;
				}
			}
		}
		return FileStatus.NOT_INSTALLED;
	}
	
}
