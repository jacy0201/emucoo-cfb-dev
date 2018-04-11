package com.emucoo.restApi.models.sms;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emucoo.dto.base.ISystem;
import com.emucoo.exception.BizException;
import com.emucoo.restApi.config.SMSConfig;
import com.sendcloud.sdk.builder.SendCloudBuilder;
import com.sendcloud.sdk.core.SendCloud;
import com.sendcloud.sdk.exception.SmsException;
import com.sendcloud.sdk.model.SendCloudSms;
import com.sendcloud.sdk.util.ResponseData;

@Service
public class SMSService {
	@Autowired
	private SMSDao smsDao;
	@Autowired
	private SMSConfig smsConfig;

    /**
     * 发送验证码
     * @param mobile
     */
    private SendCloud   sendCloud = null;
    
    private static final Logger logger = LoggerFactory.getLogger(SMSService.class);
    
    public enum MsgType{
    	SMS(0)//短信
    	,MMS(1);//彩信
    	int index;
		private MsgType(int index) {
			this.index = index;
		}
    }
    
    /**
	 * 发送验证码
	 * 
	 * @param mobile
	 */
	private void sendVcode(String mobile, int template) {
		// 单个手机15分钟最多5次
		int count = smsDao.getSendCount(mobile, template);
		if (count >= ISystem.ISMSTemplateCode.smsVcodeCountMax) {
			BizException.throwException(2000);
		}
		// 发短信
		String vcode =!smsConfig.isSend()?"123456":RandomStringUtils.randomNumeric(6);
		// 保存验证码
		smsDao.saveVcode(mobile, vcode, template);
		// 发送
		if(smsConfig.isSend()){
			Map<String, Object> map = new HashMap<>();
			map.put("code", vcode);
			List<String> list = new ArrayList<>();
			list.add(mobile);
			sendTemplate(list, template, MsgType.SMS,map);
		}
	}

	/**
	 * 校验验证码
	 * 
	 * @param mobile
	 * @param vcode
	 */
	public boolean validateVcode(String mobile, String vcode) {
		String vcode2 = smsDao.getVcode(mobile);
		return StringUtils.equalsIgnoreCase(vcode, vcode2);
	}

	/**
	 * 使验证码失效
	 * 
	 * @param mobile
	 */
	public void delVcode(String mobile) {
		smsDao.delVcode(mobile);
	}

	public void sendTemplate(List<String> phones, int templateId,MsgType msgType,Map<String, Object> vars) {
		
        SendCloudSms sendCloudSms = new SendCloudSms();
        sendCloudSms.setMsgType(msgType.index);
        sendCloudSms.setTemplateId(templateId);
        for (String phone : phones) {
        	sendCloudSms.addPhone(phone);
		}
        for (Entry<String, Object> entry : vars.entrySet()) {
        	sendCloudSms.addVars(entry.getKey(), entry.getValue().toString());
		}
		try {
			ResponseData res = sendCloud.sendSms(sendCloudSms);
			
			logger.info("向手机号:"+ phones + "发送消息   "
					+ "Result=" +res.getResult() + ",message="+res.getMessage()
					+ "StatusCode=" +res.getStatusCode() + ",Info="+res.getInfo());
			
		} catch (IOException|SmsException e) {
			BizException.throwException(2001);
		} 

	}

	public void sendRegisterVcode(String mobile) {
		  this.sendVcode(mobile, ISystem.ISMSTemplateCode.REGV);
	}

	public void sendResetpwdVcode(String mobile) {
		this.sendVcode(mobile,ISystem.ISMSTemplateCode.RESET_PWD);
	}
	
    @PostConstruct
    private void init() throws ClientProtocolException, IOException, SmsException{
        sendCloud = SendCloudBuilder.build();
    }

}
