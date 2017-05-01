package com.yonyou.yuncai.cpu.bi.domain.price;

import org.json.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * Created by fengjqc on 2017/3/22.
 */
public class BiPriceEntity implements Serializable {
    private String id ;
    private String vtenantId;
    private String vpurchaseEnterpriseId;
    private String vpurchaseErpId;
    private String vpurchaseCode;
    private String vpurchaseName;
    private String vpurchase;
    private String vsupplyTenantId;
    private String vsupplyEnterpriseId;
    private String vsupplyErpId;
    private String vsupplyCode;
    private String vsupplyName;
    private String vsupply;
    private String vmaterialId;
    private String vmaterialErpId;
    private String vmaterialCode;
    private String vmaterialName;
    private String vmaterial;
    private BigDecimal nsum;
    private BigDecimal nprice;
    private BigDecimal nmny;
    private String vcondition;
    private String vprice;
    private String vsrc;
    private String vsrcMark;
    private String dbilldate;
    private Date ts;
    private String vupdateMark;

    //20170414新增
    private String vmaterialSpec ;
    private String vsupplyProdId ;
    private String vsupplyProdName ;
    private String vsupplyProdCode ;
    private String vunitName ;
    private String vcurrency ;
    private BigDecimal npriceNoTax ;
    private BigDecimal ntax ;
    private BigDecimal nmnyNoTax ;
    private String vsrcSystem ;
    private String vaddType ;
    private String vsrcBillCode ;

    private Map<String,Object> jsupply ;
    private Map<String,Object> jmaterial;
    private Map<String,Object> jpurchase;
    private Map<String,Object> jprice;
    private Map<String,Object> jcondition;


    public String getDbilldate() {
        return dbilldate;
    }

    public void setDbilldate(String dbilldate) {
        this.dbilldate = dbilldate;
    }

    public String getVpurchaseEnterpriseId() {
        return vpurchaseEnterpriseId;
    }

    public void setVpurchaseEnterpriseId(String vpurchaseEnterpriseId) {
        this.vpurchaseEnterpriseId = vpurchaseEnterpriseId;
    }

    public String getVpurchaseErpId() {
        return vpurchaseErpId;
    }

    public void setVpurchaseErpId(String vpurchaseErpId) {
        this.vpurchaseErpId = vpurchaseErpId;
    }

    public String getVpurchaseCode() {
        return vpurchaseCode;
    }

    public void setVpurchaseCode(String vpurchaseCode) {
        this.vpurchaseCode = vpurchaseCode;
    }

    public String getVpurchaseName() {
        return vpurchaseName;
    }

    public void setVpurchaseName(String vpurchaseName) {
        this.vpurchaseName = vpurchaseName;
    }

    public String getVsupplyTenantId() {
        return vsupplyTenantId;
    }

    public void setVsupplyTenantId(String vsupplyTenantId) {
        this.vsupplyTenantId = vsupplyTenantId;
    }

    public String getVsupplyEnterpriseId() {
        return vsupplyEnterpriseId;
    }

    public void setVsupplyEnterpriseId(String vsupplyEnterpriseId) {
        this.vsupplyEnterpriseId = vsupplyEnterpriseId;
    }

    public String getVsupplyErpId() {
        return vsupplyErpId;
    }

    public void setVsupplyErpId(String vsupplyErpId) {
        this.vsupplyErpId = vsupplyErpId;
    }

    public String getVsupplyCode() {
        return vsupplyCode;
    }

    public void setVsupplyCode(String vsupplyCode) {
        this.vsupplyCode = vsupplyCode;
    }

    public String getVsupplyName() {
        return vsupplyName;
    }

    public void setVsupplyName(String vsupplyName) {
        this.vsupplyName = vsupplyName;
    }

    public String getVmaterialId() {
        return vmaterialId;
    }

    public void setVmaterialId(String vmaterialId) {
        this.vmaterialId = vmaterialId;
    }

    public String getVmaterialErpId() {
        return vmaterialErpId;
    }

    public void setVmaterialErpId(String vmaterialErpId) {
        this.vmaterialErpId = vmaterialErpId;
    }

    public String getVmaterialCode() {
        return vmaterialCode;
    }

    public void setVmaterialCode(String vmaterialCode) {
        this.vmaterialCode = vmaterialCode;
    }

    public String getVmaterialName() {
        return vmaterialName;
    }

    public void setVmaterialName(String vmaterialName) {
        this.vmaterialName = vmaterialName;
    }

    public String getVsrcMark() {
        return vsrcMark;
    }

    public void setVsrcMark(String vsrcMark) {
        this.vsrcMark = vsrcMark;
    }

    public String getVupdateMark() {
        return vupdateMark;
    }

