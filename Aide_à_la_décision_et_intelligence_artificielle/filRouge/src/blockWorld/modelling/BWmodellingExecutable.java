package blockWorld.modelling;

import modelling.Constraint;
import modelling.Variable;
import java.util.Map;


public class BWmodellingExecutable{
	public static void main(String[] args){

        //Initalisation des variables pour tester les contraintes
        BlockWorld blockWorld = new BlockWorld(8, 4);
        RegularityConstraints bwRegConstraintes = new RegularityConstraints(blockWorld);
        IncreasingConstraints bwIncr = new IncreasingConstraints(bwRegConstraintes);
        BWVariable bwVariable = blockWorld.getBWVariable();
        
        //Initalisation des differents etats
        int[][] preState0 = {
          {0, 1},
          {2, 3, 4, 5},
          {6, 7},
          {}
        };

        int[][] preState1 = {
          {0, 4},
          {1, 5},
          {2, 6},
          {3, 7}
      };
        int[][] preState2 = {
        {5, 6, 7},
        {2, 3, 4},
        {0, 1},
        {}
      };

      int[][] preState3 = {
      {3, 7},
      {2, 6},
      {1, 5},
      {0, 4}
      };
      
      //Conversion des etat de matrice en Map de variable et d'objet
      Map<Variable, Object> state0 = bwVariable.setState(preState0);
      Map<Variable, Object> state1 = bwVariable.setState(preState1);
      Map<Variable, Object> state2 = bwVariable.setState(preState2);
      Map<Variable, Object> state3 = bwVariable.setState(preState3);

    boolean allSatisfied = true;

    //Test des contraintes basiques:
    for (Constraint constraint : blockWorld.getBasiconstrains()) {
        if (!constraint.isSatisfiedBy(state0)) {
          allSatisfied = false;
        }
      }

    
    if (allSatisfied){
        System.out.println("toutes les contraintes 'basiques' ont étés validées pour " + preState0);
    }
    else{
        System.out.println("toutes les contraintes 'basiques' n'ont pas étés validées pour " + preState0);
    }

    allSatisfied = true;

    //Test des contraintes regulieres:
    for (Constraint constraint : bwRegConstraintes.getRegularityConstraints()) {
        if (!constraint.isSatisfiedBy(state1)) {
          allSatisfied = false;
        }
      }

    
    if (allSatisfied){
        System.out.println("toutes les contraintes 'regulieres' ont étés validées pour " + preState1);
    }
    else{
        System.out.println("toutes les contraintes 'regulieres' n'ont pas étés validées pour " + preState1);
    }

    allSatisfied = true;

    //Test des contraintes croissantes:
    for (Constraint constraint : bwIncr.getIncreasingConstraints()) {
        if (!constraint.isSatisfiedBy(state2)) {
          allSatisfied = false;
        }
    }

    
    if (allSatisfied){
        System.out.println("toutes les contraintes 'croissantes' ont étés validées pour " + preState2);
    }
    else{
        System.out.println("toutes les contraintes 'croissantes' n'ont pas étés validées pour " + preState2);
    }

    allSatisfied = true;

    //Test des contraintes régulieres ET croissantes 
    for (Constraint constraint : blockWorld.getConstrains()) {
        if (!constraint.isSatisfiedBy(state3)) {
          allSatisfied = false;
        }
    }

    
    if (allSatisfied){
        System.out.println("toutes les contraintes 'regulieres et croissantes' ont étés validées pour " + preState3);
    }
    else{
        System.out.println("toutes les contraintes 'regulieres et croissantes' n'ont pas étés validées pour " + preState3);
    }
    }
}

