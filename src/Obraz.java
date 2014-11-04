import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Kalsa odpowiada za pobieranie obrazu i jego przetważanie
 * Created by Marzuz on 2014-11-04.
 */
public class Obraz {
    private File wejsciowy = null;
    private int szerokosc = 0;
    private int wysokosc = 0;
    BufferedImage obrazekWejsciowy = null;

    public Obraz(final String nazwaObrazka){
        wejsciowy = new File(nazwaObrazka);
        System.out.println("Obraz [ " + nazwaObrazka + " ] załadowany \n");
    }

    public void wyswietlINT() throws IOException {
        obrazekWejsciowy = ImageIO.read(wejsciowy);
        System.out.println("Obrazek zaladowany");
    }

    private static int getR(int in) {
        return (int)((in << 8) >> 24) & 0xff;
    }
    private static int getG(int in) {
        return (int)((in << 16) >> 24) & 0xff;
    }
    private static int getB(int in) {
        return (int)((in << 24) >> 24) & 0xff;
    }
    private static int toRGB(int r,int g,int b) {
        return (int)((((r << 8)|g) << 8)|b);
    }

    public void testowe(){
        int s = obrazekWejsciowy.getRGB(1,0);
        System.out.println("[ "+getR(s)+" "+getG(s)+" "+getB(s)+" ]\n");
    }
}
