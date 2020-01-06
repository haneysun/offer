package com.example.offer.mv;

import java.util.List;

/**
 * Created by  6.
 */
public class PermissionTree extends PermissionAndCheck {

    private List<PermissionTree> children;

    public List<PermissionTree> getChildren() {
        return children;
    }

    public void setChildren(List<PermissionTree> children) {
        this.children = children;
    }

}
