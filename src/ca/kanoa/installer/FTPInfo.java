package ca.kanoa.installer;

import org.bukkit.configuration.ConfigurationSection;

public class FTPInfo {
	
	public static final String FTP_HOST;
	public static final int FTP_PORT;
	public static final String FTP_USERNAME;
	public static final String FTP_PASSWORD;
	public static final String FTP_FOLDER;
	
	static {
		ConfigurationSection ftpConfig = Installer.getInstance().getConfig()
				.getConfigurationSection("ftp");
		
		FTP_HOST = ftpConfig.getString("host");
		FTP_PORT = ftpConfig.getInt("port");
		FTP_USERNAME = ftpConfig.getString("username");
		FTP_PASSWORD = ftpConfig.getString("password");
		FTP_FOLDER = ftpConfig.getString("folder");
		
		ftpConfig = null;
	}
	
}
