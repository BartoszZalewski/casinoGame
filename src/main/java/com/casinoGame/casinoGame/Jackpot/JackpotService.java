package com.casinoGame.casinoGame.Jackpot;

import com.casinoGame.casinoGame.Base.Spin;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class JackpotService {
    private List<Jackpot> jackpots;

    public JackpotService(){
        jackpots = new ArrayList<>();

        Jackpot bronze = new Jackpot("bronze", 1000, 2000, 0.005);
        Jackpot silver = new Jackpot("silver", 3000, 5000, 0.002);
        Jackpot gold = new Jackpot("gold", 7000, 10000, 0.001);

        jackpots.add(bronze);
        jackpots.add(silver);
        jackpots.add(gold);
    }

    public void update(Spin spin) {
        double bet = spin.bet;
        Jackpot tmp = null;
        for(Jackpot jackpot : jackpots) {
            jackpot.increase(bet);
            if(jackpot.check()) {
                tmp = jackpot;
            }
        }
        if(tmp != null) {
            spin.addEvent(tmp.getEvent());
            tmp.reset();
        }
    }

    public List<Jackpot> getAll(){
        return jackpots;
    }

}
