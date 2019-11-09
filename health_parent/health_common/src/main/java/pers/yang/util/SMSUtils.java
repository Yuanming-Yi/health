package pers.yang.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/**
 * 短信发送工具类
 */
public class SMSUtils {
	public static final String VALIDATE_CODE = "SMS_176926101";//发送短信验证码
	public static final String ORDER_NOTICE = "SMS_176926306";//体检预约成功通知

	/**
	 * 发送短信验证码
	 * @param phoneNumbers
	 * @param code
	 * @throws ClientException
	 */
	public static void sendShortMessage(String templateCode,String phoneNumbers,String code) throws ClientException{
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FpeAbPoUCUmh5SiBLy1", "ASRoQtzXSslJpk23mBOZUCMm6Z0qxM");
		IAcsClient client = new DefaultAcsClient(profile);

		CommonRequest request = new CommonRequest();
		request.setMethod(MethodType.POST);
		request.setDomain("dysmsapi.aliyuncs.com");
		request.setVersion("2017-05-25");
		request.setAction("SendSms");
		request.putQueryParameter("RegionId", "cn-hangzhou");
		request.putQueryParameter("PhoneNumbers", phoneNumbers);
		request.putQueryParameter("SignName", "easylife");
		request.putQueryParameter("TemplateCode", templateCode);
		request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");
		try {
			CommonResponse response = client.getCommonResponse(request);
			System.out.println(response.getData());
		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) throws ClientException {
		SMSUtils.sendShortMessage(VALIDATE_CODE,"18951257535","1234");
	}
}
