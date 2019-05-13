package com.adpro.ticket.api;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.concurrent.CompletableFuture;


@Component
public class EmailUserNotificationService implements UserNotificationService {
    private String senderAddress;
    private EmailClient emailClient;
    private SpringTemplateEngine templateEngine;

    public EmailUserNotificationService(String senderAddress, EmailClient emailClient, SpringTemplateEngine templateEngine) {
        this.senderAddress = senderAddress;
        this.emailClient = emailClient;
        this.templateEngine = templateEngine;
    }

    private byte[] createAttachment(BookingData bookingData) {
        return "Hello".getBytes();
    }

    @Override
    public CompletableFuture<MessageResponse> sendBookingData(BookingData bookingData) {
        Context context = new Context();
        context.setVariable("booking", bookingData);

        MultipartBody body = new MultipartBody.Builder()
                .addFormDataPart("from", senderAddress)
                .addFormDataPart("to", bookingData.getEmail())
                .addFormDataPart("subject", "E-Ticket: " + bookingData.getMovieSession().getMovie().getName())
                .addFormDataPart("text", "Payment has been verified. View E-Tickets now.")
                .addFormDataPart("html", templateEngine.process("ticket-email", context))
                .addFormDataPart("attachment", "E-Ticket.pdf",
                        RequestBody.create(MediaType.parse("application/pdf"), createAttachment(bookingData))
                )
                .setType(MediaType.get("multipart/form-data"))
                .build();

        return emailClient.sendEmail(body);
    }
}