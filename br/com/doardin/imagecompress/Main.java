package br.com.doardin.imagecompress;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class Main {
    
    /**
     * Compress an image bytes
     * 
     * @param bytes to compress
     * @return compressed bytes
     */
    public byte[] compressImage(byte[] fileToCompress) {
        try {

            BufferedImage input = ImageIO.read(new ByteArrayInputStream(fileToCompress));

            BufferedImage inputTransformed = new BufferedImage(input.getWidth(), input.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            inputTransformed.createGraphics().drawImage(input, 0, 0, Color.WHITE, null);

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
            ImageWriter writer = (ImageWriter) writers.next();

            ImageOutputStream ios = ImageIO.createImageOutputStream(out);
            writer.setOutput(ios);

            ImageWriteParam param = writer.getDefaultWriteParam();

            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.20f);
            writer.write(null, new IIOImage(inputTransformed, null, null), param);

            out.close();
            ios.close();
            writer.dispose();

            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
