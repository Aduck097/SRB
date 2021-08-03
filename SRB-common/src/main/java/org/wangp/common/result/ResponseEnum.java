package org.wangp.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 2021/7/30
 *
 * @author PingW
 */
@Getter
@ToString
@AllArgsConstructor
public enum ResponseEnum {

    SUCCESS(0, "成功"),

    ERROR(-1, "服务器内部错误"),
    BORROW_AMOUNT_NULL_ERROR(10,"自定义BusinessException")
    ;

    // 响应状态码
    private Integer code;
    // 响应信息
    private String message;
}
