package vgbd.dao;

import vgbd.entity.Usage;
import vgbd.util.DbUtil;

/**
 * @author hui
 * @date 2020-12-27 19:15:32
 */
public class UsageDao {
    /**
     * 保存使用记录
     *
     * @param usage Usage 对象
     * @return true/false
     */
    public boolean saveUsage(Usage usage) {
        return DbUtil.save("INSERT INTO `usage`(car_number, householder, greenhouse_number, greenhouse_create_time, username, phone, start_time ) VALUES(?,?,?,?,?,?,?)",
                usage.getCarNumber(),
                usage.getHouseholder(),
                usage.getGreenhouseNumber(),
                usage.getGreenhouseCreateTime(),
                usage.getUsername(),
                usage.getPhone(),
                usage.getStartTime());
    }
}
