package com.njau.spring.web.mvc;

import java.lang.annotation.*;

/**
 * @auther: jeffchen
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestMapping {
    String value();
}
