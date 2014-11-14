import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Marzuz on 2014-11-03.
 */
public class Main {

    public int x;

    public static void main(String[] args) {
        System.out.println("testst");
        Obraz moje = new Obraz("lena.jpg");
        try {
            moje.inicjalizuj();
            //moje.testowe();
            moje.generujNowyObraz(0.5);

        } catch (FileNotFoundException e) {
            System.out.println( "\n Nie ma takiego pliku \n");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
