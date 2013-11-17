package ca.kanoa.installer.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import ca.kanoa.installer.FTPInfo;
import ca.kanoa.installer.Installer;

public class FTP {

	public static boolean download(String file) {
		return downloadTo(file, file);
	}
	
	public static boolean downloadTo(String file, String toFile) {
		if (file.startsWith("/")) {
			file = file.substring(1);
		}
		if (toFile.startsWith("/")) {
			toFile = toFile.substring(1);
		}
		FTPClient client = connect();
		try {
			if (client == null) {
				System.out.println(
						"FTP error (if no error shown than bad login details");
				return false;
			}
			client.setFileTransferMode(FTPClient.BINARY_FILE_TYPE);
			File f = Installer.getRootFile(toFile);
			if (f.getParentFile() != null) {
				f.getParentFile().mkdirs();
			}
			f.createNewFile();
			client.changeWorkingDirectory(FTPInfo.FTP_FOLDER);
			FileOutputStream out = new FileOutputStream(f);
			client.retrieveFile(file, out);
			client.disconnect();
			out.close();
			return true;
		} catch (SocketException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean upload(String file) {
		FTPClient client = connect();
		try {
			if (client == null) {
				System.out.println(
						"FTP error (if no error shown than bad login details");
				return false;
			}
			client.changeWorkingDirectory(FTPInfo.FTP_FOLDER);
			FileInputStream in = new FileInputStream(Installer
					.getRootFile(file));
			client.storeFile(file, in);
			client.disconnect();
			in.close();
			return true;
		} catch (SocketException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private static FTPClient connect() {
		FTPClient client = new FTPClient();
		try {
		    client.setControlEncoding("UTF-8");
			client.connect(FTPInfo.FTP_HOST, FTPInfo.FTP_PORT);
			if (!client.login(FTPInfo.FTP_USERNAME, FTPInfo.FTP_PASSWORD)) {
				return null;
			}
			return client;
		} catch (SocketException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
