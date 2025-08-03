package org.bapakos.dao;

import org.bapakos.model.dto.FindBookingByOwnerDto;
import org.bapakos.model.entity.BookingEntity;

import java.sql.SQLException;
import java.util.List;

public interface BookingDao {
    public boolean create(BookingEntity booking) throws SQLException;
    public boolean update(BookingEntity booking, int price) throws SQLException;
    public List<FindBookingByOwnerDto> findAllByOwnerKost(String ownerId) throws SQLException;
}
