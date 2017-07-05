package com.kmak.utils.plupload;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Leaf.Ye on 2017/4/7.
 */
@Data
public class Plupload {

    //文件临时名(大文件被分解时)或原名
    private String name;
    //总的块数
    private int chunks = -1;
    //当前块数(从0开始计数)
    private int chunk = -1;
    //HttpServletRequest对象，不能直接传入进来，需要手动传入
    private HttpServletRequest request;
    //保存文件上传信息，不能直接传入进来，需要手动传入
    private MultipartFile multipartFile;
}
