import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedList;
import java.util.List;


/**
 * Kalsa odpowiada za pobieranie obrazu i jego przetważanie
 * Created by Marzuz on 2014-11-04.
 */
enum Kolor {Red,Green,Blue}

public class Obraz {
    private File wejsciowy = null;
    BufferedImage obrazekWejsciowy = null;
    BufferedImage obrazekWyjsciowy = null;

    public Obraz(final String nazwaObrazka){
        wejsciowy = new File(nazwaObrazka);
        System.out.println("Obraz [ " + nazwaObrazka + " ] załadowany \n");
    }

    public void inicjalizuj() throws IOException {
        obrazekWejsciowy = ImageIO.read(wejsciowy);
        obrazekWyjsciowy = ImageIO.read(wejsciowy);
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
        int s = obrazekWejsciowy.getRGB(0,0);
        System.out.println("[ "+getR(s)+" "+getG(s)+" "+getB(s)+" ]\n");
    }
    public void generujNowyObraz(double r){
        int x=0, y=0;
        while (y<obrazekWejsciowy.getHeight()){
            while (x<obrazekWejsciowy.getWidth()){
                int red = wyznaczPixelNowegoObrazka(x,y,Kolor.Red,r);
                int green = wyznaczPixelNowegoObrazka(x,y,Kolor.Green,r);
                int blue = wyznaczPixelNowegoObrazka(x,y,Kolor.Blue,r);
                int kolor = toRGB(red,green,blue);
                obrazekWyjsciowy.setRGB(x,y,kolor);
                ++x;
            }
            ++y;
            x=0;
        }
        try {
            ImageIO.write(obrazekWyjsciowy, "jpeg", new File("out3.jpg"));
            System.out.println("\nObraz zostal wygenerowany...");
        } catch (IOException e) {
            System.out.println("\nNie udalo sie zapisac nowego obrazka");
            e.printStackTrace();
        }
    }

    private int wyznaczPixelNowegoObrazka(int x, int y, Kolor kolor, double r ){
        List<Pixel> listaSasiadow = new LinkedList<Pixel>();
        listaSasiadow.clear();
        double wartoscKoloru;
        double wartoscKoloruSasiada;
        int ileSasiadow = 0;
        double MAXmodul = 0;
        double sumaSasaidow = 0;
        double wsp =255;

        wartoscKoloru = wydobadzKolor(x,y,kolor)/wsp;

        //1 [ x-1, y ]
        if(czyWspolrzedneNalezaDoDziedziny(x-1,y)){
            ++ileSasiadow;
            wartoscKoloruSasiada = wydobadzKolor(x-1,y,kolor)/wsp;
            listaSasiadow.add(new Pixel(wartoscKoloru-wartoscKoloruSasiada ));
        }
        //2 [ x-1, y+1 ]
        if(czyWspolrzedneNalezaDoDziedziny(x-1,y+1)){
            ++ileSasiadow;
            wartoscKoloruSasiada = wydobadzKolor(x-1,y+1,kolor)/wsp;
            listaSasiadow.add(new Pixel(wartoscKoloru-wartoscKoloruSasiada ));
        }
        //3 [ x, y+1 ]
        if(czyWspolrzedneNalezaDoDziedziny(x,y+1)){
            ++ileSasiadow;
            wartoscKoloruSasiada = wydobadzKolor(x,y+1,kolor)/wsp;
            listaSasiadow.add(new Pixel(wartoscKoloru-wartoscKoloruSasiada ));
        }
        //4 [ x+1, y+1]`
        if(czyWspolrzedneNalezaDoDziedziny(x+1,y+1)){
            ++ileSasiadow;
            wartoscKoloruSasiada = wydobadzKolor(x+1,y+1,kolor)/wsp;
            listaSasiadow.add(new Pixel(wartoscKoloru-wartoscKoloruSasiada ));
        }
        //5 [ x+1, y]
        if(czyWspolrzedneNalezaDoDziedziny(x+1,y)){
            ++ileSasiadow;
            wartoscKoloruSasiada = wydobadzKolor(x+1,y,kolor)/wsp;
            listaSasiadow.add(new Pixel(wartoscKoloru-wartoscKoloruSasiada ));
        }
        //6 [ x+1, y-1]
        if(czyWspolrzedneNalezaDoDziedziny(x+1,y-1)){
            ++ileSasiadow;
            wartoscKoloruSasiada = wydobadzKolor(x+1,y-1,kolor)/wsp;
            listaSasiadow.add(new Pixel(wartoscKoloru-wartoscKoloruSasiada ));
        }
        //7 [ x, y-1]
        if(czyWspolrzedneNalezaDoDziedziny(x,y-1)){
            ++ileSasiadow;
            wartoscKoloruSasiada = wydobadzKolor(x,y-1,kolor)/wsp;
            listaSasiadow.add(new Pixel(wartoscKoloru-wartoscKoloruSasiada ));
        }
        //8 [ x-1, y-1]
        if(czyWspolrzedneNalezaDoDziedziny(x-1,y-1)){
            ++ileSasiadow;
            wartoscKoloruSasiada = wydobadzKolor(x-1,y-1,kolor)/wsp;
            listaSasiadow.add(new Pixel(wartoscKoloru-wartoscKoloruSasiada ));
        }
        for (Pixel roznica : listaSasiadow) {
            if(Math.abs(roznica.kolor) > MAXmodul)
                MAXmodul = Math.abs(roznica.kolor);
        }

        if(MAXmodul == 0)
            return wydobadzKolor(x,y,kolor);

        for (Pixel roznica : listaSasiadow) {
            roznica.kolor /= MAXmodul*255;
            roznica.kolor /= ileSasiadow;
            try {
                roznica.kolor = -gfo(roznica.kolor,r);
            } catch (BadRangeException e) {
                e.printStackTrace();
            }
        }
        for (Pixel wartoscPixelaSasiedniego : listaSasiadow) {
            sumaSasaidow += wartoscPixelaSasiedniego.kolor;
        }
        sumaSasaidow = sumaSasaidow * 255;
//        try {
//            sumaSasaidow = -gfo(sumaSasaidow,r);
//        } catch (BadRangeException e) {
//            e.printStackTrace();
//        }
        return wydobadzKolor(x,y,kolor) - (int)sumaSasaidow;
    }

    private boolean czyWspolrzedneNalezaDoDziedziny(int x, int y){
        if(x>=0 && x<obrazekWejsciowy.getWidth() && y>=0 && y<obrazekWejsciowy.getHeight())
            return true;
        else
            return false;
    }

    private int wydobadzKolor(int x, int y, Kolor kolor){
        if(Kolor.Red.equals(kolor))
            return getR(obrazekWejsciowy.getRGB(x,y));
        if(Kolor.Green.equals(kolor))
            return getG(obrazekWejsciowy.getRGB(x,y));
        if(Kolor.Blue.equals(kolor))
            return getB(obrazekWejsciowy.getRGB(x,y));
        return 0;
    }

    private double gfo(double x,double r) throws BadRangeException {
        if(x>1||x<-1)
            throw new BadRangeException();
        if(x<0){
            return -Math.pow(-(2*x+Math.pow(x,2)),r);
        }
        else{
            return Math.pow((2*x)-Math.pow(x,2),r);
        }
    }


    private class Pixel{
        public double kolor;
        public Pixel(final double kolor){
            this.kolor = kolor;
        }
    }
    public static class BadRangeException extends Exception{};
}
