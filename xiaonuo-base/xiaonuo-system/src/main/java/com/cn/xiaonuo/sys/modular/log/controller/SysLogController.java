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
package com.cn.xiaonuo.sys.modular.log.controller;

import com.cn.xiaonuo.core.annotion.BusinessLog;
import com.cn.xiaonuo.core.annotion.Permission;
import com.cn.xiaonuo.core.enums.LogAnnotionOpTypeEnum;
import com.cn.xiaonuo.core.pojo.page.PageResult;
import com.cn.xiaonuo.core.pojo.response.ResponseData;
import com.cn.xiaonuo.core.pojo.response.SuccessResponseData;
import com.cn.xiaonuo.sys.modular.log.entity.SysOpLog;
import com.cn.xiaonuo.sys.modular.log.entity.SysVisLog;
import com.cn.xiaonuo.sys.modular.log.param.SysOpLogParam;
import com.cn.xiaonuo.sys.modular.log.param.SysVisLogParam;
import com.cn.xiaonuo.sys.modular.log.service.SysOpLogService;
import com.cn.xiaonuo.sys.modular.log.service.SysVisLogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统日志控制器
 *
 * @author xuyuxiang
 * @date 2020/3/19 21:14
 */
@Controller
public class SysLogController {

    @Resource
    private SysVisLogService sysVisLogService;

    @Resource
    private SysOpLogService sysOpLogService;

    /**
     * 访问日志页面
     *
     * @author xuyuxiang
     * @date 2020/11/17 16:40
     */
    @Permission
    @GetMapping("/sysVisLog/index")
    public String visLogIndex() {
        return "system/sysVisLog/index.html";
    }

    /**
     * 访问日志详情页面
     *
     * @author xuyuxiang
     * @date 2020/11/17 16:40
     */
    @GetMapping("/sysVisLog/detail")
    public String visLogDetail() {
        return "system/sysVisLog/detail.html";
    }

    /**
     * 操作日志页面
     *
     * @author xuyuxiang
     * @date 2020/11/17 16:40
     */
    @Permission
    @GetMapping("/sysOpLog/index")
    public String opLogIndex() {
        return "system/sysOpLog/index.html";
    }

    /**
     * 操作日志详情页面
     *
     * @author xuyuxiang
     * @date 2020/11/17 16:40
     */
    @GetMapping("/sysOpLog/detail")
    public String opLogDetail() {
        return "system/sysOpLog/detail.html";
    }

    /**
     * 查询访问日志
     *
     * @author xuyuxiang
     * @date 2020/3/20 18:52
     */
    @Permission
    @ResponseBody
    @GetMapping("/sysVisLog/page")
    public PageResult<SysVisLog> visLogPage(SysVisLogParam visLogParam) {
        return sysVisLogService.page(visLogParam);
    }

    /**
     * 查询操作日志
     *
     * @author xuyuxiang
     * @date 2020/3/20 18:52
     */
    @Permission
    @ResponseBody
    @GetMapping("/sysOpLog/page")
    public PageResult<SysOpLog> opLogPage(SysOpLogParam sysOpLogParam) {
        return sysOpLogService.page(sysOpLogParam);
    }

    /**
     * 清空访问日志
     *
     * @author xuyuxiang
     * @date 2020/3/23 16:28
     */
    @Permission
    @ResponseBody
    @PostMapping("/sysVisLog/delete")
    @BusinessLog(title = "访问日志_清空", opType = LogAnnotionOpTypeEnum.CLEAN)
    public ResponseData visLogDelete() {
        sysVisLogService.delete();
        return new SuccessResponseData();
    }

    /**
     * 清空操作日志
     *
     * @author xuyuxiang
     * @date 2020/3/23 16:28
     */
    @Permission
    @ResponseBody
    @PostMapping("/sysOpLog/delete")
    @BusinessLog(title = "操作日志_清空", opType = LogAnnotionOpTypeEnum.CLEAN)
    public ResponseData opLogDelete() {
        sysOpLogService.delete();
        return new SuccessResponseData();
    }
}
