package com.yingjun.stock.mysql;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author yingjun
 */
public class ConnectionPool {


    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private volatile static ConnectionPool instance;
    private ComboPooledDataSource dataSource;


    public static ConnectionPool getInstance() {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }


    private ConnectionPool() {
        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
        try {
            properties.load(inputStream);
            String driverClass = properties.getProperty("driver.class");
            String jdbcUrl = properties.getProperty("jdbc.url");
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            int initPoolSize = Integer.parseInt(properties.getProperty("init.pool.size"));
            int minPoolSize = Integer.parseInt(properties.getProperty("min.pool.size"));
            int maxPoolSize = Integer.parseInt(properties.getProperty("max.pool.size"));
            dataSource = new ComboPooledDataSource();
            dataSource.setDriverClass(driverClass);
            dataSource.setJdbcUrl(jdbcUrl);
            dataSource.setUser(user);
            dataSource.setPassword(password);
            dataSource.setInitialPoolSize(initPoolSize);
            dataSource.setMinPoolSize(minPoolSize);
            dataSource.setMaxPoolSize(maxPoolSize);
        } catch (Exception e) {
            log.error("Exception:", e);
        }
    }


    public DataSource getDataSource(){
        return dataSource;
    }


    public void destroy() throws SQLException {
        DataSources.destroy(dataSource);
    }

}
