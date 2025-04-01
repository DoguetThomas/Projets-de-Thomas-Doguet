package datamining;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

import modelling.BooleanVariable;

//class permettant de representer les basses de données transactionnelles, prenant en argument un ensemble d'items, et creant une base vide
public class BooleanDatabase{
    private Set<BooleanVariable> items;
    private List<Set<BooleanVariable>> transactions;

    public BooleanDatabase(Set<BooleanVariable> items){
        this.items = new HashSet<>(items);
        this.transactions = new ArrayList<>();
    }

    //methode add permettant d'ajouter une transaction dans la base de donnee
    public void add(Set<BooleanVariable> transaction){
        this.transactions.add(new HashSet<>(transaction));
    }


    //accesseurs aux items et transactions
    public Set<BooleanVariable> getItems(){
        return new HashSet<>(this.items);
    }

    public List<Set<BooleanVariable>> getTransactions(){
        return new ArrayList<>(this.transactions);
    }
}
