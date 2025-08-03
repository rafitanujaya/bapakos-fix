package org.bapakos.service;

import org.bapakos.model.dto.FindBookingByOwnerDto;
import org.bapakos.model.dto.Response;
import org.bapakos.model.entity.BookingEntity;

import java.sql.SQLException;
import java.util.List;

public interface BookingService {
    public Response create(String kostId, String userId) throws SQLException;
    public Response update(String id, String kostId, BookingEntity.Status status) throws SQLException;
    public List<FindBookingByOwnerDto> findAllByOwnerKost(String ownerId) throws SQLException;
}
