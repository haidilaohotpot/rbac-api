package com.wonder4work.controller;


import com.wonder4work.service.UserService;
import com.wonder4work.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @since 1.0.0 2020/4/11
 */
@Controller
public class BaseController {


    public static final Integer COMMON_PAGE_SIZE = 10;


    // 用户上传头像的位置
    public static final String IMAGE_USER_FACE_LOCATION = File.separator +"workspaces"+File.separator+"images"+File.separator+"rbac"+File.separator+"faces";


    public static Map<String, Object> getErrors(BindingResult result) {

        List<FieldError> errorList = result.getFieldErrors();
        Map<String, Object> map = new HashMap<>();
        for (FieldError fieldError : errorList) {
            // 某一个属性
            String field = fieldError.getField();
            // 验证错误的信息
            String defaultMessage = fieldError.getDefaultMessage();

            map.put(field, defaultMessage);
        }
        return map;
    }



}
