package oct.rekord.cas.common;

import com.aliyun.tea.*;
import com.aliyun.dysmsapi20170525.*;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.*;
import com.aliyun.teaopenapi.models.*;


public class Message {

    private static String accessKeyId = "useYourOwnAccessKeyId";
    private static String accessKeySecret = "useYourOwnAccessKeySecret";

//    /**
//     * 使用AK&SK初始化账号Client
//     * @param accessKeyId
//     * @param accessKeySecret
//     * @return Client
//     * @throws Exception
//     */
    public static com.aliyun.dysmsapi20170525.Client createClient() throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }

    public static void sendMessageOrigin(String phone, String code) throws Exception {
        com.aliyun.dysmsapi20170525.Client client = Message.createClient();
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName("阿里云短信测试")
                .setTemplateCode("SMS_154950909")
                .setPhoneNumbers(phone)
                .setTemplateParam("{\"code\":\"" + code + "\"}");
        // 复制代码运行请自行打印 API 的返回值
        client.sendSms(sendSmsRequest);
    }

    // 发送指定位数的短信验证码,若不加指定，则默认为 4
    public static String sendMessage(String phone, Integer messageCodeLength) throws Exception{
        messageCodeLength = messageCodeLength == null ? 4 : messageCodeLength;
        String code = "";
        for (int i = 0; i < messageCodeLength; i++) {
            code += ((int) (Math.random() * 1000)) % 10;
        }
        sendMessageOrigin(phone, code);
        return code;
    }

}