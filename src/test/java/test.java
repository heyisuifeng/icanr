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
        //DateTimeUtils.getDateTimeMsg(new Date());
        //杨辉三角
        /*int b [][]  = new int[10][10];
        test test = new test();
        b = test.getInt(6,b);
        for (int i=0;i<10;i++){
            for (int j=0;j<10;j++){
                System.out.print(b[i][j]+" ");
            }
            System.out.println();
        }*/

        /*int [] a = new int[4];
        int p =1;
        for (int i=1;i<=3;i++){
            for (int j=1;j<=i;j++){
                int current =a[j];
                a[j] = p+current;
                p = current;
            }
        }*/
    }

    public int [][] getInt(int n,int a [][]){
        if (n==1){
            a[0][1]=1;
            return a;
        }else if (n==2){
            a[0][1]=1;
            a[1][1]=1;
            a[1][2]=2;
            a[1][3]=1;
            return a;
        }else{
            a = getInt(n-1,a);
            int k = n+1;
            for (int i=0;i<=k;i++){
                a[n-1][i+1] = a[n-2][i]+a[n-2][i+1];
            }
            return a;
        }
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
