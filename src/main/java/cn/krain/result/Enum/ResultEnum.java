package cn.krain.result.Enum;

import lombok.Getter;

/**
 * @author CC
 * @data 2020/12/15 - 16:22
 * 枚举类型，设置响应信息类型
 */
@Getter
public enum ResultEnum {

    /**
     * 状态码
     */
    SUCCESS(200,"成功"),

    ERROR(500,"错误"),

    LOGINFAILURE(501,"登录失败"),

    ERRORTOKEN(502,"当前Token无效"),

    FILEERROR(503,"文件为空"),

    FILEPATHERROR(504, "文件路径错误");

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
