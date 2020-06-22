package cn.smbms.pojo;

import java.util.Date;

/**
 * 供应商实体类
 */
public class Provider {
     private  Integer id;   //id
     private  String proCode;   //供应商编码
     private  String proName;   //供应商名称
     private  String proDesc;   //供应商详情描述
     private  String proContact;  //供应商联系人
     private  String proPhone;    //供应商电话
     private  String proAddress;  //地址
     private  String proFax;      //传真
     private  Integer createdBy;   //创建者
     private  Date creationDate;   //创建时间
     private  Integer modifyBy;     //更新者
     private  Date modifyDate;     //更新时间

    public String getCompanyLicPicPath() {
        return companyLicPicPath;
    }

    public void setCompanyLicPicPath(String companyLicPicPath) {
        this.companyLicPicPath = companyLicPicPath;
    }

    public String getOrgCodePicPath() {
        return orgCodePicPath;
    }

    public void setOrgCodePicPath(String orgCodePicPath) {
        this.orgCodePicPath = orgCodePicPath;
    }

    private  String companyLicPicPath;  //企业营业执照的编号的存储路径
     private  String orgCodePicPath;  //组织机构代码证的存储路径

    public void setId(Integer id) {
        this.id = id;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public void setProDesc(String proDesc) {
        this.proDesc = proDesc;
    }

    public void setProContact(String proContact) {
        this.proContact = proContact;
    }

    public void setProPhone(String proPhone) {
        this.proPhone = proPhone;
    }

    public void setProAddress(String proAddress) {
        this.proAddress = proAddress;
    }

    public void setProFax(String proFax) {
        this.proFax = proFax;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setModifyBy(Integer modifyBy) {
        this.modifyBy = modifyBy;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getId() {
        return id;
    }

    public String getProCode() {
        return proCode;
    }

    public String getProName() {
        return proName;
    }

    public String getProDesc() {
        return proDesc;
    }

    public String getProContact() {
        return proContact;
    }

    public String getProPhone() {
        return proPhone;
    }

    public String getProAddress() {
        return proAddress;
    }

    public String getProFax() {
        return proFax;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Integer getModifyBy() {
        return modifyBy;
    }

    public Date getModifyDate() {
        return modifyDate;
    }
}
