package org.bapakos.dao;

import org.bapakos.model.entity.FacilityEntity;
import org.bapakos.model.entity.KostEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public boolean updateById(KostEntity kost) throws SQLException {
        String query = "UPDATE kosts SET name = ?, location = ?, description = ?, price = ?, image = ?  WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, kost.getName());
            ps.setString(2, kost.getLocation());
            ps.setString(3, kost.getDescription());
            ps.setDouble(4, kost.getPrice());
            ps.setBytes(5, kost.getImage());
            ps.setString(6, kost.getId());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteById(String id) throws SQLException {
        String query = "DELETE FROM kosts WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, kost.getId());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public int countByOwnerId(String ownerId) throws SQLException {
        String query = "SELECT COUNT(*) FROM kosts WHERE owner_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, ownerId);
            return ps.executeQuery().getInt(1);
        }
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
        String query = "SELECT owner_id, name, location, image, price, description FROM kosts WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                KostEntity kost = new KostEntity();
                kost.setId(id);
                kost.setOwnerId(rs.getString("owner_id"));
                kost.setName(rs.getString("name"));
                kost.setLocation(rs.getString("location"));
                kost.setDescription(rs.getString("description"));
                kost.setPrice(rs.getInt("price"));
                kost.setImage(rs.getBytes("image"));
                return kost;
            }
        }
        return null;
    }

    @Override
    public List<KostEntity> findAll() throws SQLException {
        String query = "SELECT id, owner_id, name, location, image, price, description FROM kosts";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            List<KostEntity> kosts = new ArrayList<>();
            while (rs.next()) {
                KostEntity kost = new KostEntity();
                kost.setId(rs.getString("id"));
                kost.setOwnerId(rs.getString("owner_id"));
                kost.setName(rs.getString("name"));
                kost.setLocation(rs.getString("location"));
                kost.setDescription(rs.getString("description"));
                kost.setPrice(rs.getInt("price"));
                kost.setImage(rs.getBytes("image"));
                kosts.add(kost);
            }
            return kosts;
        }
    }

    @Override
    public List<KostEntity> findAllByOwnerId(String ownerId) throws SQLException {
        String query = "SELECT id, owner_id, name, location, image, price, description FROM kosts WHERE owner_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, ownerId);
            ResultSet rs = ps.executeQuery();
            List<KostEntity> kosts = new ArrayList<>();
            while (rs.next()) {
                KostEntity kost = new KostEntity();
                kost.setId(rs.getString("id"));
                kost.setOwnerId(rs.getString("owner_id"));
                kost.setName(rs.getString("name"));
                kost.setLocation(rs.getString("location"));
                kost.setDescription(rs.getString("description"));
                kost.setPrice(rs.getInt("price"));
                kost.setImage(rs.getBytes("image"));
                kosts.add(kost);
            }
            return kosts;
        }
    }

    @Override
    public List<KostEntity> findByOwnerIdAndKeyword(String ownerId, String keyword) throws SQLException {
        String query = "SELECT id, owner_id, name, location, image, price, description FROM kosts WHERE owner_id = ? AND name LIKE ? OR location LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, ownerId);
            ps.setString(2, "%" + keyword + "%");
            ps.setString(3, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            List<KostEntity> kosts = new ArrayList<>();
            while (rs.next()) {
                KostEntity kost = new KostEntity();
                kost.setId(rs.getString("id"));
                kost.setOwnerId(rs.getString("owner_id"));
                kost.setName(rs.getString("name"));
                kost.setLocation(rs.getString("location"));
                kost.setDescription(rs.getString("description"));
                kost.setPrice(rs.getInt("price"));
                kost.setImage(rs.getBytes("image"));
                kosts.add(kost);
            }
            return kosts;
        }

    }

    @Override
    public List<KostEntity> findByKeyword(String keyword) throws SQLException {
        String query = "SELECT id, owner_id, name, location, image, price, description FROM kosts WHERE name LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            List<KostEntity> kosts = new ArrayList<>();
            while (rs.next()) {
                KostEntity kost = new KostEntity();
                kost.setId(rs.getString("id"));
                kost.setOwnerId(rs.getString("owner_id"));
                kost.setName(rs.getString("name"));
                kost.setLocation(rs.getString("location"));
                kost.setDescription(rs.getString("description"));
                kost.setPrice(rs.getInt("price"));
                kost.setImage(rs.getBytes("image"));
                kosts.add(kost);
            }
            return kosts;
        }
    }
}
