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

    private PushPayload buildPushPayLoad(String title, String content, String deviceId, Map<String, String> extra, DeviceOS deviceOS, MsgType msgType) throws MsgPushException {
        if (StringUtils.isBlank(title) || StringUtils.isBlank(content)) {
            throw new MsgPushException("title and content must not be blank.");
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
     * @throws MsgPushException
     */
    public int sendNotification(String title, String content, String deviceId, Map<String, String> extra, DeviceOS deviceOS) throws MsgPushException {
        PushPayload payload = buildPushPayLoad(title, content, deviceId, extra, deviceOS, MSG_NOTIFICATION);
        try {
            PushResult result = jPushClient.sendPush(payload);
            if (!result.isResultOK()) {
                throw new MsgPushException("push notification fail, msg id = " + result.msg_id + ";error code = " + result.statusCode);
            }
            return result.statusCode;
        } catch (APIConnectionException e) {
            logger.error("push notification fail", e);
            throw new MsgPushException("push notification fail.", e);
        } catch (APIRequestException e) {
            logger.error("push notification fail", e);
            throw new MsgPushException("push notification fail.", e);
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
     * @throws MsgPushException
     */
    public int sendMessage(String title, String content, String deviceId, Map<String, String> extra, DeviceOS deviceOS) throws MsgPushException {
        PushPayload payload = buildPushPayLoad(title, content, deviceId, extra, deviceOS, MSG_MESSAGE);
        try {
            PushResult result = jPushClient.sendPush(payload);
            if (!result.isResultOK()) {
                throw new MsgPushException("push message fail, msg id = " + result.msg_id + ";error code = " + result.statusCode);
            }
            return result.statusCode;
        } catch (APIConnectionException e) {
            logger.error("push message fail", e);
            throw new MsgPushException("push message fail.", e);
        } catch (APIRequestException e) {
            logger.error("push message fail", e);
            throw new MsgPushException("push message fail.", e);
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
     * @throws MsgPushException
     */
    public int sendNotificationAndMessage(String title, String content, String deviceId, Map<String, String> extra, DeviceOS deviceOS) throws MsgPushException {
        PushPayload payload = buildPushPayLoad(title, content, deviceId, extra, deviceOS, MSG_ALL);
        try {
            PushResult result = jPushClient.sendPush(payload);
            if (!result.isResultOK()) {
                throw new MsgPushException("push notification and message fail, msg id = " + result.msg_id + ";error code = " + result.statusCode);
            }
            return result.statusCode;
        } catch (APIConnectionException e) {
            logger.error("push notification and message fail", e);
            throw new MsgPushException("push notification and message fail.", e);
        } catch (APIRequestException e) {
            logger.error("push notification and message fail", e);
            throw new MsgPushException("push notification and message fail.", e);
        }
    }


}
