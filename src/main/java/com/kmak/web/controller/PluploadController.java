package com.kmak.web.controller;

import com.kmak.utils.plupload.Plupload;
import com.kmak.utils.plupload.PluploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * Created by Leaf.Ye on 2017/7/5.
 */
@Slf4j
@Controller
public class PluploadController {

    @RequestMapping(value = "/plupload")
    public String index(){
        return "plupload";
    }

    /**
     * plupload上传插件的上传功能
     * @param plupload
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/plupload/resource")
    public void upload(Plupload plupload, HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            plupload.setRequest(request);
            //path为完整的文件路径
            String path = "";
            File dir = new File(path);
            String filename = PluploadUtil.upload(plupload,dir);
            //判断文件是否上传成功(被分成块的文件是否全部上传完成)，
            // 如果一次性上传多个文件，会分次判断（即上传一个判断一个）
            if (PluploadUtil.isUploadFinish(plupload)){
                //成功后做相应的逻辑处理
            }
        }catch (Exception e){
            e.printStackTrace();
            log.debug("文件上传失败:" + e.getLocalizedMessage());
        }
    }
}
