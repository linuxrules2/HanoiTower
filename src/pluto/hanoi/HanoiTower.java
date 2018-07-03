package pluto.hanoi;

import java.util.Date;

public class HanoiTower {

    public static void main(String[] args) {
	    // write your code here
        HanoiTower inst = new HanoiTower();

        inst.doHanoiTower(30);
    }

    private void printTime (Date date) {
        System.out.println("Current Time: " + date);
    }

    private void move(String from, String to, String via, int layer) {

        if(layer == 1) {
            // one layer
            System.out.println("Move one disk from " + from + " to " + to + ".");
        }
        else {
            move(from, via, to, layer -1);
            move(from, to, "N/A", 1);
            move(via, to, from, layer -1);
        }
    }

    private void doHanoiTower(int layer) {

        Date start = new Date();

        printTime(start);
        move("Pole1", "Pole2", "Pole3", layer);

        Date end = new Date();
        printTime(start);
        printTime(end);

       //system.out.println("Total Time: " + (end - start));
    }

}
