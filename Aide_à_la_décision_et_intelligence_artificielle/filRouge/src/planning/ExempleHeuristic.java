package planning;
import modelling.Variable;

import java.util.Map;

//Classe abstraite utilisé dans heuristic
public abstract class ExempleHeuristic {
	abstract double estimate(Map<Variable, Object> state);
}
