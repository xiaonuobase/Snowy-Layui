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
package com.cn.xiaonuo.sys.modular.notice.mapper;

import com.cn.xiaonuo.sys.modular.notice.entity.SysNotice;
import com.cn.xiaonuo.sys.modular.notice.result.SysNoticeReceiveResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.xiaonuo.sys.modular.notice.entity.SysNotice;
import com.cn.xiaonuo.sys.modular.notice.result.SysNoticeReceiveResult;
import org.apache.ibatis.annotations.Param;

/**
 * 系统通知公告mapper接口
 *
 * @author xuyuxiang
 * @date 2020/6/28 17:18
 */
public interface SysNoticeMapper extends BaseMapper<SysNotice> {

    /**
     * 查询当前登陆用户已收公告
     *
     * @param page         分页参数
     * @param queryWrapper 查询参数
     * @return 查询分页结果
     * @author xuyuxiang
     * @date 2020/6/29 14:32
     */
    Page<SysNoticeReceiveResult> page(@Param("page") Page page, @Param("ew") QueryWrapper queryWrapper);
}
