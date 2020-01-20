package algo.src;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class inGame {

    private int height;
    private int width;
    private int firstPlayerPoint;
    private int secondPlayerPoint;
    private Map<Map<Integer, Integer>, Map<Integer, Integer>> moves;
    inGame(int height, int width)
    {
        this.height = height;
        this.width = width;
        this.firstPlayerPoint = 0;
        this.secondPlayerPoint = 0;
        this.moves = new HashMap<>();
        int turn = 0;
        while(true)
        {
            yourTurn();
            turn++;
            printnewField();
            if (turn >= 4)
            {
                checkPoint();
            }
        }

    }

    private void checkPoint() {
        for (Map.Entry<Map<Integer, Integer>, Map<Integer, Integer>> entry : moves.entrySet()) {

            Map<Integer, Integer> startPoint = entry.getKey();
            int finish_x;
            int finish_y;
            System.out.println("looking out for points");
            System.out.println(moves);

            /*
            Broken
            for (Map<Integer, Integer> value : entry.getValue()) {
                System.out.println(value);
                if(moves.containsKey(value))
                {
                    System.out.println("found something!");
                }
            }
            */


        }

    }

    private void yourTurn() {

        System.out.println("You start!");
        System.out.println("Please put from which Dot you want to go to the next Dot");

        Scanner scanner = new Scanner(System.in);
        Map<Integer, Integer> inner = new HashMap<>();
        Map<Integer, Integer> outer = new HashMap<>();
        while(true)
            if (scanner.hasNextInt() && scanner.hasNextInt() &&
                scanner.hasNextInt() && scanner.hasNextInt()) {
                int from_height = scanner.nextInt();
                int from_width = scanner.nextInt();
                int to_height = scanner.nextInt();
                int to_width = scanner.nextInt();
                if(from_height <= this.height && from_width <= this.width)
                {
                    if((to_height == from_height + 1 || to_height == from_height || to_height == from_height -1)
                      && (to_width == from_width + 1 || to_width == from_width || to_width == from_width - 1)
                    && to_width <= this.width && to_height <= this.height)
                    {

                        inner.put(from_height, from_width);
                        outer.put(to_height,to_width);
                        if(moves.containsKey(inner))
                        {
                            moves.get(inner).put(to_height,to_width);
                        }
                        else
                        {
                            moves.put(inner, outer);
                        }
                        break;
                    }
                    else
                    {
                        System.out.println("Wrong move!");
                    }
                }
            } else {
                scanner.nextLine();
                System.out.println("Please only numbers!");
                System.out.println("Try it again");
            }

    }

    private void printnewField() {
        boolean drawLine = false;
        boolean found = false;
        for(int i = 0; i < this.height; i++)
        {
            for(int j = 0 ; j < this.width; j++)
            {
                for (Map.Entry<Map<Integer, Integer>, Map<Integer, Integer>> entry : moves.entrySet()) {
                    Map<Integer, Integer> val = entry.getKey();
                    Map<Integer, Integer> val1 = entry.getValue();
                    if (val.containsKey(i)) {
                        int width = val.get(i);
                        if (width == j) {
                            for(Map.Entry<Integer, Integer> map :val1.entrySet())
                            {
                                int to_height = map.getKey();

                                if(to_height == i)
                                {
                                    found = true;
                                }
                            }

                        }
                    }
                }

                if(found)
                {
                    System.out.print("+--");
                    found = false;

                }
                else
                {
                    System.out.print("+");
                    System.out.print("  ");
                }
            }
            System.out.println();


            for(int j = 0 ; j < this.width; j++)
            {
                for (Map.Entry<Map<Integer, Integer>, Map<Integer, Integer>> entry : moves.entrySet()) {
                    Map<Integer, Integer> val = entry.getKey();
                    Map<Integer, Integer> val1 = entry.getValue();
                    if (val.containsKey(i)) {
                        int width = val.get(i);
                        if (width == j) {
                            for(Map.Entry<Integer, Integer> map :val1.entrySet())
                            {
                                int to_height = map.getKey();
                                int to_width = map.getValue();

                                if(to_height == i + 1)
                                {
                                    if(to_width == j)
                                        drawLine = true;
                                }
                            }

                        }
                    }
                }

                if(drawLine)
                {
                    System.out.print("|  ");
                    drawLine = false;
                }
                else
                {
                    System.out.print("   ");
                }
            }
            System.out.println();

        }
    }


}
