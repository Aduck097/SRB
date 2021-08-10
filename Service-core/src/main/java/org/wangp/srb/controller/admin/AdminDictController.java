package org.wangp.srb.controller.admin;


import com.alibaba.excel.EasyExcel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.wangp.common.result.R;
import org.wangp.srb.pojo.Dict;
import org.wangp.srb.pojo.dto.ExcelDictDTO;
import org.wangp.srb.service.DictService;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 数据字典 前端控制器
 * </p>
 *
 * @author wangping
 * @since 2021-07-29
 */
@Api("数据字典管理模块")
@RestController
@RequestMapping("/admin/core/dict")
public class AdminDictController {

    @Resource
    private DictService dictService;


    @ApiOperation("获取所有字典数据")
    @GetMapping("/list/{id}")
    public R getAll(@ApiParam("根据id来查是否有子节点，便于前端显示") @PathVariable("id")Long id){
        List<Dict> list = dictService.getListById(id);

        return R.ok().data("list",list);
    }

    @ApiOperation(value = "接受前端上传过来的表格文件",notes = "controller只用将文件用流传输给service" +
            "，该层只需要返回给页面消息即可")
    @PostMapping("/import")
    public R getXlsFile(@ApiParam("获取用户提交的文件") @RequestParam("file")MultipartFile file) {
        try {
            // 获取流
            InputStream inputStream = file.getInputStream();
            // 调用service方法传入流
            dictService.handlerFileStream(inputStream);
            return R.ok().message("文件上传成功");
        }catch (Exception e) {
            return R.error().message("文件上传失败辽~，快去查看日志"+e.getMessage());
        }

    }
    @ApiOperation(value = "将表格文件传给用户")
    @GetMapping("/export")
    public void exportXls(HttpServletResponse httpServletResponse) {
        ServletOutputStream outputStream =null;
        try {
            // 设置文件
            httpServletResponse.setContentType("application/vnd.ms-excel");
            httpServletResponse.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String name = "mydict";
            String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
//            System.out.println(fileName);
            httpServletResponse.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            // 获取流
            outputStream = httpServletResponse.getOutputStream();
            // 使用流式编程
            List<ExcelDictDTO> list = dictService.list().stream().map(dictDTO->{
                // 创建一个excelDictDTO容器来装遍历出来的dictDTO
                ExcelDictDTO excelDictDTO = new ExcelDictDTO();
                // 使用BeanUtils去将遍历出来的DTO存入到自己创建的容器中
                BeanUtils.copyProperties(dictDTO,excelDictDTO);
                return excelDictDTO;
                // 将集合转换成List
            }).collect(Collectors.toList());
            EasyExcel.write(outputStream).sheet("字典").head(ExcelDictDTO.class).doWrite(list);
        }catch (Exception e) {
            System.out.println("e.getMessage() = " + e.getMessage());
        }

    }

}

