package blockWorld.planning;

import java.util.Map;

import planning.Heuristic;
import modelling.Variable;
import blockWorld.modelling.BWVariable;

//heuristique claculant le nombre de blocks placés a la mauvaise position(qui nécessiteront au moins un deplacement pour resoudre le plan)
public class BlockWithWrongPositionHeuristic implements Heuristic{

    private Map<Variable, Object> finalState;
    private BWVariable bwVariable;

    public BlockWithWrongPositionHeuristic(Map<Variable, Object> finalState, BWVariable bwVariable){
        this.finalState = finalState;
        this.bwVariable = bwVariable;
    }

    //méhode de calcul
    @Override
    public float estimate(Map<Variable, Object> state){
        int res = 0;

        for (Variable onbVar : this.getBwVariable().getOnbVar()) {
            if (!this.getFinalState().get(onbVar).equals(state.get(onbVar))) {
                res++;
            }
        }
        return res;
    }

    //accsseurs de la classe
    public Map<Variable, Object> getFinalState(){
        return this.finalState;
    }

    public BWVariable getBwVariable(){
        return this.bwVariable;
    }
}