package com.yonyou.yuncai.cpu.bi.domain.price;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by fengjqc on 2017/3/23.
 */
public class BiPriceConstant implements Serializable {
    public static final String LOWESTPRICE = "LOWESTPRICE";
    public static final String AVERAGEPRICE = "AVERAGEPRICE";
    public static final String LASTPRICE = "LASTPRICE";


    /**
     *  属性名常量值都要用小写
     */
    //属性前缀，用来判断是属于哪个分类的属性
    public static final String ERP_PREFIX_PURCHASE="vpurchase_";
    public static final String ERP_PREFIX_SUPPLY="vsupply_";
    public static final String ERP_PREFIX_MATERIAL="vmaterial_";
    public static final String ERP_PREFIX_PRICE="vprice_";
    public static final String ERP_PREFIX_CONDITION="vcondition_";


    public static final String Erp_Pk_material="pk_material";
    public static final String Erp_Id="id";
    public static final String Erp_Pk_org="pk_org";
    public static final String Erp_Pk_supplier="pk_supplier";
    public static final String Erp_Dbilldate="dbilldate";
    public static final String Erp_Norigtaxprice="norigtaxprice";

    public static final String Erp_Nsum="nsum";
    public static final String Erp_Nmny="nmny";
    public static final String Erp_Vsrc="vsrc";
    public static final String Erp_VunitName="vunitname";
    public static final String Erp_Vcurrency="vcurrency";
    public static final String Erp_NpriceNoTax="npricenotax";
    public static final String Erp_Ntax="ntax";
    public static final String Erp_NmnyNoTax="nmnynotax";
    public static final String Erp_VsrcSystem="vsrcsystem";
    public static final String Erp_VaddType="vaddtype";
    public static final String Erp_VsrcBillCode="vsrcbillcode";

//    public static final String Erp_vtenantId="vtenantId";
//    public static final String Erp_="vpurchaseEnterpriseId";
//    public static final String Erp_="vpurchaseErpId";
//    public static final String Erp_="vpurchaseCode";
//    public static final String Erp_="vpurchaseName";
//    public static final String Erp_="vpurchase";
//    public static final String Erp_="vsupplyTenantId";
//    public static final String Erp_="vsupplyEnterpriseId";
//    public static final String Erp_="vsupplyErpId";
//    public static final String Erp_="vsupplyCode";
//    public static final String Erp_="vsupplyName";
//    public static final String Erp_="vsupply";
//    public static final String Erp_="vmaterialId";
//    public static final String Erp_="vmaterialErpId";
//    public static final String Erp_="vmaterialCode";
//    public static final String Erp_="vmaterialName";
//    public static final String Erp_="vmaterial";
//    public static final String Erp_="nsum";
//    public static final String Erp_="nprice";
//    public static final String Erp_="nmny";
//    public static final String Erp_="vcondition";
//    public static final String Erp_="vprice";
//    public static final String Erp_="vsrc";
//    public static final String Erp_="vsrcMark";
//    public static final String Erp_="dbilldate";
//    public static final String Erp_="ts";
//    public static final String Erp_="vupdateMark";

    //接口查询参数名称
    public static final String param_purchaserId="purchaserId";
    public static final String param_supplierId="supplierId";
    public static final String param_materialId="materialId";

//    static {
//          Set<String> Set_Field = new HashSet<String>();
//          Set_Field.add();
//    }
}
