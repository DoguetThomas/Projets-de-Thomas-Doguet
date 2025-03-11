package planning;

import modelling.*;
import java.util.Map;
import java.util.HashMap;

public class BasicAction implements Action{
	
	//Création des attributs
	private Map<Variable, Object> precondition;
	private Map<Variable, Object> effet;
	private int cout;
	
	//Constructeur prennant en paramètre une precondition, un effet et un cout
	public BasicAction(Map<Variable, Object> precondition, Map<Variable, Object> effet, int cout){
		this.precondition = precondition;
		this.effet = effet;
		this.cout = cout;
	}
	
	//Méthode permetant de savoir si les préconditions sont applicables.
	@Override
	public boolean isApplicable(Map<Variable, Object> etat){
		for (Map.Entry<Variable, Object> entree : precondition.entrySet()){
			if (!(etat.containsKey(entree.getKey()) && etat.get(entree.getKey()).equals(entree.getValue()))){
				return false;
			}
		}
		return true;
	}
	
	//Méthode d'accés au cout
	@Override
	public int getCost(){
		return this.cout;
	}
	
	//Méthode retournant le nouvelle etat si les préconditions sont remplis
	@Override
	public Map<Variable, Object> successor(Map<Variable, Object> etat) {
		Map<Variable, Object> res = new HashMap<>(etat);
		for (Map.Entry<Variable, Object> entree : this.effet.entrySet()) {
		    res.put(entree.getKey(), entree.getValue());
		}
		return res;
	    }

	@Override
	public String toString(){
		return "de base = " + this.precondition + "apres action " + this.effet + " avec un cout de = " + this.getCost();
	}
}
