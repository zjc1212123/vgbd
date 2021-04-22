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
 * <p>url：http:/ip:port/vgbd/analysis/synthesize</p>
 * <p>请求类型: post</p>
 * <p>参数类型：json</p>
 * <p>参数列表：<code>{ province:"省", city:"市", county:"县/区", village:"村", householder:"户主", greenhouseNumber:"大棚编号",  carNumber:"小车编号", startTime:"开始时间", endTime:"结束时间" }</code></p>
 *
 * @author hui
 * @date 2020-12-26 11:37:39
 */
@WebServlet("/analysis/synthesize")
public class SynthesizeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //指定返回值类型 JSON
        response.setContentType("application/json");
        //接收前端参数
        String params = request.getParameter("params");
        //大棚查询条件
        Greenhouse gh = JsonUtil.parse2bean(params, Greenhouse.class);
        //初始化返回值
        Map<String, Object> yields = new HashMap<>(16);
        Map<String, Object> areas = new HashMap<>(16);
        //实例化Dao层对象
        CollectiondataDao collectiondataDao = new CollectiondataDao();
        GreenhouseDao greenhouseDao = new GreenhouseDao();
        //查询符合条件的所有品种
        List<Collectiondata> collectiondatas = collectiondataDao.listCollectiondata(gh);
        List<String> varieties = collectiondatas.stream().map(Collectiondata::getVegetablevar).distinct().collect(Collectors.toList());
        //品种按年聚合查询
        for (String variety : varieties) {
            yields.put(variety, collectiondataDao.listYieldByYear(gh, variety));
            areas.put(variety, greenhouseDao.listVarietyAreaByYear(gh, variety));
        }
        //设施数量按年聚合查询
        List<ChartData> greenhouseNumbers = greenhouseDao.listGreenhouseByYear(gh);
        //返回值
        Map<String, Object> result = new HashMap<>(16);
        result.put("yields", yields);
        result.put("greenhouseNumbers", greenhouseNumbers);
        result.put("areas", areas);
        PrintWriter writer = response.getWriter();
        System.out.println(JsonUtil.parse2String(result));
        writer.write(JsonUtil.parse2String(result));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
