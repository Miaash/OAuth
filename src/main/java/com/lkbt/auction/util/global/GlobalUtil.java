package com.lkbt.auction.util.global;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;

import com.lkbt.auction.exception.base.ArgsException;
import com.lkbt.auction.handler.enums.ResponseCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GlobalUtil {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> void checkValidate(final T t) {
        final Set<ConstraintViolation<T>> violations = validator.validate(t);
        if (!violations.isEmpty())
            throw new ArgsException(ResponseCode.EMPTY_PARAMETER,
                    new ConstraintViolationException(violations).getMessage());
    }

    /**
     * PI230410
     * 2023.07.28
     * 데이터가 존재하는 경우 true
     * 데이터가 존재하지 않는 경우 false
     * 
     * @param data
     * @return
     */
    public static boolean isNotNull(Object data) {
        if (data == null) {
            return false;
        } else if (data instanceof String) {
            return StringUtils.isNotBlank((String) data);
        } else if (data instanceof Collection<?>) {
            return ((Collection<?>) data).size() > 0;
        } else {
            return Optional.ofNullable(data).isPresent();
        }
    }
}
