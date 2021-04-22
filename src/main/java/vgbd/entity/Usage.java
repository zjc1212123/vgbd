package vgbd.entity;

import java.io.Serializable;

/**
 * (Usage)实体类
 *
 * @author hui
 * @since 2020-12-27 18:56:08
 */
public class Usage implements Serializable {
    private static final long serialVersionUID = 841903251857371026L;
    /**
     * 主键自增
     */
    private Integer id;
    /**
     * 智能车编号
     */
    private String carNumber;
    /**
     * 户主
     */
    private String householder;
    /**
     * 大棚编号
     */
    private String greenhouseNumber;
    /**
     * 大棚创建时间
     */
    private String greenhouseCreateTime;
    /**
     * 用户姓名
     */
    private String username;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 使用开始时间（自动获取）
     */
    private String startTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getHouseholder() {
        return householder;
    }

    public void setHouseholder(String householder) {
        this.householder = householder;
    }

    public String getGreenhouseNumber() {
        return greenhouseNumber;
    }

    public void setGreenhouseNumber(String greenhouseNumber) {
        this.greenhouseNumber = greenhouseNumber;
    }

    public String getGreenhouseCreateTime() {
        return greenhouseCreateTime;
    }

    public void setGreenhouseCreateTime(String greenhouseCreateTime) {
        this.greenhouseCreateTime = greenhouseCreateTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}