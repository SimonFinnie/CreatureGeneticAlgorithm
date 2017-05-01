package assign2;

import java.util.*;

public class World{
    
    private Monster[] monsters;
    private Creature[] creatures;
    private int[][] berryGrid;
    private int[][] mushroomGrid;
    private int finalTime = 0;
    private Draw worldImage;

    private final int MONSTERDELAY;

    public World(int worldLength, int worldWidth, int t, Creature[] creatures, Monster[] monsters, int finalTime){
        this.monsters = monsters;
        this.creatures = creatures;
        this.berryGrid = new int[worldLength][worldWidth];
	this.mushroomGrid = new int[worldLength][worldWidth];
	this.finalTime = finalTime;
	setSightRange(worldLength, worldWidth);
	monsters[0].setEdge((worldLength-1),(worldWidth-1));
	creatures[0].setEdge((worldLength-1),(worldWidth-1));
	generateWorld(worldLength*worldWidth);
	findInitialDeaths();
        MONSTERDELAY = t;
	worldImage = new Draw(worldLength, worldWidth, creatures[0].getEnergy(), monsters, creatures, berryGrid, mushroomGrid);
    }
    
    public void findInitialDeaths(){
	int[] monsterPos, creaturePos;
	for(int i = 0; i < creatures.length; i++){
	    for(int j = 0; j < monsters.length; j++){
		creaturePos = creatures[i].getPosition();
		monsterPos = monsters[j].getPosition();
		if(creaturePos[0] == monsterPos[0] && creaturePos[1] == monsterPos[1]){
		    creatures[i].dies();
		}
	    }
	}
    }

    public void generateWorld(int worldSize){
	Random posGen = new Random();
	int berryCount = worldSize/2, mushroomCount = worldSize/4, x, y, amount = 0;
	while(berryCount > 0 && mushroomCount > 0){
	    //  System.out.println(berryGrid.length + " " + berryGrid[0].length);
	    x = posGen.nextInt(berryGrid.length);
	    y = posGen.nextInt(berryGrid[0].length);
	    amount = posGen.nextInt(5) + 1;
	    if(mushroomGrid[x][y] == 0){
		berryGrid[x][y]+= amount;
		berryCount-= amount;
	    }
	    x = posGen.nextInt(mushroomGrid.length);
	    y = posGen.nextInt(mushroomGrid[0].length);
	    amount = posGen.nextInt(5) + 1;
	    if(berryGrid[x][y] == 0){
		mushroomGrid[x][y]+=amount;
		mushroomCount-= amount;
	    }
	}
	while(berryCount > 0){
	    x = posGen.nextInt(berryGrid.length);
	    y = posGen.nextInt(berryGrid[0].length);
	    amount = posGen.nextInt(5) + 1;
	    if(mushroomGrid[x][y] == 0){
		berryGrid[x][y]+= amount;
		berryCount-= amount;
	    }
	}
	while(mushroomCount > 0){
	    x = posGen.nextInt(mushroomGrid.length);
	    y = posGen.nextInt(mushroomGrid[0].length);
	    amount = posGen.nextInt(5) + 1;
	    if(berryGrid[x][y] == 0){
		mushroomGrid[x][y]+=amount;
		mushroomCount-= amount;
	    }
	}
    }
    
    public void setSightRange(int l, int w){
	int range = (l + w)/2;
	range /= 10;
	range += 1;
	monsters[0].setSightRange(range);
	creatures[0].setSightRange(range);
    }

    public void run(){
        ArrayList<Creature> aliveCreatures = new ArrayList<Creature>();
	int size = 0;
	int[] creaturePos, monsterPos;
	int[][] monsterPositions = new int[monsters.length][], 
	    livingCreaturePositions = null;
	boolean creatureDied = false;
	for(int i = 0; i < creatures.length; i++){
	    if(creatures[i].isAlive()){
		aliveCreatures.add(creatures[i]);
	    }
	}
	size = aliveCreatures.size();
	livingCreaturePositions = new int[size][];
	for(int i = 0; i < size; i++){
	    livingCreaturePositions[i] = aliveCreatures.get(i).getPosition();
	}
	for(int i = 0; i < monsters.length; i++){
	    monsterPositions[i] = monsters[i].getPosition();
	}
	for(int i = 0; i < finalTime; i++){
            if((i+1)%MONSTERDELAY == 0){
                for(int j = 0; j < monsters.length; j++){
                    if(monsters[j].move(livingCreaturePositions)){
			monsterPos = monsters[j].getPosition();
			monsterPositions[j] = monsterPos;
			for(int k = 0; k < size;k++){
			    creaturePos = creatures[k].getPosition();
			    if(creaturePos[0] == monsterPos[0] && 
			       creaturePos[1] == monsterPos[1]){
				aliveCreatures.get(k).dies();
				aliveCreatures.remove(k);
				size--;
				k--;
				creatureDied = true;
			    }
			}
			if(creatureDied){
			    livingCreaturePositions = new int[size][];
			    for(int k = 0; k < size; k++){
				livingCreaturePositions[k] = aliveCreatures.get(k).getPosition();
			    }
			    creatureDied = false;
			}
		    }
                }
            }
	    for(int j = 0; j < size; j++){
		aliveCreatures.get(j).move(berryGrid, mushroomGrid, 
				  livingCreaturePositions, monsterPositions);
		creaturePos = aliveCreatures.get(j).getPosition();
		livingCreaturePositions[j] = creaturePos;
		if(!aliveCreatures.get(j).isAlive()){
		    aliveCreatures.remove(j);
		    j--;
		    size--;
		    livingCreaturePositions = new int[size][];
		    for(int l = 0; l < size; l++){
			livingCreaturePositions[l] = aliveCreatures.get(l).getPosition();
		    }
		}
		else{
		    for(int k = 0; k < monsters.length; k++){	
			monsterPos = monsters[k].getPosition();
			if(creaturePos[0] == monsterPos[0] && 
			   creaturePos[1] == monsterPos[1]){
			    aliveCreatures.get(j).dies();
			    aliveCreatures.remove(j);
			    j--;
			    size--;
			    livingCreaturePositions = new int[size][];
			    for(int l = 0; l < size; l++){
				livingCreaturePositions[l] = aliveCreatures.get(l).getPosition();
			    }
			    break;
			}
		    }
		}
	    }
	    worldImage.update(monsters,creatures,berryGrid,mushroomGrid);
	}
	worldImage.close();
    }

}
