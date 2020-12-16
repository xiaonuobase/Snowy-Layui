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
package com.cn.xiaonuo.sys.core.error;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.log.Log;
import com.cn.xiaonuo.core.consts.AopSortConstant;
import com.cn.xiaonuo.core.context.requestno.RequestNoContext;
import com.cn.xiaonuo.core.exception.AuthException;
import com.cn.xiaonuo.core.exception.DemoException;
import com.cn.xiaonuo.core.exception.PermissionException;
import com.cn.xiaonuo.core.exception.ServiceException;
import com.cn.xiaonuo.core.exception.enums.*;
import com.cn.xiaonuo.core.sms.modular.aliyun.exp.AliyunSmsException;
import com.cn.xiaonuo.core.sms.modular.tencent.exp.TencentSmsException;
import com.cn.xiaonuo.core.util.ResponseUtil;
import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 *
 * @author xuyuxiang
 * @date 2020/3/18 19:03
 */
@Order(AopSortConstant.GLOBAL_EXP_HANDLER_AOP)
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Log log = Log.get();

    /**
     * 腾讯云短信发送异常
     *
     * @author yubaoshan
     * @date 2020/6/7 18:03
     */
    @ResponseBody
    @ExceptionHandler(TencentSmsException.class)
    public Object aliyunSmsException(TencentSmsException e) {
        log.error(">>> 发送短信异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getErrorMessage());
        return ResponseUtil.handleError(ServerExceptionEnum.SERVER_ERROR.getCode(), e.getErrorMessage());
    }

    /**
     * 阿里云短信发送异常
     *
     * @author yubaoshan
     * @date 2020/6/7 18:03
     */
    @ResponseBody
    @ExceptionHandler(AliyunSmsException.class)
    public Object aliyunSmsException(AliyunSmsException e) {
        log.error(">>> 发送短信异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getErrorMessage());
        return ResponseUtil.handleError(ServerExceptionEnum.SERVER_ERROR.getCode(), e.getErrorMessage());
    }

    /**
     * 请求参数缺失异常
     *
     * @author yubaoshan
     * @date 2020/6/7 18:03
     */
    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Object missParamException(MissingServletRequestParameterException e) {
        log.error(">>> 请求参数异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getMessage());
        String parameterType = e.getParameterType();
        String parameterName = e.getParameterName();
        String message = StrUtil.format(">>> 缺少请求的参数{}，类型为{}", parameterName, parameterType);
        return ResponseUtil.handleError(ServerExceptionEnum.SERVER_ERROR.getCode(), message);
    }

    /**
     * 拦截参数格式传递异常
     *
     * @author xuyuxiang
     * @date 2020/4/2 15:32
     */
    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Object httpMessageNotReadable(HttpMessageNotReadableException e) {
        log.error(">>> 参数格式传递异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getMessage());
        return ResponseUtil.handleError(RequestTypeExceptionEnum.REQUEST_JSON_ERROR);
    }

    /**
     * 拦截不支持媒体类型异常
     *
     * @author xuyuxiang
     * @date 2020/4/2 15:38
     */
    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Object httpMediaTypeNotSupport(HttpMediaTypeNotSupportedException e) {
        log.error(">>> 参数格式传递异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getMessage());
        return ResponseUtil.handleError(RequestTypeExceptionEnum.REQUEST_TYPE_IS_JSON);
    }

    /**
     * 拦截请求方法的异常
     *
     * @author xuyuxiang
     * @date 2020/3/18 19:14
     */
    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object methodNotSupport(HttpServletRequest request) {
        if (ServletUtil.isPostMethod(request)) {
            log.error(">>> 请求方法异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), RequestMethodExceptionEnum.REQUEST_METHOD_IS_GET.getMessage());
            return ResponseUtil.handleError(RequestMethodExceptionEnum.REQUEST_METHOD_IS_GET);
        }
        if (ServletUtil.isGetMethod(request)) {
            log.error(">>> 请求方法异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), RequestMethodExceptionEnum.REQUEST_METHOD_IS_POST.getMessage());
            return ResponseUtil.handleError(RequestMethodExceptionEnum.REQUEST_METHOD_IS_POST);
        }
        return null;
    }

    /**
     * 拦截资源找不到的运行时异常
     *
     * @author xuyuxiang
     * @date 2020/4/2 15:38
     */
    @ResponseBody
    @ExceptionHandler(NoHandlerFoundException.class)
    public Object notFound(NoHandlerFoundException e) {
        log.error(">>> 资源不存在异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getMessage());
        return ResponseUtil.handleError(PermissionExceptionEnum.URL_NOT_EXIST);
    }

    /**
     * 拦截参数校验错误异常,JSON传参
     *
     * @author xuyuxiang
     * @date 2020/4/2 15:38
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object methodArgumentNotValidException(MethodArgumentNotValidException e) {
        String argNotValidMessage = ResponseUtil.getArgNotValidMessage(e.getBindingResult());
        log.error(">>> 参数校验错误异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), argNotValidMessage);
        return ResponseUtil.handleError(ParamExceptionEnum.PARAM_ERROR.getCode(), argNotValidMessage);
    }

    /**
     * 拦截参数校验错误异常
     *
     * @author xuyuxiang
     * @date 2020/3/18 19:41
     */
    @ResponseBody
    @ExceptionHandler(BindException.class)
    public Object paramError(BindException e) {
        String argNotValidMessage = ResponseUtil.getArgNotValidMessage(e.getBindingResult());
        log.error(">>> 参数校验错误异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), argNotValidMessage);
        return ResponseUtil.handleError(ParamExceptionEnum.PARAM_ERROR.getCode(), argNotValidMessage);
    }

    /**
     * 拦截认证失败异常
     *
     * @author xuyuxiang
     * @date 2020/3/18 19:41
     */
    @ResponseBody
    @ExceptionHandler(AuthException.class)
    public Object authFail(AuthException e) {
        log.error(">>> 认证异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getMessage());
        return ResponseUtil.handleError(e.getCode(), e.getErrorMessage());
    }

    /**
     * 拦截权限异常
     *
     * @author xuyuxiang
     * @date 2020/3/18 19:41
     */
    @ResponseBody
    @ExceptionHandler(PermissionException.class)
    public Object noPermission(PermissionException e) {
        log.error(">>> 权限异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getMessage());
        return ResponseUtil.handleError(e.getCode(), e.getErrorMessage());
    }

    /**
     * 拦截业务异常
     *
     * @author xuyuxiang
     * @date 2020/3/18 19:41
     */
    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public Object businessError(ServiceException e) {
        log.error(">>> 业务异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getMessage());
        return ResponseUtil.handleError(e.getCode(), e.getErrorMessage(), e);
    }

    /**
     * 拦截mybatis数据库操作的异常
     * <p>
     * 用在demo模式，拦截DemoException
     *
     * @author yubaoshan
     * @date 2020/5/5 15:19
     */
    @ResponseBody
    @ExceptionHandler(MyBatisSystemException.class)
    public Object persistenceException(MyBatisSystemException e) {
        log.error(">>> mybatis操作出现异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getMessage());
        Throwable cause = e.getCause();
        if (cause instanceof PersistenceException) {
            Throwable secondCause = cause.getCause();
            if (secondCause instanceof DemoException) {
                DemoException demoException = (DemoException) secondCause;
                return ResponseUtil.handleErrorWithoutWrite(demoException.getCode(), demoException.getErrorMessage(), e.getStackTrace()[0].toString());
            }
        }
        return ResponseUtil.handleErrorWithoutWrite(ServerExceptionEnum.SERVER_ERROR.getCode(), ServerExceptionEnum.SERVER_ERROR.getMessage(), e.getStackTrace()[0].toString());
    }

    /**
     * 拦截未知的运行时异常
     *
     * @author xuyuxiang
     * @date 2020/3/18 19:41
     */
    @ResponseBody
    @ExceptionHandler(Throwable.class)
    public Object serverError(Throwable e) {
        log.error(">>> 服务器运行异常，请求号为：{}", RequestNoContext.get());
        e.printStackTrace();
        return ResponseUtil.handleError(e);
    }
}
