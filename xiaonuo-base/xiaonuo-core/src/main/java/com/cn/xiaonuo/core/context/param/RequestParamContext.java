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
package com.cn.xiaonuo.core.context.param;

import cn.hutool.core.lang.Dict;

/**
 * 临时保存http请求的参数
 * <p>
 * 可以保存@RequestBody的可以保存parameter方式传参的
 *
 * @author xuyuxiang
 * @date 2020/8/20
 */
public class RequestParamContext {

    private static final ThreadLocal<Dict> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 保存请求参数
     *
     * @author yubaoshan
     * @date 2020/6/21 20:17
     */
    public static void set(Dict requestParam) {
        CONTEXT_HOLDER.set(requestParam);
    }

    /**
     * 保存请求参数
     *
     * @author yubaoshan
     * @date 2020/6/21 20:17
     */
    public static void setObject(Object requestParam) {

        if (requestParam == null) {
            return;
        }

        if (requestParam instanceof Dict) {
            CONTEXT_HOLDER.set((Dict) requestParam);
        } else {
            CONTEXT_HOLDER.set(Dict.parse(requestParam));
        }
    }

    /**
     * 获取请求参数
     *
     * @author yubaoshan
     * @date 2020/6/21 20:17
     */
    public static Dict get() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清除请求参数
     *
     * @author yubaoshan
     * @date 2020/6/21 20:17
     */
    public static void clear() {
        CONTEXT_HOLDER.remove();
    }
}
