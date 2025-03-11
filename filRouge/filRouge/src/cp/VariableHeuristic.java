package cp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import modelling.Variable;

//interface qui declar une methode best, qui retourner la meilleur variable selon le type d'heuristique
public interface VariableHeuristic{
	
	public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> dommaine);
	
}
