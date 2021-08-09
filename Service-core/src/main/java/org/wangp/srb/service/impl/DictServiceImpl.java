package org.wangp.srb.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.wangp.srb.listener.ExcelDictDTOListener;
import org.wangp.srb.pojo.Dict;
import org.wangp.srb.mapper.DictMapper;
import org.wangp.srb.pojo.dto.ExcelDictDTO;
import org.wangp.srb.service.DictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author wangping
 * @since 2021-07-29
 */
@Slf4j
@Transactional
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private DictMapper dictMapper;

    @ApiOperation(value = "获取所以字典数据，并利用id来获取子节点",notes ="会将查询到得字典数据存入带redis里去" )
    @Override
    public List<Dict> getListById(Long id) {
        List<Dict> dictList=null;
        // redis 若有值则返回redis缓存里的值
        try {
            List dictListByRedis = (List<Dict>)redisTemplate.opsForValue().get("srb:core:dict" + id);
            if(dictListByRedis!=null){
                log.info("从redis缓存里取出字典数据");
                return dictListByRedis;
            }
        }catch (Exception e){
            log.error("redis缓存取值错误，异常是"+ExceptionUtils.getStackTrace(e));
        }
        log.info("从数据库取值");
        try {
            // 通过parent-id查询出所有的数据
            dictList = dictMapper.selectList(new QueryWrapper<Dict>().eq("parent_id", id));
            // 遍历dictList并且遍历的时候判断是否有子节点
            dictList.forEach(dict -> {
                // 对遍历出来的dict使justifyNode判断是否存在子节点
                boolean b = justifyNode(dict.getId());
                // 若存在子节点则向对象是否存在子节点的属性置为true，反之亦然
                dict.setHasChildren(b);
            });
        }catch (Exception e){
           log.error("数据库查询数据异常"+ ExceptionUtils.getStackTrace(e));
        }
        // 此时redis数据可以里没有值需要将值存入Redis缓存里
        log.info("将值存入redis缓存里");
        try {
            redisTemplate.opsForValue().set("srb:core:dict"+id,dictList,5, TimeUnit.MINUTES);
        }catch (Exception e){
            log.error("将dict存入redis出异常："+ExceptionUtils.getStackTrace(e));
        }
        return dictList;
    }

    @ApiOperation(value = "处理contorller的流文件",notes = "利用ExcleListener方法来读取excel文件")
    @Override
    public void handlerFileStream(@ApiParam("excel文件的流") InputStream inputStream) {
        // 调用EasyExcel来读excle
        EasyExcel
                .read(inputStream, ExcelDictDTO.class,new ExcelDictDTOListener(dictMapper))
                .sheet("数据字典")
                .doRead();
        log.info("importData finished");
    }

    @ApiOperation(value = "利用id来查询dict是否存在子节点",notes = "该方法设计给getListById调用，目的是判断是否有子节点，这样便于给dict对象是设置单一属性")
    private boolean justifyNode(Long id){
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        List<Dict> dicts = dictMapper.selectList(queryWrapper);
        return false;

    }


}
