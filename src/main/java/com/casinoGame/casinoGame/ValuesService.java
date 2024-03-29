package com.casinoGame.casinoGame;

import com.casinoGame.casinoGame.Game.Game;
import com.casinoGame.casinoGame.Base.Player;
import com.casinoGame.casinoGame.Base.Range;
import com.casinoGame.casinoGame.Base.Spin;
import com.casinoGame.casinoGame.Jackpot.JackpotService;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
@Controller
public class ValuesService {

    private List<Integer> values;
    private int next;
    private JackpotService jackpotService;
    private int reserve;

    public ValuesService(){
        System.out.println("Service init");
        jackpotService = new JackpotService();
        initMachine();
    }

    public void initMachine() {
        next = 0;
        values = new ArrayList<>();

        double winSize = 0.2;
        double size = 10000;
        double solvency = 0.9;
        double minValue = 2;

        double sumValue = size * solvency;
        for(int i = 0; i < size * winSize; i++) {
            int nextValue = (int) (new Random().nextInt((int)( sumValue - minValue)) + minValue);
            if(nextValue > 0) {
                values.add(nextValue);
            } else {
                i--;
            }
            sumValue -= nextValue;
            if(sumValue <= minValue) {
                int index = getMaxIndex(values, i);
                sumValue += values.get(index);
                values.remove(values.get(index));
                i--;
            }
        }
        if(sumValue > 0){
            int last = values.get(values.size() - 1);
            values.remove(values.size() - 1);
            values.add(last + (int)sumValue);
        }

        for(int i = 0; i < size * (1 - winSize); i++) {
            values.add(0);
        }

        int sum = getSum(values);

        for(int i =0 ; i< 10; i++) {
            Collections.shuffle(values);
        }

        int zero = matchValues(values, 0);

        System.out.println(values);
        System.out.println(sum);
        System.out.println(zero);

    }

    private int getMaxIndex(List<Integer> values, int limit){
        int max = -1;
        int index = 0;

        for(int i = 0; i < limit; i++) {
            int next = values.get(i);
            if(next > max){
                max = next;
                index = i;
            }
        }

        return index;
    }

    private int getSum(List<Integer> values){
        int sum = 0;

        for(Integer integer : values){
            sum += integer;
        }

        return sum;
    }

    private int matchValues(List<Integer> values, int value) {
        int counter = 0;

        for(Integer integer : values){
            if(integer == value){
                counter++;
            }
        }

        return counter;
    }

    @RequestMapping(value = "/nextSpin/{nickname}/{bet}", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public @ResponseBody String getNextSpin(@RequestBody int[][] currentBoard, @PathVariable String nickname, @PathVariable int bet) {

        Session session = SessionRepository.getSession(nickname);
        Game game = session.getGame();
        Player player = session.getPlayer();

        if(player.getCredits() < bet) {
            return new Gson().toJson(new Spin(currentBoard,null,0,0, 0));
        }

        int value = values.get(next) + reserve;
        next = (next + 1) % values.size();

        Spin spin = game.getNextSpin(new Range((int) (value * 0.5), value), bet);

        adjustReserve(spin, value, bet);

        player.addCredits(spin.win);
        player.deleteCredits(spin.lose);

        jackpotService.update(spin);

        return new Gson().toJson(spin);
    }

    private void adjustReserve(Spin spin, int value, int bet){
        reserve = value - spin.getWin()/bet;
        System.out.println("Reserve : " + reserve);
    }

    @RequestMapping(value = "/allJackpots")
    public @ResponseBody String getAll() {
        return new Gson().toJson(jackpotService.getAll());
    }

}
