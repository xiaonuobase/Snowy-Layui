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
package com.cn.xiaonuo.sys.modular.timer.param;

import com.cn.xiaonuo.core.pojo.base.param.BaseParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 定时任务
 *
 * @author yubaoshan
 * @date 2020/6/30 18:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysTimersParam extends BaseParam {

    /**
     * 定时器id
     */
    @NotNull(message = "主键id不能为空，请检查id字段", groups = {edit.class, detail.class, delete.class, start.class, stop.class})
    private Long id;

    /**
     * 任务名称
     */
    @NotBlank(message = "任务名称不能为空，请检查timerName字段", groups = {add.class, edit.class})
    private String timerName;

    /**
     * 执行任务的class的类名（实现了TimerTaskRunner接口的类的全称）
     */
    @NotBlank(message = "任务的class的类名不能为空，请检查actionClass字段", groups = {add.class, edit.class})
    private String actionClass;

    /**
     * 定时任务表达式
     */
    @NotBlank(message = "定时任务表达式不能为空，请检查cron字段", groups = {add.class, edit.class})
    private String cron;

    /**
     * 状态（字典 1运行  2停止）
     */
    private Integer jobStatus;

    /**
     * 备注信息
     */
    private String remark;

}
