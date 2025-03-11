package cp;

import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import modelling.Variable;
import modelling.Constraint;

//classe arcconsistency, qui prend en paramettre un ensemble de contraintes, qui verifie si elles sont uniaires ou binaires, sinon, cree un exeptiion
public class ArcConsistency{
	private Set<Constraint> contraintes;
	
	public ArcConsistency(Set<Constraint> contraintes){
		
		for (Constraint contrainte: contraintes){
			Set<Variable> scope = contrainte.getScope();
			if (!(scope.size()==1) && !(scope.size()==2)) {
				throw new IllegalArgumentException("Ni unaire ni binaire");
			}
		}
		this.contraintes = contraintes;
	}
	
	//methode qui supprimme en place les valeurs des dommaines pour lesquels il existe une contrainte unaire non satisfaite, si au moin une ne l'est pas, elle retourne false
	public boolean enforceNodeConsistency(Map<Variable, Set<Object>> domaines){
		boolean result = true;
        for (Constraint contrainte : contraintes) {
            Set<Variable> scope = contrainte.getScope();
            if (scope.size() == 1) {
                Variable var = scope.iterator().next();
                Set<Object> domaine = domaines.get(var);
                Set<Object> valuesToRemove = new HashSet<>();

                for (Object v : domaine) {
                    if (!contrainte.isSatisfiedBy(Map.of(var, v))) {
                        valuesToRemove.add(v);
                    }
                }
                domaine.removeAll(valuesToRemove);
                if (domaine.isEmpty()) {
                   result =  false;
                }
            }
        }
        return result;
	}
	
	//methode qui suppime en place les valeurs v1 de son dommaine d1, pour lequel il n'existe pas de valeur d2 d'un autre dommaine supportant v1 pour toutes les contraintes portantes sur v1 et v2, si au moins une valeur a ete supprimée on renvoie true
	public boolean revise(Variable xi, Set<Object> Dxi, Variable xj, Set<Object> Dxj) {
		boolean del = false;
		List<Object> valuesToRemove = new ArrayList<>(); 

		for (Object vi : Dxi) {
			boolean viable = false;

			for (Object vj : Dxj) {
				boolean allSatisfied = true;
				for (Constraint constraint : contraintes) {
					if (constraint.getScope().contains(xi) && constraint.getScope().contains(xj)) {
						Map<Variable, Object> assignment = Map.of(xi, vi, xj, vj);
						if (!constraint.isSatisfiedBy(assignment)) {
							allSatisfied = false;
							break;
						}
					}
				}
				if (allSatisfied) {
					viable = true;
					break;
				}
			}
			if (!viable) {
				valuesToRemove.add(vi);
				del = true;
			}
		}
		Dxi.removeAll(valuesToRemove);

		return del;
	}
	
	//methode filtrant un ensemble de dommaine suivant l'algoritme ac1, qui utilise arcConsistency
	public boolean ac1(Map<Variable, Set<Object>> domaines) {
		if (!enforceNodeConsistency(domaines)) {
			return false;
		}
		boolean change = false;
		do {
			change = false;
			for (Variable v1 : domaines.keySet()) {
				for (Variable v2 : domaines.keySet()) {
					if (!v1.equals(v2)) {
						if (revise(v1, domaines.get(v1), v2, domaines.get(v2))) {
							change = true;
						}
					}
				}
			}
		} while (change);
		for (Set<Object> domaine : domaines.values()) {
			if (domaine.isEmpty()) {
				return false;
			}
		}
		return true;
}
	
}
