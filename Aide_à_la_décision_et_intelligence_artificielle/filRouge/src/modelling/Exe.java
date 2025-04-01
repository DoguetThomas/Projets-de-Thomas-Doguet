package modelling;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.Arrays;

import modellingtests.VariableTests;
import modellingtests.BooleanVariableTests;
import modellingtests.DifferenceConstraintTests;
import modellingtests.ImplicationTests;
import modellingtests.UnaryConstraintTests;

public class Exe {

    public static void main(String[] args) {
    	boolean ok = true;
    	ok = ok && VariableTests.testGetName();
	ok = ok && VariableTests.testGetDomain();
	ok = ok && VariableTests.testEquals();
	ok = ok && VariableTests.testHashCode();
	ok = ok && BooleanVariableTests.testConstructor();
	ok = ok && BooleanVariableTests.testEquals();
	ok = ok && BooleanVariableTests.testHashCode();
	ok = ok && DifferenceConstraintTests.testGetScope();
	ok = ok && DifferenceConstraintTests.testIsSatisfiedBy();
	ok = ok && ImplicationTests.testGetScope();
	ok = ok && ImplicationTests.testIsSatisfiedBy();
	ok = ok && UnaryConstraintTests.testGetScope();
	ok = ok && UnaryConstraintTests.testIsSatisfiedBy();
	System.out.println(ok ? "All tests OK" : "At least one test KO");
        BooleanVariable boolVar = new BooleanVariable("boolVar");
        System.out.println("Nom de la variable booléenne : " + boolVar.getName());
        System.out.println("Domaine : " + boolVar.getDomain());

        Variable v1 = new Variable("v1", Set.of(1, 2));
        Variable v2 = new Variable("v2", Set.of(1, 2));
        DifferenceConstraint diffConstraint = new DifferenceConstraint(v1, v2);
        
        Map<Variable, Object> instantiation1 = Map.of(v1, 1, v2, 2);
        System.out.println("DifferenceConstraint (1, 2) : " + diffConstraint.isSatisfiedBy(instantiation1));

        Map<Variable, Object> instantiation2 = Map.of(v1, 1, v2, 1);
        System.out.println("DifferenceConstraint (1, 1) : " + diffConstraint.isSatisfiedBy(instantiation2));

        Set<Object> s1 = Set.of(1);
        Set<Object> s2 = Set.of(3);
        Implication implication = new Implication(v1, s1, v2, s2);

        Map<Variable, Object> instantiation3 = Map.of(v1, 1, v2, 3);
        System.out.println("Implication (1, 3) : " + implication.isSatisfiedBy(instantiation3));

        Map<Variable, Object> instantiation4 = Map.of(v1, 2, v2, 4);
        System.out.println("Implication (2, 4) : " + implication.isSatisfiedBy(instantiation4));

        UnaryConstraint unaryConstraint = new UnaryConstraint(v1, Set.of(1));
        Map<Variable, Object> instantiation5 = Map.of(v1, 1);
        System.out.println("UnaryConstraint (1) : " + unaryConstraint.isSatisfiedBy(instantiation5));

        Map<Variable, Object> instantiation6 = Map.of(v1, 2);
        System.out.println("UnaryConstraint (2) : " + unaryConstraint.isSatisfiedBy(instantiation6));

        Variable v3 = new Variable("var", Set.of(1, 2));
        Variable v4 = new Variable("var", Set.of(3, 4));
        System.out.println("v3 égale à v4 ? " + v3.equals(v4));

        try {
            Map<Variable, Object> instantiation7 = Map.of(v1, 1);
            diffConstraint.isSatisfiedBy(instantiation7);
        } catch (IllegalArgumentException e) {
            System.out.println("Exception attrapée : " + e.getMessage());
        }
    }
}

