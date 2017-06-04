package com.casinoGame.casinoGame;

import com.casinoGame.casinoGame.Core.Game;
import com.casinoGame.casinoGame.Core.Player;
import com.casinoGame.casinoGame.Core.Spin;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Controller
public class ValuesService {

    List<Integer> values;
    int next;

    public ValuesService(){
        System.out.println("Service init");
        initMachine();
    }

    public void initMachine() {
        next = 0;
        values = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            values.add(new Random().nextInt(50));
        }
    }

    @RequestMapping(value = "nextSpin/{nickname}/{bet}")
    public @ResponseBody String getNextSpin(Model model, @PathVariable String nickname, @PathVariable int bet) {

        Session session = SessionRepository.getSession(nickname);
        Game game = session.getGame();
        int value = values.get(next);
        next = (next + 1) % values.size();
        Spin spin = game.getNextSpin(value, bet);
        Player player = session.getPlayer();
        player.addCredits(spin.win);
        player.deleteCredits(spin.lose);

        return new Gson().toJson(spin);
    }


}
