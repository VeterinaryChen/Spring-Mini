package com.njau.spring.service;

import com.njau.spring.beans.Bean;

/**
 * @author: jeffchen
 */
@Bean
public class SalaryService {

    public Integer calSalary(Integer experience){
        return experience * 5000;
    }
}
