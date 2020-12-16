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
package com.cn.xiaonuo.sys.modular.app.service;

import cn.hutool.core.lang.Dict;
import com.cn.xiaonuo.core.pojo.page.PageResult;
import com.cn.xiaonuo.sys.modular.app.entity.SysApp;
import com.cn.xiaonuo.sys.modular.app.param.SysAppParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统应用service接口
 *
 * @author xuyuxiang
 * @date 2020/3/13 16:14
 */
public interface SysAppService extends IService<SysApp> {

    /**
     * 获取用户应用相关信息
     *
     * @param userId 用户id
     * @return 用户拥有的应用信息，格式：[{"code":"system","name":"系统应用","active":true}]
     * @author xuyuxiang
     * @date 2020/3/13 16:25
     */
    List<Dict> getLoginApps(Long userId);

    /**
     * 查询系统应用
     *
     * @param sysAppParam 查询参数
     * @return 查询分页结果
     * @author xuyuxiang
     * @date 2020/3/24 20:55
     */
    PageResult<SysApp> page(SysAppParam sysAppParam);

    /**
     * 添加系统应用
     *
     * @param sysAppParam 添加参数
     * @author xuyuxiang
     * @date 2020/3/25 14:57
     */
    void add(SysAppParam sysAppParam);

    /**
     * 删除系统应用
     *
     * @param sysAppParamList 删除参数
     * @author xuyuxiang
     * @date 2020/3/25 14:57
     */
    void delete(List<SysAppParam> sysAppParamList);

    /**
     * 编辑系统应用
     *
     * @param sysAppParam 编辑参数
     * @author xuyuxiang
     * @date 2020/3/25 14:58
     */
    void edit(SysAppParam sysAppParam);

    /**
     * 查看系统应用
     *
     * @param sysAppParam 查看参数
     * @return 系统应用
     * @author xuyuxiang
     * @date 2020/3/26 9:50
     */
    SysApp detail(SysAppParam sysAppParam);

    /**
     * 系统应用列表
     *
     * @param sysAppParam 查询参数
     * @return 系统应用列表
     * @author xuyuxiang
     * @date 2020/4/19 14:56
     */
    List<SysApp> list(SysAppParam sysAppParam);

    /**
     * 设为默认应用
     *
     * @param sysAppParam 设为默认应用参数
     * @author xuyuxiang
     * @date 2020/6/29 16:49
     */
    void setAsDefault(SysAppParam sysAppParam);
}
