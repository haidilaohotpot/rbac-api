package com.wonder4work.interceptor;

import com.wonder4work.annotation.UserPermission;
import com.wonder4work.pojo.Permission;
import com.wonder4work.pojo.User;
import com.wonder4work.pojo.vo.PermissionVO;
import com.wonder4work.util.CookieUtils;
import com.wonder4work.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @since 1.0.0 2020/6/12
 */
@Component
@Slf4j
public class PermissionInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {


        HandlerMethod handlerMethod = (HandlerMethod) handler;

        Method method = handlerMethod.getMethod();

        UserPermission annotation = AnnotationUtils.getAnnotation(method, UserPermission.class);

        if (annotation == null){
            return true;
        }

        String value = annotation.value();


        // 这里注意前端每次请求都要携带cookie 可以放到内存中 这里只作为演示
        String keepAlivePermissions = CookieUtils.getCookieValue(request, "keepAlivePermissions", true);

        if (StringUtils.isBlank(keepAlivePermissions)){
            return false;
        }

        List<Permission> permissionList = JsonUtils.jsonToList(keepAlivePermissions, Permission.class);

        if (permissionList == null){
            return false;
        }

        for (Permission permission : permissionList) {

            if (permission.getUrl().equals(value)){
                return true;
            }

        }

        return false;
    }
}
