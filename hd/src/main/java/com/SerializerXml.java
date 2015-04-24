package com;

import android.os.Environment;
import android.util.Xml;

import com.hd.rock.dao.Rock;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by OTITAN on 2015/4/24.
 */
public class SerializerXml {
    public SerializerXml() {
    }

    /**
     * 生成xml
     */
    public void createUpRocksXml(List<Rock> uprocks) {
        XmlSerializer serializer = Xml.newSerializer();// xml文件生成器
        File file = new File(Environment.getExternalStorageDirectory(),
                "uprocks.xml");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            serializer.setOutput(fos, "utf-8");// 为xml生成器设置输出流和字符编码
            serializer.startDocument("utf-8", true);// 开始文档，参数分别为字符编码和是否保持独立
            serializer.startTag(null, "Rocks"); // 开始标签,参数分别为：命名空间和标签名
            for (Rock rock : uprocks) {

                serializer.startTag(null, "Rock");
                //serializer.attribute(null, "id", person.getId() + "");

                serializer.startTag(null, "Time");// 开始标签
                serializer.text(rock.getTime());// 文本内容
                serializer.endTag(null, "Time");// 结束标签

                serializer.startTag(null, "Latitude");
                serializer.text(rock.getLatitude());
                serializer.endTag(null, "Latitude");

                serializer.startTag(null, "tLongitude");
                serializer.text(rock.getLongitude());
                serializer.endTag(null, "tLongitude");

                serializer.endTag(null, "Rock");

            }
            serializer.endTag(null, "persons");// 结束标签
            serializer.endDocument();// 结束xml文档
            // Toast.makeText(getApplicationContext(), "生成成功！", Toast.LENGTH_SHORT);
        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), "生成失败！", Toast.LENGTH_SHORT);
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
