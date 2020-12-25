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
package com.cn.xiaonuo.generate.modular.controller;


import com.cn.xiaonuo.core.annotion.BusinessLog;
import com.cn.xiaonuo.core.annotion.Permission;
import com.cn.xiaonuo.core.context.constant.ConstantContextHolder;
import com.cn.xiaonuo.core.enums.LogAnnotionOpTypeEnum;
import com.cn.xiaonuo.core.exception.DemoException;
import com.cn.xiaonuo.core.pojo.page.PageResult;
import com.cn.xiaonuo.core.pojo.response.ResponseData;
import com.cn.xiaonuo.core.pojo.response.SuccessResponseData;
import com.cn.xiaonuo.generate.modular.entity.CodeGenerate;
import com.cn.xiaonuo.generate.modular.param.CodeGenerateParam;
import com.cn.xiaonuo.generate.modular.service.CodeGenerateService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 代码生成器
 *
 * @auther yubaoshan
 * @date 12/15/20 11:20 PM
 */
@Controller
public class CodeGenerateController {

    private static String PATH_PREFIX = "generate/";

    @Resource
    private CodeGenerateService codeGenerateService;

    /**
     * 代码生成器页面
     *
     * @auther yubaoshan
     * @date 12/15/20 11:20 PM
     */
    @Permission
    @GetMapping("/codeGenerate/index")
    public String index() {
        return PATH_PREFIX + "index.html";
    }

    /**
     * 代码生成器页面
     *
     * @auther yubaoshan
     * @date 12/18/20 1:20 AM
     */
    @GetMapping("/codeGenerate/form")
    public String form() {
        return PATH_PREFIX + "form.html";
    }

    /**
     * 代码生成基础数据
     *
     * @author yubaoshan
     * @date 2020年12月16日20:58:48
     */
    @Permission
    @ResponseBody
    @GetMapping("/codeGenerate/page")
    @BusinessLog(title = "代码生成配置_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public PageResult<CodeGenerate> page(CodeGenerateParam codeGenerateParam) {
        return codeGenerateService.page(codeGenerateParam);
    }

    /**
     * 代码生成基础配置保存
     *
     * @auther yubaoshan
     * @date 12/15/20 11:20 PM
     */
    @Permission
    @ResponseBody
    @PostMapping("/codeGenerate/add")
    @BusinessLog(title = "代码生成配置_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(CodeGenerateParam.add.class) CodeGenerateParam codeGenerateParam) {
        this.codeGenerateService.add(codeGenerateParam);
        return new SuccessResponseData();
    }

    /**
     * 代码生成基础配置编辑
     *
     * @auther yubaoshan
     * @date 2020年12月16日20:56:19
     */
    @Permission
    @ResponseBody
    @PostMapping("/codeGenerate/edit")
    @BusinessLog(title = "代码生成配置_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(CodeGenerateParam.add.class) CodeGenerateParam codeGenerateParam) {
        codeGenerateService.edit(codeGenerateParam);
        return new SuccessResponseData();
    }

    /**
     * 删除代码生成基础配置
     *
     * @author yubaoshan
     * @date 2020年12月16日22:13:32
     */
    @Permission
    @ResponseBody
    @PostMapping("/codeGenerate/delete")
    @BusinessLog(title = "代码生成配置_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(CodeGenerateParam.delete.class) List<CodeGenerateParam> codeGenerateParamList) {
        codeGenerateService.delete(codeGenerateParamList);
        return new SuccessResponseData();
    }

    /**
     * 查询当前数据库用户下的所有表
     *
     * @author yubaoshan
     * @date 2020-12-16 01:55:48
     */
    @Permission
    @ResponseBody
    @GetMapping("/codeGenerate/InformationList")
    @BusinessLog(title = "数据库表列表_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData InformationList() {
        return ResponseData.success(codeGenerateService.InformationTableList());
    }

    /**
     * 代码生成基础配置生成
     *
     * @auther yubaoshan
     * @date 12/15/20 11:20 PM
     */
    @Permission
    @ResponseBody
    @PostMapping("/codeGenerate/runLocal")
    @BusinessLog(title = "代码生成_本地项目", opType = LogAnnotionOpTypeEnum.OTHER)
    public ResponseData runLocal(@RequestBody @Validated(CodeGenerateParam.detail.class) CodeGenerateParam codeGenerateParam) {
        // 演示环境开启，则不允许操作
        if (ConstantContextHolder.getDemoEnvFlag()) {
            throw new DemoException();
        }
        this.codeGenerateService.runLocal(codeGenerateParam);
        return new SuccessResponseData();
    }

    /**
     * 代码生成基础配置生成
     *
     * @auther yubaoshan
     * @date 12/15/20 11:20 PM
     */
    @Permission
    @GetMapping("/codeGenerate/runDown")
    @BusinessLog(title = "代码生成_下载方式", opType = LogAnnotionOpTypeEnum.OTHER)
    public void runDown(@Validated(CodeGenerateParam.detail.class) CodeGenerateParam codeGenerateParam, HttpServletResponse response) {
        // 演示环境开启，则不允许操作
        if (ConstantContextHolder.getDemoEnvFlag()) {
            throw new DemoException();
        }
        this.codeGenerateService.runDown(codeGenerateParam, response);
    }
}
