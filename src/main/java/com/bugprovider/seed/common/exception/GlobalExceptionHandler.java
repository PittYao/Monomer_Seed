package com.bugprovider.seed.common.exception;

import com.bugprovider.seed.common.api.CommonResult;
import com.bugprovider.seed.common.api.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 全局异常处理
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常
     */
    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public CommonResult handle(ApiException e) {
        if (e.getErrorCode() != null) {
            return CommonResult.failed(e.getErrorCode());
        }
        return CommonResult.failed(e.getMessage());
    }


    /**
     * 参数验证异常
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResult handleValidException(MethodArgumentNotValidException e) {
        log.error("参数校验异常->请求参数: {}  错误: {}  异常:", Objects.requireNonNull(e.getBindingResult().getTarget()), Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage(), e);
        return getErrorList(e.getBindingResult());
    }

    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public CommonResult handleValidException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return CommonResult.validateFailed(message);
    }

    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Exception.class)
    CommonResult handleException(Exception e) {
        log.error("系统运行时异常-> {}", e.getMessage(), e);
        return CommonResult.failed();
    }

    /**
     * 获取参数校验具体异常信息
     *
     * @param bindingResult
     */
    private CommonResult getErrorList(BindingResult bindingResult) {
        List<String> errorList = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                if (fieldError != null) {
                    String message = fieldError.getField() + fieldError.getDefaultMessage();
                    errorList.add(message);
                }
            }
        }

        return CommonResult.failed(ResultCode.VALIDATE_FAILED, errorList);
    }
}
