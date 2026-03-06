package com.example.Live.stream.service.dto;

import com.example.Live.stream.domain.enums.BookingStatus;
import com.example.Live.stream.domain.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String eventTitle;
    private LocalDateTime eventDate;
    private String serviceType;
    private String location;
    private BigDecimal price;
    private PaymentStatus paymentStatus;
    private BookingStatus bookingStatus;
    private LocalDateTime createdAt;

    // Computed fields
    public String getEventDateFormatted() {
        return eventDate != null ?
                eventDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : null;
    }

    public String getCreatedAtFormatted() {
        return createdAt != null ?
                createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : null;
    }

    public String getPaymentStatusDisplay() {
        return paymentStatus != null ? paymentStatus.name() : null;
    }

    public String getBookingStatusDisplay() {
        return bookingStatus != null ? bookingStatus.name() : null;
    }

    public String getPriceFormatted() {
        return price != null ? "$" + price.toString() : null;
    }

    public boolean isPaid() {
        return PaymentStatus.PAID.equals(paymentStatus);
    }

    public boolean isConfirmed() {
        return BookingStatus.CONFIRMED.equals(bookingStatus);
    }
}