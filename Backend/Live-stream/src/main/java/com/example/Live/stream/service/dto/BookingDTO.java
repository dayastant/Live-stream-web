package com.example.Live.stream.service.dto;

import com.example.Live.stream.domain.enums.BookingStatus;
import com.example.Live.stream.domain.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    // Private constructor for builder
    private BookingDTO(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.phone = builder.phone;
        this.email = builder.email;
        this.eventTitle = builder.eventTitle;
        this.eventDate = builder.eventDate;
        this.serviceType = builder.serviceType;
        this.location = builder.location;
        this.price = builder.price;
        this.paymentStatus = builder.paymentStatus;
        this.bookingStatus = builder.bookingStatus;
        this.createdAt = builder.createdAt;
    }

    // Static builder method
    public static Builder builder() {
        return new Builder();
    }

    // Builder class
    public static class Builder {
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

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder eventTitle(String eventTitle) {
            this.eventTitle = eventTitle;
            return this;
        }

        public Builder eventDate(LocalDateTime eventDate) {
            this.eventDate = eventDate;
            return this;
        }

        public Builder serviceType(String serviceType) {
            this.serviceType = serviceType;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder paymentStatus(PaymentStatus paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }

        public Builder bookingStatus(BookingStatus bookingStatus) {
            this.bookingStatus = bookingStatus;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public BookingDTO build() {
            return new BookingDTO(this);
        }
    }

    // ===== Getters =====

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getLocation() {
        return location;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // ===== Computed fields =====

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