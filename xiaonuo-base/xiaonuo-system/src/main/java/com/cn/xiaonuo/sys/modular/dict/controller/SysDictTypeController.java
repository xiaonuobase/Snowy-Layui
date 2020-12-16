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
package com.cn.xiaonuo.sys.modular.dict.controller;

import com.cn.xiaonuo.core.annotion.BusinessLog;
import com.cn.xiaonuo.core.annotion.Permission;
import com.cn.xiaonuo.core.enums.LogAnnotionOpTypeEnum;
import com.cn.xiaonuo.core.pojo.page.PageResult;
import com.cn.xiaonuo.core.pojo.response.ResponseData;
import com.cn.xiaonuo.core.pojo.response.SuccessResponseData;
import com.cn.xiaonuo.sys.modular.dict.entity.SysDictType;
import com.cn.xiaonuo.sys.modular.dict.param.SysDictTypeParam;
import com.cn.xiaonuo.sys.modular.dict.service.SysDictTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统字典类型控制器
 *
 * @author xuyuxiang，xuyuxiang
 * @date 2020/3/31 20:49
 */
@Controller
public class SysDictTypeController {

    @Resource
    private SysDictTypeService sysDictTypeService;

    /**
     * 字典类型管理页面
     *
     * @author xuyuxiang
     * @date 2020/11/17 16:40
     */
    @Permission
    @GetMapping("/sysDictType/index")
    public String index() {
        return "system/sysDictType/index.html";
    }

    /**
     * 字典类型表单页面
     *
     * @author xuyuxiang
     * @date 2020/11/17 16:40
     */
    @GetMapping("/sysDictType/form")
    public String form() {
        return "system/sysDictType/form.html";
    }

    /**
     * 分页查询系统字典类型
     *
     * @author xuyuxiang，xuyuxiang
     * @date 2020/3/31 20:30
     */
    @Permission
    @ResponseBody
    @GetMapping("/sysDictType/page")
    @BusinessLog(title = "系统字典类型_分页查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public PageResult<SysDictType> page(SysDictTypeParam sysDictTypeParam) {
        return sysDictTypeService.page(sysDictTypeParam);
    }

    /**
     * 获取字典类型列表
     *
     * @author xuyuxiang，xuyuxiang
     * @date 2020/3/31 21:03
     */
    @Permission
    @ResponseBody
    @GetMapping("/sysDictType/list")
    @BusinessLog(title = "系统字典类型_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(SysDictTypeParam sysDictTypeParam) {
        return new SuccessResponseData(sysDictTypeService.list(sysDictTypeParam));
    }

    /**
     * 获取字典类型下所有字典，举例，返回格式为：[{code:"M",value:"男"},{code:"F",value:"女"}]
     *
     * @author xuyuxiang，xuyuxiang yubaoshan
     * @date 2020/3/31 21:18
     */
    @ResponseBody
    @GetMapping("/sysDictType/dropDown")
    @BusinessLog(title = "系统字典类型_下拉", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData dropDown(@Validated(SysDictTypeParam.dropDown.class) SysDictTypeParam sysDictTypeParam) {
        return new SuccessResponseData(sysDictTypeService.dropDown(sysDictTypeParam));
    }

    /**
     * 查看系统字典类型
     *
     * @author xuyuxiang，xuyuxiang
     * @date 2020/3/31 20:31
     */
    @Permission
    @ResponseBody
    @GetMapping("/sysDictType/detail")
    @BusinessLog(title = "系统字典类型_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(SysDictTypeParam.detail.class) SysDictTypeParam sysDictTypeParam) {
        return new SuccessResponseData(sysDictTypeService.detail(sysDictTypeParam));
    }

    /**
     * 添加系统字典类型
     *
     * @author xuyuxiang，xuyuxiang
     * @date 2020/3/31 20:30
     */
    @Permission
    @ResponseBody
    @PostMapping("/sysDictType/add")
    @BusinessLog(title = "系统字典类型_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(SysDictTypeParam.add.class) SysDictTypeParam sysDictTypeParam) {
        sysDictTypeService.add(sysDictTypeParam);
        return new SuccessResponseData();
    }

    /**
     * 删除系统字典类型
     *
     * @author xuyuxiang，xuyuxiang
     * @date 2020/3/31 20:30
     */
    @Permission
    @ResponseBody
    @PostMapping("/sysDictType/delete")
    @BusinessLog(title = "系统字典类型_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(SysDictTypeParam.delete.class) List<SysDictTypeParam> sysDictTypeParamList) {
        sysDictTypeService.delete(sysDictTypeParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统字典类型
     *
     * @author xuyuxiang，xuyuxiang
     * @date 2020/3/31 20:31
     */
    @Permission
    @ResponseBody
    @PostMapping("/sysDictType/edit")
    @BusinessLog(title = "系统字典类型_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(SysDictTypeParam.edit.class) SysDictTypeParam sysDictTypeParam) {
        sysDictTypeService.edit(sysDictTypeParam);
        return new SuccessResponseData();
    }

    /**
     * 修改状态
     *
     * @author yubaoshan
     * @date 2020/4/30 22:20
     */
    @Permission
    @ResponseBody
    @PostMapping("/sysDictType/changeStatus")
    @BusinessLog(title = "系统字典类型_修改状态", opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData changeStatus(@RequestBody @Validated(SysDictTypeParam.changeStatus.class) SysDictTypeParam sysDictTypeParam) {
        sysDictTypeService.changeStatus(sysDictTypeParam);
        return new SuccessResponseData();
    }

    /**
     * 系统字典类型与字典值构造的树
     *
     * @author yubaoshan
     * @date 2020/4/30 22:20
     */
    @ResponseBody
    @GetMapping("/sysDictType/tree")
    @BusinessLog(title = "系统字典类型_树", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData tree() {
        return new SuccessResponseData(sysDictTypeService.tree());
    }
}
