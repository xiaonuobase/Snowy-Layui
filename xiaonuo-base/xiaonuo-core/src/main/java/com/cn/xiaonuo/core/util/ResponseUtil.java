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
package com.cn.xiaonuo.core.util;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import cn.hutool.log.Log;
import com.cn.xiaonuo.core.consts.CommonConstant;
import com.cn.xiaonuo.core.consts.SymbolConstant;
import com.cn.xiaonuo.core.context.requestno.RequestNoContext;
import com.cn.xiaonuo.core.exception.enums.AuthExceptionEnum;
import com.cn.xiaonuo.core.exception.enums.PermissionExceptionEnum;
import com.cn.xiaonuo.core.exception.enums.RequestMethodExceptionEnum;
import com.cn.xiaonuo.core.exception.enums.ServerExceptionEnum;
import com.cn.xiaonuo.core.exception.enums.abs.AbstractBaseExceptionEnum;
import com.cn.xiaonuo.core.pojo.response.ErrorResponseData;
import com.alibaba.fastjson.JSON;
import com.cn.xiaonuo.core.pojo.response.ErrorResponseData;
import lombok.SneakyThrows;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 响应工具类
 *
 * @author xuyuxiang
 * @date 2020/3/20 11:17
 */
public class ResponseUtil {

    private static final Log log = Log.get();

    /**
     * 渲染异常json
     *
     * @author yubaoshan
     * @date 2020/5/5 16:22
     */
    public static Object handleError(Integer code, String message) {
        return handleErrorWithoutWrite(code, message, "");
    }

    /**
     * 渲染异常json
     *
     * @author yubaoshan
     * @date 2020/5/5 16:22
     */
    public static Object handleError(AbstractBaseExceptionEnum baseExceptionEnum) {
        return handleErrorWithoutWrite(baseExceptionEnum.getCode(), baseExceptionEnum.getMessage(), "");
    }

    /**
     * 渲染异常json
     *
     * @author yubaoshan
     * @date 2020/5/5 16:22
     */
    public static Object handleError(Throwable throwable) {
        return handleError(((AbstractBaseExceptionEnum) ServerExceptionEnum.SERVER_ERROR).getCode(),
                ((AbstractBaseExceptionEnum) ServerExceptionEnum.SERVER_ERROR).getMessage(), throwable);
    }


    /**
     * 渲染异常json
     * <p>
     * 根据异常枚举和Throwable异常响应，异常信息响应堆栈第一行
     *
     * @author yubaoshan
     * @date 2020/5/5 16:22
     */
    @SneakyThrows
    public static Object handleError(Integer code, String message, Throwable e) {
        if (ObjectUtil.isNotNull(e)) {
            //获取所有堆栈信息
            StackTraceElement[] stackTraceElements = e.getStackTrace();

            //默认的异常类全路径为第一条异常堆栈信息的
            String exceptionClassTotalName = stackTraceElements[0].toString();

            //遍历所有堆栈信息，找到com.cn.xiaonuo开头的第一条异常信息
            for (StackTraceElement stackTraceElement : stackTraceElements) {
                if (stackTraceElement.toString().contains(CommonConstant.DEFAULT_PACKAGE_NAME)) {
                    exceptionClassTotalName = stackTraceElement.toString();
                    break;
                }
            }
            return handleErrorWithoutWrite(code, message, exceptionClassTotalName);
        } else {
            return handleErrorWithoutWrite(code, message, "");
        }
    }

