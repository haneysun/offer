package com.example.offer.mv;

/**
 * Created by Haidy on 2017/8/30.
 * 分页参数对象
 * 用于接收前端传递的分页信息
 */

import java.util.List;

public class EUDataGridResult {
    //结果总数
    private long total;
    //结果行数
    private List<?> rows;

    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
    }
    public List<?> getRows() {
        return rows;
    }
    public void setRows(List<?> rows) {
        this.rows = rows;
    }

}