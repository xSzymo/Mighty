package game.mightywarriors.other;

import game.mightywarriors.data.tables.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class PictureOperations {

    public boolean uploadFile(Image picture, MultipartFile file) {

        if (!file.isEmpty())
            try {
                byte[] bytes = file.getBytes();
                File dir = new File(picture.getUrl());
                if (!dir.exists())
                    dir.mkdirs();

                File serverFile = new File(picture.getUrl());
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));

                stream.write(bytes);
                stream.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        return false;
    }

    public void deletePicture(Image image) {
        try {
            File file = new File(image.getUrl());
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void downloadImage(String url, Image image) {
        try {
            URL imageUrl = new URL(url);
            try (InputStream imageReader = new BufferedInputStream(imageUrl.openStream());
                 OutputStream imageWriter = new BufferedOutputStream(
                         new FileOutputStream(image.getUrl()))) {
                int readByte;
                while ((readByte = imageReader.read()) != -1) {
                    imageWriter.write(readByte);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}