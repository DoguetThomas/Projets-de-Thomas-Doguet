package cp;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import modelling.Variable;

//interface declarant un methode qui trie les valeurs d'un dommaine, selon l'heuristique
public interface ValueHeuristic{
	
	public List<Object> ordering(Variable variable, Set<Object> dommaine);
	
}

