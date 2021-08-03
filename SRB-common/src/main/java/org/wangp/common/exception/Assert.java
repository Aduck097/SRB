package org.wangp.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.wangp.common.result.ResponseEnum;

@Slf4j
public abstract class Assert {

    /**
     * 断言对象不为空
     * 如果对象obj为空，则抛出异常
     * @param obj 待判断对象
     */
    public static void notNull(Object obj, ResponseEnum responseEnum) {
        if (obj == null) {
            log.info("obj is null...............");
            throw new BusinessException(responseEnum.getCode(),responseEnum.getMessage());
        }
    }

    public static void isNull(Boolean obj,ResponseEnum responseEnum ) {
        if (obj == null) {
            log.info("obj is null...............");
            throw new BusinessException(responseEnum.getCode(),responseEnum.getMessage());
        }
    }
}