    /**
     * 响应异常，向前端返回ErrorResponseData的json数据，用于全局异常处理器
     *
     * @author xuyuxiang
     * @date 2020/3/20 11:31
     */
    public static Object handleErrorWithoutWrite(Integer code, String message, String exceptionClazz) {
        HttpServletRequest request = HttpServletUtil.getRequest();
        boolean ajaxRequest = isAjaxRequest(request);
        if(ajaxRequest) {
            ErrorResponseData errorResponseData = new ErrorResponseData(code, message);
            errorResponseData.setExceptionClazz(exceptionClazz);
            return errorResponseData;
        } else {
            if(code.equals(PermissionExceptionEnum.NO_PERMISSION.getCode()) ||
                    code.equals(PermissionExceptionEnum.NO_PERMISSION_OPERATE.getCode())) {
                //跳转到全局错误处理，没有权限访问页面，403页面
                return new ModelAndView("redirect:/403");
            } else if(code.equals(PermissionExceptionEnum.URL_NOT_EXIST.getCode())) {
                //跳转到全局错误处理，路径不存在，404页面
                return new ModelAndView("redirect:/404");
            } else {
                //跳转到全局错误处理，服务器异常，500页面
                return new ModelAndView("redirect:/500");
            }
        }
    }

    /**
     * 响应异常，直接向前端写response，用于异常处理器捕获不到时手动抛出
     *
     * @author xuyuxiang
     * @date 2020/3/20 11:18
     */
    @SneakyThrows
    public static void handleErrorWithWrite(Integer code,
                                            String message,
                                            String exceptionClazz) {
        HttpServletRequest request = HttpServletUtil.getRequest();
        HttpServletResponse response = HttpServletUtil.getResponse();
        boolean ajaxRequest = isAjaxRequest(request);
        if(ajaxRequest) {
            try {
                response.setCharacterEncoding(CharsetUtil.UTF_8);
                response.setContentType(ContentType.JSON.toString());
                ErrorResponseData errorResponseData = new ErrorResponseData(code, message);
                errorResponseData.setExceptionClazz(exceptionClazz);
                String errorResponseJsonData = JSON.toJSONString(errorResponseData);
                response.getWriter().write(errorResponseJsonData);
            } catch (IOException e) {
                log.error(">>> 响应异常，请求号为：{}", RequestNoContext.get());
                e.printStackTrace();
            }
        } else {
            if(code.equals(PermissionExceptionEnum.NO_PERMISSION.getCode()) ||
                    code.equals(PermissionExceptionEnum.NO_PERMISSION_OPERATE.getCode())) {
                //跳转到全局错误处理，没有权限访问页面，403页面
                response.sendRedirect(request.getContextPath() + "/global/sessionError?errorCode=" +  PermissionExceptionEnum.NO_PERMISSION.getCode());
            } else if(code.equals(PermissionExceptionEnum.URL_NOT_EXIST.getCode())) {
                //跳转到全局错误处理，路径不存在，404页面
                response.sendRedirect(request.getContextPath() + "/global/sessionError?errorCode=" +  PermissionExceptionEnum.URL_NOT_EXIST.getCode());
            } else if(code.equals(AuthExceptionEnum.REQUEST_TOKEN_EMPTY.getCode())) {
                //跳转到全局错误处理，登录页，请先登录
                response.sendRedirect(request.getContextPath() + "/global/sessionError?errorCode=" +  AuthExceptionEnum.NOT_LOGIN.getCode());
            } else {
                //跳转到全局错误处理，登录过期需要登录
                response.sendRedirect(request.getContextPath() + "/global/sessionError");
            }
        }
    }

    /**
     * 获取请求参数不正确的提示信息
     * <p>
     * 多个信息，拼接成用逗号分隔的形式
     *
     * @author yubaoshan
     * @date 2020/5/5 16:50
     */
    public static String getArgNotValidMessage(BindingResult bindingResult) {
        if (bindingResult == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();

        //多个错误用逗号分隔
        List<ObjectError> allErrorInfos = bindingResult.getAllErrors();
        for (ObjectError error : allErrorInfos) {
            stringBuilder.append(SymbolConstant.COMMA).append(error.getDefaultMessage());
        }

        //最终把首部的逗号去掉
        return StrUtil.removePrefix(stringBuilder.toString(), SymbolConstant.COMMA);
    }

    /**
     * 判断request是否是Ajax请求
     *
     * @author xuyuxiang
     * @date 2020/11/17 11:12
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return !ServletUtil.isGetMethod(request) ||
                request.getHeader(CommonConstant.ACCEPT).contains(ContentType.JSON.getValue());
    }
}
