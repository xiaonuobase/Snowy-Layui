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
package com.cn.xiaonuo.sys.modular.pos.controller;

import com.cn.xiaonuo.core.annotion.BusinessLog;
import com.cn.xiaonuo.core.annotion.Permission;
import com.cn.xiaonuo.core.enums.LogAnnotionOpTypeEnum;
import com.cn.xiaonuo.core.pojo.page.PageResult;
import com.cn.xiaonuo.core.pojo.response.ResponseData;
import com.cn.xiaonuo.core.pojo.response.SuccessResponseData;
import com.cn.xiaonuo.sys.modular.pos.entity.SysPos;
import com.cn.xiaonuo.sys.modular.pos.param.SysPosParam;
import com.cn.xiaonuo.sys.modular.pos.service.SysPosService;
import com.cn.xiaonuo.core.annotion.BusinessLog;
import com.cn.xiaonuo.core.annotion.Permission;
import com.cn.xiaonuo.core.enums.LogAnnotionOpTypeEnum;
import com.cn.xiaonuo.core.pojo.response.ResponseData;
import com.cn.xiaonuo.core.pojo.response.SuccessResponseData;
import com.cn.xiaonuo.sys.modular.pos.param.SysPosParam;
import com.cn.xiaonuo.sys.modular.pos.service.SysPosService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统职位控制器
 *
 * @author xuyuxiang
 * @date 2020/3/20 19:44
 */
@Controller
public class SysPosController {

    @Resource
    private SysPosService sysPosService;

    /**
     * 系统职位页面
     *
     * @author xuyuxiang
     * @date 2020/11/17 16:40
     */
    @Permission
    @GetMapping("/sysPos/index")
    public String index() {
        return "system/sysPos/index.html";
    }

    /**
     * 系统职位表单页面
     *
     * @author xuyuxiang
     * @date 2020/11/17 16:40
     */
    @GetMapping("/sysPos/form")
    public String form() {
        return "system/sysPos/form.html";
    }

    /**
     * 查询系统职位
     *
     * @author xuyuxiang
     * @date 2020/3/26 10:20
     */
    @Permission
    @ResponseBody
    @GetMapping("/sysPos/page")
    @BusinessLog(title = "系统职位_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public PageResult<SysPos> page(SysPosParam sysPosParam) {
        return sysPosService.page(sysPosParam);
    }

    /**
     * 系统职位列表
     *
     * @author yubaoshan
     * @date 2020/6/21 23:38
     */
    @Permission
    @ResponseBody
    @GetMapping("/sysPos/list")
    @BusinessLog(title = "系统职位_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(SysPosParam sysPosParam) {
        return new SuccessResponseData(sysPosService.list(sysPosParam));
    }

    /**
     * 添加系统职位
     *
     * @author xuyuxiang
     * @date 2020/3/26 19:03
     */
    @Permission
    @ResponseBody
    @PostMapping("/sysPos/add")
    @BusinessLog(title = "系统职位_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(SysPosParam.add.class) SysPosParam sysPosParam) {
        sysPosService.add(sysPosParam);
        return new SuccessResponseData();
    }

    /**
     * 删除系统职位
     *
     * @author xuyuxiang
     * @date 2020/3/25 14:54
     */
    @Permission
    @ResponseBody
    @PostMapping("/sysPos/delete")
    @BusinessLog(title = "系统职位_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(SysPosParam.delete.class) List<SysPosParam> sysPosParamList) {
        sysPosService.delete(sysPosParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统职位
     *
     * @author xuyuxiang
     * @date 2020/3/25 14:54
     */
    @Permission
    @ResponseBody
    @PostMapping("/sysPos/edit")
    @BusinessLog(title = "系统职位_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(SysPosParam.edit.class) SysPosParam sysPosParam) {
        sysPosService.edit(sysPosParam);
        return new SuccessResponseData();
    }

    /**
     * 查看系统职位
     *
     * @author xuyuxiang
     * @date 2020/3/26 9:49
     */
    @Permission
    @ResponseBody
    @GetMapping("/sysPos/detail")
    @BusinessLog(title = "系统职位_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(SysPosParam.detail.class) SysPosParam sysPosParam) {
        return new SuccessResponseData(sysPosService.detail(sysPosParam));
    }
}
