package com.example.offer.core;

import com.baomidou.mybatisplus.annotation.TableField;

/**
 * Created by Haidy on 2017/8/30.
 * 分页请求对象
 */

public class EUDataGridRequest {
    //当前页码
    @TableField(exist = false)
    private Integer page=1;

    //每页记录数
    @TableField(exist = false)
    private Integer rows=10;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
}