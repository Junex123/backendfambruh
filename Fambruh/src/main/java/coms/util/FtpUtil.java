package coms.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FtpUtil {
    private static final String SERVER = "118.139.176.23"; // Replace with your FTP server address
    private static final int PORT = 21; // FTP port (default is 21)
    private static final String USERNAME = " fambruharrmy@fambruh.com";
    private static final String PASSWORD = "7738569719home";
    private static final String REMOTE_FILE_PATH = "/home/wbygzf3u1ido/public_html/fambruh.com/fambruharrmy";
    public static void uploadFile(List<MultipartFile> files) throws IOException {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(SERVER, PORT);
            ftpClient.login(USERNAME, PASSWORD);
            ftpClient.enterLocalPassiveMode();

                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                InputStream inputStream = files.get(0).getInputStream();
                boolean uploaded = ftpClient.storeFile(REMOTE_FILE_PATH, inputStream);
                inputStream.close();
                if (uploaded) {
                    System.out.println("File uploaded successfully.");
                } else {
                    System.out.println("Failed to upload file.");
                }

        } finally {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        }
    }
}
