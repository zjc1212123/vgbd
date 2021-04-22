package vgbd.servlet.moblie;

import vgbd.dao.GreenhouseDao;
import vgbd.dao.UsageDao;
import vgbd.entity.Greenhouse;
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
@WebServlet("/mobile/getGreenhouse")
public class GetGreenhouseServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置返回json
        response.setContentType("application/json");
        //从前端获取值
        String greenhouseJson = request.getParameter("greenhouse");
        Greenhouse gh = JsonUtil.parse2bean(greenhouseJson, Greenhouse.class);
        //保存到数据库
        GreenhouseDao greenhouseDao = new GreenhouseDao();
        Greenhouse greenhouse = greenhouseDao.queryGreenhouse(gh);
        PrintWriter writer = response.getWriter();
        writer.write(JsonUtil.response(0, "查询大棚信息", greenhouse));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
