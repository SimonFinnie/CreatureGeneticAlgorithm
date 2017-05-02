package assign2;

import java.util.*;

/** The monster class, whos main goal is to chase creatures
 */
public class Monster{

    /**position of the monster*/
    private int[] position = new int[2];
    /** the coordinates of the last seen creature*/
    private int[] previousTarget = null;
    /** edge of the world*/
    private static int[] worldEdge = new int[2];
    /**sight range of the monster*/
    private static int sightRange = 0;
    /**Counter of the track time */
    private int trackTime = 0;

    /**Constructor for monster, defines starting position.
     * @param x x coordinate
     * @param y y coordinate
     */
    public Monster(int x,int y){
	position[0] = x; //x position
	position[1] = y; //y position
    }

    /**returns current position.
     * @rerturn int[] representing positon.
     */
    public int[] getPosition(){
	return position;
    }

    /**sets the max x and y values for movement.
     * @param maxX max x coordinate of the map.
     * @param maxY max y coordinate of the map.
     */
    public void setEdge(int maxX, int maxY){
	worldEdge[0] = maxX;
	worldEdge[1] = maxY;
    }

    /**sets the sight range for all monsters.
     * @param n the sight range.
     */
    public void setSightRange(int n){
	sightRange = n;
    }
    /**finds the closest creature or returns null
     * @param foodPos the position of the all food.
     * @return coordinate of closest food.
     */
    public int[] findFood(int[][] foodPos){
	int[] currentOption = null, target = null;
	int xDiff = 0, yDiff = 0, choice = 0, optionXDiff = 0, optionYDiff = 0;
	for(int i = 0; i < foodPos.length; i++){
	    currentOption = foodPos[i];
	    if(Math.abs(optionXDiff = currentOption[0] - position[0]) <= sightRange
	       && Math.abs(optionYDiff = currentOption[1] - position[1]) <= 
	       sightRange){
		if(target == null){
		    trackTime = 2;
		    target = foodPos[i];
		    previousTarget = target;
		    xDiff = optionXDiff;
		    yDiff = optionYDiff;
		}
		else if((optionXDiff + optionYDiff) < (xDiff + yDiff)){
		    xDiff = optionXDiff;
		    yDiff = optionYDiff;
		    target = foodPos[i];
		    previousTarget = target;
		}
	    }
	}
	return target;
    }

    /**Goes towards food or tracks food, or moves randomly depending
     * on presence of food.
     * @param foodPos position of all food.
     * @return if it has eaten something.
     */
    public boolean move(int[][] foodPos){
	int[] target = findFood(foodPos);
	Random choiceGen = new Random();
	if(target == null && trackTime != 0){
	    moveAfter(previousTarget, choiceGen);
	    trackTime--;
	    return false;
	}
	else if(target != null){
	    return moveAfter(target, choiceGen);
	}
	randomMove(choiceGen);
	return false;
    }

    /**Moves towards the target
     * @param target the coordinates of the target.
     * @param choiceGen the random generator used to make choices.
     * @return if it has eaten something.
     */
    public boolean moveAfter(int[] target, Random choiceGen){
	int xDiff = target[0] - position[0], yDiff = target[1] - position[1];
	int choice = 0;
	if(Math.abs(xDiff) > 0 && Math.abs(yDiff) > 0){
	    choice = choiceGen.nextInt(2);
	    if(choice == 1){
		position[1] +=  yDiff/(Math.abs(yDiff));
	    }
	    else{
		position[0] += xDiff/(Math.abs(xDiff));
	    }
	}
	else if(Math.abs(xDiff) > 0){
	    position[0] += xDiff/(Math.abs(xDiff));
	    if(position[0] ==  target[0]){
		trackTime = 0;
		return true;
	    }
	}
	else if(Math.abs(yDiff) > 0){
	    position[1] +=  yDiff/(Math.abs(yDiff));
	    if(position[1] ==  target[1]){
		trackTime = 0;
		return true;
	    }
	}
	else{
	randomMove(choiceGen);
	trackTime = 0;
	}
	return false;
    }

    /**Moves randomly based off a random number generator.
     * @param choiceGen random generator used to make choices.
     */
    public void randomMove(Random choiceGen){
	int choice = choiceGen.nextInt(4);
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

}
    
