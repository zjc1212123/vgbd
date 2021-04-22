package vgbd.entity;

import java.io.Serializable;

/**
 * (Officialaddress)实体类
 *
 * @author lyw
 * @since 2020-12-11 11:33:06
 */
public class Officialaddress implements Serializable {
    private static final long serialVersionUID = -31326284213840191L;
    /**
     * 主键自增
     */
    private Integer id;
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

}