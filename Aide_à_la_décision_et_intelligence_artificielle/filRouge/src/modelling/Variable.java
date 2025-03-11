package modelling;

import java.util.Set;
import java.util.HashSet;

public class Variable{
	
	//Création des attributs
	private String nom;
	private Set<Object> domaine;
	
	//Constructeur prennant en paramètre un nom et un set
	public Variable(String nom, Set<Object> domaine){
		this.nom = nom;
		this.domaine = domaine;
	}
	
	//Méthode (override) permettant de teste l'égualité de deux objet
	@Override
	public boolean equals(Object objet){
		if (objet instanceof Variable){
			Variable enVariable = (Variable) objet;
			return this.getName().equals(enVariable.getName());
		}
		return false;
	}
	
	//Méthode (override) permettant de récuperer le hashcode du nom
	@Override
	public int hashCode(){
		return this.nom.hashCode();
	}
	
	//Méthode d'accés au nom
	public String getName(){
		return this.nom;
	}
	
	//Méthode d'accés au set des domaines.
	public Set<Object> getDomain(){
		return this.domaine;
	}

	public String toString(){
		return this.getName() + this.getDomain();
	}
}
