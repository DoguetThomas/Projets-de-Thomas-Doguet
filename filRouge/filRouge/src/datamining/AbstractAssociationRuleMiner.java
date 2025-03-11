package datamining;

import java.util.Set;
import java.util.HashSet;

import modelling.BooleanVariable;

//classe abstraite implementant l'interface associationRuleMiner prenant en paramettre une base de donnee transactionnelle

public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner{
    private BooleanDatabase database;

    public AbstractAssociationRuleMiner(BooleanDatabase database){
        this.database = database;
    }

    //accesseur a la base donnée transactionelle
    @Override
    public BooleanDatabase getDatabase(){
        return this.database;
    }

    //methode calculant la frequence
    public static float frequency(Set<BooleanVariable> items, Set<Itemset> itemsets){
        for (Itemset itemset : itemsets){
            if(itemset.getItems().equals(items)){
                return itemset.getFrequency();
            }
        }
        throw new IllegalArgumentException("L'ensemble items n'est pas dans l'ensemble d'itemsets");
    }

    //methode de calcul de la confiance selon (frequence(union(premisse, conclusion))/ frequence(premisse))
    public static float confidence(Set<BooleanVariable> premisse, Set<BooleanVariable> conclusion, Set<Itemset> itemsets){
        Set<BooleanVariable> union = new HashSet<>(premisse);
        union.addAll(conclusion);

        Float frequencePremisse = frequency(premisse, itemsets);
        Float frequenceUnion = frequency(union, itemsets);

        return frequenceUnion / frequencePremisse;
    }
}