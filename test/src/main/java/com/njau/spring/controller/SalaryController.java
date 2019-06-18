package com.njau.spring.controller;

import com.njau.spring.beans.Autowired;
import com.njau.spring.service.SalaryService;
import com.njau.spring.web.mvc.Controller;
import com.njau.spring.web.mvc.RequestMapping;
import com.njau.spring.web.mvc.RequestParam;

/**
 * @author: jeffchen
 */
@Controller
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @RequestMapping("/get_salary.json")
    public Integer getSalary(@RequestParam("name")String name,
                             @RequestParam("experience")String experience){
        return salaryService.calSalary(Integer.parseInt(experience));
    }


}
