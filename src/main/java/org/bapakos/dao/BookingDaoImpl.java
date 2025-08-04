package org.bapakos.dao;

import org.bapakos.model.dto.FindBookingByOwnerDto;
import org.bapakos.model.entity.BookingEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingDaoImpl implements BookingDao {
    private Connection conn;
    public BookingDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean create(BookingEntity booking) throws SQLException {
        String query = "INSERT INTO bookings (id, user_id, kost_id) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, booking.getId());
            ps.setString(2, booking.getUserId());
            ps.setString(3, booking.getKostId());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(BookingEntity booking, int price) throws SQLException {
        String query = "UPDATE bookings SET status = ? WHERE id = ? AND kost_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, booking.getStatus().toString());
            ps.setString(2, booking.getId());
            ps.setString(3, booking.getKostId());
            boolean updated =  ps.executeUpdate() > 0;

            if(updated && booking.getStatus().toString().equalsIgnoreCase("approve")) {
                String query2 = "UPDATE bookings SET status = 'reject' WHERE kost_id = ? AND id != ? AND status = 'pending'";
                try (PreparedStatement ps2 = conn.prepareStatement(query2)) {
                    ps2.setString(1, booking.getKostId());
                    ps2.setString(2, booking.getId());
                    ps2.executeUpdate();
                }
                String query3 = "INSERT INTO payments(id, booking_id, amount) VALUES (?, ?, ?)";
                try (PreparedStatement ps3 = conn.prepareStatement(query3)) {
                    ps3.setString(1, UUID.randomUUID().toString());
                    ps3.setString(2, booking.getId());
                    ps3.setInt(3, price);
                    ps3.executeUpdate();
                }
            }
            return updated;
        }
    }

    @Override
    public List<FindBookingByOwnerDto> findAllByOwnerKost(String ownerId) throws SQLException {
        String query = "SELECT b.id, b.kost_id, b.status, k.name AS kost_name, u.username AS user_name FROM bookings b JOIN kosts k ON b.kost_id = k.id JOIN users u ON b.user_id = u.id WHERE k.owner_id = ? ORDER BY b.created_at DESC";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, ownerId);
            try (ResultSet rs = ps.executeQuery()) {
                List<FindBookingByOwnerDto> bookings = new ArrayList<>();
                while (rs.next()) {
                    FindBookingByOwnerDto booking = new FindBookingByOwnerDto();
                    booking.setId(rs.getString("id"));
                    booking.setKostId(rs.getString("kost_id"));
                    booking.setName(rs.getString("kost_name"));
                    booking.setUsername(rs.getString("user_name"));
                    booking.setStatus(FindBookingByOwnerDto.Status.valueOf(rs.getString("status")));
                    bookings.add(booking);
                }
                return bookings;
            }
        }
    }


}
