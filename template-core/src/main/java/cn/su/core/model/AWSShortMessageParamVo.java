package cn.su.core.model;

/**
 * @Author: su rui
 * @Date: 2021/1/11 10:37
 * @Description: 亚马逊短信服务需要参数
 */
public class AWSShortMessageParamVo {
    /**
     * key
     */
    private String accessKey;

    /**
     * 密匙
     */
    private String accessSecret;

    /**
     * 发送id
     */
    private String senderId;

    /**
     * 最高价格
     */
    private String maxPrice;

    /**
     * 发送类型
     */
    private String sendType;

    /**
     * 收件人手机
     */
    private String phone;

    /**
     * 短信内容
     */
    private String message;

    /**
     * 地区
     */
    private String region;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
