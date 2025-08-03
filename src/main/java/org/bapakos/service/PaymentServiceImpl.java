package org.bapakos.service;

import org.bapakos.dao.PaymentDao;
import org.bapakos.model.dto.FindAllPaymentOwnerKostDto;
import org.bapakos.model.dto.FindAllUserPaymentDto;
import org.bapakos.model.dto.Response;

public class PaymentServiceImpl implements PaymentService {

    private PaymentDao paymentDao;

    public PaymentServiceImpl(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    @Override
    public Response pay(String id) throws Exception {
        if(!this.paymentDao.paymentExits(id)) {
            return new Response(false, "Payment tidak ditemukan");
        }
        this.paymentDao.pay(id);
        return new Response(true, "Payment Berhasil");
    }

    @Override
    public FindAllPaymentOwnerKostDto findAllOwnerKost(String ownerId) throws Exception {
        return this.findAllOwnerKost(ownerId);
    }

    @Override
    public FindAllUserPaymentDto findAllUser(String userId) throws Exception {
        return this.findAllUser(userId);
    }
}
