package org.wangp.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.wangp.common.result.ResponseEnum;

import java.math.BigDecimal;

@Slf4j
public abstract  class Assert {


    /**
     * 断言对象不为空
     * 如果对象obj为空，则抛出异常
     * @param obj 待判断对象
     */
    public static void notNull(Object obj, ResponseEnum responseEnum) {
        if (obj == null) {
            log.info("obj is null...............");
            throw new BusinessException(responseEnum);
        }
    }


    /**
     * 断言对象为空
     * 如果对象obj不为空，则抛出异常
     * @param object
     * @param responseEnum
     */
    public static void isNull(Object object, ResponseEnum responseEnum) {
        if (object != null) {
            log.info("obj is not null......");
            throw new BusinessException(responseEnum);
        }
    }

    /**
     * 断言表达式为真
     * 如果不为真，则抛出异常
     *
     * @param expression 是否成功
     */
    public static void isTrue(boolean expression, ResponseEnum responseEnum) {
        if (!expression) {
            log.info("fail...............");
            throw new BusinessException(responseEnum);
        }
    }

    /**
     * 断言两个对象不相等
     * 如果相等，则抛出异常
     * @param m1
     * @param m2
     * @param responseEnum
     */
    public static void notEquals(Object m1, Object m2,  ResponseEnum responseEnum) {
        if (m1.equals(m2)) {
            log.info("equals...............");
            throw new BusinessException(responseEnum);
        }
    }

    /**
     * 断言两个对象相等
     * 如果不相等，则抛出异常
     * @param m1
     * @param m2
     * @param responseEnum
     */
    public static void equals(Object m1, Object m2,  ResponseEnum responseEnum) {
        if (!m1.equals(m2)) {
            log.info("not equals...............");
            throw new BusinessException(responseEnum);
        }
    }

    /**
     * 断言两个对象相等
     * 如果不相等，则抛出异常
     * @param m1
     * @param m2
     * @param responseEnum
     */
    public static void equalsWp(Object m1, Object m2,  ResponseEnum responseEnum) {
        if (m1==m2) {
            log.info("not equals...............");
            throw new BusinessException(responseEnum);
        }
    }

    /**
     * 断言参数不为空
     * 如果为空，则抛出异常
     * @param s
     * @param responseEnum
     */
    public static void notEmpty(String s, ResponseEnum responseEnum) {
        if (StringUtils.isEmpty(s)) {
            log.info("is empty...............");
            throw new BusinessException(responseEnum);
        }
    }


    public static void isPositive (BigDecimal bigDecimal, ResponseEnum responseEnum) {
        if(bigDecimal.compareTo(new BigDecimal("0"))<=0){
            log.info("数字不能小于等于0...............");
            throw new BusinessException(responseEnum);
        }
    }

    /**
     * 断言验证码是否为一致
     * @param send
     * @param codeError
     */
    public static void eqCode(String send,ResponseEnum codeError) {
        if (!send.equals("ok")){
            log.info("两次验证码不一致");
            throw new BusinessException(codeError);
        }
    }

    /**
     * 判断验证码是否相同
     * @param o
     * @param code
     * @param codeError
     */
    public static void hasCode(Object o, String code, ResponseEnum codeError) {
        if(!code.equals((String)o)){
            log.error("两次验证码不一致");
            throw new BusinessException(codeError);
        }
    }
}