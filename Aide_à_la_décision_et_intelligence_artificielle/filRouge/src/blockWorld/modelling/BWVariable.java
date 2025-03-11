package blockWorld.modelling;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

import modelling.Variable;
import modelling.BooleanVariable;

//mise en place d'une classe qui va mettre en place tous les types de variables, pour chaque blocks et piles
public class BWVariable{

    private int nbBlocks;
    private int nbPiles;
    private Set<Variable> onbVar;
    private Set<Variable> fixedbVar;
    private Set<Variable> freepVar;
    private Set<Variable> variables;
    private Map<String, Variable> carteIndex;

    //constructeur utilisant une méthode externe;
    public BWVariable(int nbBlocks, int nbPiles){
        this.nbBlocks = nbBlocks;
        this.nbPiles = nbPiles;
        this.variables = new HashSet<>();
        this.carteIndex = new HashMap<>();
        this.createVar();
    }

    public void createVar(){
        
        //création des dommaines 
        Set<Object> domain = new HashSet<>();
        for (int i = -this.getNbPiles(); i<this.getNbBlock(); i++){
            domain.add(i);
        }

        //creation des onbVaribales (indiqants pour chaques blocks le block sur lequel ils sont posées
        //, si il sont sur le sol, on indique l'index de la pile au negatif)
        this.onbVar = new HashSet<>();
        
        for (int i = 0; i< this.getNbBlock(); i++){
            Set<Object> currentDomain = new HashSet<Object>(domain);
            currentDomain.remove(i);
            Variable currentOnbvar = new Variable("On_" + Integer.toString(i), currentDomain);
            this.onbVar.add(currentOnbvar);
            this.carteIndex.put("On_" + i, currentOnbvar);
        }

        //creation des fixedBvariable, une variable booleen qui prend true si le block est au dessus de la pile
        this.fixedbVar = new HashSet<>();
        for (int i = 0; i< this.getNbBlock(); i++){
            Variable currentfixedbVar = new BooleanVariable("fixed_" + Integer.toString(i));
            this.fixedbVar.add(currentfixedbVar);
            this.carteIndex.put("fixed_" + i, currentfixedbVar);
        }

        //creation des freepVariable, une variable booleaen qui prend true si la pile est libre(ne contient aucun block)
        
        this.freepVar = new HashSet<>();
        for (int i = 0; i< this.getNbPiles(); i++){
            Variable currentFreepVar = new BooleanVariable("free_" + Integer.toString(i));
            this.freepVar.add(currentFreepVar);
            this.carteIndex.put("free_" + i, currentFreepVar);
        }

        this.variables.addAll(this.getOnbVar());
        this.variables.addAll(this.getFixedbVar());
        this.variables.addAll(this.getFreepVar());

    }

    //methode pour recuperer l'index d'une variable
    public static int getIndex(Variable var) {
        String nom = var.getName();
        return  nom.charAt(nom.length() -1) - '0';
    }

    //methode pour creer un état, a partir d'une liste de liste (chaque liste representent une pile te chaques chiffres un block)
    public Map<Variable, Object> setState(int[][] state){
        int nbPilesState = state.length;
        Map<Variable, Object> res = new HashMap<>();

        for (int currentPile = 0 ; currentPile < nbPilesState ; currentPile++){
            int nbBlockPile = state[currentPile].length;
            Variable free = this.getFreeBooleanVarWithPileIndex("free_" + currentPile);
            if (nbBlockPile == 0) {
                res.put(free, true);
            } else {
                res.put(free, false);
            }
            for (int currentBlockPosition = 0 ; currentBlockPosition < nbBlockPile ; currentBlockPosition++){
                int currentBlock = state[currentPile][currentBlockPosition];
                Variable on = this.getOnBlockVarWithBlockIndex("On_" + currentBlock);
                Variable fixed = this.getFixedBooleanVarWithBlockIndex("fixed_" + currentBlock);

                if (currentBlockPosition == 0 && nbBlockPile != 1) {
                    res.put(on, -currentPile -1);
                    res.put(fixed, true);
                }
                if (nbBlockPile == 1) {
                    res.put(on, -currentPile -1);
                    res.put(fixed, false);
                }
                if (currentBlockPosition > 0 && currentBlockPosition == nbBlockPile - 1) {
                    res.put(on, state[currentPile][currentBlockPosition - 1]);
                    res.put(fixed, false);
                }
                if (currentBlockPosition > 0 && currentBlockPosition < nbBlockPile - 1) {
                    res.put(on, state[currentPile][currentBlockPosition - 1]);
                    res.put(fixed, true);
                }
            }
        }
        return res;
    }

    //acsesseurs au éléments de cette classe
    public int getNbBlock(){
        return this.nbBlocks;
    }

    
    public int getNbPiles(){
        return this.nbPiles;
    }

    public Set<Variable> getVariables(){
        return this.variables;
    }

   public Set<Variable> getOnbVar() {
        if (this.onbVar == null) {
            this.onbVar = new HashSet<>();
        }
        return this.onbVar;
    }

    public Set<Variable> getFixedbVar() {
        if (this.fixedbVar == null) {
            this.fixedbVar = new HashSet<>();
        }
        return this.fixedbVar;
    }

    public Set<Variable> getFreepVar() {
        if (this.freepVar == null) {
            this.freepVar = new HashSet<>();
        }
        return this.freepVar;
    }


    //methodes permettants de recuperer une variable grace a son index
    public Variable getOnBlockVarWithBlockIndex(String index){
        for(Variable var : this.getVariables()){
            if (var.getName().contains("On_")){
                if (this.carteIndex.get(index).equals(var)){
                    return var;
                }   
            }
        }
        return null;
    }

    public Variable getFixedBooleanVarWithBlockIndex(String index){
        for(Variable var : this.getVariables()){
            if (var.getName().contains("fixed_")){
                if (this.carteIndex.get(index).equals(var)){
                    return var;
                }
            }
        }
        return null;
    }

    public Variable getFreeBooleanVarWithPileIndex(String index){
        for(Variable var : this.getVariables()){
            if (var.getName().contains("free_")){
                if (this.carteIndex.get(index).equals(var)){
                    return var;
                }
            }
        }
        return null;
    }
}

