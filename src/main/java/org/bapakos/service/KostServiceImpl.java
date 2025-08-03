package org.bapakos.service;

import org.bapakos.dao.KostDao;
import org.bapakos.model.dto.Response;
import org.bapakos.model.entity.KostEntity;
import org.bapakos.session.Session;

import java.sql.SQLException;
import java.util.UUID;

public class KostServiceImpl implements KostService {
    private KostDao kostDao;
    public KostServiceImpl(KostDao kostDao) {
        this.kostDao = kostDao;
    }

    @Override
    public Response create(KostEntity kost) throws SQLException {
        if(this.kostDao.findByName(kost.getName())) {
            return new Response(false, "Nama kost sudah digunakan");
        }

        kost.setId(UUID.randomUUID().toString());
        kost.setOwnerId(Session.get().getId());
        kostDao.create(kost);
        return new Response(true, "Kost Berhasil Dibuat");
    }
}
