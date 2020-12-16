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
package com.cn.xiaonuo.sys.modular.monitor.controller;

import com.cn.xiaonuo.core.annotion.BusinessLog;
import com.cn.xiaonuo.core.annotion.Permission;
import com.cn.xiaonuo.core.enums.LogAnnotionOpTypeEnum;
import com.cn.xiaonuo.core.pojo.page.PageResult;
import com.cn.xiaonuo.core.pojo.response.ResponseData;
import com.cn.xiaonuo.core.pojo.response.SuccessResponseData;
import com.cn.xiaonuo.sys.modular.monitor.param.SysOnlineUserParam;
import com.cn.xiaonuo.sys.modular.monitor.result.SysOnlineUserResult;
import com.cn.xiaonuo.sys.modular.monitor.service.SysOnlineUserService;
import com.cn.xiaonuo.sys.modular.monitor.service.SysOnlineUserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 在线用户控制器
 *
 * @author xuyuxiang
 * @date 2020/4/7 16:57
 */
@Controller
public class SysOnlineUserController {

    @Resource
    private SysOnlineUserService sysOnlineUserService;

    /**
     * 在线用户管理页面
     *
     * @author xuyuxiang
     * @date 2020/11/17 16:40
     */
    @Permission
    @GetMapping("/sysOnlineUser/index")
    public String index() {
        return "system/sysOnlineUser/index.html";
    }

    /**
     * 在线用户分页查询
     *
     * @author xuyuxiang
     * @date 2020/4/7 16:58
     */
    @Permission
    @ResponseBody
    @GetMapping("/sysOnlineUser/page")
    @BusinessLog(title = "系统在线用户_分页查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public PageResult<SysOnlineUserResult> page(SysOnlineUserParam sysOnlineUserParam) {
        return sysOnlineUserService.page(sysOnlineUserParam);
    }

    /**
     * 在线用户强退
     *
     * @author xuyuxiang
     * @date 2020/4/7 17:36
     */
    @Permission
    @ResponseBody
    @PostMapping("/sysOnlineUser/forceExist")
    @BusinessLog(title = "系统在线用户_强退", opType = LogAnnotionOpTypeEnum.FORCE)
    public ResponseData forceExist(@RequestBody @Validated(SysOnlineUserParam.force.class) SysOnlineUserParam sysOnlineUserParam) {
        sysOnlineUserService.forceExist(sysOnlineUserParam);
        return new SuccessResponseData();
    }
}
