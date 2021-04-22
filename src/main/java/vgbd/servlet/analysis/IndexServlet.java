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

/**
 * <p>url：http:/ip:port/vgbd/analysis/index</p>
 * <p>请求类型: post</p>
 * <p>参数类型：json</p>
 * <p>参数列表：<code>{ province:"省", city:"市", county:"县/区", village:"村", householder:"户主", greenhouseNumber:"大棚编号",  carNumber:"小车编号", startTime:"开始时间", endTime:"结束时间" }</code></p>
 *
 * @author hui
 * @date 2020-12-26 11:37:39
 */
@WebServlet("/analysis/index")
public class IndexServlet extends   HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //总面积，设施数量，总品种，总产量
        int allArea = 0, allNumber = 0, allVariety = 0, allYield = 0;
        //指定返回值类型 JSON
        response.setContentType("application/json");
        //实例化Dao层对象
        CollectiondataDao collectiondataDao = new CollectiondataDao();
        GreenhouseDao greenhouseDao = new GreenhouseDao();
        //接收前端参数
        String params = request.getParameter("params");
        //大棚查询条件
        Greenhouse gh = JsonUtil.parse2bean(params, Greenhouse.class);
        //采集信息
        List<Collectiondata> collectiondatas = collectiondataDao.listCollectiondata(gh);
        allYield = collectiondatas.stream().mapToInt(Collectiondata::getVegetableyield1).sum() + collectiondatas.stream().mapToInt(Collectiondata::getVegetableyield2).sum() + collectiondatas.stream().mapToInt(Collectiondata::getVegetableyield3).sum();
        allVariety = (int) collectiondatas.stream().map(Collectiondata::getVegetablevar).distinct().count();
        //大棚信息
        List<Greenhouse> greenhouses = greenhouseDao.listGreenhouse(gh);
        allArea = greenhouses.stream().mapToInt(Greenhouse::getArea).sum();
        allNumber = greenhouses.size();
        //今日总产量
        List<ChartData> allYieldByHour = collectiondataDao.listYieldByHour(gh);
        //今日品种产量
        List<ChartData> allYieldByVariety = collectiondataDao.listYieldByVariety(gh);
        //构造 返回值Json
        Map<String, Object> map = new HashMap<>(16);
        map.put("allArea", allArea);
        map.put("allNumber", allNumber);
        map.put("allYield", allYield);
        map.put("allVariety", allVariety);
        map.put("allYieldByHour", allYieldByHour);
        map.put("allYieldByVariety", allYieldByVariety);
        String data = JsonUtil.parse2String(map);   //返回值
        PrintWriter writer = response.getWriter();
        writer.write(data);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
