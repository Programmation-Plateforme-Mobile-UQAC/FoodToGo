package com.example.foodtogo.data.service;

import android.content.Context;

import com.example.foodtogo.data.database.SugarOrmApp;
import com.example.foodtogo.data.model.User;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class Mail {

    public final String host = "smtp.mailtrap.io";
    public final Integer port = 2525;
    public final String ssl = "false";
    public final String tls = "true";
    public final String user = "09ab0e5852eff3";
    public final String password = "903e8ae5b6b20e";

    public Mail(){

    }


    public void sendEmail(String send_to,String code) throws MessagingException {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host",this.host);
        properties.put("mail.smtp.port",this.port);
        properties.put("mail.smtp.ssl.enable",this.ssl);
        properties.put("mail.smtp.auth",this.tls);
        String user = this.user;
        String password = this.password;


        javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user,password);
            }
        });

        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.addRecipients(MimeMessage.RecipientType.TO, new InternetAddress[]{new InternetAddress(send_to)});
        mimeMessage.setSubject("Code pour confirmer la transaction");
        mimeMessage.setText(code);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Transport.send(mimeMessage);
                }
                catch (MessagingException e){
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }


}
