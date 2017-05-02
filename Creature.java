package assign2;

import java.util.*;

/** The creature class creates a represenation of a creatures and holds all its actions*/
public class Creature{

    /**position of the creture*/
    private int[] position = new int[2];
    /**energy of the creature*/
    private int energy;
    /**sight range of the creature*/
    private static int sightRange = 50;
    /**edges of the world*/
    private static int[] worldEdge = new int[2];
    /**chromosome representing whether or not it will eat the berry*/
    private boolean eatStrawberry;
    /**chromosome representing whether or not it will eat the mushroom*/
    private boolean eatMushroom;
    /**chromosome representing how it will react to seeing a berry*/
    private int reactStrawberry;
    /**chromosome representing how it will react to seeing a mushroom*/
    private int reactMushroom;
    /**chromosome representing how it will react to seeing a monster*/
    private int reactMonster;
    /**chromosome representing how it will react to seeing another creature*/
    private int reactCreature;
    /**chromosome representing what the default move will be*/
    private int defaultMove;
    /**Weight of the eat berry action out of 1000*/
    private int eSWeight;
    /**Weight of the eat mushroom action out of 1000*/
    private int eMRWeight;
    /**Weight of the react berry action out of 1000*/
    private int rSWeight;
    /**Weight of the react mushroom action out of 1000*/
    private int rMRWeight;
    /**Weight of the react monster action out of 1000*/
    private int rMonWeight;
    /**Weight of the react creature action out of 1000*/
    private int rCWeight;
    /**How much eating a berry increases energy*/
    private int energyIncrease;
    /**how much a enery moving costs*/
    private int energyDecrease;
    /**the weights in an array*/
    private int[] weights = new int[6];
    /**an array representing which move to do*/
    private int[] moves = new int[7];
    
    /**The constructor for the class, which sets all the parameters
       and then sorts the weights so that it knows which move to do.
       * @param x the x coordinate
       * @param y the y coordinate
       * @param e the creatues total e.
       * @param eS the eatStrawberry chromosome value.
       * @param eMR the eatMushroom chromosome value.
       * @param rS the reactStrawberry chromosome value.
       * @param rMR the reactMushroom chromosome value.
       * @param rMon the reactMonster chromosome value.
       * @param rC the reactCreature chromosome value.
       * @param defaultMove the defaultMove chromosome value.
       * @param eSW the eatStrawberryWeight chromosome value.
       * @param eMRW the eatMushroomWeight chromosome value.
       * @param rSW the reactStrawberryWeight chromosome value.
       * @param rMRW the reactMushroomWeight chromosome value.
       * @param rMonW the reactMonsterWeight chromosome value.
       * @param rCW the reactCreatureWeight chromosome value.
       * @param energyIncrease the energy increase from eating a strawberry.
       * @param energtDecreaes the energy decrease from making a move.
     */
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

    /**Sorts the weight array from highest to lowest and does
     * the same thing to the moves array, so that the order of
     * moves is known
     * @param n the lenght of the weight array.
     */
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

    /**sets the edge of the world for all creatures
     *  @param maxX the max x value of the map.
     * @param maxY the max y value of the map.
     */
    public void setEdge(int maxX, int maxY){
	worldEdge[0] = maxX;
	worldEdge[1] = maxY;
    }

    /**Based on the moves array it picks which move it will do
     * and then if it can actually do it it will finish, otherwise
     * it will move onto the next move
     * @param berryGrid the grid of berries.
     * @param mushroomGrid the grid of mushrooms.
     * @param othersPos the coordinates of other creatures.
     * @param monsterPos the coordinates of the monsters.
     */
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

    /**Checks if a berry is on the square that the creature is on
     * and if so will do what eatStrawberry tells it to do
     * @param berryGrid the grid of berries.
     * @return if the move could be done.
     */
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
    /**Checks if a mushroom is on the square that the creature is on
     * and if so will do what eatMushroom tells it to do
     * @param mushroomGrid the grid of mushrooms.
     * @return if the move could be done.
     */
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

    /**given the closest point, and react it will tell the creature to
     * either run away, go toward, ignore or move randomly relative
     * to the coordinate closest.
     * @param react an int representing how to react to the thing.
     * @param closest the coordinates of the closest thing.
     * @return if the move could be done.
     */
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

    /**Moves the creature toward the point closest
     * @param closest the coordinate of the closest thing.
     * @return if the move could be done.
     */
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

    /**makes the creature run away from the point closest
     * @param closest the coordinates of the closest thing.
     * @return if the move could be done.
     */
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

    /**Finds the closest thing in the array life and returns it coordinates
     *@param life the coordinates of all this type of life.
     * @return the coordinates of the closest one within sightRange or null.
     */
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
    /** Finds the closest value greater than 0 in the double array grid within the sight range
     * and returns it coordinates
     * @param grid the grid of food.
     * @return the closest food within the line of sight.
     */
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
    /**makes the creature react to seeing a strawberry.
     * @param berryGrid a grid of berries.
     * @return if it can do the move.
     */
    public boolean reactStrawberry(int[][] berryGrid){
	int[] closest;
	closest = closestFood(berryGrid);
	if(closest != null){
	}
	return reaction(reactStrawberry, closest);
    }
    /**makes the creature react to seeing a mushroom.
     * @param mushroomGrid a grid of mushrooms.
     * @return if it can do the move.
     */
    public boolean reactMushroom(int[][] mushroomGrid){
	int[] closest;
	closest = closestFood(mushroomGrid);
	return reaction(reactMushroom, closest);
    }
    /**makes the creature react to seeing a monster.
     * @param monsters the positions of the monsters.
     * @return if it can do the move.
     */
    public boolean reactMonster(int[][] monsters){
	int[] closest; 
	closest = closestLife(monsters);
	return reaction(reactMonster, closest);
    }
    /**makes the creature react to seeing another creature.
     * @param others the positions of the other creatures.
     * @return if it can do the move.
     */
    public boolean reactCreature(int[][] others){
	int[] closest;
	closest = closestLife(others);
	return reaction(reactCreature, closest);
    }

    /**makes the creature do something, defined by the chromosome
     * value.
     */
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
    /**Makes the creature move in a random direction.
     */
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

    /**returns the creature current position
     * @return the position of the creature.
     */
    public int[] getPosition(){
	return position;
    }
    /**returns whether or not the creature is
     * a live
     * @return if the creature is alive.
     */
    public boolean isAlive(){
	return (energy > 0);
    }

    /**sets the creatures energy to 0,
     * i.e. killing it.
     */
    public void dies(){
	energy = 0;
    }

    /**returns an int array representation
     *of the creatures chromosome
     * @return the chromosome
    */
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
    /** sets the sight range for all creatures.
     *@param n the sight range.
     */
    public void setSightRange(int n){
	sightRange = n;
    }
    /** returns the energy of the creture.
     * @param the energy.
     */
    public int getEnergy(){
	return energy;
    }
    
}
