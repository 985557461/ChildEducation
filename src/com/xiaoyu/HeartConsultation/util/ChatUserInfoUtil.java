package com.xiaoyu.HeartConsultation.util;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xiaoyu on 2015/10/28.
 */
public class ChatUserInfoUtil {
    private static ConcurrentHashMap<String, UserInfo> userinfos = new ConcurrentHashMap<String, UserInfo>();

    public static UserInfo getUserInfo(String username) {
        if (userinfos.containsKey(username)) {
            return userinfos.get(username);
        }
        return null;
    }

    public static void putUserInfo(String userId, UserInfo userInfo) {
        userinfos.put(userId, userInfo);
    }

    public static void clear() {
        userinfos.clear();
    }

    public static class UserInfo {
        public String name;
        public String avatar;
    }
}
