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
package com.cn.xiaonuo.sys.modular.notice.controller;

import com.cn.xiaonuo.core.annotion.BusinessLog;
import com.cn.xiaonuo.core.annotion.Permission;
import com.cn.xiaonuo.core.enums.LogAnnotionOpTypeEnum;
import com.cn.xiaonuo.core.pojo.page.PageResult;
import com.cn.xiaonuo.core.pojo.response.ResponseData;
import com.cn.xiaonuo.core.pojo.response.SuccessResponseData;
import com.cn.xiaonuo.sys.modular.notice.entity.SysNotice;
import com.cn.xiaonuo.sys.modular.notice.param.SysNoticeParam;
import com.cn.xiaonuo.sys.modular.notice.result.SysNoticeReceiveResult;
import com.cn.xiaonuo.sys.modular.notice.service.SysNoticeService;
import com.cn.xiaonuo.sys.modular.notice.param.SysNoticeParam;
import com.cn.xiaonuo.sys.modular.notice.service.SysNoticeService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统通知公告控制器
 *
 * @author xuyuxiang
 * @date 2020/6/28 17:18
 */
@Controller
public class SysNoticeController {

    @Resource
    private SysNoticeService sysNoticeService;

    /**
     * 系统通知公告页面
     *
     * @author xuyuxiang
     * @date 2020/11/17 16:40
     */
    @Permission
    @GetMapping("/sysNotice/index")
    public String index() {
        return "system/sysNotice/index.html";
    }

    /**
     * 系统通知公告表单页面
     *
     * @author xuyuxiang
     * @date 2020/11/17 16:40
     */
    @GetMapping("/sysNotice/form")
    public String form() {
        return "system/sysNotice/form.html";
    }

    /**
     * 已收公告页面
     *
     * @author xuyuxiang
     * @date 2020/11/17 16:40
     */
    @Permission
    @GetMapping("/sysNotice/receivedPage")
    public String receivedPage() {
        return "system/sysNotice/received.html";
    }

    /**
     * 系统通知公告详情页面
     *
     * @author xuyuxiang
     * @date 2020/11/17 16:40
     */
    @GetMapping("/sysNotice/detailPage")
    public String detailPage() {
        return "system/sysNotice/detail.html";
    }

    /**
     * 查询系统通知公告
     *
     * @author xuyuxiang
     * @date 2020/6/28 17:24
     */
    @Permission
    @ResponseBody
    @GetMapping("/sysNotice/page")
    @BusinessLog(title = "系统通知公告_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public PageResult<SysNotice> page(SysNoticeParam sysNoticeParam) {
        return sysNoticeService.page(sysNoticeParam);
    }

    /**
     * 查询我收到的系统通知公告
     *
     * @author xuyuxiang
     * @date 2020/6/29 11:59
     */
    @Permission
    @ResponseBody
    @GetMapping("/sysNotice/received")
    @BusinessLog(title = "系统通知公告_已收", opType = LogAnnotionOpTypeEnum.QUERY)
    public PageResult<SysNoticeReceiveResult> received(SysNoticeParam sysNoticeParam) {
        return sysNoticeService.received(sysNoticeParam);
    }

    /**
     * 添加系统通知公告
     *
     * @author xuyuxiang
     * @date 2020/6/28 17:24
     */
    @Permission
    @ResponseBody
    @PostMapping("/sysNotice/add")
    @BusinessLog(title = "系统通知公告_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(SysNoticeParam.add.class) SysNoticeParam sysNoticeParam) {
        sysNoticeService.add(sysNoticeParam);
        return new SuccessResponseData();
    }

    /**
     * 删除系统通知公告
     *
     * @author xuyuxiang
     * @date 2020/6/28 17:25
     */
    @Permission
    @ResponseBody
    @PostMapping("/sysNotice/delete")
    @BusinessLog(title = "系统通知公告_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(SysNoticeParam.delete.class) List<SysNoticeParam> sysNoticeParamList) {
        sysNoticeService.delete(sysNoticeParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统通知公告
     *
     * @author xuyuxiang
     * @date 2020/6/28 17:25
     */
    @Permission
    @ResponseBody
    @PostMapping("/sysNotice/edit")
    @BusinessLog(title = "系统通知公告_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(SysNoticeParam.edit.class) SysNoticeParam sysNoticeParam) {
        sysNoticeService.edit(sysNoticeParam);
        return new SuccessResponseData();
    }

    /**
     * 查看系统通知公告
     *
     * @author xuyuxiang
     * @date 2020/6/28 17:25
     */
    @Permission
    @ResponseBody
    @GetMapping("/sysNotice/detail")
    @BusinessLog(title = "系统通知公告_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(SysNoticeParam.detail.class) SysNoticeParam sysNoticeParam) {
        return new SuccessResponseData(sysNoticeService.detail(sysNoticeParam));
    }

    /**
     * 修改状态
     *
     * @author xuyuxiang
     * @date 2020/6/29 9:44
     */
    @Permission
    @ResponseBody
    @PostMapping("/sysNotice/changeStatus")
    @BusinessLog(title = "系统通知公告_修改状态", opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData changeStatus(@RequestBody @Validated(SysNoticeParam.changeStatus.class) SysNoticeParam sysNoticeParam) {
        sysNoticeService.changeStatus(sysNoticeParam);
        return new SuccessResponseData();
    }
}
