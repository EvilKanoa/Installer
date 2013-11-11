package ca.kanoa.installer.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import ca.kanoa.installer.FTPInfo;
import ca.kanoa.installer.Installer;
import ca.kanoa.installer.ftp.FTP;
import ca.kanoa.installer.installation.FileInstallation;
import ca.kanoa.installer.installation.FileInstallation.FileStatus;
import ca.kanoa.installer.installation.Sync;

public class UpdateCommand extends SimpleCommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		for (String s : FTPInfo.SYNC_FILES) {
			if (!Installer.getRootFile(s).exists()) {
				sender.sendMessage(ChatColor.RED + "No install yet, use /install");
				return true;
			}
		}

		Installer.downloadSyncFiles("$path.temp");

		Sync local = new Sync();
		Sync remote = new Sync();
		
		local.add(FTPInfo.SYNC_FILES);
		for (String s : FTPInfo.SYNC_FILES) {
			remote.add(s + ".temp");
		}

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

		for (String file : FTPInfo.SYNC_FILES) {
			Installer.getRootFile(file).delete();
			Installer.getRootFile(file + ".temp").delete();
		}
		Installer.downloadSyncFiles();
		sender.sendMessage(ChatColor.GREEN + "Update complete!");
		return true;
	}

	private FileStatus getStatus(FileInstallation installation, 
			Sync local) {
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
