package edu.icet.coursework.bo.custom.impl;

import edu.icet.coursework.bo.SuperBo;
import edu.icet.coursework.bo.custom.SupplierBo;
import edu.icet.coursework.dao.DaoFactory;
import edu.icet.coursework.dao.custom.SupplierDao;
import edu.icet.coursework.dto.Supplier;
import edu.icet.coursework.entity.SupplierEntity;
import edu.icet.coursework.util.DaoType;
import org.modelmapper.ModelMapper;

public class SupplierBoImpl implements  SupplierBo{

    SupplierDao supplierDao = DaoFactory.getInstance().getDao(DaoType.SUPPLIER);

    @Override
    public boolean addSupplier(Supplier dto) {
        return supplierDao.addSupplier(new ModelMapper().map(dto, SupplierEntity.class));
    }
}