    public void setVupdateMark(String vupdateMark) {
        this.vupdateMark = vupdateMark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVtenantId() {
        return vtenantId;
    }

    public void setVtenantId(String vtenantId) {
        this.vtenantId = vtenantId;
    }

    public String getVpurchase() {
        return vpurchase;
    }

    public void setVpurchase(String vpurchase) {
        this.vpurchase = vpurchase;
    }

    public String getVsupply() {
        return vsupply;
    }

    public void setVsupply(String vsupply) {
        this.vsupply = vsupply;
    }

    public String getVmaterial() {
        return vmaterial;
    }

    public void setVmaterial(String vmaterial) {
        this.vmaterial = vmaterial;
    }

    public BigDecimal getNsum() {
        return nsum;
    }

    public void setNsum(BigDecimal nsum) {
        this.nsum = nsum;
    }

    public BigDecimal getNprice() {
        return nprice;
    }

    public void setNprice(BigDecimal nprice) {
        this.nprice = nprice;
    }

    public BigDecimal getNmny() {
        return nmny;
    }

    public void setNmny(BigDecimal nmny) {
        this.nmny = nmny;
    }

    public String getVcondition() {
        return vcondition;
    }

    public void setVcondition(String vcondition) {
        this.vcondition = vcondition;
    }

    public String getVprice() {
        return vprice;
    }

    public void setVprice(String vprice) {
        this.vprice = vprice;
    }

    public String getVsrc() {
        return vsrc;
    }

    public void setVsrc(String vsrc) {
        this.vsrc = vsrc;
    }

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }


    //20170414新增

    public String getVmaterialSpec() {
        return vmaterialSpec;
    }

    public void setVmaterialSpec(String vmaterialSpec) {
        this.vmaterialSpec = vmaterialSpec;
    }

    public String getVsupplyProdId() {
        return vsupplyProdId;
    }

    public void setVsupplyProdId(String vsupplyProdId) {
        this.vsupplyProdId = vsupplyProdId;
    }

    public String getVsupplyProdName() {
        return vsupplyProdName;
    }

    public void setVsupplyProdName(String vsupplyProdName) {
        this.vsupplyProdName = vsupplyProdName;
    }

    public String getVsupplyProdCode() {
        return vsupplyProdCode;
    }

    public void setVsupplyProdCode(String vsupplyProdCode) {
        this.vsupplyProdCode = vsupplyProdCode;
    }

    public String getVunitName() {
        return vunitName;
    }

    public void setVunitName(String vunitName) {
        this.vunitName = vunitName;
    }

    public String getVcurrency() {
        return vcurrency;
    }

    public void setVcurrency(String vcurrency) {
        this.vcurrency = vcurrency;
    }

    public BigDecimal getNpriceNoTax() {
        return npriceNoTax;
    }

    public void setNpriceNoTax(BigDecimal npriceNoTax) {
        this.npriceNoTax = npriceNoTax;
    }

    public BigDecimal getNtax() {
        return ntax;
    }

    public void setNtax(BigDecimal ntax) {
        this.ntax = ntax;
    }

    public BigDecimal getNmnyNoTax() {
        return nmnyNoTax;
    }

    public void setNmnyNoTax(BigDecimal nmnyNoTax) {
        this.nmnyNoTax = nmnyNoTax;
    }

    public String getVsrcSystem() {
        return vsrcSystem;
    }

    public void setVsrcSystem(String vsrcSystem) {
        this.vsrcSystem = vsrcSystem;
    }

    public String getVaddType() {
        return vaddType;
    }

    public void setVaddType(String vaddType) {
        this.vaddType = vaddType;
    }

    public String getVsrcBillCode() {
        return vsrcBillCode;
    }

    public void setVsrcBillCode(String vsrcBillCode) {
        this.vsrcBillCode = vsrcBillCode;
    }


    //json


    public Map<String, Object> getJsupply() {
        return jsupply;
    }

    public void setJsupply(Map<String, Object> jsupply) {
        this.jsupply = jsupply;
    }

    public Map<String, Object> getJmaterial() {
        return jmaterial;
    }

    public void setJmaterial(Map<String, Object> jmaterial) {
        this.jmaterial = jmaterial;
    }

    public Map<String, Object> getJpurchase() {
        return jpurchase;
    }

    public void setJpurchase(Map<String, Object> jpurchase) {
        this.jpurchase = jpurchase;
    }

    public Map<String, Object> getJprice() {
        return jprice;
    }

    public void setJprice(Map<String, Object> jprice) {
        this.jprice = jprice;
    }

    public Map<String, Object> getJcondition() {
        return jcondition;
    }

    public void setJcondition(Map<String, Object> jcondition) {
        this.jcondition = jcondition;
    }
}
