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
package com.cn.xiaonuo.sys.modular.timer.controller;

import cn.hutool.core.lang.Dict;
import com.cn.xiaonuo.core.annotion.BusinessLog;
import com.cn.xiaonuo.core.annotion.Permission;
import com.cn.xiaonuo.core.enums.LogAnnotionOpTypeEnum;
import com.cn.xiaonuo.core.pojo.page.PageResult;
import com.cn.xiaonuo.core.pojo.response.ResponseData;
import com.cn.xiaonuo.core.pojo.response.SuccessResponseData;
import com.cn.xiaonuo.sys.modular.timer.entity.SysTimers;
import com.cn.xiaonuo.sys.modular.timer.param.SysTimersParam;
import com.cn.xiaonuo.sys.modular.timer.service.SysTimersService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 定时任务控制器
 *
 * @author yubaoshan
 * @date 2020/6/30 18:26
 */
@Controller
public class SysTimersController {

    @Resource
    private SysTimersService sysTimersService;

    /**
     * 定时任务页面
     *
     * @author xuyuxiang
     * @date 2020/11/17 16:40
     */
    @Permission
    @GetMapping("/sysTimers/index")
    public String index() {
        return "system/sysTimers/index.html";
    }

    /**
     * 定时任务表单页面
     *
     * @author xuyuxiang
     * @date 2020/11/17 16:40
     */
    @GetMapping("/sysTimers/form")
    public String form() {
        return "system/sysTimers/form.html";
    }

    /**
     * 分页查询定时任务
     *
     * @author yubaoshan
     * @date 2020/6/30 18:26
     */
    @GetMapping("/sysTimers/page")
    @ResponseBody
    @BusinessLog(title = "定时任务_分页查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public PageResult<SysTimers> page(SysTimersParam sysTimersParam) {
        return sysTimersService.page(sysTimersParam);
    }

    /**
     * 获取全部定时任务
     *
     * @author yubaoshan
     * @date 2020/6/30 18:26
     */
    @GetMapping("/sysTimers/list")
    @ResponseBody
    @BusinessLog(title = "定时任务_查询所有", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(SysTimersParam sysTimersParam) {
        return new SuccessResponseData(sysTimersService.list(sysTimersParam));
    }

    /**
     * 查看详情定时任务
     *
     * @author yubaoshan
     * @date 2020/6/30 18:26
     */
    @GetMapping("/sysTimers/detail")
    @ResponseBody
    @BusinessLog(title = "定时任务_查看详情", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(SysTimersParam.detail.class) SysTimersParam sysTimersParam) {
        return new SuccessResponseData(sysTimersService.detail(sysTimersParam));
    }

    /**
     * 添加定时任务
     *
     * @author yubaoshan
     * @date 2020/6/30 18:26
     */
    @PostMapping("/sysTimers/add")
    @ResponseBody
    @BusinessLog(title = "定时任务_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(SysTimersParam.add.class) SysTimersParam sysTimersParam) {
        sysTimersService.add(sysTimersParam);
        return new SuccessResponseData();
    }

    /**
     * 删除定时任务
     *
     * @author yubaoshan
     * @date 2020/6/30 18:26
     */
    @PostMapping("/sysTimers/delete")
    @ResponseBody
    @BusinessLog(title = "定时任务_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(SysTimersParam.delete.class) List<SysTimersParam> sysTimersParamList) {
        sysTimersService.delete(sysTimersParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑定时任务
     *
     * @author yubaoshan
     * @date 2020/6/30 18:26
     */
    @PostMapping("/sysTimers/edit")
    @ResponseBody
    @BusinessLog(title = "定时任务_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(SysTimersParam.edit.class) SysTimersParam sysTimersParam) {
        sysTimersService.edit(sysTimersParam);
        return new SuccessResponseData();
    }

    /**
     * 获取系统的所有任务列表
     *
     * @author yubaoshan
     * @date 2020/7/1 14:34
     */
    @GetMapping("/sysTimers/getActionClasses")
    @ResponseBody
    @BusinessLog(title = "定时任务_任务列表", opType = LogAnnotionOpTypeEnum.OTHER)
    public ResponseData getActionClasses() {
        List<Dict> actionClasses = sysTimersService.getActionClasses();
        return new SuccessResponseData(actionClasses);
    }

    /**
     * 启动定时任务
     *
     * @author yubaoshan
     * @date 2020/7/1 14:34
     */
    @PostMapping("/sysTimers/start")
    @ResponseBody
    @BusinessLog(title = "定时任务_启动", opType = LogAnnotionOpTypeEnum.OTHER)
    public ResponseData start(@RequestBody @Validated(SysTimersParam.start.class) SysTimersParam sysTimersParam) {
        sysTimersService.start(sysTimersParam);
        return new SuccessResponseData();
    }

    /**
     * 停止定时任务
     *
     * @author yubaoshan
     * @date 2020/7/1 14:34
     */
    @PostMapping("/sysTimers/stop")
    @ResponseBody
    @BusinessLog(title = "定时任务_关闭", opType = LogAnnotionOpTypeEnum.OTHER)
    public ResponseData stop(@RequestBody @Validated(SysTimersParam.stop.class) SysTimersParam sysTimersParam) {
        sysTimersService.stop(sysTimersParam);
        return new SuccessResponseData();
    }

}
