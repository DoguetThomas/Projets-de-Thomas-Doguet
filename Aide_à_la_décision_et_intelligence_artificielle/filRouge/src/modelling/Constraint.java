package modelling;

import java.util.Set;
import java.util.Map;


//Interface de la méthode DifferenceConstraint
public interface Constraint{

	public Set<Variable> getScope();
	public boolean isSatisfiedBy(Map<Variable, Object> instanciation);

}
