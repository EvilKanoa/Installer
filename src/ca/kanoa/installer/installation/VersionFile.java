package ca.kanoa.installer.installation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class VersionFile {
	
	private File file;
	private FileInstallation[] fileInstallations;
	
	public VersionFile(File file) {
		this.file = file;
		load();
	}
	
	private void load() {
		Set<FileInstallation> files = new HashSet<FileInstallation>();

		if (file.isDirectory()) {
			return;
		}

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String s;
			while ((s = reader.readLine()) != null) {
				files.add(FileInstallation.parseString(s));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		fileInstallations = files.toArray(new FileInstallation[0]);
	}
	
	public String[] getFilesAsStrings() {
		String[] strings = new String[fileInstallations.length];
		for (int i = 0; i < fileInstallations.length; i++) {
			strings[i] = fileInstallations[i].getLocalLocation();
		}
		return strings;
	}
	
	public FileInstallation[] getFiles() {
		return fileInstallations;
	}
	
}
