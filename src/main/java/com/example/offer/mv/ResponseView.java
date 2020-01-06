package com.example.offer.mv;

import com.alibaba.fastjson.JSON;
import com.example.offer.core.BusinessErrorEnum;
import com.example.offer.core.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * Created by Haidy on 2017/8/31.
 */
@Slf4j
public class ResponseView implements Serializable {
    //操作是否成功，默认为true
    private boolean success = true;
    //提示给用户看的信息，不管是成功还是失败的
    private String message;
    //错误码
    private String errCode;
    //返回给前端的数据对象
    private Object data;
    //总条数
    private long total;
    //返回给前端的数据格式
    private Object rows;

    public ResponseView(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ResponseView(boolean success, String message, String errCode, Object data) {
        this.success = success;
        this.errCode = errCode;
        this.data = data;
        this.message = message;
    }

    public ResponseView(long total, Object rows) {
        this.total = total;
        this.rows = rows;
    }

    public ResponseView() {
    }

    public static ResponseView success(long total, Object rows) {
        return new ResponseView(total,rows);
    }
    public static ResponseView success(Long pages,Long size,Long current,long total,Object rows) {
        return new ResponseView(total,rows);
    }
    /*返回一条信息，自定义成功或失败状态*/
    public static ResponseView message(boolean success, String message) {
        return new ResponseView(success, message);
    }

    /*失败，带有错误提示*/
    public static ResponseView faultMessage(String message) {
        return new ResponseView(false, message = message);
    }

    /*失败，使用默认提示*/
    public static ResponseView faultMessage() {
        return new ResponseView(false, "操作失败");
    }

    /*成功：有需要返回Date信息*/
    public static ResponseView success(Object data, String message) {
        return new ResponseView(true, message, null, data);
    }

    //专门为easyui展现数据用
    public static EUDataGridResult successForPage(PageInfo<?> pageInfo) {
        EUDataGridResult euDataGrid = new EUDataGridResult();
        euDataGrid.setRows(pageInfo.getList());
        euDataGrid.setTotal(pageInfo.getTotal());
        return euDataGrid;
    }


    //专门为easyui展现数据用
    public static EUDataGridResult successForPage() {
        EUDataGridResult euDataGrid = new EUDataGridResult();
        euDataGrid.setRows(null);
        euDataGrid.setTotal(0);
        return euDataGrid;
    }

    /*成功：有需要返回Date信息*/
    public static ResponseView success(Object data) {
        return new ResponseView(true, "操作成功", null, data);
    }

    /*成功：无需返回data*/
    public static ResponseView success() {
        return new ResponseView(true, "操作成功", null, null);
    }

    public static ResponseView success(ResultCodeEnum resultCodeEnum){
        log.info("返回码:{} 返回信息:{}", resultCodeEnum.getCode(), resultCodeEnum.getMessage());
        return new ResponseView(resultCodeEnum.getCode(),resultCodeEnum.getMessage());
    }


    /*失败：返回错误信息*/
    public static ResponseView errorsInfo(String errCode, String message) {
        return new ResponseView(false, message, errCode, null);
    }

    public String asJSON() {
         return JSON.toJSONString(this);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }
}
