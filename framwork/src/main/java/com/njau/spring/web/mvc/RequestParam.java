package com.njau.spring.web.mvc;

import java.lang.annotation.*;

/**
 * @auther: jeffchen
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RequestParam {
    String value();
}
