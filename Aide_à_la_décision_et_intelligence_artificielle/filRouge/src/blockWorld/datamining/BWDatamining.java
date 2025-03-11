package blockWorld.datamining;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import blockWorld.modelling.BWVariable;
import datamining.BooleanDatabase;
import modelling.BooleanVariable;
import modelling.Variable;
import bwgeneratordemo.Demo;

//classe créant toutes les variables de transactions
public class BWDatamining{
    private Set<BooleanVariable> variables;
    private Map<String, BooleanVariable> carteNameVariable;
    private BWVariable bwVariable;
    private Random random;

    public BWDatamining(int nbBlock, int nbPile){
        this.bwVariable = new BWVariable(nbBlock, nbPile);
        this.carteNameVariable = new HashMap<>();
        this.variables = new HashSet<>();
        this.createBooleansVar();
        this.random = new Random();
    }

    public void createBooleansVar(){
        this.createOnbbBooleanVariable();
        this.createOnTablebpBooleanVariable();
    }

    //création des variables booleen onbb', qui prend true si b est posé sur b'
    private void createOnbbBooleanVariable(){
        for (Variable onVar : bwVariable.getOnbVar()) {
            for (Variable fixedVar : bwVariable.getFixedbVar()) {
                int onVarIndex = BWVariable.getIndex(onVar);
                int fixedVarIndex = BWVariable.getIndex(fixedVar);
                if (onVarIndex != fixedVarIndex){
                    String name = "on_" + Integer.toString(onVarIndex) + "_" + Integer.toString(fixedVarIndex);
                    BooleanVariable currentOnbbVar = new BooleanVariable(name);
                    variables.add(currentOnbbVar);
                    variables.add((BooleanVariable) fixedVar);
                    carteNameVariable.put(name, currentOnbbVar);
                    carteNameVariable.put("fixed_" + Integer.toString(fixedVarIndex), (BooleanVariable) fixedVar);
                }
            }
        }
    }

    //creation des variables booleennes on table b qui prend true si le le block p est en bas de la pile
    private void createOnTablebpBooleanVariable(){
        for (Variable onVar : bwVariable.getOnbVar()) {
            for (Variable freeVar : bwVariable.getFreepVar()) {
                int onVarIndex = BWVariable.getIndex(onVar);
                int freeVarIndex = BWVariable.getIndex(freeVar);
                if (onVarIndex != freeVarIndex){
                    String name = "on_" + Integer.toString(onVarIndex) + "_" + Integer.toString(freeVarIndex);
                    BooleanVariable currentOnbbVar = new BooleanVariable(name);
                    variables.add(currentOnbbVar);
                    variables.add((BooleanVariable) freeVar);
                    carteNameVariable.put(name, currentOnbbVar);
                    carteNameVariable.put("free_" + Integer.toString(freeVarIndex), (BooleanVariable) freeVar);
                }
            }
        }
    }

    //remise en place des vriables onb et fixed b dans la meme logique que dans la classe BWVariable
    public Set<BooleanVariable> setTransctionsVariables(List<List<Integer>> transaction) {
        int nbPile = transaction.size();
        Set<BooleanVariable> res = new HashSet<>();

        for (int indexPile = 0 ; indexPile < nbPile; indexPile++){
            List<Integer> currentPile = transaction.get(indexPile);
            if (currentPile.size() == 0){
                String name = "free_" + Integer.toString(indexPile);
                if (carteNameVariable.containsKey(name)){
                    res.add(carteNameVariable.get(name));
                }
                else{
                    BooleanVariable currentBooleanVariable = new BooleanVariable(name);
                    res.add(currentBooleanVariable);
                    this.carteNameVariable.put(name, currentBooleanVariable);
                }
            }
            else{
                int nbBlock = currentPile.size();
                for (int indexBlockInPile = 0 ; indexBlockInPile < nbBlock; indexBlockInPile++){
                    int currentBlock = currentPile.get(indexBlockInPile);
                    if (indexBlockInPile == 0){
                        String name = "onTable_" + Integer.toString(currentBlock)+ "_" + Integer.toString(indexPile);
                        if (carteNameVariable.containsKey(name)){
                            res.add(carteNameVariable.get(name));
                        }
                        else{
                            BooleanVariable currentBooleanVariable = new BooleanVariable(name);
                            res.add(currentBooleanVariable);
                            this.carteNameVariable.put(name, currentBooleanVariable);
                        }
                    }
                    if (indexBlockInPile < nbBlock - 1) {
                        int aboveOfCurrentBlock = currentPile.get(indexBlockInPile+1);
                        String name_b1_b = "on_" +  Integer.toString(aboveOfCurrentBlock) + "_" + Integer.toString(currentBlock);
                        if (carteNameVariable.containsKey(name_b1_b)){
                            res.add(carteNameVariable.get(name_b1_b));
                        }
                        else{
                            BooleanVariable currentBooleanVariable = new BooleanVariable(name_b1_b);
                            res.add(currentBooleanVariable);
                            this.carteNameVariable.put(name_b1_b, currentBooleanVariable);
                        }
                        String name_f = "fixed_" + Integer.toString(currentBlock);
                        if (carteNameVariable.containsKey(name_f)){
                            res.add(carteNameVariable.get(name_f));
                        }
                        else{
                            BooleanVariable currentBooleanVariable = new BooleanVariable(name_f);
                            res.add(currentBooleanVariable);
                            this.carteNameVariable.put(name_f, currentBooleanVariable);
                        }
                    }
                }
            }
        }
        return res;
    }

    public BooleanDatabase createDatabaseWithLib(int n){
        BooleanDatabase db = new BooleanDatabase(this.getBooleansVariables());
        for (int i = 0; i < n; i++) {
            // Drawing a state at random
            List<List<Integer>> state = Demo.getState(this.random);
            // Converting state to instance
            Set<BooleanVariable> instance = setTransctionsVariables(state);
            // Adding state to database
            db.add(instance);
        }
        return db;
    }

    public Set<BooleanVariable> getBooleansVariables(){
        return this.variables;
    }

    public BWVariable getBWVariable(){
        return this.bwVariable;
    }

    public Map<String, BooleanVariable> getCarte(){
        return this.carteNameVariable;
    }

    @Override
    public String toString() {
        return "{" +
            " items='" + getBooleansVariables() + "'" +
            "}";
    }
}