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
package com.cn.xiaonuo.sys.core.cache;

import cn.hutool.cache.impl.TimedCache;
import com.cn.xiaonuo.core.pojo.login.SysLoginUser;
import com.cn.xiaonuo.sys.core.cache.base.AbstractMemoryCacheOperator;
import com.cn.xiaonuo.core.pojo.login.SysLoginUser;

/**
 * 登录用户的缓存，存储了当前登录的用户
 * <p>
 * 一般用于在线用户的查看和过滤器检测用户是否登录
 * <p>
 * key为用户的唯一id，value为LoginUser对象
 *
 * @author yubaoshan
 * @date 2020/7/9 11:02
 */
public class UserCache extends AbstractMemoryCacheOperator<SysLoginUser> {

    /**
     * 登录用户缓存前缀
     */
    public static final String LOGIN_USER_CACHE_PREFIX = "LOGIN_USER_";

    public UserCache(TimedCache<String, SysLoginUser> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return LOGIN_USER_CACHE_PREFIX;
    }
}
