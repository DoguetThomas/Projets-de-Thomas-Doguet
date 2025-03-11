package cp;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Random;
import java.util.Collections;

import modelling.Variable;

//classe implementant valueHeuristique
public class RandomValueHeuristic implements ValueHeuristic{
	
    private Random random;

    public RandomValueHeuristic(Random random){
        this.random = random;
    }

    //implementation de la methode ordering, elle melange la liste de valeurs, uniformement
	public List<Object> ordering(Variable variable, Set<Object> dommaine){
        List<Object> coll= new ArrayList<>(dommaine);

        Collections.shuffle(coll, random);

        return coll;
    }
	
}