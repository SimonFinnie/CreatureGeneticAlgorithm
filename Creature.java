package assign2;

import java.util.*;

public class Creature{
    
    private int[] position = new int[2];
    private int energy;
    private static int sightRange = 50;
    private static int[] worldEdge = new int[2];
    private boolean eatStrawberry;
    private boolean eatMushroom;
    private int reactStrawberry;
    private int reactMushroom;
    private int reactMonster;
    private int reactCreature;
    private int defaultMove;
    private int eSWeight;
    private int eMRWeight;
    private int rSWeight;
    private int rMRWeight;
    private int rMonWeight;
    private int rCWeight;
    private int energyIncrease;
    private int energyDecrease;
    private int[] weights = new int[6];
    private int[] moves = new int[7];
    
    public Creature(int x, int y, int e, boolean eS, boolean eMR, int rS,
		    int rMR, int rMon, int rC, int defaultMove, int eSW, 
		    int eMRW, int rSW, int rMRW, int rMonW,  int rCW, 
		    int energyIncrease, int energyDecrease){
        this.position[0] = x; //x position
        this.position[1] = y; //y position
	this.energy = e; //energy
	this.eatStrawberry = eS;
	this.eatMushroom = eMR;
	this.reactStrawberry = rS;
	this.reactMushroom = rMR;
	this.reactMonster = rMon;
	this.reactCreature = rC;
	this.defaultMove = defaultMove;
	this.eSWeight = eSW;
	this.weights[0] = eSW;
	this.eMRWeight = eMRW;
	this.weights[1] = eMRW;
	this.rSWeight = rSW;
	this.weights[2] = rSW;
	this.rMRWeight = rMRW;
	this.weights[3] = rMRW;
	this.rMonWeight = rMonW;
	this.weights[4] = rMonW;
	this.rCWeight = rCW;
	this.weights[5] = rCW;
	this.energyIncrease = energyIncrease;
	this.energyDecrease = energyDecrease;
	for(int i = 0; i < moves.length; i++){
	    this.moves[i] = i+1;
	}
	sortMoves(weights.length);
    }
    
    public void sortMoves(int n){
	int hold = 0;
	boolean sorted = false;
	double random = 0;
	while(!sorted){
	    sorted = true;
	    for(int i = 1; i < n; i++){
		random = Math.random();
		if(weights[i] > weights[i-1] || (weights[i] == weights[i-1] && random
						 > 0.5)){
		    hold = weights[i-1];
		    weights[i-1] = weights[i];
		    weights[i] = hold;
		    hold = moves[i-1];
		    moves[i-1] = moves[i];
		    moves[i] = hold;
		    sorted = false;
		}
	    }
	}
    }
    
    public void setEdge(int maxX, int maxY){
	worldEdge[0] = maxX;
	worldEdge[1] = maxY;
    }

    public void move(int[][] berryGrid, int[][] mushroomGrid, int[][] othersPos,
		     int[][] monstersPos){
	boolean moved = false;
	int currentMove = 0;
	while(!moved){
	    switch(moves[currentMove]){
                case 1:
                    if(!eatStrawberry(berryGrid)){
                        currentMove++;
                    }
                    else{
                        moved = true;
                    }
                    break;
                case 2:
                    if(!eatMushroom(mushroomGrid)){
                        currentMove++;
                    }
                    else{
                        moved = true;
                    }
                    break;
                case 3:
                    if(!reactStrawberry(berryGrid)){
                        currentMove++;
                    }
                    else{
                        moved = true;
                    }
                    break;
                case 4:
                    if(!reactMushroom(mushroomGrid)){
                        currentMove++;
                    }
                    else{
                        moved = true;
                    }
                    break;
                case 5:
                    if(!reactMonster(monstersPos)){
                        currentMove++;
                    }
                    else{
                        moved = true;
                    }
                    break;
                case 6:
                    if(!reactCreature(othersPos)){
                        currentMove++;
                    }
                    else{
                        moved = true;
                    }
                    break;
                case 7:
                    defaultMove();
                    moved = true;
	    }
	}
	energy -= energyDecrease;
    }

