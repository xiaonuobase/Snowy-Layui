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
package com.cn.xiaonuo.sys.modular.auth.factory;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.cn.xiaonuo.core.consts.CommonConstant;
import com.cn.xiaonuo.core.context.constant.ConstantContextHolder;
import com.cn.xiaonuo.core.exception.ServiceException;
import com.cn.xiaonuo.core.exception.enums.ServerExceptionEnum;
import com.cn.xiaonuo.core.pojo.login.LoginEmpInfo;
import com.cn.xiaonuo.core.pojo.login.SysLoginUser;
import com.cn.xiaonuo.core.pojo.node.LoginMenuTreeNode;
import com.cn.xiaonuo.core.tenant.consts.TenantConstants;
import com.cn.xiaonuo.core.tenant.context.TenantCodeHolder;
import com.cn.xiaonuo.core.tenant.context.TenantDbNameHolder;
import com.cn.xiaonuo.core.util.HttpServletUtil;
import com.cn.xiaonuo.core.util.IpAddressUtil;
import com.cn.xiaonuo.core.util.UaUtil;
import com.cn.xiaonuo.sys.modular.app.service.SysAppService;
import com.cn.xiaonuo.sys.modular.emp.service.SysEmpService;
import com.cn.xiaonuo.sys.modular.menu.service.SysMenuService;
import com.cn.xiaonuo.sys.modular.role.service.SysRoleService;
import com.cn.xiaonuo.sys.modular.user.service.SysUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 登录用户工厂类
 *
 * @author xuyuxiang
 * @date 2020/3/13 14:58
 */
public class LoginUserFactory {

    private static final SysUserService sysUserService = SpringUtil.getBean(SysUserService.class);

    private static final SysEmpService sysEmpService = SpringUtil.getBean(SysEmpService.class);

    private static final SysAppService sysAppService = SpringUtil.getBean(SysAppService.class);

    private static final SysMenuService sysMenuService = SpringUtil.getBean(SysMenuService.class);

    private static final SysRoleService sysRoleService = SpringUtil.getBean(SysRoleService.class);

    /**
     * 填充登录用户相关信息
     *
     * @author xuyuxiang yubaoshan
     * @date 2020/3/13 15:01
     */
    public static void fillLoginUserInfo(SysLoginUser sysLoginUser) {
        HttpServletRequest request = HttpServletUtil.getRequest();
        if (ObjectUtil.isNotNull(request)) {
            sysLoginUser.setLastLoginIp(IpAddressUtil.getIp(request));
            sysLoginUser.setLastLoginTime(DateTime.now().toString());
            sysLoginUser.setLastLoginAddress(IpAddressUtil.getAddress(request));
            sysLoginUser.setLastLoginBrowser(UaUtil.getBrowser(request));
            sysLoginUser.setLastLoginOs(UaUtil.getOs(request));
            Long userId = sysLoginUser.getId();

            // 员工信息
            LoginEmpInfo loginEmpInfo = sysEmpService.getLoginEmpInfo(userId);
            sysLoginUser.setLoginEmpInfo(loginEmpInfo);

            // 角色信息
            List<Dict> roles = sysRoleService.getLoginRoles(userId);
            sysLoginUser.setRoles(roles);

            // 权限信息
            List<String> permissions = sysMenuService.getLoginPermissions(userId);
            sysLoginUser.setPermissions(permissions);

            // 数据范围信息
            List<Long> dataScopes = sysUserService.getUserDataScopeIdList(userId, loginEmpInfo.getOrgId());
            sysLoginUser.setDataScopes(dataScopes);

            // 具备应用信息（多系统，默认激活一个，可根据系统切换菜单）,返回的结果中第一个为激活的系统
            List<Dict> apps = sysAppService.getLoginApps(userId);
            sysLoginUser.setApps(apps);

            // 如果根本没有应用信息，则没有菜单信息
            if (ObjectUtil.isEmpty(apps)) {
                sysLoginUser.setMenus(CollectionUtil.newArrayList());
            } else {
                //菜单信息，根据人获取，用于登录后展示菜单树
                List<Dict> resultList = CollectionUtil.newArrayList();
                apps.forEach(dict -> {
                    String code = dict.getStr(CommonConstant.CODE);
                    List<LoginMenuTreeNode> loginMenus = sysMenuService.getLoginMenus(userId, code);
                    Dict tempDict = Dict.create();
                    tempDict.put(CommonConstant.CODE, code);
                    tempDict.put(CommonConstant.VALUE, loginMenus);
                    resultList.add(tempDict);
                });
                sysLoginUser.setMenus(resultList);
            }

            //如果开启了多租户功能，则设置当前登录用户的租户标识
            if (ConstantContextHolder.getTenantOpenFlag()) {
                String tenantCode = TenantCodeHolder.get();
                String dataBaseName = TenantDbNameHolder.get();
                if (StrUtil.isNotBlank(tenantCode) && StrUtil.isNotBlank(dataBaseName)) {
                    Dict tenantInfo = Dict.create();
                    tenantInfo.set(TenantConstants.TENANT_CODE, tenantCode);
                    tenantInfo.set(TenantConstants.TENANT_DB_NAME, dataBaseName);
                    sysLoginUser.setTenants(tenantInfo);
                }
                //注意，这里remove不代表所有情况，在aop remove
                TenantCodeHolder.remove();
                TenantDbNameHolder.remove();
            }

        } else {
            throw new ServiceException(ServerExceptionEnum.REQUEST_EMPTY);
        }
    }
}
