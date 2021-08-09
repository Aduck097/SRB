package org.wangp.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.wangp.common.result.ResponseEnum;

@Data
@NoArgsConstructor
public class BusinessException extends RuntimeException {

    //状态码
    private Integer code;

    //错误消息
    private String message;

    /**
     *
     * @param message 错误消息
     * @param code 错误码
     */
    public BusinessException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public BusinessException(ResponseEnum responseEnum) {
        this.message = responseEnum.getMessage();
        this.code = responseEnum.getCode();
    }


}