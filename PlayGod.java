package assign2;

import java.util.*;

/** Controls the creation and development of the creatures, monsters and
    worlds.
 */
public class PlayGod{

    /**contains all the monsters.*/
    private static Monster[] monsters;
    /**contains all the creatures.*/
    private static Creature[] creatures;
    /**the amount of energy eating a berry gives a creature*/
    private static int foodEnergy = 0;
    /**the amount of energy lost per turn for a creature*/
    private static int energyLoss = 0;
    /**The starting energy each creature has*/
    private static int startingEnergy = 0;
    /**the length of the world, default set to 2*/
    private static int worldLength = 2;
    /**the width of the world, default set to 2*/
    private static int worldWidth = 2;
    /**The number of generations left to go through, default set to 1*/
    private static int generationCount = 1;
    /**number of turns per generations*/
    private static int numTurns = 99;
    /**the number of turns a creature get before a monster does,
     * default set to 2*/
    private static int monsterDelay = 2;
    /**Total number of monsers in the world*/
    private static int numMonsters = 1;

    public static void main(String[] args){
	World world = firstCase();
	world.run();
	generationCount--;
	//System.out.println("fitness: " + getFitness());
	while(generationCount > 0){
	    if(!evolve()){
		break;
	    }
	    world = new World(worldLength, worldWidth, monsterDelay, creatures, monsters, numTurns);
	    world.run();
	    generationCount--;
	    // System.out.println("fitness: " + getFitness());
	}
    }


    public static World firstCase(){
	Random variableSelect = new Random();
	Scanner input = new Scanner(System.in);
	int numCreatures = 1;
	boolean correctInput = false;
	int[][] berryGrid, mushroomGrid;
	System.out.println("What length and width would you like the world? (Must be integers)");
	while(!correctInput){
	    if(input.hasNextInt()){
		worldLength = input.nextInt();
		if(worldLength < 2){
		    System.out.println("Min value is 2.");
		}
		else if(input.hasNextInt()){
		    worldWidth = input.nextInt();
		    if(worldWidth < 2){
			System.out.println("Min value is 2.");
		    }
		    else{
			correctInput = true;
		    }
		}
	    }
	    if(!correctInput){
		System.out.println("Try again.");
	    }
	}
	correctInput = false;
	System.out.println("How many Creatures and Monsters would you like?");
	while(!correctInput){
	    if(input.hasNextInt()){
		 numCreatures = input.nextInt();
		if(numCreatures < 1){
		    System.out.println("Min value is 1.");
		}
		else if(input.hasNextInt()){
		    numMonsters = input.nextInt();
		    if(numMonsters < 1){
			System.out.println("Min value is 1.");
		    }
		    else{
			correctInput = true;
		    }
		}
	     }
	     if(!correctInput){
		System.out.println("Try again.");
	    }
	}
	correctInput = false;
	System.out.println("How many turns do you want each creature to have");
	while(!correctInput){
	    if(input.hasNextInt()){
		numTurns = input.nextInt();
		if(numTurns < 1){
		    System.out.println("Min number is 1.\nTry again.");
		}
		else{
		    correctInput = true;
		}
	    }
	}
	correctInput = false;
	System.out.println("How many moves do you want per creature before the monsters have a turn?");
	while(!correctInput){
	    if(input.hasNextInt()){
		monsterDelay = input.nextInt();
		if(monsterDelay < 1){
		    System.out.println("Min number is 1.\nTry again.");
		}
		else{
		    correctInput = true;
		}
	    }
	}
	correctInput = false;
	System.out.println("How much energy should the creatures start with?");
	while(!correctInput){
	    if(input.hasNextInt()){
		startingEnergy = input.nextInt();
		if(startingEnergy < 1){
		    System.out.println("Min number is 1.\nTry again.");
		}
		else{
		    correctInput = true;
		}
	    }
	}
	correctInput = false;
	System.out.println("What is the energy loss per turn, and gain for eating berries? (Must be integers)");
	while(!correctInput){
	    if(input.hasNextInt()){
		energyLoss = input.nextInt();
		if(energyLoss < 1){
		    System.out.println("Min value is 1.");
		}
		else if(input.hasNextInt()){
		    foodEnergy = input.nextInt();
		    if(foodEnergy < 1){
			System.out.println("Min value is 1.");
		    }
		    else{
			correctInput = true;
		    }
		}
	    }
	    if(!correctInput){
		System.out.println("Try again.");
	    }
	}
	correctInput = false;
	System.out.println("How many generations do you want?");
	while(!correctInput){
	    if(input.hasNextInt()){
		generationCount = input.nextInt();
		if(generationCount < 1){
		    System.out.println("Min number is 1.\nTry again.");
		}
		else{
		    correctInput = true;
		}
	    }
	}
	creatures = makeCreatures(numCreatures, variableSelect);
	monsters = makeMonsters(numMonsters, variableSelect);
	return new World(worldLength, worldWidth, monsterDelay, creatures, monsters, numTurns);
    }


