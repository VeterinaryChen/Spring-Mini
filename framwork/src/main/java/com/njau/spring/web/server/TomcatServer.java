package com.njau.spring.web.server;

import com.njau.spring.web.servlet.DispatcherServlet;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

/**
 * @author: jeffchen
 */
public class TomcatServer {

    private Tomcat tomcat;

    private String[] args;

    public TomcatServer (String[] args) {
        this.args = args;
    }

    public void startServer() throws LifecycleException {
        tomcat = new Tomcat();
        tomcat.setPort(6699);
        tomcat.start();

        Context context = new StandardContext();
        context.setPath("");
        ((StandardContext) context).addApplicationLifecycleListener(new Tomcat.FixContextListener());
        DispatcherServlet testServlet = new DispatcherServlet();
        Tomcat.addServlet(context,"DispatcherServlet",testServlet).setAsyncSupported(true);
        context.addServletMappingDecoded("/","DispatcherServlet");
        tomcat.getHost().addChild(context);

        Thread awaitThread = new Thread("tomcat_await_thread"){

            @Override
            public void run(){
                TomcatServer.this.tomcat.getServer().await();
            }
        };
        awaitThread.setDaemon(false);
        awaitThread.start();
    }
}
