package com.fangqing.utils;

import java.io.Serializable;
import java.util.List;

/**
 * @功能 CSV文件解析结果，如果解析出错，则返回空集合，以及出错行列数、出错描述
 *
 * @author zhangfangqing 
 * @date 2016年7月7日 
 * @time 上午9:49:40
 */
public class CsvParseResult<T> implements Serializable {
    private static final long serialVersionUID = -5441694080838883653L;

    /**
     * 出错行数
     */
    private Integer           row;

    /**
     * 出错列数
     */
    private Integer           column;

    /**
     * 错误描述
     */
    private String            errorDesc;

    /**
     * 解析结果集
     */
    private List<T>           resultList;

    /**
     * 如为false，则表示读取文件时就报错
     */
    private Boolean           success          = false;

    /**
     * 文件总记录数
     */
    private int               total;

    /**
     * 文件第一行内容，部分报文第一行里会添加一些信息
     */
    private String            firstRow;

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(String firstRow) {
        this.firstRow = firstRow;
    }
}