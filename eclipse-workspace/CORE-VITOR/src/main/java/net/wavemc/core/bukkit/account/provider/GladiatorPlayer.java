package net.wavemc.core.bukkit.account.provider;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor @Data
public class GladiatorPlayer {

    private int wins, defeats, winstreak;

    public void addWins(int wins) {
        setWins(getWins() + wins);
    }

    public void addDefeats(int defeats)  {
        setDefeats(getDefeats() + defeats);
    }

    public void addWinstreak(int winstreak) {
        setWinstreak(getWinstreak() + winstreak);
    }
}
