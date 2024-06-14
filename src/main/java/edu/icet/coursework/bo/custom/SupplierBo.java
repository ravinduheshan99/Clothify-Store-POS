package edu.icet.coursework.bo.custom;

import edu.icet.coursework.bo.SuperBo;
import edu.icet.coursework.dto.Supplier;


public interface SupplierBo extends SuperBo {
    public boolean addSupplier(Supplier supplier);
}
