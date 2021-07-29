package org.wangp.srb.controller;

import org.springframework.web.bind.annotation.*;
import org.wangp.srb.pojo.IntegralGrade;
import org.wangp.srb.service.IntegralGradeService;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin/core/integralGrade")
public class AdminIntegralGradeController {

    @Resource
    private IntegralGradeService integralGradeService;

    @GetMapping("/list")
    public List<IntegralGrade> listAll(){
        return integralGradeService.list();
    }

    @DeleteMapping("/remove/{id}")
    public String deleteById(@PathVariable("id") Long id){

        boolean b = integralGradeService.removeById(id);
        if (b){
            return "delete successful";
        }else {
            return "delete error,please try again";
        }
    }


}