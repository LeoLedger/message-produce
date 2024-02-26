package hw.topevery.handler;

import hw.topevery.exception.ParamsException;
import hw.topevery.framework.exception.TokenException;
import hw.topevery.framework.util.LogUtil;
import hw.topevery.framework.web.HttpResultCode;
import hw.topevery.framework.web.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理类
 *
 * @author whw
 * @date 2020/4/1 9:27
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final static String ERROR_MESSAGE = "系统异常，请联系开发人员";

    /**
     * 异常处理程序
     *
     * @param e e
     * @return {@link JsonResult}<{@link String}>
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonResult<String> exceptionHandler(Exception e) {
        LogUtil.error(e);
        return JsonResult.error(ERROR_MESSAGE);
    }

    /**
     * 异常处理程序
     *
     * @param e e
     * @return {@link JsonResult}<{@link String}>
     */
    @ExceptionHandler(ParamsException.class)
    @ResponseBody
    public JsonResult<String> exceptionHandler(ParamsException e) {
        LogUtil.error(e);
        HttpResultCode err = HttpResultCode.PARAMETER_EXCEPTION;
        return JsonResult.error(err.getMsg(), err.getCode());
    }

    /**
     * 异常处理程序
     *
     * @param e e
     * @return {@link JsonResult}<{@link String}>
     */
    @ExceptionHandler(TokenException.class)
    @ResponseBody
    public JsonResult<String> exceptionHandler(TokenException e) {
        return JsonResult.error(e.getMessage(), e.getCode());
    }

    /**
     * 方法参数不是有效异常处理程序
     *
     * @param e e
     * @return {@link JsonResult}<{@link String}>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public JsonResult<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return JsonResult.error(ERROR_MESSAGE, HttpResultCode.PARAMETER_EXCEPTION.getCode());
    }

    /**
     * 绑定异常处理程序
     *
     * @param e e
     * @return {@link JsonResult}<{@link String}>
     */
    @ExceptionHandler(BindException.class)
    public JsonResult<String> bindExceptionHandler(BindException e) {
        //获取异常中随机一个异常信息
        return JsonResult.error(ERROR_MESSAGE);
    }

    /**
     * 约束违反异常处理程序
     *
     * @param e e
     * @return {@link JsonResult}<{@link String}>
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public JsonResult<String> constraintViolationExceptionHandler(ConstraintViolationException e) {
        //获取异常中第一个错误信息
        return JsonResult.error(ERROR_MESSAGE);
    }
}
