package com.wyx.demo.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("C://Users//zving//Desktop//test1//biuld-sftp.md");
        InputStream inputStream = new FileInputStream(file);
//        download(SFTP.hostName, SFTP.username, SFTP.password, "C://Users//zving//Desktop//test1//test.pdf", "/data/test/test.pdf");
        StringBuilder sBuffer = new StringBuilder();
        SimpleDateFormat sd = new SimpleDateFormat("/yyyy/MM/dd/");
        String time = sd.format(new Date());
        sBuffer.append("data/module11").append(time).append("test1.pdf");
        String remoteFilePath = sBuffer.toString();
       //upload(inputStream, remoteFilePath);
       //upload("/data/module6/2019/12/26/", "test5.md",inputStream);
        upload("/sftpuser/", "test6.md",inputStream);
       try {
           inputStream.close();
       } catch (IOException e) {
           e.printStackTrace();
       }
//       exist(SFTP.hostName, SFTP.username, SFTP.password, "/data/module3/2019/12/25/test.pdf");
//        delete(SFTP.hostName, SFTP.username, SFTP.password, "/data/200.txt");
    }

    public static String createConnectionString(String host, Integer port,
            String username, String password, String remoteFilePath) {
        return "sftp://" + username + ":" + password + "@" + host + ":" + port + "/"
                + remoteFilePath;
    }


    public static FileSystemOptions createDefaultOptions()
            throws FileSystemException {
        FileSystemOptions opts = new FileSystemOptions();
        SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(
                opts, "no");
        SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, true);
        SftpFileSystemConfigBuilder.getInstance().setSessionTimeoutMillis(opts, 30000);
        return opts;
    }

    public static boolean upload(String directory, String sftpFileName, InputStream inputStream) {
        String remoteFilePath = directory + sftpFileName;
        OutputStream output  =  null;
        StandardFileSystemManager manager = new StandardFileSystemManager();
        FileObject remoteFile = null;
        try {
            manager.init();
            remoteFile = manager.resolveFile(
                    createConnectionString(SFTP.host,SFTP.port, SFTP.username, SFTP.password,
                            remoteFilePath), createDefaultOptions());
            if(null != remoteFile && remoteFile.exists() == false){
                remoteFile.createFile();
            }
            output = remoteFile.getContent().getOutputStream();
            byte[] data = IOUtils.toByteArray(inputStream);
            IOUtils.write(data, output);
            System.out.println("File upload success");
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                inputStream.close();
                output.close();
            } catch (IOException e) {
            }
            manager.close();
        }
    }
    

    public static boolean download(HttpServletRequest request, HttpServletResponse response,String fileName, String remoteFilePath) {
        StandardFileSystemManager manager = new StandardFileSystemManager();
        InputStream is = null;
        try {
            manager.init();
            FileObject remoteFile = manager.resolveFile(
                    createConnectionString(SFTP.host, SFTP.port, SFTP.username, SFTP.password,
                            remoteFilePath), createDefaultOptions());
            is = remoteFile.getContent().getInputStream();
            //download(request, response, fileName, is);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (is!=null) {
                    is.close();
                }
            } catch (IOException e) {

            }
            manager.close();
        }
    }
    

    public static boolean exist(String host, Integer port,String username,
            String password, String remoteFilePath) {
        StandardFileSystemManager manager = new StandardFileSystemManager();

        try {
            manager.init();

            // Create remote object
            FileObject remoteFile = manager.resolveFile(
                    createConnectionString(host, port, username, password,
                            remoteFilePath), createDefaultOptions());

            System.out.println("File exist: " + remoteFile.exists());

            return remoteFile.exists();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            manager.close();
        }
    }

    public static void delete(String host, Integer port, String username,
            String password, String remoteFilePath) {
        StandardFileSystemManager manager = new StandardFileSystemManager();
     
        try {
            manager.init();
     
            // Create remote object
            FileObject remoteFile = manager.resolveFile(
                    createConnectionString(host,port, username, password,
                            remoteFilePath), createDefaultOptions());
     
            if (remoteFile.exists()) {
                remoteFile.delete();
                System.out.println("Delete remote file success");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            manager.close();
        }
    }
}
