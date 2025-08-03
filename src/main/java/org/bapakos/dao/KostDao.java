package org.bapakos.dao;

import org.bapakos.model.entity.FacilityEntity;
import org.bapakos.model.entity.KostEntity;

import java.sql.SQLException;
import java.util.List;

public interface KostDao {
    public boolean create(KostEntity kost) throws SQLException;
    public boolean addFacility(List<FacilityEntity> facility, String kostId) throws SQLException;
    public boolean update(KostEntity kost) throws SQLException;
    public boolean delete(KostEntity kost) throws SQLException;
    public int countByOwnerId(String ownerId) throws SQLException;
    public boolean findByName(String name) throws SQLException;
    public KostEntity findById(String id) throws SQLException;
    public List<KostEntity> findAll() throws SQLException;
    public List<KostEntity> findAllByOwnerId(String ownerId) throws SQLException;
    public List<KostEntity> findByOwnerIdAndKeyword(String ownerId, String keyword) throws SQLException;
    public List<KostEntity> findByKeyword(String keyword) throws SQLException;
}
