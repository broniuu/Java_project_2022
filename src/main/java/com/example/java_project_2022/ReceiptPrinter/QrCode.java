package com.example.java_project_2022.ReceiptPrinter;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Klasa tworzaca kod QR
 */
public final class QrCode {


    /**
     *
     * Create QR
     *
     * @param data  zapisany urzytkownik wraz z portwami, iloscia potraw i cena
     * @param charset  the charset
     * @param height  wysokosc kodu qr
     * @param width  szerokosc kodu qr
     * @return BufferedImage kod qr
     * @throws
    WriterException
     * @throws  IOException

     */
    public static BufferedImage createQR(String data, String charset, int height, int width)
            throws WriterException, IOException
    {

        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<>();

        hashMap.put(EncodeHintType.ERROR_CORRECTION,
                ErrorCorrectionLevel.L);

        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset),
                BarcodeFormat.QR_CODE, width, height);

        return MatrixToImageWriter.toBufferedImage(matrix);
    }

}
