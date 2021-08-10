package org.wangp.srb.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.wangp.common.exception.Assert;
import org.wangp.common.exception.BusinessException;
import org.wangp.common.result.ResponseEnum;
import org.wangp.srb.pojo.IntegralGrade;
import org.wangp.common.result.R;
import org.wangp.srb.service.IntegralGradeService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "积分管理")
@RestController
@RequestMapping("/admin/core/integralGrade")
public class AdminIntegralGradeController {
    @Resource
    private IntegralGradeService integralGradeService;
     
    @ApiOperation(value = "查询积分等级列表",notes = "Get方法查询")
    @GetMapping("/list")
    public R listAll(){
        QueryWrapper<IntegralGrade> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("borrow_amount");
        Map<String, Object> map = new HashMap<>();
        List<IntegralGrade> list = integralGradeService.list(queryWrapper);
        map.put("data",list);
//        throw new BusinessException(ResponseEnum.BORROW_AMOUNT_NULL_ERROR.getCode(),
//                ResponseEnum.BORROW_AMOUNT_NULL_ERROR.getMessage());
         return R.ok().data(map);
    }

    @ApiOperation(value = "积分等级表删除",notes = "逻辑删除")
    @DeleteMapping("/remove/{id}")
    public R deleteById(@ApiParam(value = "删除的ID") @PathVariable("id") Long id){
        Map<String, Object> map = new HashMap<>();
        boolean b = integralGradeService.removeById(id);
        if (b){
            map.put("success","删除成功");
            return R.ok().data(map);
        }else {
            map.put("error","delete error,please try again");
            return R.error();

        }
    }
    @ApiOperation(value = "添加积分等级",notes = "添加")
    @PostMapping("/add")
    public R addIntegralGrade(@ApiParam("积分等级列表前端表单")@RequestBody IntegralGrade integralGrade){
      if(null!=integralGrade.getId()&&integralGrade.getId()>0){
            // 修改
            integralGradeService.updateById(integralGrade);
        }else {
            integralGradeService.save(integralGrade);
        }

        return R.ok().message("保存数据成功");
    }



    @ApiOperation(value = "积分等级表修改",notes = "通过id修改数据库资源")
    @GetMapping("/get/{id}")
    public R selectById(@PathVariable("id") Long id ){
        IntegralGrade integralGrade = integralGradeService.getById(id);

        return R.ok().data("integralGrade",integralGrade);

    }

}