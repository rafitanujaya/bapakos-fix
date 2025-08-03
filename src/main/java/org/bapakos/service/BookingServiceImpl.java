package org.bapakos.service;

import org.bapakos.dao.BookingDao;
import org.bapakos.dao.KostDao;
import org.bapakos.model.dto.FindBookingByOwnerDto;
import org.bapakos.model.dto.Response;
import org.bapakos.model.entity.BookingEntity;
import org.bapakos.model.entity.KostEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class BookingServiceImpl implements BookingService {
    private BookingDao bookingDao;
    private KostDao kostDao;

    public BookingServiceImpl(BookingDao bookingDao, KostDao kostDao) {
        this.bookingDao = bookingDao;
        this.kostDao = kostDao;
    }

    @Override
    public Response create(String kostId, String userId) throws SQLException {
        KostEntity kost = this.kostDao.findById(kostId);
        if (kost == null) {
            return new Response(false, "Kost Tidak ada");
        }
        BookingEntity booking = new BookingEntity();
        booking.setId(UUID.randomUUID().toString());
        booking.setUserId(userId);
        booking.setKostId(kostId);
        this.bookingDao.create(booking);
        return new Response(true, "Booking Telah Dibuat");
    }

    @Override
    public Response update(String id, String kostId, BookingEntity.Status status) throws SQLException {
        KostEntity kost = this.kostDao.findById(kostId);
        if (kost == null) {
            return new Response(false, "Kost Tidak ada");
        }
        BookingEntity booking = new BookingEntity();
        booking.setId(id);
        booking.setKostId(kostId);
        booking.setStatus(status);
        this.bookingDao.update(booking, kost.getPrice());
        return new Response(true, "Booking Telah Diupdate");
    }

    @Override
    public List<FindBookingByOwnerDto> findAllByOwnerKost(String ownerId) throws SQLException {
        return this.bookingDao.findAllByOwnerKost(ownerId);
    }
}
