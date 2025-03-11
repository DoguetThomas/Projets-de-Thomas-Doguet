package planning;

import modelling.Variable;  
import java.util.Map;

//Création de l'interface pour la class BasicAction
public interface Action{

	public boolean isApplicable(Map<Variable, Object> etat);
	public Map<Variable, Object> successor(Map<Variable, Object> etat);
	public int getCost();

}
