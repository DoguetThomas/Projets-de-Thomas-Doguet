package modelling;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;

public class DifferenceConstraint implements Constraint{
	
	//Création des attributs
	private Variable v1;
	private Variable v2;
	
	//Constructeur prenant en paramètre deux variables
	public DifferenceConstraint(Variable v1, Variable v2){
		this.v1 = v1;
		this.v2 = v2;
	}
	
	//Méthode d'accés à la première variable
	public Variable getV1(){
		return this.v1;
	}
	
	//Méthode d'accés à la deuxième variable
	public Variable getV2(){
		return this.v2;
	}
	
	//Méthode d'accés à la liste des variables
	@Override
	public Set<Variable> getScope(){
		Set<Variable> set = new HashSet<>();
		set.add(this.getV1());
		set.add(this.getV2());
		return set;
	}
	
	//Méthode testant si les deux variables marchent par rapport à l'instanciation
	@Override
	public boolean isSatisfiedBy(Map<Variable, Object> instanciation){
		for (Variable variable : this.getScope()){
			if (!instanciation.containsKey(variable)){
				throw new IllegalArgumentException("Variable manquante dans le scope : " + variable.getName());
			}
		}
		return !instanciation.get(this.getV1()).equals(instanciation.get(this.getV2()));
	}
	
	public String toString(){
		return "contrainte de diference entre " + this.v1 + " et " + this.v2;
	}
}
