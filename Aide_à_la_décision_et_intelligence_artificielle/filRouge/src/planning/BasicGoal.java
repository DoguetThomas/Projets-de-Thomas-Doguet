package planning;

import modelling.*;
import java.util.Map;

public class BasicGoal implements Goal{

	//Création de l'unique attribut
	private Map<Variable, Object> precondition;
	
	//Constructeur prennant en paramètre une precondition
	public BasicGoal(Map<Variable, Object> precondition){
		this.precondition = precondition;
	}
	
	//Méthode regardant si la précondition est satisfaite par rapport à l'etat données
	public boolean isSatisfiedBy(Map<Variable, Object> etat){
		for (Map.Entry<Variable, Object> entree : precondition.entrySet()){
			if (!(etat.containsKey(entree.getKey()) && etat.get(entree.getKey()).equals(entree.getValue()))){
				return false;
			}
		}
		return true;
	}
	
	
}
