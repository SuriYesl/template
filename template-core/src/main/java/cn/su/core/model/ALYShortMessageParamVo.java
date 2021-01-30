package cn.su.core.model;

/**
 * @AUTHOR: sr
 * @DATE: Create In 23:04 2021/1/18 0018
 * @DESCRIPTION: 阿里云短信参数
 */
public class ALYShortMessageParamVo {
    /**
     * key
     */
    private String accessKey;

    /**
     * 密匙
     */
    private String accessSecret;

    /**
     * 参数
     */
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
