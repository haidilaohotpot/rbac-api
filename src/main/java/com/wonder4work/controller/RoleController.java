package com.wonder4work.controller;


import com.fasterxml.jackson.databind.ser.Serializers;
import com.wonder4work.annotation.UserPermission;
import com.wonder4work.pojo.Permission;
import com.wonder4work.pojo.Role;
import com.wonder4work.pojo.bo.RoleBO;
import com.wonder4work.pojo.bo.UserBO;
import com.wonder4work.pojo.vo.PermissionVO;
import com.wonder4work.service.PermissionService;
import com.wonder4work.service.RoleService;
import com.wonder4work.util.JSONResult;
import com.wonder4work.util.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
 * 角色表 前端控制器
 * </p>
 *
 * @author wonder4work
 * @since 2020-05-18
 */
@RestController
@RequestMapping("/role")
@Api(tags = {"角色操作相关接口"},value = "角色操作")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "分页获取角色信息", notes = "分页获取角色信息",httpMethod = "GET")
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

        PagedGridResult pagedGridResult = roleService.getRoleList(queryText,page,pageSize);

        return JSONResult.ok(pagedGridResult);
    }


//    @UserPermission("/queryRoleByRoleId")
    @ApiOperation(value = "根据ID获取角色信息", notes = "根据ID获取角色信息",httpMethod = "GET")
    @GetMapping("/{roleId}")
    public JSONResult queryUserByUserId(@ApiParam(name = "roleId",value = "角色ID",required = true)
                                        @PathVariable("roleId") String roleId){

        if (StringUtils.isBlank(roleId)) {
            return JSONResult.errorMsg("角色ID不能为空");
        }

        Role role = roleService.queryRoleByRoleId(roleId);
        return JSONResult.ok(role);
    }


    @ApiOperation(value = "新增角色", notes = "新增角色",httpMethod = "POST")
    @PostMapping("/insert")
    public JSONResult insert(@ApiParam(name = "roleBO",value = "角色信息",required = true)
                             @RequestBody RoleBO roleBO){

        if (null == roleBO || StringUtils.isBlank(roleBO.getName())){
            return JSONResult.errorMsg("角色名不能为空");
        }

        boolean isExist = roleService.queryNameIsExist(roleBO.getName());
        if (isExist){
            return JSONResult.errorMsg("此角色名已经存在");
        }

        //新增角色
        Role dbRole = roleService.insert(roleBO);

        return JSONResult.ok(dbRole);
    }

    @ApiOperation(value = "修改角色信息", notes = "修改角色信息",httpMethod = "POST")
    @PostMapping("/update")
    public JSONResult update(@ApiParam(name = "roleId",value = "要修改的角色ID",required = true)
                             @RequestParam String roleId,
                             @ApiParam(name = "roleBO",value = "角色信息",required = true)
                             @RequestBody RoleBO roleBO){

        if (StringUtils.isBlank(roleId)) {
            return JSONResult.errorMsg("ID不能为空");
        }

        if (null == roleBO||StringUtils.isBlank(roleBO.getName())){
            return JSONResult.errorMsg("信息不能为空");
        }

        boolean isExist = roleService.queryNameIsExist(roleBO.getName());

        if (isExist){
            return JSONResult.errorMsg("此角色已经存在");
        }

        //更新
        Role user = roleService.update(roleId,roleBO);

        return JSONResult.ok(user);
    }

    @ApiOperation(value = "一次性获取所有角色信息", notes = "获取所有角色信息",httpMethod = "GET")
    @GetMapping("/list")
    public JSONResult list(){

        List<Role> roleList = roleService.list();

        return JSONResult.ok(roleList);
    }

    @ApiOperation(value = "为角色重新分配权限，覆盖原先", notes = "为角色重新分配权限，覆盖原先",httpMethod = "POST")
    @PostMapping("/doAssign")
    public JSONResult doAssign(@ApiParam(name = "roleId",value = "角色ID",required = true)
                               @RequestParam String roleId,
                               @ApiParam(name = "permissionIds",value = "要分配的权限ID数组",required = true)
                               @RequestBody String[] permissionIds){

        if (StringUtils.isBlank(roleId)) {
            return JSONResult.errorMsg("ID不能为空");
        }

        if (!checkRole(roleId)){
            return JSONResult.errorMsg("角色不存在");
        }

        if (null == permissionIds) {
            permissionIds = new String[]{};
        }

        // 分配角色
        roleService.doAssign(roleId, permissionIds);

        return JSONResult.ok();
    }


    @ApiOperation(value = "获取角色已经拥有和未拥有的权限(已经拥有将被选中)", notes = "获取角色已经拥有和未拥有的权限(已经拥有将被选中)",httpMethod = "GET")
    @GetMapping("/assign/{roleId}")
    public JSONResult assign(@ApiParam(name = "roleId",value = "角色ID",required = true)
                             @PathVariable("roleId") String roleId){

        if (StringUtils.isBlank(roleId)) {
            return JSONResult.errorMsg("ID不能为空");
        }

        if (!checkRole(roleId)){
            return JSONResult.errorMsg("角色不存在");
        }

        List<PermissionVO> permissions = new ArrayList<PermissionVO>();
        List<Permission> permissionList = permissionService.list();

        if (permissionList == null||permissionList.size()==0){
            return JSONResult.ok(permissions);
        }

        List<PermissionVO> ps = new ArrayList<>();

        permissionList.forEach(permission -> {
            PermissionVO permissionVO = new PermissionVO();
            BeanUtils.copyProperties(permission,permissionVO);
            ps.add(permissionVO);
        });

        //获取当前角色已经分配的许可信息
        List<Integer> permissionIds = permissionService.queryPermissionIdsByRoleId(roleId);

        Map<Integer, PermissionVO> permissionMap = new HashMap<Integer, PermissionVO>();

        for (PermissionVO p : ps) {

            if (permissionIds != null && permissionIds.size()>0) {
                if (permissionIds.contains(p.getPermissionId())) {
                    p.setChecked(true);
                } else {
                    p.setChecked(false);
                }
            }

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


        return JSONResult.ok(permissions);
    }


    private boolean checkRole(String roleId) {

        Role role = roleService.queryRoleByRoleId(roleId);
        return null != role;
    }
}

