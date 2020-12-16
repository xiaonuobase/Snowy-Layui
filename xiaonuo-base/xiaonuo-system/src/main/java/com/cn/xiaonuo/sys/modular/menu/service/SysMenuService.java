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
package com.cn.xiaonuo.sys.modular.menu.service;

import com.cn.xiaonuo.core.pojo.node.CommonBaseTreeNode;
import com.cn.xiaonuo.core.pojo.node.LoginMenuTreeNode;
import com.cn.xiaonuo.core.pojo.page.PageResult;
import com.cn.xiaonuo.sys.modular.menu.entity.SysMenu;
import com.cn.xiaonuo.sys.modular.menu.node.MenuBaseTreeNode;
import com.cn.xiaonuo.sys.modular.menu.param.SysMenuParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统菜单service接口
 *
 * @author xuyuxiang
 * @date 2020/3/13 16:05
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获取用户权限相关信息
     *
     * @param userId 用户id
     * @return 权限集合
     * @author xuyuxiang
     * @date 2020/3/13 16:26
     */
    List<String> getLoginPermissions(Long userId);

    /**
     * 获取用户菜单相关信息，前端使用
     *
     * @param userId  用户id
     * @param appCode 应用编码
     * @return 菜单信息结果集
     * @author yubaoshan
     * @date 2020/4/17 17:48
     */
    List<LoginMenuTreeNode> getLoginMenus(Long userId, String appCode);

    /**
     * 获取用户菜单所属的应用编码集合
     *
     * @param userId 用户id
     * @return 用户菜单所属的应用编码集合
     * @author xuyuxiang
     * @date 2020/3/21 11:01
     */
    List<String> getUserMenuAppCodeList(Long userId);

    /**
     * 系统菜单列表（树表）
     *
     * @return 菜单树表列表
     * @author xuyuxiang
     * @date 2020/3/26 10:19
     */
    PageResult<SysMenu> page();

    /**
     * 添加系统菜单
     *
     * @param sysMenuParam 添加参数
     * @author xuyuxiang
     * @date 2020/3/27 9:03
     */
    void add(SysMenuParam sysMenuParam);

    /**
     * 删除系统菜单
     *
     * @param sysMenuParamList 删除参数
     * @author xuyuxiang
     * @date 2020/3/27 9:03
     */
    void delete(List<SysMenuParam> sysMenuParamList);

    /**
     * 编辑系统菜单
     *
     * @param sysMenuParam 编辑参数
     * @author xuyuxiang
     * @date 2020/3/27 9:03
     */
    void edit(SysMenuParam sysMenuParam);

    /**
     * 查看系统菜单
     *
     * @param sysMenuParam 查看参数
     * @return 系统菜单
     * @author xuyuxiang
     * @date 2020/3/27 9:03
     */
    SysMenu detail(SysMenuParam sysMenuParam);

    /**
     * 获取系统菜单树，用于新增，编辑时选择上级节点
     *
     * @param sysMenuParam 查询参数
     * @return 菜单树列表
     * @author xuyuxiang
     * @date 2020/3/27 15:56
     */
    List<MenuBaseTreeNode> tree(SysMenuParam sysMenuParam);

    /**
     * 获取系统菜单树，用于给角色授权时选择
     *
     * @param sysMenuParam 查询参数
     * @return 菜单树列表
     * @author xuyuxiang
     * @date 2020/4/5 15:01
     */
    List<MenuBaseTreeNode> treeForGrant(SysMenuParam sysMenuParam);

    /**
     * 根据应用编码判断该机构下是否有状态为正常的菜单
     *
     * @param appCode 应用编码
     * @return 该应用下是否有正常菜单，true是，false否
     * @author xuyuxiang
     * @date 2020/6/28 12:14
     */
    boolean hasMenu(String appCode);
}
