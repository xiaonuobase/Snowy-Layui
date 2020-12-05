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
package com.cn.xiaonuo.core.context.constant;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import com.cn.xiaonuo.core.consts.CommonConstant;
import com.cn.xiaonuo.core.consts.SymbolConstant;
import com.cn.xiaonuo.core.exception.ServiceException;
import com.cn.xiaonuo.core.pojo.email.EmailConfigs;
import com.cn.xiaonuo.core.pojo.oauth.OauthConfigs;
import com.cn.xiaonuo.core.pojo.sms.AliyunSmsConfigs;
import com.cn.xiaonuo.core.pojo.sms.TencentSmsConfigs;

import java.util.List;

import static com.cn.xiaonuo.core.exception.enums.ServerExceptionEnum.CONSTANT_EMPTY;

/**
 * 系统参数配置获取
 *
 * @author xuyuxiang
 * @date 2020/4/14 15:34
 */
public class ConstantContextHolder {

    private static final Log log = Log.get();

    /**
     * 获取租户功能是否开启
     *
     * @author xuyuxiang
     * @date 2020/9/3
     */
    public static Boolean getTenantOpenFlag() {
        return getSysConfigWithDefault("XIAONUO_TENANT_OPEN", Boolean.class, false);
    }

    /**
     * 获取验证码功能是否开启
     *
     * @author xuyuxiang
     * @date 2020/9/3
     */
    public static Boolean getCaptchaFlag() {
        return getSysConfigWithDefault("XIAONUO_CAPTCHA_OPEN", Boolean.class, false);
    }

    /**
     * 获取druid监控的密码
     *
     * @author yubaoshan
     * @date 2020/7/8 9:53
     */
    public static String getDruidMonitorPassword() {
        return getSysConfigWithDefault("XIAONUO_DRUID_PASSWORD", String.class, RandomUtil.randomString(10));
    }

    /**
     * 获取druid的账号
     *
     * @author yubaoshan
     * @date 2020/7/8 9:53
     */
    public static String getDruidMonitorUsername() {
        return getSysConfigWithDefault("XIAONUO_DRUID_USERNAME", String.class, RandomUtil.randomString(10));
    }

    /**
     * 获取放开xss过滤的接口
     *
     * @author yubaoshan
     * @date 2020/6/20 22:13
     */
    public static List<String> getUnXssFilterUrl() {
        String xiaoNuoUnXssFilterUrl = getSysConfigWithDefault("XIAONUO_UN_XSS_FILTER_URL", String.class, null);
        if (ObjectUtil.isEmpty(xiaoNuoUnXssFilterUrl)) {
            return CollectionUtil.newArrayList();
        } else {
            return CollectionUtil.toList(xiaoNuoUnXssFilterUrl.split(SymbolConstant.COMMA));
        }
    }

    /**
     * 获取演示环境开关是否开启，默认为false
     *
     * @author yubaoshan
     * @date 2020/6/20 22:13
     */
    public static Boolean getDemoEnvFlag() {
        return getSysConfigWithDefault("XIAONUO_DEMO_ENV_FLAG", Boolean.class, false);
    }

    /**
     * 邮件的配置
     *
     * @author yubaoshan
     * @date 2020/6/19 18:08
     */
    public static EmailConfigs getEmailConfigs() {
        String host = getSysConfig("XIAONUO_EMAIL_HOST", String.class, true);
        String username = getSysConfig("XIAONUO_EMAIL_USERNAME", String.class, true);
        String password = getSysConfig("XIAONUO_EMAIL_PASSWORD", String.class, true);
        Integer port = getSysConfig("XIAONUO_EMAIL_PORT", Integer.class, true);
        String from = getSysConfig("XIAONUO_EMAIL_FROM", String.class, true);
        Boolean ssl = getSysConfig("XIAONUO_EMAIL_SSL", Boolean.class, true);

        EmailConfigs emailConfigs = new EmailConfigs();
        emailConfigs.setHost(host);
        emailConfigs.setUser(username);
        emailConfigs.setPass(password);
        emailConfigs.setPort(port);
        emailConfigs.setFrom(from);
        emailConfigs.setSslEnable(ssl);
        return emailConfigs;
    }

    /**
     * 获取腾讯云短信的配置
     *
     * @author yubaoshan
     * @date 2020/6/19 18:08
     */
    public static TencentSmsConfigs getTencentSmsConfigs() {
        String xiaoNuoTencentSmsSecretId = getSysConfig("XIAONUO_TENCENT_SMS_SECRET_ID", String.class, true);
        String xiaoNuoTencentSmsSecretKey = getSysConfig("XIAONUO_TENCENT_SMS_SECRET_KEY", String.class, true);
        String xiaoNuoTencentSmsSdkAppId = getSysConfig("XIAONUO_TENCENT_SMS_SDK_APP_ID", String.class, true);
        String xiaoNuoTencentSmsSign = getSysConfig("XIAONUO_TENCENT_SMS_SIGN", String.class, true);

        TencentSmsConfigs tencentSmsConfigs = new TencentSmsConfigs();
        tencentSmsConfigs.setSecretId(xiaoNuoTencentSmsSecretId);
        tencentSmsConfigs.setSecretKey(xiaoNuoTencentSmsSecretKey);
        tencentSmsConfigs.setSdkAppId(xiaoNuoTencentSmsSdkAppId);
        tencentSmsConfigs.setSign(xiaoNuoTencentSmsSign);
        return tencentSmsConfigs;
    }

