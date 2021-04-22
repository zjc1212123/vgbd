package vgbd.dao;

import vgbd.entity.ChartData;
import vgbd.entity.Collectiondata;
import vgbd.entity.Greenhouse;
import vgbd.util.DbUtil;
import vgbd.util.SqlUtil;

import java.util.List;

/**
 * @author hui
 * @date 2020-12-26 11:19:05
 */
public class CollectiondataDao {
    /**
     * 查询统计信息
     *
     * @param greenhouse Greenhouse 对象
     * @return Collectiondata 集合对象
     */
    public List<Collectiondata> listCollectiondata(Greenhouse greenhouse) {
        String sql = "SELECT c.id, " +
                " c.usage_id AS usageId, " +
                " c.vegetablename, " +
                " c.vegetablevar, " +
                " c.vegetableyield1, " +
                " c.vegetableyield2, " +
                " c.vegetableyield2, " +
                " c.vegetableyield3, " +
                " c.gps_latitude as gpsLatitude, " +
                " c.gps_longitude as gpsLongitude, " +
                " c.latitudetype, " +
                " c.longitudetype, " +
                " c.collecttime, " +
                " c.carstatus  " +
                "FROM ( collectiondata c LEFT JOIN `usage` u ON c.usage_id = u.id ) " +
                " LEFT JOIN greenhouse g ON u.greenhouse_number = g.greenhouse_number  " +
                " AND u.householder = g.householder  " +
                " AND YEAR ( u.greenhouse_create_time ) = YEAR (g.create_time) WHERE 1 = 1";
        sql = SqlUtil.dataCondition(sql, greenhouse);
        if (greenhouse.getStartTime() != null && !"".equals(greenhouse.getStartTime())) {
            sql += " AND g.create_time >= '" + greenhouse.getStartTime() + "'";
        }
        if (greenhouse.getEndTime() != null && !"".equals(greenhouse.getEndTime())) {
            sql += " AND g.create_time <= '" + greenhouse.getEndTime() + "'";
        }
        return DbUtil.list(Collectiondata.class, sql);
    }

    /**
     * 按小时查询今日总产量
     *
     * @param greenhouse Greenhouse 对象
     * @return ChartData 集合对象
     */
    public List<ChartData> listYieldByHour(Greenhouse greenhouse) {
        String sql = "SELECT DATE_FORMAT(collecttime, '%H:00' ) as 'name', " +
                " SUM(vegetableyield1) + SUM(vegetableyield2) + SUM(vegetableyield3) as 'value'  " +
                "FROM ( collectiondata c LEFT JOIN `usage` u ON c.usage_id = u.id ) " +
                " LEFT JOIN greenhouse g ON u.greenhouse_number = g.greenhouse_number  " +
                " AND u.householder = g.householder  " +
                " AND YEAR ( u.greenhouse_create_time ) = YEAR (g.create_time) WHERE to_days(collecttime) = to_days(now())  ";
        sql = SqlUtil.dataCondition(sql, greenhouse);
        sql += " GROUP BY collecttime ORDER BY `name`;";
        return DbUtil.list(ChartData.class, sql);
    }


    /**
     * 按品种查询今日总产量
     *
     * @param greenhouse Greenhouse 对象
     * @return ChartData 集合对象
     */
    public List<ChartData> listYieldByVariety(Greenhouse greenhouse) {
        String sql = "SELECT vegetablevar as 'name', " +
                " SUM(vegetableyield1) + SUM(vegetableyield2) + SUM(vegetableyield3) as 'value'  " +
                "FROM ( collectiondata c LEFT JOIN `usage` u ON c.usage_id = u.id ) " +
                " LEFT JOIN greenhouse g ON u.greenhouse_number = g.greenhouse_number  " +
                " AND u.householder = g.householder  " +
                " AND YEAR ( u.greenhouse_create_time ) = YEAR (g.create_time) WHERE to_days(collecttime) = to_days(now())  ";
        sql = SqlUtil.dataCondition(sql, greenhouse);
        sql += " GROUP BY vegetablevar;";
        return DbUtil.list(ChartData.class, sql);
    }

    /**
     * 按年统计品种产量
     *
     * @param greenhouse
     * @param variety
     * @return
     */
    public List<ChartData> listYieldByYear(Greenhouse greenhouse, String variety) {
        String sql = "SELECT YEAR(collecttime) as 'name', " +
                " SUM(vegetableyield1) + SUM(vegetableyield2) + SUM(vegetableyield3) as 'value'  " +
                "FROM ( collectiondata c LEFT JOIN `usage` u ON c.usage_id = u.id ) " +
                " LEFT JOIN greenhouse g ON u.greenhouse_number = g.greenhouse_number  " +
                " AND u.householder = g.householder  " +
                " AND YEAR ( u.greenhouse_create_time ) = YEAR (g.create_time) WHERE vegetablevar = ? ";
        sql = SqlUtil.dataCondition(sql, greenhouse);
        sql += " GROUP BY YEAR(collecttime);";
        return DbUtil.list(ChartData.class, sql, variety);
    }

