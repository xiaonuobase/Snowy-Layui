package com.cn.xiaonuo.sys.modular;

import com.cn.xiaonuo.core.context.login.LoginContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 杂项页面控制器
 *
 * @author xuyuxiang
 * @date 2020/11/9 11:32
 */
@Controller
public class OtherController {

    /**
     * 欢迎页
     *
     * @author xuyuxiang
     * @date 2020/11/9 11:34
     */
    @GetMapping("/other/welcomeHtml")
    public String welcomeHtml() {
        return "other/welcome.html";
    }

    /**
     * 主题页
     *
     * @author xuyuxiang
     * @date 2020/11/9 11:34
     */
    @GetMapping("/other/themeHtml")
    public String themeHtml() {
        return "other/theme.html";
    }

    /**
     * 锁屏页
     *
     * @author xuyuxiang
     * @date 2020/11/9 11:34
     */
    @GetMapping("/other/lockScreenHtml")
    public String lockScreenHtml() {
        return "other/lock-screen.html";
    }

    /**
     * 便签页
     *
     * @author xuyuxiang
     * @date 2020/11/9 11:34
     */
    @GetMapping("/other/noteHtml")
    public String noteHtml() {
        return "other/note.html";
    }

    /**
     * 消息页
     *
     * @author xuyuxiang
     * @date 2020/11/9 11:34
     */
    @GetMapping("/other/messageHtml")
    public String messageHtml() {
        return "other/message.html";
    }

    /**
     * 个人中心页
     *
     * @author xuyuxiang
     * @date 2020/11/9 11:34
     */
    @GetMapping("/other/userInfoHtml")
    public String userInfoHtml(Model model) {
        model.addAttribute("userInfo", LoginContextHolder.me().getSysLoginUserWithoutException());
        return "other/user-info.html";
    }

    /**
     * 修改密码页
     *
     * @author xuyuxiang
     * @date 2020/11/9 11:34
     */
    @GetMapping("/other/updatePasswordHtml")
    public String updatePasswordHtml() {
        return "other/update-password.html";
    }

    /**
     * 控制台
     *
     * @author xuyuxiang
     * @date 2020/11/9 11:34
     */
    @GetMapping("/other/consoleHtml")
    public String consoleHtml() {
        return "other/console.html";
    }

    /**
     * 分析页
     *
     * @author xuyuxiang
     * @date 2020/11/9 11:34
     */
    @GetMapping("/other/dashboardHtml")
    public String dashboardHtml() {
        return "other/dashboard.html";
    }

    /**
     * 工作台
     *
     * @author xuyuxiang
     * @date 2020/11/9 11:34
     */
    @GetMapping("/other/workplaceHtml")
    public String workplaceHtml() {
        return "other/workplace.html";
    }
}