    /**
     * 获取阿里云短信的配置
     *
     * @author yubaoshan
     * @date 2020/6/19 18:08
     */
    public static AliyunSmsConfigs getAliyunSmsConfigs() {
            String xiaoNuoSmsAccesskeyId = getSysConfig("XIAONUO_ALIYUN_SMS_ACCESSKEY_ID", String.class, true);
        String xiaoNuoSmsAccesskeySecret = getSysConfig("XIAONUO_ALIYUN_SMS_ACCESSKEY_SECRET", String.class, true);
        String xiaoNuoSmsSignName = getSysConfig("XIAONUO_ALIYUN_SMS_SIGN_NAME", String.class, true);
        String xiaoNuoSmsLoginTemplateCode = getSysConfig("XIAONUO_ALIYUN_SMS_LOGIN_TEMPLATE_CODE", String.class, true);
        String xiaoNuoSmsInvalidateMinutes = getSysConfig("XIAONUO_ALIYUN_SMS_INVALIDATE_MINUTES", String.class, true);

        AliyunSmsConfigs aliyunSmsProperties = new AliyunSmsConfigs();
        aliyunSmsProperties.setAccessKeyId(xiaoNuoSmsAccesskeyId);
        aliyunSmsProperties.setAccessKeySecret(xiaoNuoSmsAccesskeySecret);
        aliyunSmsProperties.setSignName(xiaoNuoSmsSignName);
        aliyunSmsProperties.setLoginTemplateCode(xiaoNuoSmsLoginTemplateCode);
        aliyunSmsProperties.setInvalidateMinutes(Convert.toInt(xiaoNuoSmsInvalidateMinutes));
        return aliyunSmsProperties;
    }

    /**
     * 获取jwt密钥，默认是32位随机字符串
     *
     * @author yubaoshan
     * @date 2020/6/19 18:08
     */
    public static String getJwtSecret() {
        return getSysConfigWithDefault("XIAONUO_JWT_SECRET", String.class, RandomUtil.randomString(32));
    }

    /**
     * 获取默认密码
     *
     * @author yubaoshan
     * @date 2020/6/19 18:08
     */
    public static String getDefaultPassWord() {
        return getSysConfigWithDefault("XIAONUO_DEFAULT_PASSWORD", String.class, CommonConstant.DEFAULT_PASSWORD);
    }

    /**
     * 获取会话过期时间，默认2小时
     *
     * @author yubaoshan
     * @date 2020/7/9 16:18
     */
    public static Long getSessionTokenExpireSec() {
        return getSysConfigWithDefault("XIAONUO_SESSION_EXPIRE", Long.class, 2 * 60 * 60L);
    }

    /**
     * 获取token过期时间（单位：秒）
     * <p>
     * 默认时间1天
     *
     * @author xuyuxiang
     * @date 2020/6/19 18:08
     */
    public static Long getTokenExpireSec() {
        return getSysConfigWithDefault("XIAONUO_TOKEN_EXPIRE", Long.class, 86400L);
    }

    /**
     * 获取自定义的windows环境本地文件上传路径
     *
     * @author xuyuxiang
     * @date 2020/6/19 18:09
     */
    public static String getDefaultFileUploadPathForWindows() {
        return getSysConfigWithDefault("XIAONUO_FILE_UPLOAD_PATH_FOR_WINDOWS", String.class, "");
    }

    /**
     * 获取自定义的linux环境本地文件上传路径
     *
     * @author xuyuxiang
     * @date 2020/6/19 18:09
     */
    public static String getDefaultFileUploadPathForLinux() {
        return getSysConfigWithDefault("XIAONUO_FILE_UPLOAD_PATH_FOR_LINUX", String.class, "");
    }

    /**
     * 获取是否允许单用户登陆的开关， 默认为false
     *
     * @author xuyuxiang
     * @date 2020/6/19 18:09
     */
    public static Boolean getEnableSingleLogin() {
        return getSysConfigWithDefault("XIAONUO_ENABLE_SINGLE_LOGIN", Boolean.class, false);
    }

    /**
     * 获取阿里云定位接口
     *
     * @author xuyuxiang
     * @date 2020/7/20 9:20
     **/
    public static String getIpGeoApi() {
        return getSysConfig("XIAONUO_IP_GEO_API", String.class, false);
    }

    /**
     * 获取阿里云定位appCode
     *
     * @author xuyuxiang
     * @date 2020/7/20 10:33
     **/
    public static String getIpGeoAppCode() {
        return getSysConfig("XIAONUO_IP_GEO_APP_CODE", String.class, false);
    }

