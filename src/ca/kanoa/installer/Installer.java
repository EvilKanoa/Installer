package ca.kanoa.installer;

import org.bukkit.plugin.java.JavaPlugin;

import ca.kanoa.installer.commands.InstallCommand;
import ca.kanoa.installer.commands.UninstallCommand;
import ca.kanoa.installer.commands.UpdateCommand;

public class Installer extends JavaPlugin {
	
	private static Installer instance;
	
	private InstallCommand installCommand;
	private UninstallCommand uninstallCommand;
	private UpdateCommand updateCommand;
	
	@Override
	public void onEnable() {
		instance = this;
		saveResource("config.yml", false);
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
}
