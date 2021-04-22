package vgbd.servlet.analysis;

import vgbd.dao.CollectiondataDao;
import vgbd.dao.GreenhouseDao;
import vgbd.entity.ChartData;
import vgbd.entity.Collectiondata;
import vgbd.entity.Greenhouse;
import vgbd.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hui
 * @date 2020-12-29 17:05:09
 */
@WebServlet("/analysis/areaDiff")
public class AreaDiffServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //指定返回值类型 JSON
        response.setContentType("application/json");
        //接收前端参数
        String params = request.getParameter("params");
        //按地区品种产量
        Map<String, Object> yieldsByCity = new HashMap<>(16);
        //按地区品种面积
        Map<String, Object> areaByCity = new HashMap<>(16);
        //大棚查询条件
        Greenhouse gh = JsonUtil.parse2bean(params, Greenhouse.class);
        //实例化Dao层对象
        CollectiondataDao collectiondataDao = new CollectiondataDao();
        GreenhouseDao greenhouseDao = new GreenhouseDao();
        //查询符合条件的所有品种
        List<Collectiondata> collectiondatas = collectiondataDao.listCollectiondata(gh);
        List<String> varieties = collectiondatas.stream().map(Collectiondata::getVegetablevar).distinct().collect(Collectors.toList());
        //品种按年聚合查询
        for (String variety : varieties) {
            yieldsByCity.put(variety, collectiondataDao.diffYieldByCity(gh, variety));
            areaByCity.put(variety, collectiondataDao.diffAreaByCity(gh, variety));
        }
        //按地区总产量
        List<ChartData> allYieldByCity = collectiondataDao.diffAllYieldByArea(gh);
        //按地区总面积
        List<ChartData> allAreaByCity = greenhouseDao.diffAllAreaByCity(gh);
        //按地区设施数量
        List<ChartData> allGreenhouseByCity = greenhouseDao.diffAllGreenhouseByCity(gh);
        //返回值
        Map<String, Object> result = new HashMap<>(16);
        result.put("yieldsByArea", yieldsByCity);
        result.put("areaByCity", areaByCity);
        result.put("allYieldByCity", allYieldByCity);
        result.put("allAreaByCity", allAreaByCity);
        result.put("allGreenhouseByCity", allGreenhouseByCity);
        PrintWriter writer = response.getWriter();
        System.out.println(JsonUtil.parse2String(result));
        writer.write(JsonUtil.parse2String(result));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
