package com.example.offer.mv;


/**
 * Created by  on 2017/9/5.
 */
public class UserSearch extends EUDataGridRequest {
    private Integer userId;
    private String  name;

    //用做保证金记录和充值记录和佣金记录
    private Integer transType;


  //提现申请的id
    private Integer transId;

    //根据代理id查询用户列表
    private Integer agentId;
    private String bumenname;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBumenname() {
        return bumenname;
    }

    public void setBumenname(String bumenname) {
        this.bumenname = bumenname;
    }

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public Integer getTransId() {
        return transId;
    }

    public void setTransId(Integer transId) {
        this.transId = transId;
    }


    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public UserSearch(String name, String bumenname) {
        this.name = name;
        this.bumenname = bumenname;
    }

    public UserSearch() {
    }
}
