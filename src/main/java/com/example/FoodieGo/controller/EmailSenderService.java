/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.FoodieGo.controller;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String toEmail,
                                String body,
                                String subject) throws MessagingException 
    {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("foodiego.inderjit@gmail.com");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText(body,true);
        mimeMessageHelper.setSubject(subject);

//        FieSystemResource fileSystem
//                = new FileSystemResource(new File(attachment));
//leSystemResource fileSystem
//                = new FileSystemResource(new File(attachment));

//        mimeMessageHelper.addAttachment(fileSystem.getFilename(),
//                fileSystem);

        mailSender.send(mimeMessage);
        System.out.println("Mail Send...");
        
        
        
        
        
//        SimpleMailMessage message = new SimpleMailMessage();
//
//        message.setFrom("inderjit.singh18112000@gmail.com");
//        message.setTo(toEmail);
//        
//        message.setText(body);
//    
//
//        mailSender.send(message);
//        System.out.println("Mail Send...");
    }
}