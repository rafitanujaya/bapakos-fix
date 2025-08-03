package org.bapakos.dao;

import org.bapakos.model.dto.FindAllPaymentOwnerKostDto;
import org.bapakos.model.dto.FindAllUserPaymentDto;
import org.bapakos.model.dto.Response;
import org.bapakos.model.entity.PaymentEntity;

import java.util.List;

public interface PaymentDao {
    public Response pay(String id) throws Exception;
    public List<FindAllPaymentOwnerKostDto> findAllOwnerKost(String ownerKost) throws Exception;
    public List<FindAllUserPaymentDto> findAllUser(String userId) throws Exception;
}
