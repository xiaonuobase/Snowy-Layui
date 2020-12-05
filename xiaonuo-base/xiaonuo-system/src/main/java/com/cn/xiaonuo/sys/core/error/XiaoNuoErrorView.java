package com.cn.xiaonuo.sys.core.error;

import cn.hutool.http.ContentType;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 错误视图解析器
 *
 * @author xuyuxiang
 * @date 2020/11/6 14:20
 */
public class XiaoNuoErrorView implements View {

    @Override
    public String getContentType() {
        return ContentType.TEXT_HTML.getValue();
    }

    @Override
    public void render(Map<String, ?> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getRequestDispatcher("/global/error").forward(request, response);
    }
}
