package org.bapakos.service;

import org.bapakos.model.dto.CreateKostDto;
import org.bapakos.model.dto.Response;
import org.bapakos.model.entity.KostEntity;

import java.sql.SQLException;
import java.util.List;

public interface KostService {
    public Response create(CreateKostDto kost) throws SQLException;
    public List<KostEntity> findAllByOwnerId(String ownerId) throws SQLException;
}
