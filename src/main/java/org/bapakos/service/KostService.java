package org.bapakos.service;

import org.bapakos.model.dto.CreateKostDto;
import org.bapakos.model.dto.KostReportDto;
import org.bapakos.model.dto.Response;
import org.bapakos.model.entity.KostEntity;

import java.sql.SQLException;
import java.util.List;

public interface KostService {
    public Response create(CreateKostDto kost) throws SQLException;
    public List<KostEntity> findAllByOwnerId(String ownerId) throws SQLException;
    public Response updateById(KostEntity kost) throws SQLException;
    public Response deleteById(String id) throws SQLException;
    public KostEntity findById(String id) throws SQLException;
    public List<KostEntity> findAll() throws SQLException;
    public List<KostEntity> findByOwnerIdAndKeyword(String ownerId, String keyword) throws SQLException;
    public List<KostEntity> findByKeyword(String keyword) throws SQLException;
    public KostReportDto getReportById(String id) throws SQLException;
}
