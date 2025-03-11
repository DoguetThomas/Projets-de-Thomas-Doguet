package blockWorld.planning;

import java.util.Map;

import planning.Heuristic;
import modelling.Variable;
import blockWorld.modelling.BWVariable;

//heuristique n1, calculant le nombre de blocks bloqués, c'est a dire les nombre de blocks placé au dessous d'un autre
//qui sont aussi a la mauvaise position, ce qui necessitera son deplacement, maise aussi le deplacement de celui au dessu de lui

public class BlockedBlockHeuristicOnWrongPosition implements Heuristic{

    private Map<Variable, Object> finalState;
    private BWVariable bwVariable;

    public BlockedBlockHeuristicOnWrongPosition(Map<Variable, Object> finalState, BWVariable bwVariable){
        this.finalState = finalState;
        this.bwVariable = bwVariable;
    }

    @Override
    public float estimate(Map<Variable, Object> state){
        int res = 0;

        for (Variable onbVar : this.getBwVariable().getOnbVar()) {
            int blockIndex = this.getBwVariable().getIndex(onbVar);
            Variable fixedVar = this.getBwVariable().getFixedBooleanVarWithBlockIndex("fixed_" + blockIndex);
            if (state.get(fixedVar).equals(true) && !this.getFinalState().get(onbVar).equals(state.get(onbVar))) {
                res++;
            }
        }
        return res;
    }

    public Map<Variable, Object> getFinalState(){
        return this.finalState;
    }

    public BWVariable getBwVariable(){
        return this.bwVariable;
    }

}