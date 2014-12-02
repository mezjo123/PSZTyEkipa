import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Marzuz on 2014-11-03.
 */
public class Main {

    public int x;

    public static void main(String[] args) {
        System.out.println("testst");
        Obraz moje = new Obraz("lena.jpg"); // tworzenie obrazu
        try {
            moje.inicjalizuj(); // inicjalizacja obiektu
            //moje.testowe();
            moje.generujNowyObraz(0.5);// wlasciwa czesc

        } catch (FileNotFoundException e) {
            System.out.println( "\n Nie ma takiego pliku \n");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
