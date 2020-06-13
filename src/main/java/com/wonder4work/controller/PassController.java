package com.wonder4work.controller;

import com.wonder4work.pojo.Permission;
import com.wonder4work.pojo.User;
import com.wonder4work.pojo.bo.UserBO;
import com.wonder4work.pojo.vo.PermissionVO;
import com.wonder4work.service.PermissionService;
import com.wonder4work.service.UserService;
import com.wonder4work.util.CookieUtils;
import com.wonder4work.util.JSONResult;
import com.wonder4work.util.JsonUtils;
import com.wonder4work.util.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @since 1.0.0 2020/6/12
 */
@RestController
@RequestMapping("/pass")
@Api(tags = {"登录操作相关接口"},value = "登录操作")
public class PassController {


    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;


    @ApiOperation(value = "用户登录", notes = "用户登录",httpMethod = "POST")
    @PostMapping("/login")
    public JSONResult login(@ApiParam(name = "userBO",value = "用户",required = true)
                               @RequestBody UserBO userBO,
                               HttpServletRequest request, HttpServletResponse response){

        String username = userBO.getUsername();
        String password = userBO.getPassword();

        if (userBO == null){
            return JSONResult.errorMsg("用户名或密码错误");
        }

        if (StringUtils.isBlank(username)||StringUtils.isBlank(userBO.getPassword())) {
            return JSONResult.errorMsg("用户名或密码错误");
        }

        User check = null;
        try {
            check = checkUser(username,MD5Utils.getMD5Str(password));
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.errorMsg("用户名或密码错误");
        }

        if (null == check){
            return JSONResult.errorMsg("用户名或密码错误");
        }

        check.setPassword("");

        // TODO: 2020/6/12 将用户名放入Cookie中
        CookieUtils.setCookie(request,response,"user",JsonUtils.objectToJson(check),true);

        List<Permission> permissionList = permissionService.queryPermissionByUser(check.getUserId());


        // TODO: 2020/6/13
        List<PermissionVO> permissionVOTree = buildPermission(permissionList);

        CookieUtils.setCookie(request,response,"permissionTree",JsonUtils.objectToJson(permissionVOTree),true);
        CookieUtils.setCookie(request,response,"keepAlivePermissions",JsonUtils.objectToJson(permissionList),true);

        return JSONResult.ok();
    }


    @ApiOperation(value = "退出登录", notes = "退出登录",httpMethod = "POST")
    @PostMapping("/logout")
    public JSONResult logout(HttpServletRequest request, HttpServletResponse response){


        CookieUtils.deleteCookie(request,response,"user");
        CookieUtils.deleteCookie(request,response,"permissionTree");

        return JSONResult.ok();
    }

    private User checkUser(String username, String password) {

        User user = userService.queryByUsernameAndPassword(username, password);
        return user;

    }


    private List<PermissionVO> buildPermission(List<Permission> permissionList){


        if (permissionList == null||permissionList.size()==0){
            return null;
        }

        List<PermissionVO> ps = new ArrayList<>();

        permissionList.forEach(permission -> {
            PermissionVO permissionVO = new PermissionVO();
            BeanUtils.copyProperties(permission,permissionVO);
            ps.add(permissionVO);
        });

        List<PermissionVO> permissions = new ArrayList<>();

        Map<Integer, PermissionVO> permissionMap = new HashMap<Integer, PermissionVO>();
        for (PermissionVO p : ps) {
            permissionMap.put(p.getPermissionId(), p);
        }

        for (PermissionVO p : ps) {
            PermissionVO child = p;
            if (child.getParentId() == 0) {
                permissions.add(p);
            } else {
                PermissionVO parent = permissionMap.get(child.getParentId());
                parent.getChildren().add(child);
            }

        }

        return permissions;

    }


}
