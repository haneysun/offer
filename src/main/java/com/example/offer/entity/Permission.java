package com.example.offer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.example.offer.mv.EUDataGridRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 权限配置表
 * </p>
 *
 * @author MaHC
 * @since 2019-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Permission extends EUDataGridRequest implements Serializable  {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * url,接口配置
     */
    @TableField("url")
    private String url;

    /**
     * 中文权限名
     */
    @TableField("name")
    private String name;

    /**
     * 排序
     */
    @TableField("serial_no")
    private Integer serialNo;

    /**
     * 类型
     */
    @TableField("type")
    private String type;

    /**
     * 子菜单
     */
    @TableField("pid")
    private String pid;

    /**
     * 编码
     */
    @TableField("code")
    private String code;

    /**
     * 权限图标
     */
    @TableField("icon")
    private String icon;

    /**
     * vue路由
     */
    @TableField("english_name")
    private String englishName;

    @TableField("version")
    private Integer version;


}
