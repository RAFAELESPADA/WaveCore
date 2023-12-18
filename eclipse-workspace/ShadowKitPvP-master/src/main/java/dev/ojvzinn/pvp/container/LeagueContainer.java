package dev.ojvzinn.pvp.container;

import dev.ojvzinn.pvp.league.LeagueManager;
import dev.slickcollections.kiwizin.database.data.DataContainer;
import dev.slickcollections.kiwizin.database.data.interfaces.AbstractContainer;

public class LeagueContainer extends AbstractContainer {
    public LeagueContainer(DataContainer dataContainer) {
        super(dataContainer);
        if (this.dataContainer.getAsInt() == -1) {
            System.out.println(LeagueManager.listLeagues().get(LeagueManager.listLeagues().size() - 1).getName());
            this.dataContainer.set(LeagueManager.listLeagues().get(LeagueManager.listLeagues().size() - 1));
        }
    }

    public void setLeagueID(int id) {
        this.dataContainer.set(id);
    }

    public int getLeagueID() {
        return this.dataContainer.getAsInt();
    }
}
