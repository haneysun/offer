package com.example.offer.mv;

import com.example.offer.entity.Role;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;


/**
 * Created by  on 2017/9/5.
 */
public class RoleAndCheck extends Role implements Serializable {
    /**
     * 是否勾选
     */
    private String marker;
    /**
     * user Id
     */
    private String userId;

    public boolean isCheck(){
        return StringUtils.equals(userId,marker);
    }
    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
