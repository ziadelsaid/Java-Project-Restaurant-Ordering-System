import java.util.Scanner;
public class Main {

    static Scanner scannerInt = new Scanner(System.in);
    static Scanner scannerLine = new Scanner(System.in);

    public static void main(String[] args) {

        Restaurant bremerRestaurant  = new Restaurant();
        boolean programStatus = true;
        while (programStatus) {
            programStatus = bremerRestaurant.signIn();
        }
    }
}
