package datamining;

import java.util.Set;

import modelling.BooleanVariable;

//classe permetant de representer un ensemble de variableBooleennes et sa frequence
public class Itemset{
    private Set<BooleanVariable> items;
    private float frequence;

    public Itemset(Set<BooleanVariable> items, float frequence){
        this.items = items;
        if (frequence<=1 && frequence >= 0){
            this.frequence = frequence;
        }
        else{
            throw new IllegalArgumentException("frequency not valable");
        }

    }

    //accesseurs aux Items et a la frequence
    public Set<BooleanVariable> getItems(){
        return this.items;
    }

    public float getFrequency(){
        return this.frequence;
    }

    public String toString(){
        String res ="";
        for (BooleanVariable var : this.getItems()){
            res += " "+var.getName();
        }
        res += " frequence = " + this.getFrequency();
        return res;
    }
}