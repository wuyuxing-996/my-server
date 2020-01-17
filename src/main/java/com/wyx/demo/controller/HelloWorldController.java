package com.wyx.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wyx.demo.test.Main;
import com.wyx.demo.test.SFTP;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;



/**
 * @author zving
 * @Date 2019-10-17 14:42:33
 * @Description 
 *
 */
@Controller
@CrossOrigin(allowCredentials="true",maxAge = 3600)
public class HelloWorldController {
    @GetMapping("/hello")
    public void hello(HttpServletRequest request, HttpServletResponse response){
           boolean a = Main.download(request, response,"32.md", "/sftpuser/readme.md");
           System.out.println(a+"*************************");
    }
    
    
    private static void download1(HttpServletRequest request, HttpServletResponse response,String fileName, String path) throws JSchException, SftpException{
        Session session = null;
        ChannelSftp sftpChannel = null;
        InputStream is = null;
        session = getSession();
        Channel channel=session.openChannel("sftp");
        channel.connect();
        sftpChannel=(ChannelSftp)channel;
        is = sftpChannel.get(path);
        //IOUtil.download(request, response, fileName, is);
        sftpChannel.disconnect();
        sftpChannel.getSession().disconnect();
        try {
            if (is != null) {
                is.close(); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static Session getSession(){
        String user = SFTP.username;
        String passwd = SFTP.password;
        String host = "work.zving.com";
        int port = 2294;
        JSch jsch=new JSch(); 
        Session session = null;
        try {
            session=jsch.getSession(user, host, port);  
            session.setPassword(passwd);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setTimeout(6000);
            session.connect();
            return session;
        } catch (JSchException e) {
            e.printStackTrace();
            if(session!=null && session.isConnected()){
                session.disconnect();
            }
            return null;
        } 
    }
}
