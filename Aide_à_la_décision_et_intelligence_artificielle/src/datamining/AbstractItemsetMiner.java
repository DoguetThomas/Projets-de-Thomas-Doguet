package datamining;

import java.util.Set;
import java.util.List;
import java.util.Comparator;

import modelling.BooleanVariable;

//classe abstraite implementant ItemSetMiner prenant en argument une base de donnee transactionnelle
public abstract class AbstractItemsetMiner implements ItemsetMiner {
	protected BooleanDatabase base;

    public static final Comparator<BooleanVariable> COMPARATOR = 
        (var1, var2) -> var1.getName().compareTo(var2.getName());

    public AbstractItemsetMiner(BooleanDatabase base){
        this.base = base;
    }
    //accesseur a la base de donnee
    @Override
    public BooleanDatabase getDatabase(){
        return this.base;
    }

    //methode retournant la frequence d'un emsemble d'items en fonction de la bse de donnée
    public float frequency(Set<BooleanVariable> items) {
        int count = 0;
        List<Set<BooleanVariable>> transactions = base.getTransactions();

        for (Set<BooleanVariable> transaction : transactions) {
            if (transaction.containsAll(items)) {
                count++;
            }
        }
        return (float) count / transactions.size();
    }
}
