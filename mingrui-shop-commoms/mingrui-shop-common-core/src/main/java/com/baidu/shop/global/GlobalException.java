package com.baidu.shop.global;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.Result;
import com.baidu.shop.status.HTTPStatus;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TestSpringBootApplication
 * @Description: TODO
 * @Author fuguanglong
 * @Date 2020/12/24
 * @Version V1.0
 **/
@RestControllerAdvice
@Slf4j
public class GlobalException {
    @ExceptionHandler(value = RuntimeException.class)
    public Result<JSONObject> testException(RuntimeException e){
        log.error("code : {} , message : {}",HTTPStatus.ERROR,e.getMessage());
        return new Result<JSONObject>(HTTPStatus.ERROR,e.getMessage(),null);
    }

//    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    public Map<String,Object> methodArgumentNotValidHandler(MethodArgumentNotValidException exception) throws  Exception{
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("code",HTTPStatus.PARAMS_VALIDATE_ERROR);
//        ArrayList<Object> msgList = new ArrayList<>();
//        exception.getBindingResult().getFieldErrors().stream().forEach(error -> {
//            msgList.add("Field --> " + error.getField() + ":" + error.getDefaultMessage());
//            log.error("Field --> " + error.getField() + ":" + error.getDefaultMessage());
//        });
//        String message = (String) msgList.parallelStream().collect(Collectors.joining(","));
//        map.put("message",message);
//        return map;
//    }
    @ExceptionHandler(value=MethodArgumentNotValidException.class)
    public List<Result<JsonObject>> MethodArgumentNotValidHandler(HttpServletRequest request, MethodArgumentNotValidException exception) throws Exception {
        List<Result<JsonObject>> objects = new ArrayList<>();
        //按需重新封装需要返回的错误信息
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            Result<JsonObject> jsonObjectResult = new Result<>();
            jsonObjectResult.setCode(HTTPStatus.PARAMS_VALIDATE_ERROR);
            jsonObjectResult.setMessage("Field --> " + error.getField() + " : " + error.getDefaultMessage());
            log.debug("Field --> " + error.getField() + " : " + error.getDefaultMessage());
            objects.add(jsonObjectResult);
        }
        return objects;
}


}
