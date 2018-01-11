package com.neil.servlet;

import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class H2Listener implements ServletContextListener {
	private static final Logger logger = LoggerFactory.getLogger(H2Listener.class);

    //H2数据库服务器启动实例
    private Server server;
    /* 
     * Web应用初始化时启动H2数据库
     */
    public void contextInitialized(ServletContextEvent sce) {
        try {  
        	logger.info("正在启动h2数据库...");
            //使用org.h2.tools.Server这个类创建一个H2数据库的服务并启动服务，由于没有指定任何参数，那么H2数据库启动时默认占用的端口就是8082
            server = Server.createTcpServer().start(); 
            logger.info("h2数据库启动成功...");
        } catch (SQLException e) {  
        	logger.error("启动h2数据库出错：" + e.toString());  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        }  
    }

    /* 
     * Web应用销毁时停止H2数据库
     */
    public void contextDestroyed(ServletContextEvent sce) {
        if (this.server != null) {
            // 停止H2数据库
            this.server.stop();
            this.server = null;
        }
    }
}