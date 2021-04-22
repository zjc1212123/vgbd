package vgbd.entity;

import java.io.Serializable;

/**
 * (Greenhouse)实体类
 *
 * @author hui
 * @since 2020-12-27 18:56:34
 */
public class Greenhouse implements Serializable {
    private static final long serialVersionUID = 912991530853393197L;
    /**
     * 主键id，自增
     */
    private Integer id;
    /**
     * 大棚编号
     */
    private String greenhouseNumber;
    /**
     * 户主姓名
     */
    private String householder;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 县/区
     */
    private String county;
    /**
     * 村
     */
    private String village;
    /**
     * 地址
     */
    private String address;
    /**
     * 品种及面积[{name:"",value:""}]
     */
    private String variety;
    /**
     * 大棚面积
     */
    private Integer area;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 截止时间
     */
    private String endTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGreenhouseNumber() {
        return greenhouseNumber;
    }

    public void setGreenhouseNumber(String greenhouseNumber) {
        this.greenhouseNumber = greenhouseNumber;
    }

    public String getHouseholder() {
        return householder;
    }

    public void setHouseholder(String householder) {
        this.householder = householder;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Greenhouse{" +
                "id=" + id +
                ", greenhouseNumber='" + greenhouseNumber + '\'' +
                ", householder='" + householder + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", village='" + village + '\'' +
                ", address='" + address + '\'' +
                ", variety='" + variety + '\'' +
                ", area=" + area +
                ", createTime='" + createTime + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}