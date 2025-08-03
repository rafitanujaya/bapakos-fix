package org.bapakos.dao;

import org.bapakos.model.entity.FacilityEntity;
import org.bapakos.model.entity.KostEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class KostDaoImpl implements KostDao {
    private Connection conn;

    public KostDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean create(KostEntity kost) throws SQLException {
        String query = "INSERT INTO kosts(id, owner_id, name, location, description, price, image) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, kost.getId());
            ps.setString(2, kost.getOwnerId());
            ps.setString(3, kost.getName());
            ps.setString(4, kost.getLocation());
            ps.setString(5, kost.getDescription());
            ps.setDouble(6, kost.getPrice());
            ps.setBytes(7, kost.getImage());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean addFacility(List<FacilityEntity> facility, String kostId) throws SQLException {
        if (facility == null || facility.isEmpty()) {
            return true;
        }

        String query = "INSERT INTO kost_facilities(id, kost_id, facility_id) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            for (FacilityEntity facilityEntity : facility) {
                ps.setString(1, UUID.randomUUID().toString());
                ps.setString(2, kostId);
                ps.setString(3, facilityEntity.getId());
                ps.addBatch();
            }
            ps.executeBatch();
            return true;
        }
    }

    @Override
    public boolean update(KostEntity kost) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(KostEntity kost) throws SQLException {
        return false;
    }

    @Override
    public int countByOwnerId(String ownerId) throws SQLException {
        return 0;
    }

    @Override
    public boolean findByName(String name) throws SQLException {
        String query = "SELECT name FROM kosts WHERE name = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            return ps.executeQuery().next();
        }
    }

    @Override
    public KostEntity findById(String id) throws SQLException {
        return null;
    }

    @Override
    public List<KostEntity> findAll() throws SQLException {
        return List.of();
    }

    @Override
    public List<KostEntity> findAllByOwnerId(String ownerId) throws SQLException {
        return List.of();
    }

    @Override
    public List<KostEntity> findByOwnerIdAndKeyword(String role) throws SQLException {
        return List.of();
    }

    @Override
    public List<KostEntity> findByKeyword(String keyword) throws SQLException {
        return List.of();
    }
}
