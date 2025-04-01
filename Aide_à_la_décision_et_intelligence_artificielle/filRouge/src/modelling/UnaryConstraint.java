package modelling;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;

public class UnaryConstraint implements Constraint{
	
	//Création des attributs
	private Variable v;
	private Set<Object> s;
	
	//Constructeur prenant en paramètre une variable et un set
	public UnaryConstraint(Variable v, Set<Object> s){
		this.v = v;
		this.s = s;
	}
	
	//Méthode d'accés à la variable
	public Variable getV(){
		return this.v;
	}
	
	//Méthode d'accés au set
	public Set<Object> getS(){
		return this.s;
	}
	
	//Méthode d'accés à la liste de Variable.
	@Override
	public Set<Variable> getScope(){
		Set<Variable> scope = new HashSet<>();
		scope.add(this.getV());
		return scope;
	}
	
	//Méthode pour tester si l'instanciation est satisfaite
	@Override
	public boolean isSatisfiedBy(Map<Variable, Object> instanciation){
		for (Variable variable : this.getScope()){
			if (!instanciation.containsKey(variable)){
				throw new IllegalArgumentException("Variable manquante dans le scope : " + variable.getName());
			}
		}
		return this.getS().contains(instanciation.get(this.getV()));
	}
}
