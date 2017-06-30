import com.kmak.utils.DateTimeUtils;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by Leaf.Ye on 2017/6/13.
 */
public class test {
    public static void main(String [] arg ) throws Exception {
        /*long day = DateTimeUtils.getBetweenDays("2017-02-01","2017-02-02");
        System.out.println(day);*/

        /*Date date = DateTimeUtils.string2Date("2016-02-28","yyyy-MM-dd");
         date = DateTimeUtils.getMonthLastDate(date);
        System.out.println(DateTimeUtils.date2String(date,"yyyy-MM-dd"));*/
        //getBB();
        DateTimeUtils.getDateTimeMsg(new Date());

    }

    public static void getAA() throws IOException, UnsupportedAudioFileException {
        File file = new File("D:\\kmakedu-platform\\item-res\\326417940943564800\\326419792997203968\\1\\0\\20170628153918360.mp3");
        AudioFileFormat aff = AudioSystem.getAudioFileFormat(file);
        Map property = aff.properties();
        if (property.containsKey("duration")){
            long total = (long )property.get("duration");
            System.out.println("获取时长为："+total);
        }else {
            System.out.println("文件没有该属性");
        }
    }

    /**
     * 获取磁盘音频文件的时长
     * @throws Exception
     */
    public static void getBB() throws Exception{
        File file = new File("D:/kmakedu-platform/item-res/326417940943564800/326419792997203968/1/0/20170628153918360.mp3");
        MP3File f = (MP3File) AudioFileIO.read(file);
        MP3AudioHeader audioHeader = (MP3AudioHeader) f.getMP3AudioHeader();
        System.out.println(audioHeader.getTrackLength()/60+":"+audioHeader.getTrackLength()%60);
    }
}
