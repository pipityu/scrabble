package com.sce.demo.controller;
import com.sce.demo.model.Token;
import com.sce.demo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
public class GameController {
    Logger logger = Logger.getLogger(GameController.class.getName());
    @Autowired
    GameService gameService;

    @GetMapping("/gamestate")
    public Map<String, Token> getGameState(){
        return gameService.getGameState();
    }
    @GetMapping("/remaintokens")
    public Map<String, Token>remainTokensInGame(){
        return gameService.remainTokens();
    }
    @PutMapping("/newgame")
    public void newGame(){
        gameService.startNewGame();
    }
    @GetMapping("/generate")
    public ArrayList<Token> generate(){
        return gameService.generateMyTokens();
    }

    @PutMapping("/changetoken")
    public void changeToken(@RequestParam String charName, String id){
        System.out.println(charName+"--"+id);
        gameService.updateGameState(id,charName);
        gameService.deleteToken(charName);
    }
    @GetMapping("/hints")
    public Map<String, Integer> getTest(){
        return gameService.getFiveBestWords();
    }


}
