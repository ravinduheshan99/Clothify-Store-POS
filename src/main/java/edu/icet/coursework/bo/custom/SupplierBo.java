package edu.icet.coursework.bo.custom;

import edu.icet.coursework.bo.SuperBo;
import edu.icet.coursework.dto.Product;
import edu.icet.coursework.dto.Supplier;
import edu.icet.coursework.dto.User;
import javafx.collections.ObservableList;


public interface SupplierBo extends SuperBo {
    public boolean addSupplier(Supplier supplier);
    public Supplier searchSupplier(String pid);
    public boolean removeSupplier(String cid);
    public boolean updateSupplier(Supplier supplier);
    public ObservableList<Supplier> searchAllSuppliers();
}
