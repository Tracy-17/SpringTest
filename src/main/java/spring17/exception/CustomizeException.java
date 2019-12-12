package spring17.exception;

/**
 * Author:ShiQi
 * Date:2019/12/13-2:26
 * 继承后，调用时不需要try...catch
 */
public class CustomizeException extends RuntimeException{
    private String message;
    public CustomizeException(ICustomizeErrorCode errorCode){
        this.message=errorCode.getMessage();
    }
    public CustomizeException(String message){
        this.message=message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
