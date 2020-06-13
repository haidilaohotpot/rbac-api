package com.wonder4work.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @since 1.0.0 2020/5/18
 */
@Mapper
public interface UserRoleMapper {

    /**
     * 为用户分配角色
     * @param map
     */
    void doAssign(@Param("map") Map<String, Object> map);

    /**
     * 取消用户已经分配的角色
     * @param map
     */
    void cancelAssign(@Param("map")Map<String, Object> map);
}
