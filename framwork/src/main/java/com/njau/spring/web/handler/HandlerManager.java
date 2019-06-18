package com.njau.spring.web.handler;

import com.njau.spring.web.mvc.Controller;
import com.njau.spring.web.mvc.RequestMapping;
import com.njau.spring.web.mvc.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jeffchen
 */
public class HandlerManager {

    public static List<MappingHandler> mappingHandlerList = new ArrayList<>();

    public static void resolveMappingHandler(List<Class<?>> classList){
        for (Class<?> cls : classList){
            if(cls.isAnnotationPresent(Controller.class)){
                parseHandlerFromController(cls);
            }
        }
    }

    private static void parseHandlerFromController(Class<?> cls){

        //反射获取类的所有方法
        Method[] methods = cls.getDeclaredMethods();
        for(Method method : methods){

            // 若该方法无 RequestMapping 注解，则不处理。
            if(!method.isAnnotationPresent(RequestMapping.class)){
                continue;
            }

            // 从该方法的属性里获得构成 MappingHandler 的属性。
            String uri = method.getDeclaredAnnotation(RequestMapping.class).value();
            List<String> paramNameList = new ArrayList<>();
            // 获取有 RequestParam 注解的参数。
            for(Parameter parameter : method.getParameters()){
                if (parameter.isAnnotationPresent(RequestParam.class)){
                    paramNameList.add(parameter.getDeclaredAnnotation(RequestParam.class).value());
                }
            }
            String[] params = paramNameList.toArray(new String[paramNameList.size()]);
            MappingHandler mappingHandler = new MappingHandler(uri,method,cls,params);

            HandlerManager.mappingHandlerList.add(mappingHandler);
        }
    }
}
