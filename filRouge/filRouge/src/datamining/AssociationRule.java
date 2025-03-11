package datamining;

import java.util.Set;

import modelling.BooleanVariable;

//classe representant une regle d'association prenant en parametre un premisse, une conclusion, un frequence et une confiance
public class AssociationRule{

    private Set<BooleanVariable> premisse;
    private Set<BooleanVariable> conclusion;
    private float frequence;
    private float confiance;

    public AssociationRule(Set<BooleanVariable> premisse, Set<BooleanVariable> conclusion, float frequence, float confiance){
        this.premisse = premisse;
        this.conclusion = conclusion;
        this.frequence = frequence;
        this.confiance = confiance;
    }

    //acsesseurs vers les attributs de cette classe
    public Set<BooleanVariable> getPremise(){
        return this.premisse;
    }

    public Set<BooleanVariable> getConclusion(){
        return this.conclusion;
    }

    public float getFrequency(){
        return this.frequence;
    }

    public float getConfidence(){
        return this.confiance;
    }

    public String toString (){
        String res = "Premisse = ";

        for (BooleanVariable var : this.getPremise()){
            res += " " + var.getName();
        }

        res += " confidence = " + this.getConfidence() + " frequency " + this.getFrequency();
        return res;
    }
}