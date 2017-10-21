package util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Email{

    public Email() {
    }

    public static void enviarEmail(String email_para, String descricao){

        if(email_para.equals(""))
            return;
        
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(props,
            new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("tbksistemas@gmail.com","xozinega2017");
                }
            });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("tbksistemas@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email_para));
            message.setSubject("  Requisição de serviços solicitada com sucesso");

            String mensagem = "  Assim que possivel iremos analizar sua solicitação e tomaremos as medidas necessarias para "
                    + "solucionoar o seu problema.;\n  Prefeitura municipal de Bagé a serviço da população!";

            message.setText(mensagem);            
                        
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }        
}
    
    
