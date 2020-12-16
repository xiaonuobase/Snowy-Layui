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
package com.cn.xiaonuo.sys.modular.role.service;

import com.cn.xiaonuo.sys.modular.role.entity.SysRoleDataScope;
import com.cn.xiaonuo.sys.modular.role.param.SysRoleParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统角色数据范围service接口
 *
 * @author xuyuxiang
 * @date 2020/3/13 15:51
 */
public interface SysRoleDataScopeService extends IService<SysRoleDataScope> {

    /**
     * 授权数据
     *
     * @param sysRoleParam 授权参数
     * @author xuyuxiang
     * @date 2020/3/28 16:36
     */
    void grantDataScope(SysRoleParam sysRoleParam);

    /**
     * 根据角色id获取角色数据范围集合
     *
     * @param roleIdList 角色id集合
     * @return 数据范围id集合
     * @author xuyuxiang
     * @date 2020/4/5 17:44
     */
    List<Long> getRoleDataScopeIdList(List<Long> roleIdList);

    /**
     * 根据机构id集合删除对应的角色-数据范围关联信息
     *
     * @param orgIdList 机构id集合
     * @author xuyuxiang
     * @date 2020/6/28 14:14
     */
    void deleteRoleDataScopeListByOrgIdList(List<Long> orgIdList);

    /**
     * 根据角色id删除对应的角色-数据范围关联信息
     *
     * @param roleId 角色id
     * @author xuyuxiang
     * @date 2020/6/28 14:47
     */
    void deleteRoleDataScopeListByRoleId(Long roleId);
}
