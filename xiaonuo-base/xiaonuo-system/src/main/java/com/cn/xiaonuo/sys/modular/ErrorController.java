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
import com.cn.xiaonuo.core.exception.AuthException;
import com.cn.xiaonuo.core.exception.enums.AuthExceptionEnum;
import com.cn.xiaonuo.core.exception.enums.PermissionExceptionEnum;
import com.cn.xiaonuo.core.exception.enums.ServerExceptionEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 错误页面控制器
 *
 * @author xuyuxiang
 * @date 2020/3/18 11:20
 */
@Controller
public class ErrorController {

    /**
     * 跳转到404页面
     *
     * @author xuyuxiang
     * @date 2020/11/6 14:24
     */
    @GetMapping("/global/error")
    public String errorPage() {
        return "redirect:/404";
    }

    /**
     * 跳转到403页面
     *
     * @author xuyuxiang
     * @date 2020/11/6 14:24
     */
    @GetMapping("/403")
    public String noPermissionPage() {
        return "error/403.html";
    }

    /**
     * 跳转到404页面
     *
     * @author xuyuxiang
     * @date 2020/11/6 14:24
     */
    @GetMapping("/404")
    public String notFoundPage() {
        return "error/404.html";
    }

    /**
     * 跳转到500页面
     *
     * @author xuyuxiang
     * @date 2020/11/6 14:24
     */
    @GetMapping("/500")
    public String serverErrorPage() {
        return "error/500.html";
    }

    /**
     * 跳转到session超时页面
     *
     * @author xuyuxiang
     * @date 2020/11/6 14:24
     */
    @GetMapping(path = "/global/sessionError")
    public String sessionError(Model model, Integer errorCode) {
        if (ObjectUtil.isNotNull(errorCode)) {
            if (errorCode.equals(PermissionExceptionEnum.NO_PERMISSION.getCode())) {
                //如果是没权限，403页面
                return "redirect:/403";
            }
            if (errorCode.equals(PermissionExceptionEnum.URL_NOT_EXIST.getCode())) {
                //如果是资源路径不存在，404页面
                return "redirect:/404";
            }
            if (errorCode.equals(AuthExceptionEnum.NOT_LOGIN.getCode())) {
                //如果需要登录，登录页
                return "redirect:/";
            }
        } else {
            //登录过期，设置登录过期的提示消息
            model.addAttribute("tips", AuthExceptionEnum.LOGIN_EXPIRED.getMessage());
            return "login/login.html";
        }
        return null;
    }
}
