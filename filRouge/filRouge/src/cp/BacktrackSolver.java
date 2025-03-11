package cp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.LinkedList;

import modelling.Variable;
import modelling.Constraint;

//classe heritant de AbstractSolver qui va creer un sover dans un algoritme de backstract
public class BacktrackSolver extends AbstractSolver{

	
	public BacktrackSolver(Set<Variable> variables, Set<Constraint> contraintes){
		super(variables, contraintes);
	}
	
	//methode solve utilisant ensuite l'algoritme de bacstract
	public Map<Variable, Object> solve(){
		return backStract(new HashMap<>(), new LinkedList<>(this.variables));
	}
	
	//algoritme de parcour en backstract, permettant de retourner la solution si elle existe, null sinon
	public Map<Variable, Object> backStract(Map<Variable, Object> i, LinkedList<Variable> v){
		if (v.isEmpty()){
			return i;
		}
		Variable xi = v.poll();
		
		for (Object vi : xi.getDomain()){
			Map<Variable, Object> ni = new HashMap<>(i);
			ni.put(xi, vi);
			if (this.isConsistent2(ni)){
				Map<Variable, Object> res = this.backStract(ni, v);
				if (res != null){
					return res;
				}
			}
		}
		v.add(xi);
		return null;
	}
	
	//nouvelle methode isConsistent, utile pour un algoritme de backstract
	public boolean isConsistent2(Map<Variable, Object> affectationPartielle){
		for (Constraint contrainte : this.contraintes){
			if (affectationPartielle.keySet().containsAll(contrainte.getScope())){
				if (!contrainte.isSatisfiedBy(affectationPartielle)){
					return false;
				}
			}
		}
		return true;
	}
}
