package io.github.maxwell_nc.imageloader.utils;

import android.util.Log;

import java.util.ArrayList;

/**
 * 日志工具类
 */
public class LogUtils {

    private static final String LOG_TAG = "ImageLoader";
    private LogInfo mLogInfo;
    private static boolean isLog = false;//默认不打印日志

    private LogUtils(LogInfo logInfo) {
        this.mLogInfo = logInfo;
    }

    public static void switchLog(boolean isLog) {
        LogUtils.isLog = isLog;
    }

    /**
     * 创建日志
     *
     * @return 日志信息
     */
    public static LogInfo log() {
        return new LogInfo();
    }

    /**
     * 打印日志队列
     */
    public void execute() {
        if (isLog) {
            for (String msg : mLogInfo.msgList) {
                android.util.Log.println(mLogInfo.priority, mLogInfo.tag, mLogInfo.mark + msg);
            }
        }
    }

    /**
     * 日志信息
     */
    public static class LogInfo {

        /**
         * 标记
         */
        String mark;

        /**
         * 打印等级
         */
        int priority = Log.INFO;

        /**
         * 日志标记
         */
        String tag = LOG_TAG;

        /**
         * 日志队列
         */
        ArrayList<String> msgList;

        /**
         * 设置日志标记
         *
         * @param tag 标记
         * @return 日志信息
         */
        public LogInfo tag(String tag) {
            this.tag = tag;
            return this;
        }

        /**
         * 添加日志
         *
         * @param msg 日志内容
         * @return 日志信息
         */
        public LogInfo addMsg(String msg) {
            if (msgList == null) {
                msgList = new ArrayList<>();
            }
            msgList.add(msg);
            return this;
        }

        /**
         * 设置日志等级
         *
         * @param priority 日志等级
         * @return 日志信息
         */
        public LogInfo priority(int priority) {
            this.priority = priority;
            return this;
        }

        /**
         * 生成日志信息工具类
         *
         * @return 日志信息工具类
         */
        public LogUtils build() {
            int hashCode = (msgList.get(0) + System.currentTimeMillis()).hashCode();
            mark = "(" + Integer.toHexString(hashCode) + ")";
            return new LogUtils(this);
        }

    }

}
