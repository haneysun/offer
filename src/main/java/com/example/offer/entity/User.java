package com.example.offer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 后台用户表
 * </p>
 *
 * @author 孔邹祥
 * @since 2019-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 登录名
     */
    @TableField("login_name")
    private String loginName;

    /**
     * 登录密码（加密）
     */
    @TableField("login_pass")
    private String loginPass;

    @TableField(exist = false)
    private String sfId;
    @TableField(exist = false)
    private String token;


    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 电话
     */
    @TableField("tel")
    private String tel;

    /**
     * ip
     */
    @TableField("ip")
    private String ip;

    /**
     * 创建时间
     */
    @TableField("add_date")
    private LocalDateTime addDate;

    /**
     * 登录次数
     */
    @TableField("login_count")
    private Integer loginCount;

    /**
     * 最近一次登录时间
     */
    @TableField("login_date")
    private LocalDateTime loginDate;

    /**
     * 状态：0为冻结；1为正常
     */
    @TableField("lockup")
    private Integer lockup;

    /**
     * 数据筛选
     */
    @TableField("val")
    private String val;

    /**
     * 外键，自关联的外键
     */
    @TableField("mid")
    private Integer mid;

    /**
     * 角色（role）id
     */
    @TableField("bumen")
    private Integer bumen;

    @TableField("text")
    private String text;

    /**
     * 用户类型: 1,管理员 2，代理 3，组织者
     */
    @TableField("type")
    private Integer type;

    @TableField("version")
    private Integer version;


    @TableField(exist = false)
    private String availableFund;
    @TableField(exist = false)
    private String marginFund;
    @TableField(exist = false)
    private String baseCurrency;
    @TableField(exist = false)
    private String execRate;
    @TableField(exist = false)
    private Integer dataStatus;
    @TableField(exist = false)
    private Date createTime;
    @TableField(exist = false)
    private Date updateTime;
    @TableField(exist = false)
    private String confirmDate;
    @TableField(exist = false)
    private String sms;
    @TableField(exist = false)
    private String userId;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", loginName='" + loginName + '\'' +
                ", loginPass='" + loginPass + '\'' +
                ", sfId='" + sfId + '\'' +
                ", token='" + token + '\'' +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", ip='" + ip + '\'' +
                ", addDate=" + addDate +
                ", loginCount=" + loginCount +
                ", loginDate=" + loginDate +
                ", lockup=" + lockup +
                ", val='" + val + '\'' +
                ", mid=" + mid +
                ", bumen=" + bumen +
                ", text='" + text + '\'' +
                ", type=" + type +
                ", version=" + version +
                ", availableFund='" + availableFund + '\'' +
                ", marginFund='" + marginFund + '\'' +
                ", baseCurrency='" + baseCurrency + '\'' +
                ", execRate='" + execRate + '\'' +
                ", dataStatus=" + dataStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", confirmDate='" + confirmDate + '\'' +
                ", sms='" + sms + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
