package blockWorld.view;

import java.util.List;

import view.View;
import planning.Action;

//classe qui expend THREAD qui permet de lancer la vue pour affcihe le derourelemnt d'un plan placé en parametre
public class BWView extends Thread{
    private View view;
    private List<Action> plan;

    public BWView(View view, List<Action> plan){
        this.view = view;
        this.plan = plan;
    }

    @Override
    public void run() {
        view.playingPlan(plan);
    }
}