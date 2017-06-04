package com.casinoGame.casinoGame;

import com.casinoGame.casinoGame.Core.Player;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SessionRepository {

    private static List<Session> sessions = new ArrayList<>();

    public SessionRepository(){
        sessions = new ArrayList<>();
    }

    public static synchronized boolean createNew(Session session){
        if(sessions.contains(session)) {
            return false;
        }
        sessions.add(session);
        return true;
    }
    public static synchronized List<Session> getAll() {
        return sessions;
    }

    public static synchronized Player getPlayer(String name) {
        for(Session session : sessions) {
            if(session.getPlayer().nickName.equals(name)) {
                return session.getPlayer();
            }
        }
        return null;
    }

    public static synchronized Session getSession(String name) {
        for(Session session : sessions) {
            if(session.getPlayer().nickName.equals(name)) {
                return session;
            }
        }
        return null;
    }

    @RequestMapping("/allPlayers")
    public @ResponseBody String getAllPlayers(){
        List<Player> players = new ArrayList<>();
        for(Session session : SessionRepository.getAll()) {
            Player player = session.getPlayer();
            players.add(player);
        }
        return new Gson().toJson(players);
    }

}
