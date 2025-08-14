package com.project.repair.Utils;

import com.project.repair.Entity.RepairWorker;
import com.project.repair.Entity.User;

public class UserContext {

    /**
     * 线程本地变量，用于存储当前线程的用户信息
     */
    private static final ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    private static final ThreadLocal<RepairWorker> repairWorkerThreadLocal = new ThreadLocal<>();

    public static void setUser(User user) {
        userThreadLocal.set(user);
    }

    public static User getUser() {
        return userThreadLocal.get();
    }

    public static void clear() {
        userThreadLocal.remove();
    }

    public static void setRepair(RepairWorker repairWorker) {
        repairWorkerThreadLocal.set(repairWorker);
    }

    public static RepairWorker getRepair() {
        return repairWorkerThreadLocal.get();
    }

    public static void clearRepair() {
        repairWorkerThreadLocal.remove();
    }



    /**
     * 使用springsecret 上下文
     * */

}
