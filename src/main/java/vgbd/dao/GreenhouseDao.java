package vgbd.dao;

import vgbd.entity.ChartData;
import vgbd.entity.Greenhouse;
import vgbd.util.DbUtil;
import vgbd.util.SqlUtil;

import java.util.List;
import java.util.Queue;

/**
 * @author hui
 * @date 2020-12-27 19:44:10
 */
public class GreenhouseDao {
    /**
     * 查询大棚信息
     *
     * @param greenhouse Greenhouse对象
     * @return Greenhouse对象
     */
    public Greenhouse queryGreenhouse(Greenhouse greenhouse) {
        return DbUtil.query(Greenhouse.class, "SELECT id, " +
                        "greenhouse_number as greenhouseNumber, " +
                        "householder, " +
                        "province, " +
                        "city, " +
                        "county, " +
                        "village, " +
                        "address, " +
                        "variety, " +
                        "area, " +
                        "create_time as createTime FROM `greenhouse` WHERE householder = ? AND greenhouse_number = ? AND YEAR(create_time) = ?",
                greenhouse.getHouseholder(),
                greenhouse.getGreenhouseNumber(),
                greenhouse.getCreateTime());
    }

    /**
     * 查询条件内所有大棚
     *
     * @param greenhouse Greenhouse对象
     * @return Greenhouse集合对象
     */
    public List<Greenhouse> listGreenhouse(Greenhouse greenhouse) {
        String sql = "SELECT id, " +
                "greenhouse_number as greenhouseNumber, " +
                "householder, " +
                "province, " +
                "city, " +
                "county, " +
                "village, " +
                "address, " +
                "variety, " +
                "area, " +
                "create_time as createTime FROM greenhouse WHERE 1 = 1";
        sql = SqlUtil.greenhouseCondition(sql, greenhouse);
        return DbUtil.list(Greenhouse.class, sql);
    }

    /**
     * 按年统计设施数量
     *
     * @param greenhouse Grennhouse对象
     * @return ChartData 集合对象
     */
    public List<ChartData> listGreenhouseByYear(Greenhouse greenhouse) {
        String sql = "SELECT YEAR( create_time ) AS 'name', " +
                " count(*) AS 'value'  " +
                "FROM greenhouse WHERE 1 = 1";
        sql = SqlUtil.greenhouseCondition(sql, greenhouse);
        sql += " GROUP BY YEAR ( create_time ) ORDER BY `name`;";
        return DbUtil.list(ChartData.class, sql);
    }

    /**
     * 按年统计品种面积
     *
     * @param greenhouse Greenhouse对象
     * @param variety    品种
     * @return ChartData集合对象
     */
    public List<ChartData> listVarietyAreaByYear(Greenhouse greenhouse, String variety) {
        String sql = "SELECT SUM(variety -> '$.\"" + variety + "\"') AS \"value\", YEAR(create_time) AS \"name\" " +
                "FROM `greenhouse` WHERE variety -> '$.\"" + variety + "\"' IS NOT NULL";
        sql = SqlUtil.greenhouseCondition(sql, greenhouse);
        sql += " GROUP BY YEAR(create_time) ORDER BY YEAR(create_time)";
        return DbUtil.list(ChartData.class, sql);
    }

    /**
     * 按地区统计设施数量
     *
     * @param greenhouse Greenhouse对象
     * @return ChartData对象
     */
    public List<ChartData> diffAllGreenhouseByCity(Greenhouse greenhouse) {
        String sql = "SELECT city AS \"name\", count(*) AS \"value\" FROM greenhouse ";
        SqlUtil.greenhouseCondition(sql, greenhouse);
        sql += " GROUP BY city";
        return DbUtil.list(ChartData.class, sql);
    }

    /**
     * 按地区统计总面积
     *
     * @param greenhouse Greenhouse对象
     * @return ChartData对象
     */
    public List<ChartData> diffAllAreaByCity(Greenhouse greenhouse) {
        String sql = "SELECT city AS \"name\", sum(area) AS \"value\" FROM greenhouse ";
        SqlUtil.greenhouseCondition(sql, greenhouse);
        sql += " GROUP BY city";
        return DbUtil.list(ChartData.class, sql);
    }

    /**
     * 按时间统计设施数量
     *
     * @param greenhouse Greenhouse对象
     * @return ChartData对象
     */
    public List<ChartData> diffAllGreenhouseByDate(Greenhouse greenhouse) {
        String sql = "SELECT DATE(create_time) AS \"name\", count(*) AS \"value\" FROM greenhouse ";
        SqlUtil.greenhouseCondition(sql, greenhouse);
        sql += " GROUP BY DATE(create_time) ORDER BY DATE(create_time)";
        return DbUtil.list(ChartData.class, sql);
    }

    /**
     * 按时间统计总面积
     *
     * @param greenhouse Greenhouse对象
     * @return ChartData对象
     */
    public List<ChartData> diffAllAreaByDate(Greenhouse greenhouse) {
        String sql = "SELECT DATE(create_time) AS \"name\", sum(area) AS \"value\" FROM greenhouse ";
        SqlUtil.greenhouseCondition(sql, greenhouse);
        sql += " GROUP BY DATE(create_time) ORDER BY DATE(create_time)";
        return DbUtil.list(ChartData.class, sql);
    }
}