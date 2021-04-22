package vgbd.util;

import vgbd.entity.Greenhouse;

/**
 * @author hui
 * @date 2020-12-29 18:03:45
 */
public class SqlUtil {

    public static String dataCondition(String sql, Greenhouse greenhouse) {
        if (greenhouse.getProvince() != null && !"".equals(greenhouse.getProvince())) {
            sql += " AND g.province = '" + greenhouse.getProvince() + "'";
        }
        if (greenhouse.getCity() != null && !"".equals(greenhouse.getCity())) {
            sql += " AND g.city = '" + greenhouse.getCity() + "'";
        }
        if (greenhouse.getCounty() != null && !"".equals(greenhouse.getCounty())) {
            sql += " AND g.county = '" + greenhouse.getCounty() + "'";
        }
        if (greenhouse.getVillage() != null && !"".equals(greenhouse.getVillage())) {
            sql += " AND g.village = '" + greenhouse.getVillage() + "'";
        }
        if (greenhouse.getHouseholder() != null && !"".equals(greenhouse.getVillage())) {
            sql += " AND g.householder = '" + greenhouse.getHouseholder() + "'";
        }
        if (greenhouse.getGreenhouseNumber() != null && !"".equals(greenhouse.getGreenhouseNumber())) {
            sql += " AND g.greenhouse_number = '" + greenhouse.getGreenhouseNumber() + "'";
        }
        return sql;
    }

    public static String greenhouseCondition(String sql, Greenhouse greenhouse) {
        if (greenhouse.getProvince() != null && !"".equals(greenhouse.getProvince())) {
            sql += " AND province = '" + greenhouse.getProvince() + "'";
        }
        if (greenhouse.getCity() != null && !"".equals(greenhouse.getCity())) {
            sql += " AND city = '" + greenhouse.getCity() + "'";
        }
        if (greenhouse.getCounty() != null && !"".equals(greenhouse.getCounty())) {
            sql += " AND county = '" + greenhouse.getCounty() + "'";
        }
        if (greenhouse.getVillage() != null && !"".equals(greenhouse.getVillage())) {
            sql += " AND village = '" + greenhouse.getVillage() + "'";
        }
        if (greenhouse.getHouseholder() != null && !"".equals(greenhouse.getVillage())) {
            sql += " AND householder = '" + greenhouse.getHouseholder() + "'";
        }
        if (greenhouse.getGreenhouseNumber() != null && !"".equals(greenhouse.getGreenhouseNumber())) {
            sql += " AND greenhouse_number = '" + greenhouse.getGreenhouseNumber() + "'";
        }
        if (greenhouse.getStartTime() != null && !"".equals(greenhouse.getStartTime())) {
            sql += " AND create_time >= '" + greenhouse.getStartTime() + "'";
        }
        if (greenhouse.getEndTime() != null && !"".equals(greenhouse.getEndTime())) {
            sql += " AND create_time <= '" + greenhouse.getEndTime() + "'";
        }
        return sql;
    }
}
