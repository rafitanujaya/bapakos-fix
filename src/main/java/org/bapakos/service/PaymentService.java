package org.bapakos.service;

import org.bapakos.model.dto.FindAllPaymentOwnerKostDto;
import org.bapakos.model.dto.FindAllUserPaymentDto;
import org.bapakos.model.dto.Response;

public interface PaymentService {
    public Response pay(String id) throws Exception;
    public FindAllPaymentOwnerKostDto findAllOwnerKost(String ownerId) throws Exception;
    public FindAllUserPaymentDto findAllUser(String userId) throws Exception;
}
