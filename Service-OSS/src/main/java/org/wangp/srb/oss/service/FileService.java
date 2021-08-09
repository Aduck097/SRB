package org.wangp.srb.oss.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 2021/8/4
 *
 * @author PingW
 */
public interface FileService {

    void uplodeFile(String filename, String moudle, InputStream inputStream);

    void delete(String url);
}
