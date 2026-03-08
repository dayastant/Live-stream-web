package com.example.Live.stream.service.mapper;

import com.example.Live.stream.domain.entity.booking.Booking;
import com.example.Live.stream.domain.enums.BookingStatus;
import com.example.Live.stream.domain.enums.PaymentStatus;
import com.example.Live.stream.service.dto.BookingDTO;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class BookingMapper {

    public BookingDTO toDto(Booking booking) {
        if (booking == null) return null;

        return BookingDTO.builder()
                .id(booking.getId())
                .name(booking.getName())
                .phone(booking.getPhone())
                .email(booking.getEmail())
                .eventTitle(booking.getEventTitle())
                .eventDate(booking.getEventDate())
                .serviceType(booking.getServiceType())
                .location(booking.getLocation())
                .price(booking.getPrice())
                .paymentStatus(booking.getPaymentStatus())
                .bookingStatus(booking.getBookingStatus())
                .createdAt(booking.getCreatedAt())
                .build();
    }

    public Booking toEntity(BookingDTO dto) {
        if (dto == null) return null;

        Booking booking = new Booking();
        booking.setId(dto.getId());
        booking.setName(dto.getName());
        booking.setPhone(dto.getPhone());
        booking.setEmail(dto.getEmail());
        booking.setEventTitle(dto.getEventTitle());
        booking.setEventDate(dto.getEventDate());
        booking.setServiceType(dto.getServiceType());
        booking.setLocation(dto.getLocation());
        booking.setPrice(dto.getPrice());
        booking.setPaymentStatus(dto.getPaymentStatus() != null ? dto.getPaymentStatus() : PaymentStatus.UNPAID);
        booking.setBookingStatus(dto.getBookingStatus() != null ? dto.getBookingStatus() : BookingStatus.PENDING);
        booking.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());

        return booking;
    }

    public void updateEntity(BookingDTO dto, Booking booking) {
        if (dto == null || booking == null) return;

        if (dto.getName() != null) booking.setName(dto.getName());
        if (dto.getPhone() != null) booking.setPhone(dto.getPhone());
        if (dto.getEmail() != null) booking.setEmail(dto.getEmail());
        if (dto.getEventTitle() != null) booking.setEventTitle(dto.getEventTitle());
        if (dto.getEventDate() != null) booking.setEventDate(dto.getEventDate());
        if (dto.getServiceType() != null) booking.setServiceType(dto.getServiceType());
        if (dto.getLocation() != null) booking.setLocation(dto.getLocation());
        if (dto.getPrice() != null) booking.setPrice(dto.getPrice());
        if (dto.getPaymentStatus() != null) booking.setPaymentStatus(dto.getPaymentStatus());
        if (dto.getBookingStatus() != null) booking.setBookingStatus(dto.getBookingStatus());
    }

    public BookingDTO toConfirmationDto(Booking booking) {
        if (booking == null) return null;

        return BookingDTO.builder()
                .id(booking.getId())
                .name(booking.getName())
                .eventTitle(booking.getEventTitle())
                .eventDate(booking.getEventDate())
                .bookingStatus(booking.getBookingStatus())
                .build();
    }

    public BookingDTO toInvoiceDto(Booking booking) {
        if (booking == null) return null;

        return BookingDTO.builder()
                .id(booking.getId())
                .name(booking.getName())
                .eventTitle(booking.getEventTitle())
                .eventDate(booking.getEventDate())
                .price(booking.getPrice())
                .paymentStatus(booking.getPaymentStatus())
                .createdAt(booking.getCreatedAt())
                .build();
    }
}