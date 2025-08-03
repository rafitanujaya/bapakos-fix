package org.bapakos.service;

import org.bapakos.dao.KostDao;
import org.bapakos.model.dto.CreateKostDto;
import org.bapakos.model.dto.Response;
import org.bapakos.model.entity.KostEntity;
import org.bapakos.session.Session;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class KostServiceImpl implements KostService {
    private KostDao kostDao;
    public KostServiceImpl(KostDao kostDao) {
        this.kostDao = kostDao;
    }

    @Override
    public Response create(CreateKostDto kost) throws SQLException {
        if(this.kostDao.findByName(kost.getName())) {
            return new Response(false, "Nama kost sudah digunakan");
        }

        KostEntity newKost = new KostEntity();
        newKost.setId(UUID.randomUUID().toString());
        newKost.setName(kost.getName());
        newKost.setOwnerId(Session.get().getId());
        newKost.setLocation(kost.getLocation());
        newKost.setImage(kost.getImage());
        newKost.setDescription(kost.getDescription());
        newKost.setPrice(kost.getPrice());
        kostDao.create(newKost);
        kostDao.addFacility(kost.getFacilities(), newKost.getId());

        return new Response(true, "Kost Berhasil Dibuat");
    }

    @Override
    public List<KostEntity> findAllByOwnerId(String ownerId) throws SQLException {
        return this.kostDao.findAllByOwnerId(ownerId);
    }
}
