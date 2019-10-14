package CritterPack;

import java.awt.*;

public class Orca extends Critter
{
    private String currentSweep = "LEFT";//left, right, or stay -- direction to turn
    private int spread = 1;//every spreadNumb steps, move forwards one tile
    private int spreadNumb = 1999; //wait spreadNumb steps, and then begin to spread
    private int spreadNumbShift = 3;//once we've waited the initial 1999 steps, start waiting a much smaller unit
                                    // of steps between spreading
    private int timeSinceInfect = 0;//how long since we've infected anyone? used for visual display

    //          getMove -- What move shall this critter make?
    //Parameters - CritterInfo - Passes CritterInfo methods for use within getMove.
    //Returns    - Action - which action shall this critter take?
    public Action getMove(CritterInfo info)
    {
        timeSinceInfect++;

        if (info.getFront() == Neighbor.OTHER) {
            spread = 0;
            timeSinceInfect = 0;
            return Action.INFECT;
        }

        if(info.getLeft() == Neighbor.OTHER)
            return Action.LEFT;
        if (info.getRight() == Neighbor.OTHER)
            return Action.RIGHT;
        if (info.getBack() == Neighbor.OTHER)
            return Action.LEFT;

        spread++;
        if (spread%spreadNumb == 0)
        {
            spreadNumb = spreadNumbShift;
            spreadNumbShift = (int)(1+Math.random()*10);
            spread = 0;
            return Action.HOP;
        }

        currentSweep = dirToSweep(info);
        if (currentSweep == "LEFT")
        {
            return Action.LEFT;
        }
        else if (currentSweep == "RIGHT")
        {
            return Action.RIGHT;
        }else if (currentSweep == "STAY")
        {
            return Action.INFECT;
        }



        return Action.HOP;
    }//end of getMove

    //          getColor -- Which color shall we print this creature as?
    //Parameters - None
    //Returns    - Color (Returns a color on a gradient based on how long it's been since this critter infected anyone)
    public Color getColor()
    {
        timeSinceInfect = Math.min(timeSinceInfect,255);
        return new Color((float)((255-timeSinceInfect)/255.0),0.3f,(float)((timeSinceInfect)/255.0));
    }//end of getColor
    

    //          dirToSweep -- tells the critter how it should turn to avoid facing towards a member of its own species
    //Parameters - CritterInfo - Passes CritterInfo methods for use within getMove.
    //Returns    - LEFT, RIGHT, or STAY
    private String dirToSweep(CritterInfo info)
    {
        if (Math.random() < 0.02D)
        {
            if (Math.random() < 0.5D)
            {
                return "LEFT";
            }
            return "RIGHT";
        }


        if (currentSweep == "STAY")
        {
            if (Math.random() < 0.5D)
            {
                currentSweep = "LEFT";
            }
            currentSweep = "RIGHT";
        }
        if ((info.getLeft() == Neighbor.SAME || info.getLeft() == Neighbor.WALL) && (info.getRight() == Neighbor.SAME || info.getRight() == Neighbor.WALL))
        {
            if (info.getFront()==Neighbor.SAME || info.getFront() == Neighbor.WALL)
            {
                if (Math.random() < 0.5D)
                return "LEFT";
                return "RIGHT";
            }
            return "STAY";
        }
        if (currentSweep == "LEFT")
        {
            if (info.getLeft() == Neighbor.SAME || info.getLeft() == Neighbor.WALL)
                return "RIGHT";
            return "LEFT";

        }

        if (currentSweep == "RIGHT")
        {
            if (info.getRight() == Neighbor.SAME ||  info.getRight() == Neighbor.WALL)
                return "LEFT";
            return "RIGHT";

        }

        return "STAY";
    }//end of dirToSweep


    //          toString -- What string should the creature be printed as?
    //Parameters - None
    //Returns    - String (the string to print as)
    public String toString()
    {
        return "0";
    }//end of toString
    
}
