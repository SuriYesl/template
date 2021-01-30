package cn.su.core.util;

import cn.su.core.constants.VarTypeConstants;
import cn.su.core.exception.BusinessException;
import cn.su.core.model.AWSShortMessageParamVo;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @AUTHOR: sr
 * @DATE: Create In 0:32 2021/1/18 0018
 * @Description: 亚马逊短信
 */
public class AWSShortMessageUtil {
    private static final String SENDER_ID = "AWS.SNS.SMS.SenderID";
    private static final String MAX_PRICE = "AWS.SNS.SMS.MaxPrice";
    private static final String SMS_TYPE = "AWS.SNS.SMS.SMSType";

    private static final String VALUE_REGION = "ap-southeast-1";
    private static final String VALUE_SENDER_ID = "test";
    private static final String VALUE_MAX_PRICE = "10.00001";
    private static final String DEFAULT_VALUE_SMS_TYPE = "Promotional";
    private static final String VALUE_SMS_TYPE = "Transactional";

    private static void fillParam(AWSShortMessageParamVo aws) {
        aws.setRegion(NormHandleUtil.isEmpty(aws.getRegion()) ? VALUE_REGION : aws.getRegion());
        aws.setMaxPrice(NormHandleUtil.isEmpty(aws.getMaxPrice()) ? VALUE_MAX_PRICE : aws.getMaxPrice());
        aws.setSenderId(NormHandleUtil.isEmpty(aws.getSenderId()) ? VALUE_SENDER_ID : aws.getSenderId());
        aws.setSendType(NormHandleUtil.isEmpty(aws.getSendType()) ? VALUE_SMS_TYPE : aws.getSendType());
    }

    public static void sendMessage(AWSShortMessageParamVo awsShortMessageParamVo) {
        if (NormHandleUtil.isEmpty(awsShortMessageParamVo.getAccessKey()) || NormHandleUtil.isEmpty(awsShortMessageParamVo.getAccessSecret()))
            throw new BusinessException();

        fillParam(awsShortMessageParamVo);

        AWSCredentials awsCredentials = new AWSCredentials() {
            @Override
            public String getAWSAccessKeyId() {
                return awsShortMessageParamVo.getAccessKey();
            }

            @Override
            public String getAWSSecretKey() {
                return awsShortMessageParamVo.getAccessSecret();
            }
        };

        AWSCredentialsProvider awsCredentialsProvider = new AWSCredentialsProvider() {
            @Override
            public AWSCredentials getCredentials() {
                return awsCredentials;
            }

            @Override
            public void refresh() {

            }
        };

        AmazonSNS snsClient = AmazonSNSClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(awsShortMessageParamVo.getRegion()).build();
        Map<String, MessageAttributeValue> smsAttributes = new HashMap<>();
        smsAttributes.put(SENDER_ID, new MessageAttributeValue()
                .withStringValue(awsShortMessageParamVo.getSenderId())
                .withDataType(VarTypeConstants.STRING));
        smsAttributes.put(MAX_PRICE, new MessageAttributeValue()
                .withStringValue(awsShortMessageParamVo.getMaxPrice())
                .withDataType(VarTypeConstants.NUMBER));
        smsAttributes.put(SMS_TYPE, new MessageAttributeValue()
                .withStringValue(awsShortMessageParamVo.getSendType())
                .withDataType(VarTypeConstants.STRING));

        PublishResult result = snsClient.publish(new PublishRequest()
                .withMessage(awsShortMessageParamVo.getMessage())
                .withPhoneNumber(awsShortMessageParamVo.getPhone())
                .withMessageAttributes(smsAttributes));
        System.out.println(result);
    }
}
