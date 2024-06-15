package edu.icet.coursework.bo.custom.impl;

import edu.icet.coursework.bo.custom.ProductBo;
import edu.icet.coursework.dao.DaoFactory;
import edu.icet.coursework.dao.custom.ProductDao;
import edu.icet.coursework.dto.Product;
import edu.icet.coursework.entity.ProductEntity;
import edu.icet.coursework.util.DaoType;
import org.modelmapper.ModelMapper;

public class ProductBoImpl implements ProductBo {

    ProductDao productDaoImpl = DaoFactory.getInstance().getDao(DaoType.PRODUCT);

    @Override
    public boolean addProduct(Product dto) {
        return productDaoImpl.addProduct(new ModelMapper().map(dto, ProductEntity.class));
    }
}
