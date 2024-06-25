package edu.icet.coursework.bo.custom.impl;

import edu.icet.coursework.bo.custom.SupplierBo;
import edu.icet.coursework.dao.DaoFactory;
import edu.icet.coursework.dao.custom.SupplierDao;
import edu.icet.coursework.dto.Supplier;
import edu.icet.coursework.entity.SupplierEntity;
import edu.icet.coursework.util.DaoType;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;


public class SupplierBoImpl implements  SupplierBo{

    SupplierDao supplierDao = DaoFactory.getInstance().getDao(DaoType.SUPPLIER);

    @Override
    public boolean addSupplier(Supplier dto) {
        return supplierDao.addSupplier(new ModelMapper().map(dto, SupplierEntity.class));
    }

    @Override
    public Supplier searchSupplier(String sid) {
        return supplierDao.searchSupplier(sid);
    }

    @Override
    public boolean removeSupplier(String sid) {
        return supplierDao.removeSupplier(sid);
    }

    @Override
    public boolean updateSupplier(Supplier dto) {
        return supplierDao.updateSupplier(new ModelMapper().map(dto,SupplierEntity.class));
    }

    @Override
    public ObservableList<Supplier> searchAllSuppliers() {
        return supplierDao.searchAllSuppliers();
    }

}