    public boolean eatStrawberry(int[][] berryGrid){
	if(berryGrid[position[0]][position[1]] > 0){
	    if(eatStrawberry){
		berryGrid[position[0]][position[1]]--;
		energy += energyIncrease;
	    }
	    return true;
	}
	return false;
    }
    
    public boolean eatMushroom(int[][] mushroomGrid){
	if(mushroomGrid[position[0]][position[1]] > 0){
	    if(eatMushroom){
		mushroomGrid[position[0]][position[1]]--;
		dies();
	    }
	    return true;
	}
	return false;
    }

    public boolean reaction(int react, int[] closest){
	int moveChoice = 0, xDiff = 0, yDiff = 0;
	Random moveChoiceGen;
	switch(react){
            case 1:
                return goTo(closest);
            case 2:
                return runAway(closest);
            case 3:
                if(closest != null){
                    return true;
                }
                return false;
            case 4:
                if(closest != null){
                    randomMove();
                    return true;
                }
                return false;
	}
        return false;
    }
    
    public boolean goTo(int[] closest){
        int xDiff, yDiff, moveChoice;
        Random moveChoiceGen;
        if(closest != null){
	    xDiff = closest[0] - position[0];
	    yDiff = closest[1] - position[1];
	    if(Math.abs(xDiff) > 0 && Math.abs(yDiff) > 0){
		moveChoiceGen = new Random();
		moveChoice = moveChoiceGen.nextInt(2);
		if(moveChoice == 0){
		    position[0] += xDiff/Math.abs(xDiff);
		}
		else{
		    position[1] += yDiff/Math.abs(yDiff);
		}
	    }
	    else if(Math.abs(xDiff) > 0){
                position[0] += xDiff/Math.abs(xDiff);
            }
            else if(Math.abs(yDiff) > 0){
            position[1] += (yDiff/Math.abs(yDiff));
            }
            return true;
        }
        return false;
    }

    public boolean runAway(int[] closest){
        int x = position[0], y = position[1], choice, xDiff, yDiff;
        Random choiceGen;
        if(closest != null){
            choiceGen = new Random();
            if(x != closest[0] && y != closest[1]){
                choice = choiceGen.nextInt(2);
                switch(choice){
                    case 0:
                        if(((xDiff = closest[0] - x) > 0 && x > 0) || (xDiff < 0 && x < worldEdge[0])){
                            position[0] -= xDiff/Math.abs(xDiff);
			    break;
                        }
                    case 1:
                        if(((yDiff = closest[1] - y) > 0 && y > 0) || (yDiff < 0 && y < worldEdge[1])){
                            position[1] -= yDiff/Math.abs(yDiff);
			    break;
                        }
                        else if(((xDiff = closest[0] - x) > 0 && x > 0) || (xDiff < 0 && x < worldEdge[0])){
                            position[0] -= xDiff/Math.abs(xDiff);
			    break;
                        }
                }
            }
            else if(x == closest[0]){
                choice = choiceGen.nextInt(2);
                switch(choice){
                    case 0:
                        if(y > 0){
                            position[1]--;
			    break;
                        }
                    case 1:
                        if(y < worldEdge[1]){
                            position[1]++;
			    break;
                        }
                        else if(y > 0){
                            position[1]--;
			    break;
                        }
                }
            }
            else if(y == closest[1]){
                choice = choiceGen.nextInt(2);
                switch(choice){
                    case 0:
                        if(x > 0){
                            position[0]--;
			    break;
                        }
                    case 1:
                        if(x < worldEdge[0]){
                            position[0]++;
                        }
                        else if(x > 0){
                            position[0]--;
			    break;
                        }
                }
            }
            return true;
        }
        return false;
    }

    public int[] closestLife(int[][] life){
	int x = position[0], y = position[1], xDiff = sightRange + 1, yDiff = sightRange,
	    optionXDiff = 0, optionYDiff = 0;
	int[] closest = null, currentOption;
	for(int i = 0; i < life.length; i++){
	    currentOption = life[i];
	    if((optionXDiff = Math.abs(currentOption[0] - x)) <= sightRange
	       && (optionYDiff = Math.abs(currentOption[1] - y)) <= 
	       sightRange && (optionYDiff != 0 || optionXDiff != 0)){
		if((optionXDiff + optionYDiff) < (xDiff + yDiff)){
		    closest = currentOption;
		    xDiff = optionXDiff;
		    yDiff = optionYDiff;
		}
	    } 
	}
	return closest;
    }