    /**
     * 获取Oauth码云第三方登录的配置
     *
     * @author xuyuxiang
     * @date 2020/7/28 17:16
     **/
    public static OauthConfigs getGiteeOauthConfigs() {
        String xiaoNuoClientId = getSysConfig("XIAONUO_OAUTH_GITEE_CLIENT_ID", String.class, true);
        String xiaoNuoClientSecret = getSysConfig("XIAONUO_OAUTH_GITEE_CLIENT_SECRET", String.class, true);
        String xiaoNuoRedirectUri = getSysConfig("XIAONUO_OAUTH_GITEE_REDIRECT_URI", String.class, true);

        OauthConfigs oauthConfigs = new OauthConfigs();
        oauthConfigs.setClientId(xiaoNuoClientId);
        oauthConfigs.setClientSecret(xiaoNuoClientSecret);
        oauthConfigs.setRedirectUri(xiaoNuoRedirectUri);
        return oauthConfigs;
    }

    /**
     * 获取OauthGithub第三方登录的配置
     *
     * @author xuyuxiang
     * @date 2020/7/28 17:16
     **/
    public static OauthConfigs getGithubOauthConfigs() {
        String xiaoNuoClientId = getSysConfig("XIAONUO_OAUTH_GITHUB_CLIENT_ID", String.class, true);
        String xiaoNuoClientSecret = getSysConfig("XIAONUO_OAUTH_GITHUB_CLIENT_SECRET", String.class, true);
        String xiaoNuoRedirectUri = getSysConfig("XIAONUO_OAUTH_GITHUB_REDIRECT_URI", String.class, true);

        OauthConfigs oauthConfigs = new OauthConfigs();
        oauthConfigs.setClientId(xiaoNuoClientId);
        oauthConfigs.setClientSecret(xiaoNuoClientSecret);
        oauthConfigs.setRedirectUri(xiaoNuoRedirectUri);
        return oauthConfigs;
    }

    /**
     * 获取是否允许Oauth用户登陆的开关， 默认为false
     *
     * @author xuyuxiang
     * @date 2020/7/28 16:37
     **/
    public static Boolean getEnableOauthLogin() {
        return getSysConfigWithDefault("XIAONUO_ENABLE_OAUTH_LOGIN", Boolean.class, false);
    }

    /**
     * 获取前端项目的地址
     *
     * @author xuyuxiang
     * @date 2020/7/29 14:08
     **/
    public static String getWebUrl() {
        return getSysConfig("XIAONUO_WEB_URL", String.class, true);
    }

    /**
     * 获取支付宝支付成功转发地址
     *
     * @author xuyuxiang
     * @date 2020/7/29 14:08
     **/
    public static String getAlipayReturnUrl() {
        return getSysConfig("XIAONUO_ALIPAY_RETURN_URL", String.class, true);
    }

    /**
     * 获取config表中的配置，如果为空返回默认值
     *
     * @param configCode   变量名称，对应sys_config表中的code
     * @param clazz        返回变量值的类型
     * @param defaultValue 如果结果为空返回此默认值
     * @author yubaoshan
     * @date 2020/6/20 22:03
     */
    public static <T> T getSysConfigWithDefault(String configCode, Class<T> clazz, T defaultValue) {
        String configValue = ConstantContext.me().getStr(configCode);
        if (ObjectUtil.isEmpty(configValue)) {
            // 将默认值加入到缓存常量
            log.warn(">>> 系统配置sys_config表中存在空项，configCode为：{}，系统采用默认值：{}", configCode, defaultValue);
            ConstantContext.me().put(configCode, defaultValue);
            return defaultValue;
        } else {
            try {
                return Convert.convert(clazz, configValue);
            } catch (Exception e) {
                return defaultValue;
            }
        }
    }

    /**
     * 获取config表中的配置，如果为空，是否抛出异常
     *
     * @param configCode   变量名称，对应sys_config表中的code
     * @param clazz        返回变量值的类型
     * @param nullThrowExp 若为空是否抛出异常
     * @author yubaoshan
     * @date 2020/6/20 22:03
     */
    public static <T> T getSysConfig(String configCode, Class<T> clazz, Boolean nullThrowExp) {
        String configValue = ConstantContext.me().getStr(configCode);
        if (ObjectUtil.isEmpty(configValue)) {
            if (nullThrowExp) {
                String format = StrUtil.format(">>> 系统配置sys_config表中存在空项，configCode为：{}", configCode);
                log.error(format);
                throw new ServiceException(CONSTANT_EMPTY.getCode(), format);
            } else {
                return null;
            }
        } else {
            try {
                return Convert.convert(clazz, configValue);
            } catch (Exception e) {
                if (nullThrowExp) {
                    String format = StrUtil.format(">>> 系统配置sys_config表中存在格式错误的值，configCode={}，configValue={}", configCode, configValue);
                    log.error(format);
                    throw new ServiceException(CONSTANT_EMPTY.getCode(), format);
                } else {
                    return null;
                }
            }
        }
    }

}
