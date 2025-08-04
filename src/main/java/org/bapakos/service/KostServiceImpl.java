package org.bapakos.service;

import org.bapakos.dao.KostDao;
import org.bapakos.model.dto.CreateKostDto;
import org.bapakos.model.dto.KostReportDto;
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

    @Override
    public Response updateById(KostEntity kost) throws SQLException {
        KostEntity currentKost =  this.kostDao.findById(kost.getId());
        if(currentKost == null) {
            return new Response(false, "kost tidak ditemukan");
        }
        currentKost.setName(kost.getName());
        currentKost.setLocation(kost.getLocation());
        currentKost.setImage(kost.getImage());
        currentKost.setDescription(kost.getDescription());
        currentKost.setPrice(kost.getPrice());
        kostDao.updateById(currentKost);
        return new Response(true, "Kost Berhasil Diedit");
    }

    @Override
    public Response deleteById(String id) throws SQLException {
        KostEntity kost = this.kostDao.findById(id);
        if(kost == null) {
            return new Response(false, "kost tidak ditemukan");
        }
        kostDao.deleteById(kost.getId());
        return new Response(true, "Kost Berhasil Dihapus");
    }

    @Override
    public KostEntity findById(String id) throws SQLException {
        KostEntity kost = this.kostDao.findById(id);
        if(kost == null) {
            return null;
        }
        return kost;
    }

    @Override
    public List<KostEntity> findAll() throws SQLException {
        return kostDao.findAll();
    }

    @Override
    public List<KostEntity> findByOwnerIdAndKeyword(String ownerId, String keyword) throws SQLException {
        return kostDao.findByOwnerIdAndKeyword(ownerId, keyword);
    }

    @Override
    public List<KostEntity> findByKeyword(String keyword) throws SQLException {
        return this.kostDao.findByKeyword(keyword);
    }

    @Override
    public KostReportDto getReportById(String id) throws SQLException {
        return this.kostDao.getReport(id);
    }


}
