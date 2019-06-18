package com.njau.spring.web.handler;

import com.njau.spring.beans.BeansFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: jeffchen
 */
public class MappingHandler {

    private String uri;
    private Method method;
    private Class<?> controller;
    private String[] args;

    public MappingHandler (String uri, Method method, Class<?> controller, String[] args) {
        this.uri = uri;
        this.method = method;
        this.controller = controller;
        this.args = args;
    }

    public boolean handle(ServletRequest request , ServletResponse response) throws IllegalAccessException, InstantiationException, InvocationTargetException, IOException {
        String requestUri = ((HttpServletRequest)request).getRequestURI();
        if(!uri.equals(requestUri)){
            return false;
        }

        Object[] parameters= new Object[args.length];
        for (int i=0 ; i<args.length ; i++){
            parameters[i] = request.getParameter(args[i]);
        }

        // controller 可能返回多种类型，用 object 存储结果。
        Object ctl = BeansFactory.getBean(controller);

        Object res = method.invoke(ctl,parameters);
        response.getWriter().println(res);

        return true;
    }
}
