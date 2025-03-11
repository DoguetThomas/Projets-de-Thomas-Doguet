package planning;

import java.util.Map;
import java.util.HashSet;

import java.lang.Math;

import modelling.Variable;

//Création de l'interface 
public interface Heuristic{

     public float estimate(Map<Variable, Object> state);

    }

