package com.shf.gulimall.product.exception;

import com.shf.common.exception.BizCodeEnume;
import com.shf.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * 集中处理所有异常
 */
@Slf4j
@RestControllerAdvice("com.shf.gulimall.product.controller")
public class GulimallExceptionControllerAdvice {
    @ExceptionHandler(value = Exception.class)
    public R handleVaildException(MethodArgumentNotValidException e){
        log.error("数据校验出现问题{},异常类型：{}",e.getMessage(),e.getClass());
        BindingResult bindingResult = e.getBindingResult();

        HashMap<String, String> erroeMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach((fieldError -> {
            erroeMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }));
        return R.error(BizCodeEnume.VAILD_EXCEPTION.getCode(),BizCodeEnume.VAILD_EXCEPTION.getMsg()).put("data", erroeMap);
    }

    @ExceptionHandler(value = Throwable.class)
    public R handleException(){
        return R.error(BizCodeEnume.UNKNOW_EXCEPTION.getCode(),BizCodeEnume.UNKNOW_EXCEPTION.getMsg());
    }
}
