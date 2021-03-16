package com.codervibe.Utils;
/*
 * Created by Administrator on 2021/3/16  0016
 * DateTime:2021/03/16 11:04
 * Description:
 * Others:
 */

import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author Administrator
 */
public class PicUtils {
    /**
     *    以下是常量,按照阿里代码开发规范,不允许代码中出现魔法值
     */
    private static final Logger logger = LoggerFactory.getLogger(PicUtils.class);
    private static final Integer ZERO = 0;
    private static final Integer ONE_ZERO_TWO_FOUR = 1024;
    private static final Integer NINE_ZERO_ZERO = 900;
    private static final Integer THREE_TWO_SEVEN_FIVE = 3275;
    private static final Integer TWO_ZERO_FOUR_SEVEN = 2047;
    private static final Double ZERO_EIGHT_FIVE = 0.85;
    private static final Double ZERO_SIX = 0.6;
    private static final Double ZERO_FOUR_FOUR = 0.44;
    private static final Double ZERO_FOUR = 0.4;

    /**
     * 根据指定大小压缩图片
     *
     * @param imageBytes  源图片字节数组
     * @param desFileSize 指定图片大小，单位kb
     * @return 压缩质量后的图片字节数组
     */
    public static byte[] compressPicForScale(byte[] imageBytes, long desFileSize) {
        if (imageBytes == null || imageBytes.length <= ZERO || imageBytes.length < desFileSize * ONE_ZERO_TWO_FOUR) {
            return imageBytes;
        }
        long srcSize = imageBytes.length;
        double accuracy = getAccuracy(srcSize / ONE_ZERO_TWO_FOUR);
        try {
            while (imageBytes.length > desFileSize * ONE_ZERO_TWO_FOUR) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
                Thumbnails.of(inputStream)
                        .scale(accuracy)
                        .outputQuality(accuracy)
                        .toOutputStream(outputStream);
                imageBytes = outputStream.toByteArray();
            }
            logger.info("图片原大小={}kb | 压缩后大小={}kb",
                    srcSize / ONE_ZERO_TWO_FOUR, imageBytes.length / ONE_ZERO_TWO_FOUR);
        } catch (Exception e) {
            logger.error("【图片压缩】msg=图片压缩失败!", e);
        }
        return imageBytes;
    }

    /**
     * 自动调节精度(经验数值)
     *
     * @param size 源图片大小
     * @return 图片压缩质量比
     */
    private static double getAccuracy(long size) {
        double accuracy;
        if (size < NINE_ZERO_ZERO) {
            accuracy = ZERO_EIGHT_FIVE;
        } else if (size < TWO_ZERO_FOUR_SEVEN) {
            accuracy = ZERO_SIX;
        } else if (size < THREE_TWO_SEVEN_FIVE) {
            accuracy = ZERO_FOUR_FOUR;
        } else {
            accuracy = ZERO_FOUR;
        }
        return accuracy;
    }

    /**
     * 将文件内容转换成byte数组返回,如果文件不存在或者读入错误返回null
     *
     * 这里需要在内存中创建一个字节数组缓冲区，将读取的文件字节数据写入到缓冲区中
     * 最后将字节流转换成byte数组，并关闭资源
     * 当出现文件找不到异常和输入输出异常时需要捕获
     */
    public static byte[] file2buf(File fobj){
        byte[] buffer = null;
        try{
            if (!fobj.exists())
            {
                return null;
            }

            FileInputStream fis = new FileInputStream(fobj);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len=-1;
            while ((len = fis.read(b)) != -1)
            {
                bos.write(b, 0, len);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return buffer;
    }


    /**
      *字节数组到文件的过程
      */
    public static void ByteArrayToFile(byte[] data,String newFileNmae) {
        //选择源
        File file = new File(newFileNmae);
        //选择流
        FileOutputStream fos = null;
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(data);
            fos = new FileOutputStream(file);
            int temp;
            byte[] bt = new byte[1024*10];
            while((temp = bais.read(bt))!= -1) {
                fos.write(bt,0,temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关流
            try {
                if(null != fos) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
