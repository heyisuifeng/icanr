package com.kmak.utils;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.util.CollectionUtils;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析xml工具：带有CDATA格式的
 * Created by Leaf.Ye on 2017/4/13.
 */
public class JdomParseXmlUtils {

    /**
     * 将xml字符串转换为map集合
     * @param xml
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String,String> xmlString2Map(String xml){
        Map<String,String> map = new HashMap<>();
        try {
            StringReader reader = new StringReader(xml);
            // 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
            InputSource source = new InputSource(reader);
            // 创建一个新的SAXBuilder
            SAXBuilder sb = new SAXBuilder();
            // 通过输入源构造一个Document
            Document doc =  sb.build(source);

            //获取根节点
            Element root = doc.getRootElement();
            List<Element> list = root.getChildren();

            if (!CollectionUtils.isEmpty(list)){
                for (Element element : list) {
                    map.put(element.getName(),element.getText());
                }
            }
        } catch (JDOMException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }
}