    /**
     * 按地区对比分析品种产量
     *
     * @param greenhouse Greenhouse对象
     * @param variety    品种
     * @return ChartData集合对象
     */
    public List<ChartData> diffYieldByCity(Greenhouse greenhouse, String variety) {
        String sql = "SELECT city AS `name`, " +
                " SUM( vegetableyield1 ) + SUM( vegetableyield2 ) + SUM( vegetableyield3 ) AS 'value'  " +
                "FROM ( collectiondata c LEFT JOIN `usage` u ON c.usage_id = u.id ) " +
                " LEFT JOIN greenhouse g ON u.greenhouse_number = g.greenhouse_number  " +
                " AND u.householder = g.householder  " +
                " AND YEAR ( u.greenhouse_create_time ) = YEAR ( g.create_time )  " +
                "WHERE vegetablevar = ? ";
        sql = SqlUtil.dataCondition(sql, greenhouse);
        sql += " GROUP BY city";
        return DbUtil.list(ChartData.class, sql, variety);
    }

    /**
     * 按地区对比分析品种面积
     *
     * @param greenhouse Greenhouse对象
     * @param variety    品种
     * @return ChartData集合对象
     */
    public List<ChartData> diffAreaByCity(Greenhouse greenhouse, String variety) {
        String sql = "SELECT city AS \"name\",  " +
                " SUM( variety -> '$.\"" + variety + "\"' ) AS \"value\"  " +
                "FROM `greenhouse` " +
                "WHERE variety -> '$.\"" + variety + "\"' IS NOT NULL";
        sql = SqlUtil.greenhouseCondition(sql, greenhouse);
        sql += " GROUP BY city";
        return DbUtil.list(ChartData.class, sql);
    }

    /**
     * 按时间对比分析品种产量
     *
     * @param greenhouse Greenhouse对象
     * @param variety    品种
     * @return ChartData集合对象
     */
    public List<ChartData> diffYieldByDate(Greenhouse greenhouse, String variety) {
        String sql = "SELECT DATE(collecttime) AS `name`, " +
                " SUM( vegetableyield1 ) + SUM( vegetableyield2 ) + SUM( vegetableyield3 ) AS 'value'  " +
                "FROM ( collectiondata c LEFT JOIN `usage` u ON c.usage_id = u.id ) " +
                " LEFT JOIN greenhouse g ON u.greenhouse_number = g.greenhouse_number  " +
                " AND u.householder = g.householder  " +
                " AND YEAR ( u.greenhouse_create_time ) = YEAR ( g.create_time )  " +
                "WHERE vegetablevar = ? ";
        sql = SqlUtil.dataCondition(sql, greenhouse);
        sql += " GROUP BY DATE(collecttime) ORDER BY DATE(collecttime) ";
        return DbUtil.list(ChartData.class, sql, variety);
    }

    /**
     * 按时间对比分析品种面积
     *
     * @param greenhouse Greenhouse对象
     * @param variety    品种
     * @return ChartData集合对象
     */
    public List<ChartData> diffAreaByDate(Greenhouse greenhouse, String variety) {
        String sql = "SELECT DATE(create_time) AS \"name\", SUM( variety -> '$.\"" + variety + "\"' ) AS \"value\"  " +
                "FROM `greenhouse`  " +
                "WHERE variety -> '$.\"" + variety + "\"' IS NOT NULL  ";
        sql = SqlUtil.greenhouseCondition(sql, greenhouse);
        sql += " GROUP BY DATE(create_time) ORDER BY DATE(create_time)";
        return DbUtil.list(ChartData.class, sql);
    }

    public List<ChartData> diffAllYieldByArea(Greenhouse greenhouse) {
        String sql = "SELECT city AS 'name', " +
                " SUM( vegetableyield1 ) + SUM( vegetableyield2 ) + SUM( vegetableyield3 ) AS 'value'  " +
                "FROM ( collectiondata c LEFT JOIN `usage` u ON c.usage_id = u.id ) " +
                " LEFT JOIN greenhouse g ON u.greenhouse_number = g.greenhouse_number  " +
                " AND u.householder = g.householder  " +
                " AND YEAR ( u.greenhouse_create_time ) = YEAR ( g.create_time ) ";
        sql = SqlUtil.dataCondition(sql, greenhouse);
        sql += " GROUP BY city;";
        return DbUtil.list(ChartData.class, sql);
    }

    public List<ChartData> diffAllYieldByDate(Greenhouse greenhouse) {
        String sql = "SELECT " +
                " DATE(collecttime) AS 'name', " +
                " SUM( vegetableyield1 ) + SUM( vegetableyield2 ) + SUM( vegetableyield3 ) AS 'value'  " +
                "FROM ( collectiondata c LEFT JOIN `usage` u ON c.usage_id = u.id ) " +
                " LEFT JOIN greenhouse g ON u.greenhouse_number = g.greenhouse_number  " +
                " AND u.householder = g.householder  " +
                " AND YEAR ( u.greenhouse_create_time ) = YEAR ( g.create_time )  ";
        sql = SqlUtil.dataCondition(sql, greenhouse);
        sql += " GROUP BY DATE(collecttime) ORDER BY DATE(collecttime);";
        return DbUtil.list(ChartData.class, sql);
    }
}