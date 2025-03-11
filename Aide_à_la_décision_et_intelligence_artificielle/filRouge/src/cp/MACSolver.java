package cp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.LinkedList;

import modelling.Variable;
import modelling.Constraint;

public class MACSolver extends AbstractSolver{
	private ArcConsistency arcConsistency;
	
	public MACSolver(Set<Variable> variables, Set<Constraint> contraintes){
		super(variables, contraintes);
		this.arcConsistency = new ArcConsistency(contraintes);
	}
	
	@Override
	public Map<Variable, Object> solve(){
		Map<Variable, Object> instantiation = new HashMap<>();
		LinkedList<Variable> nonInstantiatedVars = new LinkedList<>(variables);
		Map<Variable, Set<Object>> domaines = new HashMap<>();
        for (Variable variable : this.variables) {
            domaines.put(variable, new HashSet<>(variable.getDomain()));
        }
		return mac(instantiation, nonInstantiatedVars, domaines);
	}
	
	public Map<Variable, Object> mac(Map<Variable, Object> instantiation, LinkedList<Variable> nonInstantiatedVars, Map<Variable, Set<Object>> domaines){
		if (nonInstantiatedVars.isEmpty()){
			return instantiation;
		}
		
		if (!this.arcConsistency.ac1(domaines)){
			return null;
		}
		Variable x = nonInstantiatedVars.poll();
		for (Object vi : new HashSet<>(domaines.get(x))){
			Map<Variable, Object> ni = new HashMap<>(instantiation);
			ni.put(x, vi);
			if (this.isConsistent(ni)){
				Map<Variable, Object> res = mac(ni, nonInstantiatedVars, domaines);
				if (res != null){
					return res;
				}
			}
		}
		nonInstantiatedVars.addFirst(x);
		return null;
	}
}
