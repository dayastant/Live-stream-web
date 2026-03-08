package com.example.Live.stream.domain.entity.booking;

import com.example.Live.stream.domain.base.BaseEntity;
import com.example.Live.stream.domain.enums.BookingStatus;
import com.example.Live.stream.domain.enums.PaymentStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(length = 20)
    private String phone;

    @Column(length = 100)
    private String email;

    @Column(name = "event_title")
    private String eventTitle;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @Column(name = "service_type")
    private String serviceType;

    private String location;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status", nullable = false)
    private BookingStatus bookingStatus = BookingStatus.PENDING;

    // Default constructor
    public Booking() {
    }

    // Constructor with fields
    public Booking(String name, String phone, String email, String eventTitle,
                   LocalDateTime eventDate, String serviceType, String location,
                   BigDecimal price) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.serviceType = serviceType;
        this.location = location;
        this.price = price;
        this.paymentStatus = PaymentStatus.UNPAID;
        this.bookingStatus = BookingStatus.PENDING;
    }

    // ===== Getters and Setters =====

    @Override
    public String getId() {
        return super.getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    // ===== Business methods =====

    public void confirm() {
        if (this.bookingStatus == BookingStatus.PENDING) {
            this.bookingStatus = BookingStatus.CONFIRMED;
        } else {
            throw new IllegalStateException("Cannot confirm booking in status: " + this.bookingStatus);
        }
    }

    public void cancel() {
        this.bookingStatus = BookingStatus.CANCELLED;
    }

    public void markAsPaid() {
        this.paymentStatus = PaymentStatus.PAID;
    }
}