package com.ce.cechat.data.db;

/**
 * @author cekiasoo
 *
 *
 * 数据库配置类
 */

public class UserDbConfig {

    private static final String DATABASE_NAME = "UserDB";

    private static final int DATABASE_VERSION = 1;

    private volatile static UserDbConfig sSqLiteDatabaseConfig;

    private UserDbConfig() {

    }

    public static UserDbConfig getInstance() {
        if (sSqLiteDatabaseConfig == null) {
            synchronized (UserDbConfig.class) {
                if (sSqLiteDatabaseConfig == null) {
                    sSqLiteDatabaseConfig = new UserDbConfig();
                }
            }
        }
        return sSqLiteDatabaseConfig;
    }

    public static String getDatabaseName() {
        return DATABASE_NAME;
    }

    public static int getDatabaseVersion() {
        return DATABASE_VERSION;
    }

}