    public static Creature[] makeCreatures(int num, Random variableSelect){
	Creature[] creatures = new Creature[num];
	int x, y, eatStrawberryInt, eatMushroomInt, reactStrawberry, 
	    reactMushroom, reactMonster, reactCreature, defaultMove, eSWeight,
	    eMRWeight, rSWeight, rMRWeight, rMonWeight, rCWeight,
	    maxX = worldLength, maxY = worldWidth;
	boolean eatStrawberry, eatMushroom;
	for(int i = 0; i < num; i++){
	    x = variableSelect.nextInt(maxX);
	    y = variableSelect.nextInt(maxY);
	    eatStrawberryInt = variableSelect.nextInt(2);
	    if(eatStrawberryInt == 1){
		eatStrawberry = true;
	    }
	    else{
		eatStrawberry = false;
	    }
	    eatMushroomInt = variableSelect.nextInt(2);
	    if(eatMushroomInt == 1){
		eatMushroom = true;
	    }
	    else{
		eatMushroom = false;
	    }
	    reactStrawberry = variableSelect.nextInt(4) + 1;
	    reactMushroom = variableSelect.nextInt(4) + 1;
	    reactMonster = variableSelect.nextInt(4) + 1;
	    reactCreature = variableSelect.nextInt(4) + 1;
	    defaultMove = variableSelect.nextInt(5) + 1;
	    eSWeight = variableSelect.nextInt(1000) + 1;
	    eMRWeight = variableSelect.nextInt(1000) + 1;
	    rSWeight = variableSelect.nextInt(1000) + 1;
	    rMRWeight = variableSelect.nextInt(1000) + 1;
	    rMonWeight = variableSelect.nextInt(1000) + 1;
	    rCWeight = variableSelect.nextInt(1000) + 1;
	    creatures[i] = new Creature(x,y, startingEnergy, eatStrawberry, eatMushroom, 
					reactStrawberry, reactMushroom, reactMonster, reactCreature, defaultMove,
					eSWeight, eMRWeight, rSWeight, rMRWeight, rMonWeight, rCWeight, foodEnergy, energyLoss);
	}
	return creatures;
    }

    public static Monster[] makeMonsters(int num, Random variableSelect){
	Monster[] monsters = new Monster[num];
	int x, y, maxX = worldLength, maxY = worldWidth;
	for(int i = 0; i < num; i++){
	    x = variableSelect.nextInt(maxX);
	    y = variableSelect.nextInt(maxY);
	    monsters[i] = new Monster(x,y);
	}
	return monsters;
	
    }


    public static boolean evolve(){
	ArrayList<Creature> aliveCreatures = new ArrayList<Creature>();
	Creature[] matingCreatures;
	int size;
	for(int i = 0; i < creatures.length; i++){
	    if(creatures[i].isAlive()){
		aliveCreatures.add(creatures[i]);
	    }
	}
	if((size = aliveCreatures.size()) == 0){
	    System.out.println("Extinction");
	    return false;
	}
	else{
	    matingCreatures = aliveCreatures.toArray(new Creature[size]);
	    mate(matingCreatures);
	}
	return true;
    }

