package com.ce.cechat.model.db;

/**
 * @author cekiasoo
 *
 *
 * 数据库配置类
 */

public class DbConfig {

    private static final int DATABASE_VERSION = 1;

    private volatile static DbConfig sSqLiteDatabaseConfig;

    private DbConfig() {

    }

    public static DbConfig getInstance() {
        if (sSqLiteDatabaseConfig == null) {
            synchronized (DbConfig.class) {
                if (sSqLiteDatabaseConfig == null) {
                    sSqLiteDatabaseConfig = new DbConfig();
                }
            }
        }
        return sSqLiteDatabaseConfig;
    }

    public static int getDatabaseVersion() {
        return DATABASE_VERSION;
    }

}
