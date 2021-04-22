package vgbd.servlet.moblie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hui
 * @date 2020-12-24 11:13:11
 */
@WebServlet("/mobile/index")
public class IndexServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取二维码小车编号
        String carNumber = request.getParameter("carNumber");
        //页面跳转
        request.setAttribute("carNumber", carNumber);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
