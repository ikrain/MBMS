package cn.krain.result;

import lombok.Data;

/**
 * @author CC
 * @data 2020/12/15 - 16:05
 */
@Data
public class ResultVo<T> {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;
}
