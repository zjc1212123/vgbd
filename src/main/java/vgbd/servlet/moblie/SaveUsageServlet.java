package vgbd.servlet.moblie;

import vgbd.dao.UsageDao;
import vgbd.entity.Usage;
import vgbd.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author hui
 * @date 2020-12-11 15:57:10
 */
@WebServlet("/mobile/saveUsage")
public class SaveUsageServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置返回json
        response.setContentType("application/json");
        //初始化返回值
        int code = -1;
        String msg = "未知错误";
        //从前端获取值
        String usageJson = request.getParameter("usage");
        Usage usage = JsonUtil.parse2bean(usageJson, Usage.class);
        //保存到数据库
        UsageDao usageDao = new UsageDao();
        boolean state = usageDao.saveUsage(usage);
        if (state) {
            code = 0;
            msg = "保存成功";
        } else {
            code = 1;
            msg = "保存失败";
        }
        PrintWriter writer = response.getWriter();
        writer.write(JsonUtil.response(code, msg));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
