package spring17.exception;

/**
 * Author:ShiQi
 * Date:2019/12/13-2:39
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUND("寻找的问题不在啦QAQ");

    @Override
    public String getMessage(){
        return message;
    }
    private String message;
    CustomizeErrorCode(String message){
        this.message=message;
    }
}
