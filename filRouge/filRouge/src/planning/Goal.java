package planning;

import modelling.*;
import java.util.Map;

//Création de l'interface pour la classe BasicGoal
public interface Goal{
	public boolean isSatisfiedBy(Map<Variable,Object> etat);

}
