package com.wonder4work.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @since 1.0.0 2020/5/18
 */
@Mapper
public interface RolePermissionMapper {

    /**
     * 清空原有的权限
     * @param roleId
     */
    void removeAll(@Param("roleId") String roleId);

    /**
     * 分配权限
     * @param map
     */
    void doAssign(@Param("map") Map<String, Object> map);

}