    public int[] closestFood(int[][] grid){
	int x = position[0], y = position[1], closestX = sightRange + 1, closestY = sightRange;
	int[] closest = new int[2];
	boolean foodFound = false;
	for(int i = x - sightRange; i <= x + sightRange; i++){
	    for(int j = y - sightRange; j <= y + sightRange; j++){
		if(i > -1 && i <= worldEdge[0] && j > -1 && j <= worldEdge[1]){
		    if(i != x || j != y){
			if(grid[i][j] > 0){
			    if(foodFound && (Math.abs(i) + Math.abs(j)) < 
			       (Math.abs(closestX) + Math.abs(closestY))){
				closestX = i;
				closestY = j;
			    }
			    else if((Math.abs(i) + Math.abs(j)) < 
				    (Math.abs(closestX) + Math.abs(closestY))){
				closestX = i;
				closestY = j;
				foodFound = true;
			    }
			}
		    }
		}
	    }
	}
	if(foodFound){
	    closest[0] = closestX;
	    closest[1] = closestY;
	    return closest;
	}
	return null;
    }
    
    public boolean reactStrawberry(int[][] berryGrid){
	int[] closest;
	closest = closestFood(berryGrid);
	if(closest != null){
	}
	return reaction(reactStrawberry, closest);
    }
    
    public boolean reactMushroom(int[][] mushroomGrid){
	int[] closest;
	closest = closestFood(mushroomGrid);
	return reaction(reactMushroom, closest);
    }

    public boolean reactMonster(int[][] monsters){
	int[] closest; 
	closest = closestLife(monsters);
	return reaction(reactMonster, closest);
    }
    
    public boolean reactCreature(int[][] others){
	int[] closest;
	closest = closestLife(others);
	return reaction(reactCreature, closest);
    }
    
    public void defaultMove(){
	switch(this.defaultMove){
            case 1: 
                randomMove();
                break;
            case 2:
                if(this.position[1] < worldEdge[1]){
                    position[1]++;
                }
                break;
            case 3:
                if(this.position[1] > 0){
                    position[1]--;
                }
                break;
            case 4:
                if(this.position[0] < worldEdge[0]){
                    position[0]++;
                }
                break;
            case 5:
                if(this.position[0] > 0){
                    position[0]--;
                }
                break;
	}
    }

    public void randomMove(){
	int choice = 0;
	Random choiceGen = new Random();
	choice = choiceGen.nextInt(4);
	switch(choice){
	case 0:
	    if(position[0] < worldEdge[0]){
		position[0] += 1;
		break;
	    }
	    
	case 1:
	    if(position[0] > 0){
		position[0] -= 1;
	    }
	    else{
		position[0] += 1;
	    }
	    break;
	case 2:
	    if(position[1] < worldEdge[1]){
		position[1] += 1;
		break;
	    }
	case 3:
	    if(position[1] > 0){
		position[1] -= 1;
	    }
	    else{
		position[1] += 1;
	    }
	    break;
	}
    }
    
    public int[] getPosition(){
	return position;
    }

    public boolean isAlive(){
	return (energy > 0);
    }

    public void dies(){
	energy = 0;
    }

    public int[] extractChromosome(){
	int[] chromosome = new int[13];
	chromosome[0] = 0;
	if(eatStrawberry){
	    chromosome[0] = 1;
	}
	chromosome[1] = 0;
	if(eatMushroom){
	    chromosome[1] = 1;
	}
	chromosome[2] = reactStrawberry;
	chromosome[3] = reactMushroom;
	chromosome[4] = reactCreature;
	chromosome[5] = reactMonster;
	chromosome[6] = defaultMove;
	chromosome[7] = eSWeight;
	chromosome[8] = eMRWeight;
	chromosome[9] = rSWeight;
	chromosome[10] = rMRWeight;
	chromosome[11] = rMonWeight;
	chromosome[12] = rCWeight;
	return chromosome;
    }

    public void setSightRange(int n){
	sightRange = n;
    }

    public int getEnergy(){
	return energy;
    }
    
}
