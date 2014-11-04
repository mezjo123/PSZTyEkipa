import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by Marzuz on 2014-11-03.
 */
public class Main {

    public int x;

    public static void main(String[] args) {
        System.out.println("testst");
        Obraz moje = new Obraz("huj.bmp");
        try {
            moje.wyswietlINT();
            moje.testowe();
        } catch (FileNotFoundException e) {
            System.out.println( "\n Nie ma takiego pliku \n");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
