package com.emucoo.manager.component;

public final class SystemConstant {
    //是否debug模式 默认是debug
    public static boolean isDebug =  false;

    public static final int SYSUSER = -1;

    public static final String SLAT = "*emucoo*";

    public static final class AdminResetPwd {
        public static final int RESET = 1;
        public static final int NO_RESET = 0;
    }

    public enum UserAgent {
        UNKNOWN(0), ANDROID(1), IOS(2), WEB(3);

        private int index;

        private UserAgent(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }

}
