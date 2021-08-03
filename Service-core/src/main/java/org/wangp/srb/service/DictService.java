package org.wangp.srb.service;

import org.wangp.srb.pojo.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author wangping
 * @since 2021-07-29
 */
public interface DictService extends IService<Dict> {

    List<Dict> getListById(Long id);

    void handlerFileStream(InputStream inputStream);
}
