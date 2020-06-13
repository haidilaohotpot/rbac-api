package com.wonder4work.config;

import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsProcessor;
import org.springframework.web.cors.DefaultCorsProcessor;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

/**
 * @since 1.0.0 2020/3/29
 * 跨域配置
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {

        // 跨域配置基本信息
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.setAllowCredentials(true); // 凭证 是否发送cookie信息
        corsConfiguration.addAllowedMethod("*"); // 设置允许请求的方式
        corsConfiguration.addAllowedHeader("*"); // 设置允许的header

        // 为url添加映射路径
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**", corsConfiguration);
        // 返回定义好的source
        CorsFilter corsFilter =  new CorsFilter(corsSource);

        corsFilter.setCorsProcessor(new MyCorsProcessor());

        return corsFilter;
    }


    class MyCorsProcessor extends DefaultCorsProcessor{

        @Override
        public boolean processRequest(CorsConfiguration config, HttpServletRequest request, HttpServletResponse response) throws IOException {
            super.processRequest(config, request, response);

            String token = request.getHeader("token");

            if (null != token &&token.equals("wonder4work")) {
                return true;
            }

            return false;

        }
    }


    /**
     * 解决问题：
     * There was an unexpected error (type=Internal Server Error, status=500).
     * An invalid domain [.xxx.com] was specified for this cookie
     *
     * @return
     */
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
        return (factory) -> factory.addContextCustomizers(
                (context) -> context.setCookieProcessor(new LegacyCookieProcessor()));
    }

}
