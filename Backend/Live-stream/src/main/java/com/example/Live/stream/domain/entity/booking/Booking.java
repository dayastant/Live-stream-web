package com.example.Live.stream.domain.entity.booking;


import com.example.Live.stream.domain.base.BaseEntity;
import com.example.Live.stream.domain.enums.BookingStatus;
import com.example.Live.stream.domain.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings",
        indexes = {
                @Index(name = "idx_booking_event_date", columnList = "eventDate"),
                @Index(name = "idx_booking_status", columnList = "bookingStatus")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
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
    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status", nullable = false)
    @Builder.Default
    private BookingStatus bookingStatus = BookingStatus.PENDING;

    // Business methods
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