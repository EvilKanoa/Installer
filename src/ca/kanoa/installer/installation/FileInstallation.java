package ca.kanoa.installer.installation;

import java.io.File;

import ca.kanoa.installer.Installer;

public class FileInstallation {
	
	private double version;
	private final String file;
	
	public FileInstallation(String file, double version) {
		this.file = file;
		this.version = version;
	}

	public static FileInstallation parseString(String string)
			throws IllegalArgumentException {
		if (!string.contains(":")) {
			throw new IllegalArgumentException("String string does not contain a info divider (\":\")");
		}

		String[] raw = (new StringBuilder(string).reverse().toString().split(":", 2));
		for (int i = 0; i < raw.length; i++) {
			raw[i] = new StringBuilder(raw[i]).reverse().toString().trim();
		}
		
		double version;
		try {
			version = Double.parseDouble(raw[0]);
		} catch (NumberFormatException ex) {
			version = 1;
		}
		return new FileInstallation(raw[1], version);
	}
	
	public double getVersion() {
		return version;
	}
	
	public String getLocalLocation() {
		return file;
	}

	public File getFile() {
		return new File(Installer.getInstance().getDataFolder().getParentFile()
				.getParentFile(), file);
	}
	
	public enum FileStatus {
		INSTALLED,
		OUTDATED,
		NOT_INSTALLED;
	}
	
}
