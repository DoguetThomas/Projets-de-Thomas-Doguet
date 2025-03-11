package cp;

import java.util.Map;
import java.util.Set;

import modelling.Variable;
import modelling.Constraint;

//classe abstraite implementant l'interface solver
public abstract class AbstractSolver implements Solver{
	protected Set<Variable> variables;
	protected Set<Constraint> contraintes;
	
	public AbstractSolver(Set<Variable> variables, Set<Constraint> contraintes){
		this.variables = variables;
		this.contraintes = contraintes;
	}
	//methode abstraite solve retournant un etat(solution)
	public abstract Map<Variable, Object> solve();
	
	//methode retournat true si toutes contraintes de la classes ont satisfaites par l'affectation partielle passée un argument
	public boolean isConsistent(Map<Variable, Object> affectationPartielle){
		for (Constraint contrainte : this.contraintes){
			if (!affectationPartielle.keySet().containsAll(contrainte.getScope())) {
				continue;
			}
			if (!contrainte.isSatisfiedBy(affectationPartielle)){
				return false;
			}
		}
		return true;
	}
}
