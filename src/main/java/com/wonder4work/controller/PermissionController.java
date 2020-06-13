package com.wonder4work.controller;


import com.wonder4work.pojo.Permission;
import com.wonder4work.pojo.bo.PermissionBO;
import com.wonder4work.pojo.vo.PermissionVO;
import com.wonder4work.service.PermissionService;
import com.wonder4work.util.JSONResult;
import com.wonder4work.util.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author wonder4work
 * @since 2020-05-18
 */
@RestController
@RequestMapping("/permission")
@Api(tags = {"权限操作相关接口"},value = "权限操作")
public class PermissionController extends BaseController {

    @Autowired
    private PermissionService permissionService;


    @ApiOperation(value = "新增权限,0表示根", notes = "新增权限，0表示根",httpMethod = "POST")
    @PostMapping("/insert")
    public JSONResult insert(@ApiParam(name = "permissionBO",value = "权限信息",required = true)
                             @RequestBody @Valid PermissionBO permissionBO,
                             BindingResult bindingResult){




        if (null == permissionBO
                || StringUtils.isBlank(permissionBO.getName())
                || null == permissionBO.getParentId()){
            return JSONResult.errorMsg("权限信息不完整");
        }


        // 判断验证中是否有错误
        if (bindingResult.hasErrors()) {
            Map<String, Object> objectMap = BaseController.getErrors(bindingResult);
            return JSONResult.errorMap(objectMap);
        }

        if (0 != permissionBO.getParentId()) {
            boolean isExist = checkPermissionIsExist(permissionBO.getParentId());
            if (!isExist) {
                return JSONResult.errorMsg("上一级目录不存在");
            }
        }


        //新增
        permissionService.insert(permissionBO);

        return JSONResult.ok();
    }


    @ApiOperation(value = "修改权限信息", notes = "修改权限信息",httpMethod = "POST")
    @PostMapping("/update")
    public JSONResult update(@ApiParam(name = "permissionId",value = "要修改的权限ID",required = true)
                             @RequestParam String permissionId,
                             @ApiParam(name = "permissionBO",value = "权限信息",required = true)
                             @RequestBody @Valid PermissionBO permissionBO,
                             BindingResult bindingResult){

        if (StringUtils.isBlank(permissionId)) {
            return JSONResult.errorMsg("ID不能为空");
        }

        if (null == permissionBO
                || StringUtils.isBlank(permissionBO.getName())
                || null == permissionBO.getParentId()){
            return JSONResult.errorMsg("权限信息不完整");
        }


        // 判断验证中是否有错误
        if (bindingResult.hasErrors()) {
            Map<String, Object> objectMap = BaseController.getErrors(bindingResult);
            return JSONResult.errorMap(objectMap);
        }

        if (0 != permissionBO.getParentId()) {
            boolean isExist = checkPermissionIsExist(permissionBO.getParentId());
            if (!isExist) {
                return JSONResult.errorMsg("上一级目录不存在");
            }
        }


        permissionService.update(permissionId,permissionBO);

        return JSONResult.ok();
    }

    @ApiOperation(value = "获取权限信息(按层级组装好的)", notes = "获取权限信息(按层级组装好的)",httpMethod = "GET")
    @GetMapping("/list")
    public JSONResult list(){
        List<PermissionVO> permissions = new ArrayList<>();

        // 不用递归 提高效率
        List<Permission> permissionList = permissionService.list();

        if (permissionList == null || permissionList.size()==0){
            return JSONResult.ok(permissions);
        }

        List<PermissionVO> ps = new ArrayList<>();

        permissionList.forEach(permission -> {
            PermissionVO permissionVO = new PermissionVO();
            BeanUtils.copyProperties(permission,permissionVO);
            ps.add(permissionVO);
        });



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

        return  JSONResult.ok(permissions);
    }

    @ApiOperation(value = "获取权限信息(未组装)", notes = "获取权限信息(未组装)",httpMethod = "GET")
    @GetMapping("/query")
    public JSONResult query(@ApiParam(name = "queryText",value = "模糊查询关键字",required = false)
                              @RequestParam String queryText,
                          @ApiParam(name = "page",value = "查询的页数",required = false)
                              @RequestParam Integer page,
                          @ApiParam(name = "pageSize",value = "每页显示多少条",required = false)
                              @RequestParam Integer pageSize){


        if (page==null){
            page = 1;
        }
        if (pageSize == null){
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult pagedGridResult = permissionService.getPermissionList(queryText,page,pageSize);

        return JSONResult.ok(pagedGridResult);
    }


    private boolean checkPermissionIsExist(Integer id){

        Permission permission = permissionService.queryPermissionById(id);
        return null != permission;
    }

}

