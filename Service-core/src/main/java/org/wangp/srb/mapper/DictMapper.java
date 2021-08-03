package org.wangp.srb.mapper;

import org.wangp.srb.pojo.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.wangp.srb.pojo.dto.ExcelDictDTO;

import java.util.List;

/**
 * <p>
 * 数据字典 Mapper 接口
 * </p>
 *
 * @author wangping
 * @since 2021-07-29
 */
public interface DictMapper extends BaseMapper<Dict> {

    void insertBatch(List<ExcelDictDTO> list);
}
