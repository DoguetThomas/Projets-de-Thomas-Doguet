package blockWorld.datamining;

import datamining.BooleanDatabase;
import datamining.Apriori;
import datamining.Itemset;
import datamining.AssociationRule;
import datamining.BruteForceAssociationRuleMiner;

import java.util.Set;

public class BWDataminingExecutable {
	public static void main(String[] args) {
        BWDatamining booleanDatamining = new BWDatamining(18, 9);

        BooleanDatabase booleanDatabase = booleanDatamining.createDatabaseWithLib(10000);

        //on teste d'abord pour apriori avec une frequence de 2/3

        Apriori apriori = new Apriori(booleanDatabase);
        Set<Itemset> items = apriori.extract((float)2/3);

        for (Itemset item : items) {
			System.out.println(item);
		}

        BruteForceAssociationRuleMiner miner = new BruteForceAssociationRuleMiner(booleanDatabase);
		Set<AssociationRule> rules = miner.extract((float) 2 / 3, (float) 95 / 100);
		for (AssociationRule rule : rules) {
			System.out.println(rule);
		}
    }
}