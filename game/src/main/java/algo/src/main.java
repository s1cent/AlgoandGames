package algo.src;

import algo.src.inGame;
import java.util.Scanner;


public class main {

        public static int height;
        public static int width;

        public static void main(String[] args) {
            System.out.println("Dots and Boxes!");
            new Game();
            // Outdated
            //start();
        }

        public static void start() {
                System.out.println("How big do you want the field?");
                System.out.println("First is Height and second value is Width");
                boolean error = true;
                Scanner scanner = new Scanner(System.in);
                while(error)
                {
                    if(scanner.hasNextInt() && scanner.hasNextInt()) {
                        height = scanner.nextInt();
                        width = scanner.nextInt();
                        error = false;
                    }
                    else
                    {
                        scanner.nextLine();
                        System.out.println("Please only numbers!");
                        System.out.println("Try it again");
                    }
                }

                System.out.println("Height: " + height + " Width: " + width);
                buildField();
                inGame game = new inGame(height, width);
        }

    private static void buildField() {
            for(int i = 0; i < height ; i ++)
            {
                for(int j = 0; j < width ; j++)
                {
                    System.out.print("+");
                    System.out.print("  ");
                }
                System.out.println();
                System.out.println();
            }
    }

}
