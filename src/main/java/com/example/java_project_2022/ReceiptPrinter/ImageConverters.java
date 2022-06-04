package com.example.java_project_2022.ReceiptPrinter;

import com.google.zxing.WriterException;
import com.itextpdf.io.source.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.example.java_project_2022.ReceiptPrinter.QrCode.createQR;


/**
 * klasa Image converters przekształcająca buffered image na byte array.
 *
 * potrzeba do zapisywania kodu qr w pdf
 */
public class ImageConverters {

    /**
     *
     * klasa posrednia ułatwiająca pobranie kodu qr jako byte[]
     *
     * @param data  the data
     * @param charset  the charset
     * @param height  the height
     * @param width  the width
     * @return the image as byte array
     * @throws   IOException
     * @throws  WriterException
     */
    public static byte[] getImageAsByteArray(String data, String charset, int height, int width) throws IOException, WriterException {

        return convertToByteArray(createQR(data, charset, height, width));
    }


    /**
     *
     * konwertuje bufferd image do byte[]
     *
     * @param bi  the bi
     * @return byte[]
     * @throws   IOException
     */
    public static byte[] convertToByteArray(BufferedImage bi) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, "png", baos);
        return baos.toByteArray();
    }
}
