package cp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import modelling.Variable;
import modelling.Constraint;

//classe implementant VariableHeuristic
public class NbConstraintsVariableHeuristic implements VariableHeuristic{
	private Set<Constraint> contraintes;
	private boolean bool;
	
	public NbConstraintsVariableHeuristic(Set<Constraint> contraintes, boolean bool){
		this.contraintes = contraintes;
		this.bool = bool;
	}
	
	//implementntation de la methode best, ici, la meilleure variable, est celle qui apparrait le plus ou moins dans les contraintes(selon le boolean en argument) 
	@Override
	public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> dommaine){
		Map<Variable, Integer> constraintCount = new HashMap<>();
		for (Variable var : variables){
			int cpt = 0;
			for (Constraint contrainte : this.contraintes){
				if (contrainte.getScope().contains(var)){
					cpt+=1;
				}
			}
			constraintCount.put(var, cpt);
		}
		
		Variable maxVariable = null;
		Variable minVariable = null;
		int maxCount = Integer.MIN_VALUE; 
		int minCount = Integer.MAX_VALUE;

		for (Map.Entry<Variable, Integer> entry : constraintCount.entrySet()) {
			Variable currentVariable = entry.getKey();
			int currentCount = entry.getValue();
			if (maxVariable == null || currentCount > maxCount) {
				maxVariable = currentVariable;
				maxCount = currentCount;
			}
			if (minVariable == null || currentCount < minCount) {
				minVariable = currentVariable;
				minCount = currentCount;
			}
		}
		if (this.bool){
			return maxVariable;
		}
		return minVariable;
	}

}