    public static void mate(Creature[] aliveCreatures){
	Random selectiveBreading = new Random();
	Creature[] newCreatures = new Creature[creatures.length];
	Creature creatureA, creatureB;
	boolean found = false;
	int totalE, creatureAIndex, creatureBIndex, cAIndex = 0, cBIndex = 0;
	int[] creaturesE = new int[aliveCreatures.length];
	monsters = makeMonsters(numMonsters, selectiveBreading);
	creaturesE[0] = aliveCreatures[0].getEnergy();
	totalE = creaturesE[0];
	for(int i = 1; i < creaturesE.length; i++){
	    creaturesE[i] = creaturesE[i-1] + (aliveCreatures[i].getEnergy());
	    totalE += creaturesE[i];
	}
	for(int i = 0; i < newCreatures.length; i++){ 
	    creatureAIndex = selectiveBreading.nextInt(totalE);
	    creatureBIndex = selectiveBreading.nextInt(totalE);
	    for(int j = 0; j < creaturesE.length; j++){
		if(creatureAIndex <= creaturesE[j]){
		    cAIndex = j;
		    break;
		}
	    }
	    for(int j = 0; j < creaturesE.length; j++){
		if(creatureBIndex <= creaturesE[j]){
		    cBIndex = j;
		    break;
		}
		
	    }
	    creatureA = aliveCreatures[cAIndex];
	    creatureB = aliveCreatures[cBIndex];
	    newCreatures[i] = geneSplice(creatureA, creatureB, selectiveBreading);
	}
	creatures = newCreatures;
    }    


    public static Creature geneSplice(Creature a, Creature b, Random splicePointSelector){
	int splicePoint = splicePointSelector.nextInt(13), x = splicePointSelector.nextInt(worldWidth), y = splicePointSelector.nextInt(worldLength);
	int[] chromosomeA = a.extractChromosome(), chromosomeB = b.extractChromosome(), newChromosome = new int[13];
	boolean eatBerry = false, eatMushroom = false;
	for(int i = 0; i < splicePoint; i++){
	    newChromosome[i] = chromosomeA[i];
	}
	for(int i = splicePoint; i < newChromosome.length; i++){
	    newChromosome[i] = chromosomeB[i];
	}
	newChromosome = mutate(newChromosome);
	if(newChromosome[0] == 1){
	    eatBerry = true;
	}
	if(newChromosome[1] == 1){
	    eatMushroom = true;
	}
	return new Creature(x, y, startingEnergy, eatBerry, eatMushroom, newChromosome[2], newChromosome[3], newChromosome[4], newChromosome[5],
			    newChromosome[6], newChromosome[7], newChromosome[8], newChromosome[9], newChromosome[10],
			    newChromosome[11], newChromosome[12], foodEnergy, energyLoss);
			    
    }

    public static int[] mutate(int[] chromosome){
	Random random = new Random();
	int chance = random.nextInt(200) + 1;
	if(chance == 100){
	    chromosome[0] = random.nextInt(2);
	}
	chance = random.nextInt(200) + 1;
	if(chance == 100){
	    chromosome[1] = random.nextInt(2);
	}
	chance = random.nextInt(200) + 1;
	if(chance == 100){
	    chromosome[2] = random.nextInt(4) + 1;
	}
	chance = random.nextInt(200) + 1;
	if(chance == 100){
	    chromosome[3] = random.nextInt(4) + 1;
	}
	chance = random.nextInt(200) + 1;
	if(chance == 100){
	    chromosome[4] = random.nextInt(4) + 1;
	}
	chance = random.nextInt(200) + 1;
	if(chance == 100){
	    chromosome[5] = random.nextInt(4) + 1;
	}
	chance = random.nextInt(200) + 1;
	if(chance == 100){
	    chromosome[6] = random.nextInt(4) + 1;
	}
	chance = random.nextInt(200) + 1;
	if(chance == 100){
	    chromosome[7] = random.nextInt(1000) + 1;
	}
	chance = random.nextInt(200) + 1;
	if(chance == 100){
	    chromosome[8] = random.nextInt(1000) + 1;
	}
	chance = random.nextInt(200) + 1;
	if(chance == 100){
	    chromosome[9] = random.nextInt(1000) + 1;
	}
	chance = random.nextInt(200) + 1;
	if(chance == 100){
	    chromosome[10] = random.nextInt(1000) + 1;
	}
	chance = random.nextInt(200) + 1;
	if(chance == 100){
	    chromosome[11] = random.nextInt(1000) + 1;
	}
	chance = random.nextInt(200) + 1;
	if(chance == 100){
	    chromosome[12] = random.nextInt(1000) + 1;
	}
	
	return chromosome;
    }

    public static double getFitness(){
	double energy = 0.0, hold = 0.0;
	for(int i = 0; i < creatures.length; i++){
	    if((hold = creatures[i].getEnergy()) >= 0){
		energy += hold;
	    }
	}
	return energy;
    }
}


