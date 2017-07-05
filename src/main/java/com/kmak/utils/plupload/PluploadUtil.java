package com.kmak.utils.plupload;

import com.kmak.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Leaf.Ye on 2017/4/7.
 */
@Slf4j
public class PluploadUtil {

    private static final int BUF_SIZE = 2*1024;

    /**
     * 用于Plupload插件的文件上传,自动生成唯一的文件保存名
     * @param plupload
     * @param dir
     * @throws IllegalStateException
     * @throws Exception
     */
    public static String upload(Plupload plupload, File dir) throws IllegalStateException, Exception {
        //生成唯一的文件名
        String filename = DateTimeUtils.date2String(new Date(), "yyyyMMddHHmmssSSS") + plupload.getName().substring(plupload.getName().lastIndexOf("."));
        upload(plupload,dir,filename);
        return filename;
    }

    /**
     * 用于Plupload插件的文件上传
     * @param plupload - 存放上传所需参数的bean
     * @param dir - 保存目标文件目录
     * @param filename - 保存的文件名
     * @throws IllegalStateException
     * @throws IOException
     */
    public static void upload(Plupload plupload, File dir, String filename) throws IllegalStateException, IOException {
        int chunks = plupload.getChunks(); //获取总的碎片数
        int chunk = plupload.getChunk();   //获取当前的碎片（从0开始计数）

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) plupload.getRequest();
        MultiValueMap<String, MultipartFile> map = multipartRequest.getMultiFileMap();

        if (!CollectionUtils.isEmpty(map)){
            if (!dir.exists()){
                dir.mkdirs();
            }

            //事实上迭代器中只存在一个值，所以只需要返回一个值即可
            Iterator<String> iterator = map.keySet().iterator();
            while (iterator.hasNext()){
                String key = iterator.next();
                List<MultipartFile> fileList = map.get(key);
                for (MultipartFile multipartFile : fileList) {
                    //因为只存在一个值，所以最后返回的既是第一个也是最后一个值
                    plupload.setMultipartFile(multipartFile);

                    //创建新目标文件
                    File targetFile = new File(dir.getPath() + "/" + filename);

                    //当chunks > 1 则说明当前传的文件为一块碎片，需要合并
                    if (chunks > 1){
                        //创建临时的文件名，最后再更改名称
                        File tempFile = new File(dir.getPath() + "/" + multipartFile.getName());
                        //如果chunk == 0，则代表第一块碎片，不需要合并
                        saveUploadFile(multipartFile.getInputStream(), tempFile, chunk == 0 ? false : true);
                        //上传合并完成，则将临时名称更改为指定名称
                        if (chunks - chunk == 1){
                            tempFile.renameTo(targetFile);
                        }
                    } else {
                        //否则直接将文件内容拷贝至新文件
                        //使用transferTo（dest）方法将上传文件写到服务器上指定的文件
                        multipartFile.transferTo(targetFile);
                    }
                }
            }
        }
    }

    /**
     * 保存上传文件，兼合并功能
     * @param in
     * @param targetFile
     * @param append
     * @throws IOException
     */
    private static void saveUploadFile(InputStream in, File targetFile, boolean append) throws IOException {
        OutputStream out = null;
        try {
            if (targetFile.exists() && append){
                out = new BufferedOutputStream(new FileOutputStream(targetFile, true), BUF_SIZE);
            } else {
                out = new BufferedOutputStream(new FileOutputStream(targetFile), BUF_SIZE);
            }

            byte[] buffer = new byte[BUF_SIZE];
            int len = 0;
            //写入文件
            while ((len = in.read(buffer)) > 0){
                out.write(buffer,0,len);
            }
        }catch (IOException e){
            throw e;
        }finally {
            //关闭输入输出流
            if (null != in){
                try {
                    in.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (null != out){
                try {
                    out.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 判断是否全部上传完成
     * 碎片需合并后才返回真
     * @param plupload
     * @return
     */
    public static boolean isUploadFinish(Plupload plupload){
        return (plupload.getChunks() - plupload.getChunk() == 1);
    }

    /**
     * 删除文件
     * @param filename 文件名称
     * @return
     */
    public static boolean deleteFile(String filename) throws IOException {
        File file = new File(filename);
        if (file.exists() && file.isFile()){
            if (file.delete()){
                log.error("删除文件成功");
                return true;
            }else {
                log.error("删除文件失败");
                return false;
            }
        } else {
            log.error("删除文件失败,文件不存在");
            return false;
        }
    }

    /**
     * 获取音频时长
     * @param fileUrl
     * @return
     * @throws LineUnavailableException
     * @throws IOException
     * @throws UnsupportedAudioFileException
     */
    public static String getAudioDuration(String fileUrl){
        File file = new File(fileUrl);
        MP3File f = null;
        String duration = "";
        try {
            f = (MP3File) AudioFileIO.read(file);
            MP3AudioHeader audioHeader = f.getMP3AudioHeader();
            duration = audioHeader.getTrackLength()/60+":"+audioHeader.getTrackLength()%60;

        } catch (CannotReadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TagException e) {
            e.printStackTrace();
        } catch (ReadOnlyFileException e) {
            e.printStackTrace();
        } catch (InvalidAudioFrameException e) {
            e.printStackTrace();
        }
        return duration;
    }

}
