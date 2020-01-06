package com.example.offer.mv;

import com.example.offer.entity.Permission;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;


/**
 * Created by  on 2017/9/5.
 */
public class PermissionAndCheck extends Permission implements Serializable {
    /**
     * 是否勾选
     */
    private String marker;
    /**
     * role Id
     */
    private String roleId;


    public boolean isChecked(){
        return StringUtils.equals(roleId,marker);
    }
    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }
    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }


}
