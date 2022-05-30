package ReceiptPrinter;

import com.google.zxing.WriterException;
import com.itextpdf.io.source.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static ReceiptPrinter.QrCode.createQR;

public class ImageConverters {
    public static byte[] getImageAsByteArray(String data, String charset, int height, int width) throws IOException, WriterException {
        return convertToByteArray(createQR(data, charset, height, width));
    }

    public static byte[] convertToByteArray(BufferedImage bi) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, "png", baos);
        return baos.toByteArray();
    }
}
