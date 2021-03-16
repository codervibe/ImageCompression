package com.codervibe;
/*
 * Created by Administrator on 2021/3/16  0016
 * DateTime:2021/03/16 11:26
 * Description:
 * Others:
 */

import com.codervibe.Utils.PicUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class PicTest {
    @Test
    public void runTest() throws IOException {
        File file = new File("Screenshot_20210316_121131.jpg");
        //压缩图片到指定120K以内,不管你的图片有多少兆,都不会超过120kb,精度还算可以,不会模糊
        byte[] bytes = PicUtils.compressPicForScale(PicUtils.file2buf(file), 120);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        //生成保存在服务器的图片名称，统一修改原后缀名为:jpg
        String newFileName = UUID.randomUUID() + ".jpg";
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(bytes);
        byte[] bytes1 = byteArrayOutputStream.toByteArray();
        PicUtils.ByteArrayToFile(bytes1,newFileName);

    }
}
