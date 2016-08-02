package com.fangqing.utils;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 * @功能     类ValidateUtil.java的实现描述：基于JSR-303规范验证参数
 *
 * @author zhangfangqing
 * @date 2016年7月27日
 * @time 下午2:25:21
 */
public class ValidateTool {

	/**
	 * @功能 TODO
	 *
	 * @author zhangfangqing
	 * @date 2016年7月27日
	 * @time 下午3:29:55
	 */
    public static <T> void validate(final T t) throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        //验证Bean参数，并返回验证结果信息
        Set<ConstraintViolation<T>> validators = validator.validate(t);
        for (ConstraintViolation<T> constraintViolation : validators) {
            throw new Exception(constraintViolation.getMessage());
        }
    }

}
