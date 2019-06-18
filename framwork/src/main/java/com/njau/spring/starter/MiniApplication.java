package com.njau.spring.starter;

import com.njau.spring.core.ClassScanner;
import com.njau.spring.web.handler.HandlerManager;
import com.njau.spring.web.server.TomcatServer;
import org.apache.catalina.LifecycleException;

import java.io.IOException;
import java.util.List;

/**
 * @author: jeffchen
 */
public class MiniApplication {

    public static void run(Class<?> clz , String[] args){
        System.out.println("Hello Mini-Spirng!");
        TomcatServer tomcatServer = new TomcatServer(args);
        try {
            tomcatServer.startServer();
            List<Class<?>> classList = ClassScanner.scanClasses(clz.getPackage().getName());
            HandlerManager.resolveMappingHandler(classList);
            classList.forEach(item -> System.out.println(item.getName()));
        } catch (LifecycleException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
