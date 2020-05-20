package com.haiyi.listener;

import com.haiyi.netty.server.MyServer;
import com.haiyi.service.impl.DevServiceImpl;
import com.haiyi.utils.SpringUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class NettySocketListener implements ServletContextListener{

    MyServer myServer;
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        SpringUtil.init(servletContextEvent.getServletContext());
        myServer = new MyServer();
    }
}
