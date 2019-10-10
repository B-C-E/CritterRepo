package CritterPack;

import java.awt.*;

public class Orca extends Critter
{
    private String lastDirFacing = ">";
    private int numbNeighbors;
    private boolean stateSweep; //sweeping = true, moving forwards = false
    private String currentSweep = "LEFT";//left, right, or stay -- direction to turn
    private int spread = 1;//every spreadNumb steps, move forwards one tile
    private int spreadNumb = 1999;
    private int spreadNumbShift = 10;// after a while, switch to spreading more often
    private int timeSinceInfect = 0;//how long since we've infected anyone?

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
            spread = 0;
            return Action.HOP;
        }

        //numbNeighbors = getNumbNeighbors(info);


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
    }

    public Color getColor()
    {
        timeSinceInfect = Math.min(timeSinceInfect,255);
        return new Color((float)((timeSinceInfect)/255.0),0.0f,(float)((timeSinceInfect)/255.0));
    }

    private int getNumbNeighbors(CritterInfo info)
    {
        int count = 0;
        if ((info.getFront()==Neighbor.SAME )|| (info.getFront() == Neighbor.WALL))
            count++;

        if ((info.getRight()==Neighbor.SAME )|| (info.getRight() == Neighbor.WALL))
            count++;

        if ((info.getLeft()==Neighbor.SAME )|| (info.getLeft() == Neighbor.WALL))
            count++;

        if ((info.getBack()==Neighbor.SAME )|| (info.getBack() == Neighbor.WALL))
            count++;

        return count;
    }

    //          dirToFace -- what direction faces away from other members of the same species?
    //Parameters - CritterInfo - Passes CritterInfo methods for use within getMove.
    //Returns    - LEFT or RIGHT
    private String dirToSweep(CritterInfo info)
    {
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
                return "LEFT";
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
    }

    public String toString()
    {
        return "0";
    }
}
