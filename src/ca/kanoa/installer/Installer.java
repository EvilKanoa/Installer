package ca.kanoa.installer;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import ca.kanoa.installer.commands.InstallCommand;
import ca.kanoa.installer.commands.UninstallCommand;
import ca.kanoa.installer.commands.UpdateCommand;
import ca.kanoa.installer.ftp.FTP;

public class Installer extends JavaPlugin {

	private static Installer instance;

	private InstallCommand installCommand;
	private UninstallCommand uninstallCommand;
	private UpdateCommand updateCommand;

	@Override
	public void onEnable() {
		instance = this;
		if (!new File(getDataFolder(), "config.yml").exists()) {
			saveResource("config.yml", false);
		}
		installCommand = new InstallCommand();
		uninstallCommand =  new UninstallCommand();
		updateCommand = new UpdateCommand();
		getCommand("install").setExecutor(installCommand);
		getCommand("uninstall").setExecutor(uninstallCommand);
		getCommand("update").setExecutor(updateCommand);
	}

	public static Installer getInstance() {
		return instance;
	}

	public static void downloadSyncFiles() {
		for (String s : FTPInfo.SYNC_FILES) {
			FTP.download(s);
		}
	}

	/**
	 * Downloads the sync files to a modified location
	 * @param mod Use the variable $path to represent the normal path
	 */
	public static void downloadSyncFiles(String mod) {
		for (String s : FTPInfo.SYNC_FILES) {
			FTP.downloadTo(s, mod.replace("$path", s));
		}
	}

	public static File getRootFile(String file) {
		return new File(instance.getDataFolder().getParentFile()
				.getParentFile(), file);
	}

}
