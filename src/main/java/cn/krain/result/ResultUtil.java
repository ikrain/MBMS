package cn.krain.result;

import cn.krain.result.Enum.ResultEnum;

/**
 * @author CC
 * @data 2020/12/15 - 16:20
 */
public class ResultUtil {

    /**
     * 操作成功，返回对应信息体
     * @param msg   信息
     * @param object    数据（JSONObject类型）
     * @return
     */
    public static ResultVo success(String msg, Object object){
        ResultVo<Object> resultVo = new ResultVo<>();
        resultVo.setCode(ResultEnum.SUCCESS.getCode());
        resultVo.setMsg(msg);
        resultVo.setData(object);
        return resultVo;
    }

    /**
     * 操作成功，只返回msg
     * @param msg
     * @return
     */
    public static ResultVo success(String msg){
        ResultVo<Object> resultVo = new ResultVo<>();
        resultVo.setCode(ResultEnum.SUCCESS.getCode());
        resultVo.setMsg(msg);
        return resultVo;
    }

    /**
     * 操作失败，只返回msg
     * @param msg
     * @return
     */
    public static ResultVo error(String msg){
        ResultVo<Object> resultVo = new ResultVo<>();
        resultVo.setCode(ResultEnum.LOGINFAILURE.getCode());
        resultVo.setMsg(msg);
        return resultVo;
    }

    /**
     * 上传文件错误
     * @param msg
     * @return
     */
    public static ResultVo fileError(String msg) {
        ResultVo<Object> resultVo = new ResultVo<>();
        resultVo.setCode(ResultEnum.FILEERROR.getCode());
        resultVo.setMsg(msg);
        return resultVo;
    }

    /**
     * 删除文件错误
     * @param msg
     * @return
     */
    public static ResultVo filePathError(String msg) {
        ResultVo<Object> resultVo = new ResultVo<>();
        resultVo.setCode(ResultEnum.FILEPATHERROR.getCode());
        resultVo.setMsg(msg);
        return resultVo;
    }
}
