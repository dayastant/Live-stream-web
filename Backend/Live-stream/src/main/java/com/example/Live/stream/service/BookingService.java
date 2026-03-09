package com.example.Live.stream.service;

import com.example.Live.stream.service.dto.BookingDTO;
import java.util.List;

public interface BookingService {

    BookingDTO createBooking(BookingDTO dto);

    BookingDTO confirmBooking(String id);

    BookingDTO updatePaymentAndPrice(String id, BookingDTO dto);

    List<BookingDTO> getAllBookings();

    BookingDTO getBooking(String id);

    BookingDTO getBookerInfo(String id);
}