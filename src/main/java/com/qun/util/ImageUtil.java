package com.qun.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageUtil {
    public static boolean changeSize(int newWidth, int newHeight, String path) {
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(path));

            //字节流转图片对象
            Image bi = ImageIO.read(in);
            //构建图片流
            BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            //绘制改变尺寸后的图
            tag.getGraphics().drawImage(bi, 0, 0, newWidth, newHeight, null);
            //输出流
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path));
            //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            //encoder.encode(tag);
            ImageIO.write(tag, "PNG", out);
            in.close();
            out.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
