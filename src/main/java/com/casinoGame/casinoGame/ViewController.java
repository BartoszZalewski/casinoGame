package com.casinoGame.casinoGame;

import com.casinoGame.casinoGame.Base.Player;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @RequestMapping("/")
    public String index(){
        return "welcomePage";
    }

    @RequestMapping("/gamesPanel/{name}")
    public String game(Model model, @PathVariable String name) {
        Session session = new Session(new Player(name, 500));
        SessionRepository.createNew(session);
        model.addAttribute("username", name);
        model.addAttribute("credits", SessionRepository.getPlayer(name).getCredits());
        return "gamesPanel";
    }


}
