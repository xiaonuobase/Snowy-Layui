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
4.分发源码时候，请注明软件出处 https://gitee.com/xiaonuo/xiaonuo-layui
5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/xiaonuo/xiaonuo-layui
6.若您的项目无法满足以上几点，可申请商业授权，获取XiaoNuo商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package com.cn.xiaonuo.sys.modular;

import cn.hutool.core.util.ObjectUtil;
import com.cn.xiaonuo.core.consts.CommonConstant;
import com.cn.xiaonuo.core.context.login.LoginContextHolder;
import com.cn.xiaonuo.core.exception.enums.AuthExceptionEnum;
import com.cn.xiaonuo.core.pojo.login.SysLoginUser;
import com.cn.xiaonuo.core.util.HttpServletUtil;
import com.cn.xiaonuo.sys.modular.auth.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 首页控制器
 *
 * @author xuyuxiang
 * @date 2020/3/18 11:20
 */
@Controller
public class IndexController {

    @Resource
    private AuthService authService;

    /**
     * 访问首页，提示语
     *
     * @author xuyuxiang
     * @date 2020/4/8 19:27
     */
    @GetMapping("/")
    public String index(Model model) {
        //判断是否登录
        boolean hasLogin = LoginContextHolder.me().hasLogin();
        if(hasLogin) {
            SysLoginUser sysLoginUser = LoginContextHolder.me().getSysLoginUserWithoutException();
            model.addAttribute("loginUser", sysLoginUser);
            return "index.html";
        } else {
            HttpServletRequest request = HttpServletUtil.getRequest();
            String token = authService.getTokenFromRequest(request);
            if(ObjectUtil.isNotNull(token)) {
                model.addAttribute("tips", AuthExceptionEnum.LOGIN_EXPIRED.getMessage());
            } else {
                model.addAttribute("tips", AuthExceptionEnum.NOT_LOGIN.getMessage());
            }
            return "login/login.html";
        }
    }
}
