package vgbd.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 乱码过滤器
 *
 * @author hui
 * @date 2020-07-21 09:18:00
 */
@WebFilter(filterName = "CharacterFilter", urlPatterns = {"*"})
public class CharacterFilter implements Filter {
    @Override
    public void init(FilterConfig config) {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String path = request.getServletPath();
        //处理中文乱码
        if (!path.endsWith(".js") && !path.endsWith(".css") && !path.endsWith(".html")) {
            req.setCharacterEncoding("utf-8");
            resp.setCharacterEncoding("utf-8");
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }

}
