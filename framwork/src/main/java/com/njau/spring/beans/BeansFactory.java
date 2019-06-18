package com.njau.spring.beans;

import com.njau.spring.web.mvc.Controller;
import org.apache.naming.factory.BeanFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: jeffchen
 */

public class BeansFactory {

    private static Map<Class<?>,Object> classTobean= new ConcurrentHashMap<>();

    public static Object getBean(Class<?>cls){
        return classTobean.get(cls);
    }

    public static void initBean(List<Class<?>> classList) throws Exception {
        List<Class<?>> toCreate = new ArrayList<>(classList);
        //初始化Bean
        while(toCreate.size()!=0){
            int remainSize = toCreate.size();
            for (int i=0 ; i<toCreate.size() ; i++){
                if(finishCreate(toCreate.get(i))){
                    toCreate.remove(i);
                }
            }
            //防止循环引用陷入死循环
            if(toCreate.size() == remainSize){
                throw new Exception("cycle dependency!");
            }
        }
    }

    private static boolean finishCreate(Class<?> cls) throws IllegalAccessException, InstantiationException {
        //如果该类非 Bean 则从列表中删除。
        if(!cls.isAnnotationPresent(Bean.class) && !cls.isAnnotationPresent(Controller.class)){
            return true;
        }

        Object bean = cls.newInstance();
        for (Field field : cls.getDeclaredFields()){
            if(field.isAnnotationPresent(Autowired.class)){
                Class<?> fieldType = field.getType();
                Object reliantBean = BeansFactory.getBean(fieldType);
                if(reliantBean == null){
                    return false;
                }
                field.setAccessible(true);
                field.set(bean,reliantBean);
            }
        }
        classTobean.put(cls,bean);
        return true;
    }
}
