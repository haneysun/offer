package com.example.offer.mv;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/31.
 */
public class UserLogin implements Serializable {
    private Long id;

    private String loginname;

    private String loginpass;

    private String name;

    private String tel;

    private String qq;

    private String ip;

    private Date adddate;

    private Integer qx;

    private Integer logincount;

    private Date logindate;

    private Integer lock;

    @Column(name = "CITYID1")
    private Integer cityid1;

    @Column(name = "CITYID")
    private Integer cityid;

    private String val;

    private String mid;

    @Column(name = "sh_id")
    private String shId;

    private Integer bumen;

    @Column(name = "sf_id")
    private String sfId;

    private String text;
    private String getcode;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return loginname
     */
    public String getLoginname() {
        return loginname;
    }

    /**
     * @param loginname
     */
    public void setLoginname(String loginname) {
        this.loginname = loginname == null ? null : loginname.trim();
    }

    /**
     * @return loginpass
     */
    public String getLoginpass() {
        return loginpass;
    }

    /**
     * @param loginpass
     */
    public void setLoginpass(String loginpass) {
        this.loginpass = loginpass == null ? null : loginpass.trim();
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel
     */
    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    /**
     * @return qq
     */
    public String getQq() {
        return qq;
    }

    /**
     * @param qq
     */
    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    /**
     * @return ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * @return adddate
     */
    public Date getAdddate() {
        return adddate;
    }

    /**
     * @param adddate
     */
    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }

    /**
     * @return qx
     */
    public Integer getQx() {
        return qx;
    }

    /**
     * @param qx
     */
    public void setQx(Integer qx) {
        this.qx = qx;
    }

    /**
     * @return logincount
     */
    public Integer getLogincount() {
        return logincount;
    }

    /**
     * @param logincount
     */
    public void setLogincount(Integer logincount) {
        this.logincount = logincount;
    }

    /**
     * @return logindate
     */
    public Date getLogindate() {
        return logindate;
    }

    /**
     * @param logindate
     */
    public void setLogindate(Date logindate) {
        this.logindate = logindate;
    }

    /**
     * @return lock
     */
    public Integer getLock() {
        return lock;
    }

    /**
     * @param lock
     */
    public void setLock(Integer lock) {
        this.lock = lock;
    }

    /**
     * @return CITYID1
     */
    public Integer getCityid1() {
        return cityid1;
    }

    /**
     * @param cityid1
     */
    public void setCityid1(Integer cityid1) {
        this.cityid1 = cityid1;
    }

    /**
     * @return CITYID
     */
    public Integer getCityid() {
        return cityid;
    }

    /**
     * @param cityid
     */
    public void setCityid(Integer cityid) {
        this.cityid = cityid;
    }

    /**
     * @return val
     */
    public String getVal() {
        return val;
    }

    /**
     * @param val
     */
    public void setVal(String val) {
        this.val = val == null ? null : val.trim();
    }

    /**
     * @return mid
     */
    public String getMid() {
        return mid;
    }

    /**
     * @param mid
     */
    public void setMid(String mid) {
        this.mid = mid == null ? null : mid.trim();
    }

    /**
     * @return sh_id
     */
    public String getShId() {
        return shId;
    }

    /**
     * @param shId
     */
    public void setShId(String shId) {
        this.shId = shId == null ? null : shId.trim();
    }

    /**
     * @return bumen
     */
    public Integer getBumen() {
        return bumen;
    }

    /**
     * @param bumen
     */
    public void setBumen(Integer bumen) {
        this.bumen = bumen;
    }

    /**
     * @return sf_id
     */
    public String getSfId() {
        return sfId;
    }

    /**
     * @param sfId
     */
    public void setSfId(String sfId) {
        this.sfId = sfId == null ? null : sfId.trim();
    }

    /**
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text
     */
    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public String getGetcode() {
        return getcode;
    }

    public void setGetcode(String getcode) {
        this.getcode = getcode;
    }
}
