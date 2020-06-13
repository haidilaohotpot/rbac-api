package com.wonder4work.controller;


import com.wonder4work.pojo.User;
import com.wonder4work.pojo.bo.UserBO;
import com.wonder4work.service.UserService;
import com.wonder4work.service.impl.BaseService;
import com.wonder4work.util.JSONResult;
import com.wonder4work.util.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author wonder4work
 * @since 2020-05-18
 */
@RestController
@RequestMapping("/user")
@Api(tags = {"用户操作相关接口"},value = "用户操作")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取所有的用户信息", notes = "获取所有的用户信息",httpMethod = "GET")
    @GetMapping("/query")
    public JSONResult query(@ApiParam(name = "queryText",value = "模糊查询关键字",required = false)
                                @RequestParam String queryText,
                                @ApiParam(name = "page",value = "查询的页数",required = false)
                                @RequestParam Integer page,
                                @ApiParam(name = "pageSize",value = "每页显示多少条",required = false)
                                @RequestParam Integer pageSize) {

        if (page==null){
            page = 1;
        }
        if (pageSize == null){
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult pagedGridResult = userService.getUserList(queryText,page,pageSize);

        return JSONResult.ok(pagedGridResult);
    }


    @ApiOperation(value = "根据ID获取用户信息", notes = "根据ID获取用户信息",httpMethod = "GET")
    @GetMapping("/{userId}")
    public JSONResult queryUserByUserId(@ApiParam(name = "userId",value = "用户ID",required = true)
                                            @PathVariable("userId") String userId){

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户ID不能为空");
        }

        User user = userService.queryUserByUserId(userId);
        return JSONResult.ok(user);
    }

    @ApiOperation(value = "禁用用户", notes = "禁用用户",httpMethod = "POST")
    @PostMapping("/disabled")
    public JSONResult disabled(@ApiParam(name = "userIds",value = "要禁用的用户ID数组",required = true)
                               @RequestBody String[] userIds){



        if (null == userIds || userIds.length == 0) {
            return JSONResult.errorMsg("用户ID不能为空");
        }
        // 禁用用户
        userService.disabled(userIds);
        return JSONResult.ok();
    }

    @ApiOperation(value = "启用用户", notes = "启用用户",httpMethod = "POST")
    @PostMapping("/open")
    public JSONResult open(@ApiParam(name = "userIds",value = "要启用的用户ID数组",required = true)
                               @RequestBody String[] userIds){

        if (null == userIds || userIds.length == 0) {
            return JSONResult.errorMsg("用户ID不能为空");
        }
        // 禁用用户
        userService.open(userIds);
        return JSONResult.ok();
    }


    @ApiOperation(value = "新增用户", notes = "新增用户",httpMethod = "POST")
    @PostMapping("/insert")
    public JSONResult insert(@ApiParam(name = "userBO",value = "用户信息",required = true)
                               @RequestBody @Valid UserBO userBO,
                               BindingResult bindingResult){

        if (null == userBO){
            return JSONResult.errorMsg("用户信息不能为空");
        }

        // 判断验证中是否有错误
        if (bindingResult.hasErrors()) {
            Map<String, Object> objectMap = BaseController.getErrors(bindingResult);
            return JSONResult.errorMap(objectMap);
        }

        boolean isExist = userService.queryUsernameIsExist(userBO.getUsername());
        if (isExist){
            return JSONResult.errorMsg("此用户名已经存在");
        }

        //新增用户
        User user = userService.insert(userBO);

        return JSONResult.ok(user);
    }

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息",httpMethod = "POST")
    @PostMapping("/update")
    public JSONResult update(@ApiParam(name = "userId",value = "要修改的用户ID",required = true)
                                 @RequestParam String userId,
                               @ApiParam(name = "userBO",value = "用户信息",required = true)
                               @RequestBody @Valid UserBO userBO,
                               BindingResult bindingResult){

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户ID不能为空");
        }

        if (null == userBO){
            return JSONResult.errorMsg("用户信息不能为空");
        }

        // 判断验证中是否有错误
        if (bindingResult.hasErrors()) {
            Map<String, Object> objectMap = BaseController.getErrors(bindingResult);
            return JSONResult.errorMap(objectMap);
        }

        boolean isExist = userService.queryUsernameIsExist(userBO.getUsername());

        if (isExist){
            return JSONResult.errorMsg("此用户名已经存在");
        }

        //更新
        User user = userService.update(userId,userBO);

        return JSONResult.ok(user);
    }

    @ApiOperation(value = "为用户分配角色", notes = "为用户分配角色",httpMethod = "POST")
    @PostMapping("/doAssign")
    public JSONResult doAssign(@ApiParam(name = "userId",value = "用户ID",required = true)
                             @RequestParam String userId,
                             @ApiParam(name = "roleIds",value = "要分配的角色ID数组",required = true)
                             @RequestBody String[] roleIds){

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户ID不能为空");
        }

        if (!checkUser(userId)){
            return JSONResult.errorMsg("用户不存在");
        }

        if (null == roleIds || roleIds.length ==0) {
            return JSONResult.errorMsg("请选择要分配的角色");
        }

        // 分配角色
        userService.doAssign(userId, roleIds);

        return JSONResult.ok();
    }


    @ApiOperation(value = "取消已经为用户分配的角色", notes = "取消已经为用户分配的角色",httpMethod = "POST")
    @PostMapping("/cancelAssign")
    public JSONResult cancelAssign(@ApiParam(name = "userId",value = "用户ID",required = true)
                             @RequestParam String userId,
                             @ApiParam(name = "roleIds",value = "要取消的角色ID数组",required = true)
                             @RequestBody String[] roleIds){

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户ID不能为空");
        }

        if (!checkUser(userId)){
            return JSONResult.errorMsg("用户不存在");
        }

        if (null == roleIds || roleIds.length ==0 ) {
            return JSONResult.errorMsg("请选择要取消的角色");
        }

        // 取消分配角色
        userService.cancelAssign(userId, roleIds);

        return JSONResult.ok();
    }


    @ApiOperation(value = "获取用户已经拥有和未拥有的角色", notes = "获取用户已经拥有和未拥有的角色",httpMethod = "GET")
    @GetMapping("/assign/{userId}")
    public JSONResult assign(@ApiParam(name = "userId",value = "用户ID",required = true)
                                        @PathVariable("userId") String userId){

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户ID不能为空");
        }

        if (!checkUser(userId)){
            return JSONResult.errorMsg("用户不存在");
        }

        Map<String,Object> result = userService.assign(userId);

        return JSONResult.ok(result);
    }



    private boolean checkUser(String userId){
        User user = userService.queryUserByUserId(userId);
        return null != user;
    }

}

