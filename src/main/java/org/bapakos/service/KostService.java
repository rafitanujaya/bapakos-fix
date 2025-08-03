package org.bapakos.service;

import org.bapakos.model.dto.Response;
import org.bapakos.model.entity.KostEntity;

import java.sql.SQLException;

public interface KostService {
    public Response create(KostEntity kost) throws SQLException;
}
