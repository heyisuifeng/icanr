package com.kmak.web.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Leaf.Ye on 2017/6/27.
 */
@Controller
public class UeditorController {

    @RequestMapping(value = "/upload/image")
    @ResponseBody
    public Map<String, Object> images (MultipartFile upfile, HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> params = new HashMap<String, Object>();
        try{
            String basePath = LoadPropertiesDataUtils.getValue("lyz.uploading.url");
            String visitUrl = LoadPropertiesDataUtils.getValue("lyz.visit.url");
            if(basePath == null || "".equals(basePath)){
                basePath = "d:/lyz/static";  //与properties文件中lyz.uploading.url相同，未读取到文件数据时为basePath赋默认值
            }
            if(visitUrl == null || "".equals(visitUrl)){
                visitUrl = "/upload/"; //与properties文件中lyz.visit.url相同，未读取到文件数据时为visitUrl赋默认值
            }
            String ext = StringUtils.getExt(upfile.getOriginalFilename());
            String fileName = String.valueOf(System.currentTimeMillis()).concat("_").concat(RandomUtils.getRandom(6)).concat(".").concat(ext);
            StringBuilder sb = new StringBuilder();
            //拼接保存路径
            sb.append(basePath).append("/").append(fileName);
            visitUrl = visitUrl.concat(fileName);
            File f = new File(sb.toString());
            if(!f.exists()){
                f.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(f);
            FileCopyUtils.copy(upfile.getInputStream(), out);
            params.put("state", "SUCCESS");
            params.put("url", visitUrl);
            params.put("size", upfile.getSize());
            params.put("original", fileName);
            params.put("type", upfile.getContentType());
        } catch (Exception e){
            params.put("state", "ERROR");
        }
        return params;
    }
}
