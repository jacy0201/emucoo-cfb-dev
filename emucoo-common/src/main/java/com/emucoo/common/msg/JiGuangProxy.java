package com.emucoo.common.msg;

import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosAlert;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.emucoo.common.msg.JiGuangProxy.MsgType.*;

/**
 * message pushing proxy.
 *
 * @author Shayne.Wang
 * @date 2018-06-12
 */

public class JiGuangProxy {
    private static Logger logger = LoggerFactory.getLogger(JiGuangProxy.class);

    @SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
    public enum DeviceOS {
        OS_ALL, OS_IOS, OS_ANDROID
    }

    @SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
    public enum MsgType {
        MSG_ALL, MSG_NOTIFICATION, MSG_MESSAGE
    }

    public static JiGuangProxy createJiGuangProxyInstance(String appKey, String appSecrect, boolean apns) {
        return new JiGuangProxy(appKey, appSecrect, apns);
    }

    private boolean apnsProduction;
    private String appKey;
    private String appSecrect;
    private JPushClient jPushClient;

    private JiGuangProxy(String key, String secret, boolean apns) {
        this.appKey = key;
        this.appSecrect = secret;
        this.apnsProduction = apns;
        this.jPushClient = new JPushClient(appSecrect, appKey);
    }

    private Options buildPushOptions() {
        return Options.newBuilder().setApnsProduction(apnsProduction).setSendno(ServiceHelper.generateSendno()).setTimeToLive(14400).build();
    }

    private Notification buildNotification(String title, String content, Map<String, String> extra, DeviceOS deviceOS) {
        Notification.Builder builder = Notification.newBuilder();
        IosNotification.Builder iosBuilder = IosNotification.newBuilder().setAlert(IosAlert.newBuilder().setTitleAndBody(title, null, content).build()).setSound("default");
        AndroidNotification.Builder androidBuilder = AndroidNotification.newBuilder().setTitle(title).setAlert(content);
        if (extra != null) {
            iosBuilder.addExtras(extra);
            androidBuilder.addExtras(extra);
        }
        switch (deviceOS) {
            case OS_IOS:
                builder.addPlatformNotification(iosBuilder.build());
                break;
            case OS_ANDROID:
                builder.addPlatformNotification(androidBuilder.build());
                break;
            default:
                builder.addPlatformNotification(iosBuilder.build()).addPlatformNotification(androidBuilder.build());
        }
        return builder.build();
    }

    private Message buildMessage(String title, String content, Map<String, String> extra, DeviceOS deviceOS) {
        Message.Builder builder = Message.newBuilder();
        builder.setMsgContent(content);
        if (extra != null) {
            builder.addExtras(extra);
        }
        return builder.build();
    }

    private Platform buildPlatform(DeviceOS deviceOS) {
        switch (deviceOS) {
            case OS_IOS:
                return Platform.ios();
            case OS_ANDROID:
                return Platform.android();
            default:
                return Platform.all();
        }
    }

    private PushPayload buildPushPayLoad(String title, String content, String deviceId, Map<String, String> extra, DeviceOS deviceOS, MsgType msgType) throws IllegalArgumentException {
        if (StringUtils.isBlank(title) || StringUtils.isBlank(content)) {
            throw new IllegalArgumentException("Title and content must not be blank!");
        }
        PushPayload.Builder builder = PushPayload.newBuilder();
        builder.setOptions(buildPushOptions());
        builder.setAudience(Audience.registrationId(deviceId));
        builder.setPlatform(buildPlatform(deviceOS));
        switch (msgType) {
            case MSG_NOTIFICATION:
                builder.setNotification(buildNotification(title, content, extra, deviceOS));
                break;
            case MSG_MESSAGE:
                builder.setMessage(buildMessage(title, content, extra, deviceOS));
                break;
            default:
                builder.setNotification(buildNotification(title, content, extra, deviceOS));
                builder.setMessage(buildMessage(title, content, extra, deviceOS));
        }
        return builder.build();
    }


    /**
     * 发送消息到手机系统通知栏。
     *
     * @param title
     * @param content
     * @param deviceId
     * @param extra
     * @param deviceOS
     * @return
     */
    public int sendNotification(String title, String content, String deviceId, Map<String, String> extra, DeviceOS deviceOS) {
        try {
            PushPayload payload = buildPushPayLoad(title, content, deviceId, extra, deviceOS, MSG_NOTIFICATION);
            PushResult result = jPushClient.sendPush(payload);
            return result.statusCode;
        } catch (APIConnectionException e) {
            logger.error(e.getMessage(), e);
            return -1;
        } catch (APIRequestException e) {
            logger.error(e.getMessage(), e);
            return -2;
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
            return -3;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return -4;
        }
    }

    /**
     * 发送app应用内部通知。
     *
     * @param title
     * @param content
     * @param deviceId
     * @param extra
     * @param deviceOS
     * @return
     */
    public int sendMessage(String title, String content, String deviceId, Map<String, String> extra, DeviceOS deviceOS) {
        try {
            PushPayload payload = buildPushPayLoad(title, content, deviceId, extra, deviceOS, MSG_MESSAGE);
            PushResult result = jPushClient.sendPush(payload);
            return result.statusCode;
        } catch (APIConnectionException e) {
            logger.error(e.getMessage(), e);
            return -1;
        } catch (APIRequestException e) {
            logger.error(e.getMessage(), e);
            return -2;
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
            return -3;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return -4;
        }
    }

    /**
     * 系统通知栏和app通知都发。
     *
     * @param title
     * @param content
     * @param deviceId
     * @param extra
     * @param deviceOS
     * @return
     */
    public int sendNotificationAndMessage(String title, String content, String deviceId, Map<String, String> extra, DeviceOS deviceOS) {
        try {
            PushPayload payload = buildPushPayLoad(title, content, deviceId, extra, deviceOS, MSG_ALL);
            PushResult result = jPushClient.sendPush(payload);
            return result.statusCode;
        } catch (APIConnectionException e) {
            logger.error(e.getMessage(), e);
            return -1;
        } catch (APIRequestException e) {
            logger.error(e.getMessage(), e);
            return -2;
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
            return -3;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return -4;
        }
    }

    public String judgeFailReason(int code) {
        switch(code) {
            case 0:
                return "推送成功";
            case 1000:
                return "服务器端内部逻辑错误，请稍后重试。";
            case 1001:
                return "只支持 HTTP Post 方法";
            case 1002:
                return "缺少了必须的参数";
            case 1003:
                return "参数值不合法";
            case 1004:
                return "验证失败";
            case 1005:
                return "消息体太大";
            case 1008:
                return "app_key参数非法";
            case 1009:
                return "推送对象中有不支持的key";
            case 1011:
                return "没有满足条件的推送目标";
            case 1020:
                return "只支持 HTTPS 请求";
            case 1030:
                return "内部服务超时";
            case 2002:
                return "API调用频率超出该应用的限制";
            case 2003:
                return "该应用appkey已被限制调用 API";
            case 2004:
                return "无权限执行当前操作";
            case 2005:
                return "信息发送量超出合理范围";
            case -1:
                return "无法连接推送服务器或连接超时";
            case -2:
                return "请求出错";
            case -3:
                return "非空参数出现空值";
            case -4:
            default:
                return "其它错误, 请查看错误日志";
        }
    }


}
