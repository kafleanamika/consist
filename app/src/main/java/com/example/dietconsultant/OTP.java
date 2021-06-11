package com.example.dietconsultant;

import android.os.StrictMode;
import android.widget.EditText;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.net.Authenticator;
import java.util.Properties;

public class OTP extends Authenticator {




    String email;
    String my_email = "Kafleanamika01@gmail.com";
    String password = "Assfgh123@";
    String OTP = "";



    public OTP(String email, String OTP){

        this.email = email;
        this.OTP = OTP;
    }

    public void send_OTP(){

        Properties p = new Properties();
        p.put("mail.smtp.auth","true");
        p.put("mail.smtp.starttls.enable","true");
        p.put("mail.smtp.host","smtp.gmail.com");
        p.put("mail.smtp.port","587");


        Session s = Session.getInstance(p,

                new javax.mail.Authenticator() {

            protected PasswordAuthentication getPWA(){

                return  new PasswordAuthentication(my_email,password);
            }
                });


        try {

            Transport transport = s.getTransport("smtp");
            transport.connect("user","passw@rd");
            Message m = new MimeMessage(s);
            m.setFrom(new InternetAddress(my_email));
            m.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            m.setSubject("OTP FOR CONSULTANT DIET");
            m.setText(OTP);
            Transport.send(m);

        } catch (MessagingException e) {
            e.printStackTrace();
        }


        StrictMode.ThreadPolicy pol = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(pol);


    }


}
