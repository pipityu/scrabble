package com.sce.demo.model;

import com.sce.demo.file.FileHandler;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Game {
    private List<Token> tokens = new ArrayList<>();
    private HashMap<String, Token> state = new HashMap<>();
    private Map<String, Integer> words = new HashMap<>();
    //private TreeMap<String, Token> gameState = new TreeMap<>();
    private SortedMap<String, Token> gameState = new TreeMap<>(new Comparator<String>() {
        @Override
        public int compare(String s1, String s2) {
            String []arrayA = s1.split("-");
            String []arrayB = s2.split("-");
            int a = Integer.parseInt(arrayA[0]);
            int b = Integer.parseInt(arrayB[0]);
            int c = Integer.parseInt(arrayA[1]);
            int d = Integer.parseInt(arrayB[1]);
            if(a!=b)return (a>b)?1:-1;
            else if(a==b) return (c>d)?1:-1;
            else return 0;
        }
    });

    {

        //Init block for all tokens in the game, and the default game state
        int i;
        for(i=0; i<6; i++){
            tokens.add(new Token(TokenEnum.A));
            tokens.add(new Token(TokenEnum.E));
            tokens.add(new Token(TokenEnum.K));
            tokens.add(new Token(TokenEnum.T));
            tokens.add(new Token(TokenEnum.Á));
            tokens.add(new Token(TokenEnum.L));
            tokens.add(new Token(TokenEnum.R));
            tokens.add(new Token(TokenEnum.N));
            tokens.add(new Token(TokenEnum.I));
            tokens.add(new Token(TokenEnum.M));
            tokens.add(new Token(TokenEnum.O));
            tokens.add(new Token(TokenEnum.S));
        }
        for(i=0;i<3;i++){
            tokens.add(new Token(TokenEnum.B));
            tokens.add(new Token(TokenEnum.D));
            tokens.add(new Token(TokenEnum.G));
            tokens.add(new Token(TokenEnum.Ó));
            tokens.add(new Token(TokenEnum.É));
            tokens.add(new Token(TokenEnum.H));
            tokens.add(new Token(TokenEnum.SZ));
            tokens.add(new Token(TokenEnum.V));
        }
        for(i=0;i<2;i++){
            tokens.add(new Token(TokenEnum.F));
            tokens.add(new Token(TokenEnum.GY));
            tokens.add(new Token(TokenEnum.J));
            tokens.add(new Token(TokenEnum.Ö));
            tokens.add(new Token(TokenEnum.P));
            tokens.add(new Token(TokenEnum.U));
            tokens.add(new Token(TokenEnum.Ü));
            tokens.add(new Token(TokenEnum.Z));
            tokens.add(new Token(TokenEnum.JOKER));
        }
        tokens.add(new Token(TokenEnum.C));
        tokens.add(new Token(TokenEnum.Í));
        tokens.add(new Token(TokenEnum.NY));
        tokens.add(new Token(TokenEnum.CS));
        tokens.add(new Token(TokenEnum.Ő));
        tokens.add(new Token(TokenEnum.Ú));
        tokens.add(new Token(TokenEnum.Ű));
        tokens.add(new Token(TokenEnum.LY));
        tokens.add(new Token(TokenEnum.ZS));
        tokens.add(new Token(TokenEnum.TY));

        String id;
        for(int l=1; l<=15; l++){
            for(int m=1; m<=15; m++){
               id = l+"-"+m;
               state.put(id,new Token());
            }
        }
        FileHandler fh = new FileHandler();
        words = fh.readAllWords("src/main/resources/file/freedict");
/*        for(Map.Entry<String, Integer> entry : words.entrySet()){//########nem sajat###########x
            System.out.println(entry.getKey());
        }*/

    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Map<String, Token> getGameState() {

        return gameState;
    }

    public void setGameState(HashMap<String, Token> gameState) {
        this.state = gameState;
    }

    public void changeState(String id, Token t){
        this.state.put(id,t);
        gameState.clear();
        gameState.putAll(state);
    }

    public Map<String, Integer> getWords() {
        return words;
    }
}
