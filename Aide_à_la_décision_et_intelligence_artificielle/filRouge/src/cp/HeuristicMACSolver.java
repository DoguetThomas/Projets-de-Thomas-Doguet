package cp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.LinkedList;

import modelling.Variable;
import modelling.Constraint;

//classe etendant la class AbstractSolver
public class HeuristicMACSolver extends AbstractSolver{
    private VariableHeuristic variableHeuristic;
    public ValueHeuristic valueHeuristic;
    private ArcConsistency arcConsistency;

    public HeuristicMACSolver(Set<Variable> variables, Set<Constraint> contraintes, VariableHeuristic variableHeuristic, ValueHeuristic valueHeuristic){
        super(variables, contraintes);
        this.variableHeuristic = variableHeuristic;
        this.valueHeuristic = valueHeuristic;
        this.arcConsistency = new ArcConsistency(contraintes);
    }

	@Override
    public Map<Variable, Object> solve(){
		Map<Variable, Object> instantiation = new HashMap<>();
		LinkedList<Variable> nonInstantiatedVars = new LinkedList<>();
        if (this.variables != null) {
            nonInstantiatedVars.addAll(this.variables);
        } 
		Map<Variable, Set<Object>> domaines = new HashMap<>();
        for (Variable variable : this.variables) {
            domaines.put(variable, new HashSet<>(variable.getDomain()));
        }
		return mac(instantiation, nonInstantiatedVars, domaines);
	}
	
	//methode de parcour solon l'algoritme mac, afin de trouver la solution si elle existe
	private Map<Variable, Object> mac(Map<Variable, Object> instantiation, LinkedList<Variable> nonInstantiatedVars, Map<Variable, Set<Object>> domaines) {
		if (nonInstantiatedVars.isEmpty()){
			return instantiation;
		}
		
        if (!this.arcConsistency.ac1(domaines)){
			return null;
		}

		Variable x = variableHeuristic.best(new HashSet<>(nonInstantiatedVars), domaines);
        nonInstantiatedVars.remove(x);

        List<Object> ordonne = valueHeuristic.ordering(x, domaines.get(x));

		for (Object vi : ordonne){
			Map<Variable, Object> ni = new HashMap<>(instantiation);
			ni.put(x, vi);
			if (this.isConsistent(ni)){
				Map<Variable, Object> res = this.mac(ni, nonInstantiatedVars, domaines);
				if (res != null){
					return res;
				}
			}
		}
		nonInstantiatedVars.add(x);
		return null;
	}
}