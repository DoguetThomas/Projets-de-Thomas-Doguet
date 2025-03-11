package cp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import modelling.Variable;
import modelling.Constraint;

//class implementant la Variableheuristique
public class DomainSizeVariableHeuristic implements VariableHeuristic{

	private boolean bool;

	public DomainSizeVariableHeuristic(boolean bool){
		this.bool = bool;
	}

	//implementation de la methode best, ici la meilleure variable sera celle avec le plus grand ou le plus petit dommaine(selon le booleen en argument)
    @Override
	public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> dommaines){
		Map<Variable, Integer> domainssize = new HashMap<>();

        for (Variable var : variables){
            domainssize.put(var, dommaines.get(var).size());
		}
        Variable maxVariable = null;
		Variable minVariable = null;
		int maxSize = Integer.MIN_VALUE; 
		int minSize = Integer.MAX_VALUE;

		for (Map.Entry<Variable, Integer> entry : domainssize.entrySet()) {
			Variable currentVariable = entry.getKey();
			int currentSize = entry.getValue();
			if (maxVariable == null || currentSize > maxSize) {
				maxVariable = currentVariable;
				maxSize = currentSize;
			}
			if (minVariable == null || currentSize < minSize) {
				minVariable = currentVariable;
				minSize = currentSize;
			}
		}
		if (this.bool){
			return maxVariable;
		}
		return minVariable;
	}
}