package cp;
import java.util.Map;

import modelling.Variable;
//interface Solver qui sera implementée pour chaques solvers
public interface Solver{
	
	//méthode solve retournant une solution de type Map<Variable, Object>
	public Map<Variable, Object> solve();
}
