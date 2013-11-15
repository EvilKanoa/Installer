package ca.kanoa.installer.installation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ca.kanoa.installer.Installer;

public class Sync {
	
	private List<FileInstallation> fileInstallations;
	
	public Sync() {
		fileInstallations = new ArrayList<FileInstallation>();
	}
	
	public void add(String[] files) {
		for (String s : files) {
			add(s);
		}
	}
	
	public void add(File[] files) {
		for (File f : files) {
			add (f);
		}
	}
	
	public void add(String file) {
		add(Installer.getRootFile(file));
	}
	
	public void add(File file) {

		if (file.isDirectory()) {
			return;
		}

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String s;
			while ((s = reader.readLine()) != null) {
				FileInstallation fi = FileInstallation.parseString(s);
				fileInstallations.add(fi);
				for (FileInstallation f : fileInstallations) {
					if (f != fi &&
							f.getLocalLocation().equalsIgnoreCase(fi.getLocalLocation())) {
						if (f.getVersion() >= fi.getVersion()) {
							fileInstallations.remove(fi);
							break;
						} else {
							fileInstallations.remove(f);
						}
					}
				}
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
		
	}
	
	public String[] getFilesAsStrings() {
		String[] strings = new String[fileInstallations.size()];
		for (int i = 0; i < fileInstallations.size(); i++) {
			strings[i] = fileInstallations.get(i).getLocalLocation();
		}
		return strings;
	}
	
	public FileInstallation[] getFiles() {
		return fileInstallations.toArray(new FileInstallation[0]);
	}
	
}
