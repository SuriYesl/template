package cn.su.core.util;

import cn.su.core.model.ALYShortMessageParamVo;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/**
 * @AUTHOR: sr
 * @DATE: Create In 0:32 2021/1/18 0018
 * @Description: 阿里云短信
 */
public class ALYShortMessageUtil {
    public static void sendRegCode(String accessKeyId, String accessSecret, String phoneNumber, String code) throws Exception {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "Myheartmed");
        request.putQueryParameter("TemplateCode", "SMS_206275061");
        request.putQueryParameter("TemplateParam", " { \"code\": " + code + "}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
            throw new Exception();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    /**
     * 获取国内通用短信内容
     *
     * @param content 内容
     * @return 短信内容
     */
    private static String getNormMessage(ALYShortMessageParamVo content) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", content.getName());
        return jsonObject.toJSONString();
    }

    /**
     * 发送国内短信通知
     *
     * @param accessKeyId id
     * @param accessSecret 密匙
     * @param phoneNumber 手机号
     * @param content 短信内容
     */
    public static void sendNoticeMessage(String accessKeyId, String accessSecret,
                                         String phoneNumber, ALYShortMessageParamVo content) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers","+86" + phoneNumber);
        request.putQueryParameter("SignName", "Myheartmed");
        request.putQueryParameter("TemplateCode", "");
        request.putQueryParameter("TemplateParam", getNormMessage(content));
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData() + "--" + "--" + phoneNumber + "--" + getNormMessage(content));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
