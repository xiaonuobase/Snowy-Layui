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
package com.cn.xiaonuo.core.exception.enums;

import com.cn.xiaonuo.core.annotion.ExpEnumType;
import com.cn.xiaonuo.core.consts.ExpEnumConstant;
import com.cn.xiaonuo.core.exception.enums.abs.AbstractBaseExceptionEnum;
import com.cn.xiaonuo.core.factory.ExpEnumCodeFactory;
import com.cn.xiaonuo.core.annotion.ExpEnumType;
import com.cn.xiaonuo.core.consts.ExpEnumConstant;
import com.cn.xiaonuo.core.factory.ExpEnumCodeFactory;

/**
 * 授权和鉴权异常的枚举
 * <p>
 * 认证和鉴权的区别：
 * <p>
 * 认证可以证明你能登录系统，认证的过程是校验token的过程
 * 鉴权可以证明你有系统的哪些权限，鉴权的过程是校验角色是否包含某些接口的权限
 *
 * @author xuyuxiang
 * @date 2020/3/12 10:14
 */
@ExpEnumType(module = ExpEnumConstant.XIAONUO_CORE_MODULE_EXP_CODE, kind = ExpEnumConstant.PERMISSION_EXCEPTION_ENUM)
public enum PermissionExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 资源路径不存在
     */
    URL_NOT_EXIST(1, "资源路径不存在，请检查请求地址"),

    /**
     * 没有权限访问资源
     */
    NO_PERMISSION(2, "没有权限访问资源，请联系管理员"),

    /**
     * 没有权限操作该数据
     */
    NO_PERMISSION_OPERATE(3, "没有权限操作该数据，请联系管理员");

    private final Integer code;

    private final String message;

    PermissionExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return ExpEnumCodeFactory.getExpEnumCode(this.getClass(), code);
    }

    @Override
    public String getMessage() {
        return message;
    }

}
