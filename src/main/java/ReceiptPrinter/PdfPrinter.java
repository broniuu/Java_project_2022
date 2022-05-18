package ReceiptPrinter;

import com.google.zxing.WriterException;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import model.CartItem;
import model.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import static ReceiptPrinter.QrCode.getImageAsByteArray;
import static com.itextpdf.kernel.geom.PageSize.A4;

public class PdfPrinter {
    PdfWriter pdfWriter=new PdfWriter("D:\\123\\Customer.pdf");

    public PdfPrinter() throws FileNotFoundException {
    }

    public void makePdf(User currentUser, List<CartItem> cartItems, boolean delivery, String note) throws IOException, WriterException {
        Random random=new Random();
        long nr= random.nextLong(2, (long) 123.00);

        com.itextpdf.layout.element.List orders=new com.itextpdf.layout.element.List();
        com.itextpdf.layout.element.List orders2=new com.itextpdf.layout.element.List();
        StringBuilder qrOrders= new StringBuilder();
        int it=0;
        double price=0;
        for (CartItem cartItem:cartItems) {
            if(it>=12){
                orders2.add(cartItem.getDish().getName()+" x"+cartItem.getCountOfDish());
                price+=cartItem.getCountOfDish()*cartItem.getDish().getPrice();
                qrOrders.append(cartItem.getDish().getName()).append(" x").append(cartItem.getCountOfDish());
            }
            else{
                orders.add(cartItem.getDish().getName()+" x"+cartItem.getCountOfDish());
                price+=cartItem.getCountOfDish()*cartItem.getDish().getPrice();
                qrOrders.append(cartItem.getDish().getName()).append(" x").append(cartItem.getCountOfDish());
            }
            it++;
        }
        PdfDocument pdfDocument=new PdfDocument(pdfWriter);
        pdfDocument.addNewPage();
        Document document=new Document(pdfDocument);
        pdfDocument.setDefaultPageSize(A4);
        float col=260f;
        float[] columnWidth={col,col};
        Table table=new Table(columnWidth);
        table.setBackgroundColor(new DeviceRgb(65,123,243)).setFontColor(Color.WHITE);
        table.addCell(new Cell().add("Szama(n)")
                .setMargin(30f)
                .setMarginBottom(30f)
                .setFontSize(30f).setBorder(Border.NO_BORDER));

        //tworzenie KODU QR
        String infoToCode=currentUser.getUserId()+" | "+currentUser.getLogin()+" | "+currentUser.getName()+" | "+currentUser.getSurname()+" | "+" ORDER: "+
                nr+"\n"+qrOrders;

        ImageData data= ImageDataFactory.createPng(getImageAsByteArray(infoToCode,"UTF-8",180,180));
        table.addCell(new Cell().add(new Image(data)).setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.RIGHT));


        Table customerinfo=new Table(columnWidth);
        customerinfo.addCell(new Cell(0,4)
                .setFontColor(Color.WHITE)
                .add("Customer INFO")
                .setBold().setBackgroundColor(new DeviceRgb(50,150,250)));
        customerinfo.addCell(new Cell().add("Name & Surname"));
        customerinfo.addCell(new Cell().add(currentUser.getName()+" "+currentUser.getSurname()));
        customerinfo.addCell(new Cell().add("login"));
        customerinfo.addCell(new Cell().add(currentUser.getLogin()));
        customerinfo.addCell(new Cell().add("ID"));
        customerinfo.addCell(new Cell().add(""+currentUser.getUserId()));

        Table header=new Table(columnWidth);

        header.setBackgroundColor(new DeviceRgb(65,123,243));
        header.addCell(new Cell()
                .add("Order")
                .setMargin(30f)
                .setMarginBottom(30f)
                .setFontSize(30f).setBorder(Border.NO_BORDER)
                .setFontColor(Color.WHITE));


        Table customerOrder=new Table(columnWidth);
        customerOrder.setBorder(Border.NO_BORDER).setFontSize(12);


        Table summary=new Table(columnWidth);
        summary.setBackgroundColor(new DeviceRgb(65,123,243));
        summary.addCell(new Cell()
                .setFontColor(Color.WHITE)
                .add("Price: "+price+"zl")
                .setMargin(20f)
                .setMarginBottom(20f)
                .setFontSize(20f).setBorder(Border.NO_BORDER)
                .setFontColor(Color.WHITE));
        if(delivery)summary.addCell("Delivery: YES") .setFontColor(Color.WHITE);else summary.addCell("Delivery: NO") .setFontColor(Color.WHITE);
        if(!note.isEmpty())summary.addCell(note) .setFontColor(Color.WHITE);
        summary.addCell(new Cell().add("Order Number: "+nr) .setFontColor(Color.WHITE));
        Cell listcell1=new Cell();
        Cell listcell2=new Cell();
        listcell1.add(orders);
        listcell2.add(orders2);
        customerOrder.addCell(listcell1);
        customerOrder.addCell(listcell2);


        document.add(table);
        document.add(new Paragraph("\n"));
        document.add(customerinfo);
        document.add(new Paragraph("\n"));
        document.add(header);
        document.add(customerOrder);
        document.add(new Paragraph("\n"));
        document.add(summary);
        document.close();
    }


}
