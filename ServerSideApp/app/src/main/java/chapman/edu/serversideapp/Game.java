package chapman.edu.serversideapp;

/**
 * Created by DCole on 5/8/17.
 */

import java.util.ArrayList;
import java.util.*;
import java.io.*;
import java.util.Collections;

public class Game
{
    private String mId;
    private String mAddress;
    private ArrayList <Player> players;

    public Game()
    {
        players = new ArrayList<>();
    }

    void addPlayer(Player p)
    {
        players.add(p);
    }

    void removePlayer(Player pEliminated, Player pKiller)
    {
        players.remove(pEliminated);
        pKiller.setTarget(pEliminated.getTarget());
    }

    void printPlayers()
    {
        for (Player p : players)
        {
            System.out.println("");
            System.out.println("Name: "+p.getAddress());
            System.out.println("Target: "+p.getTarget().getAddress());
            System.out.println("");
        }
    }

    void assignTargets()
    {
        Collections.shuffle(players);
        int i = 1;

        for(Player p: players)
        {
            if(i == players.size())
            {
                i = 0;
            }
            p.setTarget(players.get(i));
            i++;
        }
    }
}