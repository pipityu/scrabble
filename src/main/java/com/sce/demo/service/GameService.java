package com.sce.demo.service;

import com.sce.demo.model.Game;
import com.sce.demo.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {
    private List<List<Token>> hints = new ArrayList<>();
    private Map<String, Integer> hintsWithPoints = new HashMap<>();
    private ArrayList<Token> myTokens;
    private String letters = "";

    @Autowired
    Game game;

    //Update
    public void updateGameState(String id, String name){
        try{
            for(Token t : game.getTokens()){
                if(t.getName().compareTo(name)==0){
                    game.changeState(id,t);
                    break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Map<String, Token> getGameState(){
        return game.getGameState();
    }

    //Generate my 7 Tokens
    public ArrayList<Token> generateMyTokens(){

        List<Token> gameTokens = game.getTokens();
        myTokens = new ArrayList<>();
        this.letters = "";
        int random;
        for(int i=0; i<7;i++){
            random = (int)((Math.random()*gameTokens.size()-1)+1);
            myTokens.add(gameTokens.get(random));
            this.letters += gameTokens.get(random).getName().toLowerCase();
            gameTokens.remove(random);
        }
        game.setTokens(gameTokens);

        return myTokens;
    }

    //Start a new game
    public void startNewGame(){
        this.game = new Game();
        this.hints.clear();
        this.hintsWithPoints.clear();
        this.myTokens.clear();
        this.letters = "";
    }

    //Delete a certain token from the all
    public void deleteToken(String name){
        List<Token> tokens = game.getTokens();
        for(int i=0; i<tokens.size(); i++){
            if(tokens.get(i).getName().compareTo(name)==0){
                tokens.remove(i);
                break;
            }
        }
        game.setTokens(tokens);
    }

    //Get the remain tokens
    public Map<String, Token> remainTokens(){
        Map<String,Token> tokenMap = new TreeMap<>();
        for (Token token : game.getTokens()) tokenMap.put(token.getName(),token);
        return tokenMap;
    }


    //Get 5 best word(according to points)
    public Map<String, Integer> getFiveBestWords(){

        SortedMap<String, Token> filled = new TreeMap<>();
        HashMap<Integer,String> idMap = new HashMap<>();
        int i = 0;
        int j;
        //collect the fields which contains token
        for(Map.Entry<String, Token> entry : game.getGameState().entrySet()){
            String key = entry.getKey();
            Token token = entry.getValue();
            if(token.getName() != null){
                filled.put(key,token);
                idMap.put(i,key);
                i++;
            }
        }
        for(Map.Entry<String, Token> entry : filled.entrySet()){
            String key = entry.getKey();
            Token token = entry.getValue();
            System.out.println("key:"+key+", token:"+token.getName());
        }

        //calculate the start and the end field
        int startId = 0;
        int endId = 0;
        List<Token> word = new ArrayList<>();
        for(Map.Entry<String, Token> entry : filled.entrySet()){
            word.add(entry.getValue());
        }

        //calculate row/col index to use for the inverse search
        int oneCharacterIndex = 0;
        if(idMap.size()>0) {
            String[] startIndexSplitted = idMap.get(0).split("-");
            String[] endIndexSplitted = idMap.get(idMap.size() - 1).split("-");
            if (startIndexSplitted[0].compareTo(endIndexSplitted[0]) == 0) {
                startId = Integer.parseInt(startIndexSplitted[1]);
                endId = Integer.parseInt(endIndexSplitted[1]);
                oneCharacterIndex = Integer.parseInt(startIndexSplitted[0]);
            } else {
                startId = Integer.parseInt(startIndexSplitted[0]);
                endId = Integer.parseInt(endIndexSplitted[0]);
                oneCharacterIndex = Integer.parseInt(startIndexSplitted[1]);
            }
        }
        System.out.println("START:"+startId+" END:"+endId+" ONECHARINDEX:"+oneCharacterIndex);

        List<Token> prefix = new ArrayList<>();
        List<Token> suffix = new ArrayList<>();
        List<Token> partLetters;
        List<Token> fullword = new ArrayList<>();
        List<List<Token>> combinationsForRemove = new ArrayList<>();

        /*
        * - loop through the tokens (ABCD):A,AB,ABC,ABCD,B,BC...
        * - combine the inside tokens(ABCD):B,C,BC
        * - remove the combinations and permute with the remaining tokens
        * - check the borders and call the collectHints() method
        * */
        for(i=0; i<myTokens.size(); i++) {
            for(j=i+1;j<=myTokens.size();j++) {
                partLetters = myTokens.subList(i,j);
                combinationsForRemove.clear();
                if(partLetters.size()>2) {
                    combinationsForRemove = combine(partLetters.subList(1, partLetters.size() - 1));    //A...D
                }else combinationsForRemove.add(partLetters);

                for (int d = 0; d < combinationsForRemove.size(); d++) {
                    partLetters = new ArrayList<>(myTokens.subList(i, j));
                    if(partLetters.size()>2) {
                        partLetters.removeAll(combinationsForRemove.get(d));
                    }

                    List<List<Token>> permuteList = permute(partLetters);
                    HashMap<String, List<Token>> tokensMap = new HashMap<>();
                    String str;
                    for(List<Token> pl: permuteList) {
                        str="";
                        for (Token t : pl) {
                            str+=t.getName();
                        }
                        tokensMap.put(str,pl);
                    }

                    for(Map.Entry<String, List<Token>> entry : tokensMap.entrySet()) {
                        for (int k = 0; k <= entry.getValue().size(); k++) {
                            fullword.clear();
                            prefix.clear();
                            suffix.clear();
                            prefix.addAll(entry.getValue().subList(0, entry.getValue().size() - k));
                            suffix.addAll(entry.getValue().subList(entry.getValue().size() - k, entry.getValue().size()));

                            if((prefix.size()<startId)){
                                fullword.addAll(prefix);
                            }

                            fullword.addAll(word);

                            if((endId+suffix.size())<=15){
                                fullword.addAll(suffix);
                            }
                            if(!fullword.equals(word)){
                                collectHints(fullword);
                            }

                            if(prefix.size()>=oneCharacterIndex){continue;}
                            if((oneCharacterIndex+suffix.size())>15){continue;}
                            for (int inverse = 0; inverse < word.size(); inverse++) {
                                fullword.clear();
                                fullword.addAll(prefix);
                                fullword.add(word.get(inverse));
                                fullword.addAll(suffix);
                                if(!fullword.equals(word)) {
                                    collectHints(fullword);
                                }
                            }

                        }

                    }
                }
            }
        }
        //After collect hints select the 5 best
        Map<String, Integer> hintsWithPoints = calculatePoints();
        for(Map.Entry<String,Integer> entry:hintsWithPoints.entrySet()){
            System.out.println(entry.getKey());
        }
        int max = 0;
        String key = "";
        if(hintsWithPoints.size()<=5){
            return hintsWithPoints;
        }else{
            Map<String, Integer> fiveBest = new HashMap<>();
            for (int f=0; f<5; f++) {
                max = 0;
                for(Map.Entry<String, Integer> entry : hintsWithPoints.entrySet()){
                    if(entry.getValue()>max){
                        max = entry.getValue();
                        key = entry.getKey();
                    }
                }
                fiveBest.put(key,max);
                hintsWithPoints.remove(key);
            }
            return  fiveBest;
        }
    }

    //combines the tokens
    public List<List<Token>> combine(List<Token> tokens) {
        List<Token> combine;
        List<List<Token>> combines = new ArrayList<>();
        int n = tokens.size();
        String binaryStr;
        String prefix="";

        if(tokens.size()<1){
            return combines;
        }

        for(int i=0; i<=Math.pow(2,n)-1; i++){
            binaryStr = Integer.toBinaryString(i);
            combine = new ArrayList<>();
            if(binaryStr.length()<tokens.size()){
                int diff = tokens.size()-binaryStr.length();
                prefix = "";
                for(int d=0;d<diff;d++){prefix+="0";}
                prefix+=binaryStr;
                binaryStr=prefix;
            }
            for(int j = 0; j<binaryStr.length();j++){
                if(binaryStr.charAt(j) == '1'){
                    combine.add(tokens.get(j));
                }
            }
            combines.add(combine);
        }

        return combines;
    }

    //permutes the combinations
    public List<List<Token>> permute(List<Token> original) {
        if (original.isEmpty()) {
            List<List<Token>> result = new ArrayList<>();
            result.add(new ArrayList<>());
            return result;
        }
        Token firstElement = original.remove(0);
        List<List<Token>> returnValue = new ArrayList<>();
        List<List<Token>> permutations = permute(original);
        for (List<Token> smallerPermutated : permutations) {
            for (int index=0; index <= smallerPermutated.size(); index++) {
                List<Token> temp = new ArrayList<>(smallerPermutated);
                temp.add(index, firstElement);
                returnValue.add(temp);
            }
        }
        return returnValue;
    }

    //collect hints
    public void collectHints(List<Token> fullword){
        String strTokens = "";
        for(Token t : fullword){
            strTokens+=t.getName().toLowerCase();
        }
        if (game.getWords().containsKey(strTokens)){
            List<Token> wordToken = new ArrayList<>(fullword);
            hints.add(wordToken);
        }
    }

    //calculate points and use hashmap to avoid duplicates
    public Map<String,Integer> calculatePoints() throws IllegalArgumentException{
        int points;
        String strTokens;
        for(List<Token> lt: hints){
            points = 0;
            strTokens = "";
            for(Token t: lt){
                strTokens+=t.getName();
                points+=t.getValue();
            }
            hintsWithPoints.put(strTokens,points);
        }

        return hintsWithPoints;
    }


}
