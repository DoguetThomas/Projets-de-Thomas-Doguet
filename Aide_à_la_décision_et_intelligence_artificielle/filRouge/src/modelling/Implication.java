package modelling;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;

public class Implication implements Constraint{
	
	
	//Création des attributs
	private Variable v1;
	private Set<Object> S1;
	private Variable v2;
	private Set<Object> S2;
	
	
	//Constructeur prenant en paramètre deux variables et deux sets
	public Implication(Variable v1, Set<Object> S1, Variable v2, Set<Object> S2){
		this.v1 = v1;
		this.S1 = S1;
		this.v2 = v2;
		this.S2 = S2;
	}
	
	//Méthode d'accés à la variable 1
	public Variable getV1(){
		return this.v1;
	}
	
	//Méthode d'accés à la variable 1
	public Variable getV2(){
		return this.v2;
	}
	
	//Méthode d'accés au set 1
	public Set<Object> getS1(){
		return this.S1;
	}
	
	//Méthode d'accés au set 2
	public Set<Object> getS2(){
		return this.S2;
	}
	
	//Méthode d'accés a un set des variables
	@Override
	public Set<Variable> getScope(){
		Set<Variable> set = new HashSet<>();
		set.add(this.getV1());
		set.add(this.getV2());
		return set;
	}
	
	//Méthode testant si la contrainte est validé
	@Override
	public boolean isSatisfiedBy(Map<Variable, Object> instanciation){
		if(instanciation.get(this.v1) == null || instanciation.get(this.v2) == null) {
            throw new IllegalArgumentException("Variable manquante dans le scope");
        }
        
        return (!this.S1.contains(instanciation.get(this.v1)) || this.S2.contains(instanciation.get(this.v2)));
    }
	
	public String toString(){
		return "contrainte d'implication entre " + this.v1 + " son dommaine " + this.S1 +" et " + this.v2 + " et son domaine " + this.S2;
	}
}
