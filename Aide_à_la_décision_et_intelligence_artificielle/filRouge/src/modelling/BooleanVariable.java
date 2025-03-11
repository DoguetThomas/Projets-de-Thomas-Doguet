package modelling;

import java.util.Set;
import java.util.HashSet;

//Création des variables booléennes
public class BooleanVariable extends Variable{
	
	public BooleanVariable(String nom){
		super(nom, Set.of(true, false));
	}

	
}
