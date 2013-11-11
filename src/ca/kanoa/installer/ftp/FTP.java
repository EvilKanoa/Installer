package ca.kanoa.installer.ftp;

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
		FTPClient client = connect();
		try {
			if (client == null) {
				System.out.println(
						"FTP error (if no error shown than bad login details");
				return false;
			}
			
			client.changeWorkingDirectory(FTPInfo.FTP_FOLDER);
			client.setFileTransferMode(FTPClient.BINARY_FILE_TYPE);
			client.retrieveFile(file, new FileOutputStream(Installer
					.getRootFile(toFile)));
			System.out.println(Installer.getRootFile("TEST_FILE").getAbsolutePath());
			client.disconnect();
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
			client.storeFile(file, new FileInputStream(Installer
					.getRootFile(file)));
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
