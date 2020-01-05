package com.example.offer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色and权限
 * </p>
 *
 * @author 孔邹祥
 * @since 2019-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * permission_id
     */
    @TableField("pid")
    private Integer pid;

    /**
     * role_id
     */
    @TableField("rid")
    private Integer rid;

    @TableField("version")
    private Integer version;


    public RolePermission() {
    }
    public RolePermission(Long rid, Long pid) {
        this.rid = Math.toIntExact(rid);
        this.pid = Math.toIntExact(pid);
    }

}
