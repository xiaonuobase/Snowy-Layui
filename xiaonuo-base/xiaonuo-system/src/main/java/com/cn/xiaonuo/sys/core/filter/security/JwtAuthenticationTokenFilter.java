/*
Copyright [2020] [https://www.xiaonuo.vip]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

XiaoNuo采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：

1.请不要删除和修改根目录下的LICENSE文件。
2.请不要删除和修改XiaoNuo源码头部的版权声明。
3.请保留源码和相关描述文件的项目出处，作者声明等。
4.分发源码时候，请注明软件出处 https://gitee.com/xiaonuobase/xiaonuo-layui
5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/xiaonuobase/xiaonuo-layui
6.若您的项目无法满足以上几点，可申请商业授权，获取XiaoNuo商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package com.cn.xiaonuo.sys.core.filter.security;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import cn.hutool.log.Log;
import com.cn.xiaonuo.core.consts.CommonConstant;
import com.cn.xiaonuo.core.consts.SpringSecurityConstant;
import com.cn.xiaonuo.core.context.requestno.RequestNoContext;
import com.cn.xiaonuo.core.exception.AuthException;
import com.cn.xiaonuo.core.exception.enums.AuthExceptionEnum;
import com.cn.xiaonuo.core.exception.enums.RequestMethodExceptionEnum;
import com.cn.xiaonuo.core.exception.enums.ServerExceptionEnum;
import com.cn.xiaonuo.core.pojo.login.SysLoginUser;
import com.cn.xiaonuo.core.util.ResponseUtil;
import com.cn.xiaonuo.sys.modular.auth.service.AuthService;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 这个过滤器，在所有请求之前，也在spring security filters之前
 * <p>
 * 这个过滤器的作用是：接口在进业务之前，添加登录上下文（SecurityContext）
 * <p>
 * 因为现在没有用session了，只能token来校验当前的登录人的身份，所以在进业务之前要给当前登录人设置登录状态
 *
 * @author yubaoshan，xuyuxiang
 * @date 2020/4/11 13:02
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Log log = Log.get();

    @Resource
    private AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // 1.如果当前请求带了token，判断token时效性，并获取当前登录用户信息
        SysLoginUser sysLoginUser = null;
        try {
            String token = authService.getTokenFromRequest(request);
            if (StrUtil.isNotEmpty(token)) {
                sysLoginUser = authService.getLoginUserByToken(token);
            }
        } catch (AuthException ae) {
            //如果是不需要权限校验的接口则放开
            String servletPath = request.getServletPath();
            for (String reg : SpringSecurityConstant.NONE_SECURITY_URL_PATTERNS) {
                if (new AntPathMatcher().match(reg, servletPath)) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }

            //传入了token，且获取不到用户信息，则表示token错误，或token过期，统一按照token过期处理，需重新登录
            //无论如何，先删除cookie
            authService.deleteLoginCookie();
            //处理异常
            ResponseUtil.handleErrorWithWrite(AuthExceptionEnum.LOGIN_EXPIRED.getCode(), AuthExceptionEnum.LOGIN_EXPIRED.getMessage(), ae.getStackTrace()[0].toString());
            return;
        }

        // 2.如果当前登录用户不为空，就设置spring security上下文
        if (ObjectUtil.isNotNull(sysLoginUser)) {
            authService.setSpringSecurityContextAuthentication(sysLoginUser);
        }

        // 3.其他情况放开过滤
        filterChain.doFilter(request, response);
    }
}
