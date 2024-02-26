package hw.topevery.exception;

/**
 * 自定定义参数异常
 *
 * @author LeoLedger
 * @date 2023/06/21
 */
public class ParamsException extends RuntimeException {

    public ParamsException(){
        super();
    }

    public ParamsException(String message){
        super(message);
    }
    public ParamsException(String message, Throwable cause) {
        super(message, cause);
    }
    public ParamsException(Throwable cause) {
        super(cause);
    }
}
