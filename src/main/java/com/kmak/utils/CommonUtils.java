package com.kmak.utils;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Slf4j
public class CommonUtils {
	
	public static void getCaptcha(HttpServletRequest request, HttpServletResponse response, String sessionName) throws Exception {
    	int width = 120;
    	int height = 35;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // 设置背景色
        g.setColor(new Color(237, 237, 237));
        g.fillRect(0, 0, width, height);
        // 设置边框
        g.setColor(new Color(237, 237, 237));
        g.drawRect(1, 1, width - 2, height - 2);
        // 画干扰线
        g.setColor(Color.darkGray);
        for (int i = 0; i < 6; i++) {
            int x1 = new Random().nextInt(width);
            int y1 = new Random().nextInt(height);
            int x2 = new Random().nextInt(width);
            int y2 = new Random().nextInt(height);
            g.drawLine(x1, y1, x2, y2);
        }
        // 写随机数
        String random = CommonUtils.drawRandomNum((Graphics2D) g);
        request.getSession().setAttribute(sessionName, random);
        // 这三个头是决定所有的浏览器不缓存
        response.setDateHeader("expries", -1);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        ImageIO.write(image, "jpg", response.getOutputStream());
    }
	
    private static String drawRandomNum(Graphics2D g) {
        Map<String, Color> colorMap = new HashMap<>();
        colorMap.put("0", new Color(9, 70, 120));
        colorMap.put("1", new Color(50, 115, 113));
        colorMap.put("2", new Color(52, 36, 10));
        colorMap.put("3", new Color(50, 115, 113));

        g.setFont(new Font("宋体", Font.BOLD, 24));

        String cnCode = "abcdefgh2jkmnp9rstuvwx3yABC7DEF4GHJK5MNPQRS6UVW8XY";
        StringBuffer sb = new StringBuffer();
        int x = 5;
        for (int i = 0; i < 4; i++) {
            g.setColor(colorMap.get(String.valueOf(i)));
            int degree = new Random().nextInt() % 30;
            String ch = cnCode.charAt(new Random().nextInt(cnCode.length()))
                    + "";
            sb.append(ch);
            // 设置旋转的弧度
            g.rotate(degree * Math.PI / 180, x, 20);
            g.drawString(ch, x, 20);
            g.rotate(-degree * Math.PI / 180, x, 20);
            x += 30;
        }
        return sb.toString();
    }
    
    /**
     * 裁剪图片
     *
     * @param src    源地址
     * @param dest   目标地址
     * @param format 文件格式
     * @param x      起点x
     * @param y      起点y
     * @param w      宽度
     * @param h      高度
     * @throws IOException
     */
    public static void cutImage(String src, String dest, String format, int x, int y, int w, int h) {
        Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName(format);
        ImageReader reader = iterator.next();

        InputStream in = null;
        ImageInputStream iis = null;
        try {
            in = new FileInputStream(src);
            iis = ImageIO.createImageInputStream(in);
            reader.setInput(iis, true);
            ImageReadParam param = reader.getDefaultReadParam();
            Rectangle rect = new Rectangle(x, y, w, h);
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            ImageIO.write(bi, "jpg", new File(dest));
        } catch (IOException e) {
            log.error("裁剪图片异常！"+ e);
            throw new RuntimeException("裁剪图片异常！");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (iis != null) {
                try {
                    iis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static BigDecimal str2BigDecimal(String str) {
    	if(str == null || "".equals(str.trim()) || "null".equals(str)) {
    		return new BigDecimal(0);
    	}
    	return new BigDecimal(str);
    }

    public static long javaTimestamp(long time) {
        if (time <= 0) {
            log.error("javaTimestamp meet null argument.");
            throw new IllegalArgumentException("argument is null.");
        }
        //mysql 时间戳只有10位，只精确到秒，而Java时间戳精确到毫秒，故要做处理
        String dateline = String.valueOf(time);
        dateline = dateline.substring(0, 10);
        return Long.parseLong(dateline);
    }

    /**
     * 根据TradeNo获取平台订单号
     *
     * @param tradeno
     * @return
     */
    public static final long getOrderIdFromTradeNo(String tradeno) {
        String[] splitTradeNo = tradeno.split("_");
        return (splitTradeNo[0] != null) ? Long.parseLong(splitTradeNo[0]) : -1;
    }

    /**
     * 将平台订单号转成支付系统所需的TradeNo
     * @param orderid
     * @return
     */
    public static final String genTradeNoFromOrderId(long orderid) {
        //拼接商业订单号 orderId+"_"+timestamp
        //微信商业订单号长度32，支付宝为64
        StringBuffer sb = new StringBuffer();
        sb.append(orderid);
        sb.append("_");
        sb.append(CommonUtils.javaTimestamp(new Date().getTime()));
        return sb.toString();
    }
    
    public static final String getMonthFirstDayMonString(Date nowDate){
    	Calendar cale = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 获取前月的第一天  
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        String firstday = format.format(cale.getTime());
        return firstday;
    }
    
    public static final String getMonthLastDaySunString(Date nowDate){
    	Calendar cale = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 获取前月的最后一天  
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        String lastday = format.format(cale.getTime());
        return lastday;
    }
    
    public static final List<String> getAllDay(String startDayStr, String endDayStr) {
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar startDay = Calendar.getInstance();
    	Calendar endDay = Calendar.getInstance();
    	try {
			startDay.setTime(format.parse(startDayStr));
			endDay.setTime(format.parse(endDayStr)); 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    	if (startDay.compareTo(endDay) >= 0) {  
    	   return null;  
    	}  
    	Calendar tempDay = startDay;
    	List<String> list = new ArrayList<String>();
    	list.add(startDayStr);
    	while (true) {  
    	   // 日期加一  
    		tempDay.add(Calendar.DATE, 1);
    	   // 日期加一后判断是否达到终了日，达到则终止
    	   if (tempDay.compareTo(endDay) == 0) {  
    		   break;  
    	   }  
    	   list.add(format.format(tempDay.getTime()));
    	}
    	list.add(endDayStr);
    	return list;
    }
    
    public static final List<String> batchConversionDay(List<String> dateData) {
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	DateFormat format2 = new SimpleDateFormat("yyyy/MM/dd");
    	List<String> newList = new ArrayList<String>();
    	try {
	    	for(String str:dateData) {
	    		Date temp = format.parse(str);
	    		newList.add(format2.format(temp));
	    	}
    	} catch (ParseException e) {
			e.printStackTrace();
		}
    	return newList;
    }
}
