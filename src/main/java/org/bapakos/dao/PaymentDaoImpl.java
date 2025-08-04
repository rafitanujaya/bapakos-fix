package org.bapakos.dao;

import org.bapakos.model.dto.FindAllPaymentOwnerKostDto;
import org.bapakos.model.dto.FindAllUserPaymentDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PaymentDaoImpl implements PaymentDao {

    private Connection conn;
    public PaymentDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean pay(String id) throws Exception {
        String query = "UPDATE payments set status = 'paid' where payment_id=?";

        try(PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean paymentExits(String id) throws Exception {
        String query = "SELECT id FROM payments WHERE payment_id = ?";
        try(PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setString(1, id);
            return ps.executeQuery().next();
        }
    }

    @Override
    public List<FindAllPaymentOwnerKostDto> findAllOwnerKost(String ownerKost) throws Exception {
        String query = "SELECT p.id, u.username, p.ammount, p.status FROM payments p JOIN bookings b ON p.booking_id = b.id JOIN users u ON b.user_id = u.id JOIN kosts k ON b.kost_id = k.id WHERE k.owner_id = ? ORDER BY p.id DESC";
        try(PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setString(1, ownerKost);
            ResultSet rs = ps.executeQuery();
            List<FindAllPaymentOwnerKostDto> result = new ArrayList<>();
            while (rs.next()) {
                FindAllPaymentOwnerKostDto dto = new FindAllPaymentOwnerKostDto();
                dto.setId(rs.getString("id"));
                dto.setUsername(rs.getString("username"));
                dto.setAmmount(rs.getInt("ammount"));
                dto.setStatus(FindAllPaymentOwnerKostDto.Status.valueOf(rs.getString("status")));
                result.add(dto);
            }
            return result;
        }
    }

    @Override
    public List<FindAllUserPaymentDto> findAllUser(String userId) throws Exception {
        String query = "SELECT p.id, p.ammount, k.name, p.status FROM payments p JOIN bookings b ON p.booking_id = b,id JOIN kosts k ON b.kost_id = k.id WHERE b.user_id = ? ORDER BY p.id DESC";
        try(PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            List<FindAllUserPaymentDto> result = new ArrayList<>();
            while (rs.next()) {
                FindAllUserPaymentDto dto = new FindAllUserPaymentDto();
                dto.setId(rs.getString("id"));
                dto.setNameKost(rs.getString("name"));
                dto.setAmmount(rs.getInt("ammount"));
                dto.setStatus(FindAllUserPaymentDto.Status.valueOf(rs.getString("status")));
                result.add(dto);
            }
            return result;
        }
    }
}
