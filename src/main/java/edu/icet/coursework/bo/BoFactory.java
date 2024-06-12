package edu.icet.coursework.bo;

public class BoFactory {
    private BoFactory(){}
    private static BoFactory instance;
    public static BoFactory getInstance(){
        return instance!=null? instance:(instance=new BoFactory());
    }
}
