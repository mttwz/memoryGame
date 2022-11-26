package com.example.memorygame;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.net.URL;
import java.util.*;

import static com.example.memorygame.Application.mainStage;

public class Controller implements Initializable {

    int matches = 0;
    int minute = 0;
    int second = 0;
    int clickCounter = 0;
    int tileNumber;
    int attemptCounter = 0;

    ArrayList<Integer> randomizedNumbers = new ArrayList<>();
    ArrayList<Tile> clickedTiles = new ArrayList<>();
    List<ImageView> imageViews = new ArrayList<>();
    List<Tile> tiles = new ArrayList<>();
    List<ImageView> disabledTiles = new ArrayList<>();
    boolean gameEndedByUser = false;
    Timer timer;
    Integer timerHelper = 0;

    @FXML
    private Label attemptsLabel;

    @FXML
    private Label matchesLabel;

    @FXML
    private ImageView tile1;

    @FXML
    private ImageView tile10;

    @FXML
    private ImageView tile11;

    @FXML
    private ImageView tile12;

    @FXML
    private ImageView tile13;

    @FXML
    private ImageView tile14;

    @FXML
    private ImageView tile15;

    @FXML
    private ImageView tile16;

    @FXML
    private ImageView tile2;

    @FXML
    private ImageView tile3;

    @FXML
    private ImageView tile4;

    @FXML
    private ImageView tile5;

    @FXML
    private ImageView tile6;

    @FXML
    private ImageView tile7;

    @FXML
    private ImageView tile8;

    @FXML
    private ImageView tile9;

    @FXML
    private ImageView win1;

    @FXML
    private ImageView win2;

    @FXML
    private Label timerLabel;


    @FXML
    void clickOnTile(MouseEvent event) {
        tileNumber = Integer.parseInt(event.getSource().toString().split("e")[3].split(",")[0]);
        clickFunction(tiles.get(tileNumber-1));
    }

    void clickFunction(Tile tile) {
        timerHelper++;
        if (timerHelper == 1){
            memoryGameTimer();
        }
        clickCounter++;
        if(!tile.isRevealed()){
            tile.setRevealed(true);
            imageViews.get(tile.getId()-1).setImage(tile.getImage());
        }
        if(clickCounter==2){
            clickCounter = 0;
            checkMatches();
            attemptCounter++;
            attemptsLabel.setText(String.valueOf(attemptCounter));
        }


    }
    void checkMatches() {
        for (Tile tile : tiles) {
            if (tile.isRevealed()) {
                clickedTiles.add(tile);
            }
        }

        if(Objects.equals(clickedTiles.get(0).getImage().getUrl(), clickedTiles.get(1).getImage().getUrl())){
            clickedTiles.get(0).setFound(true);
            clickedTiles.get(1).setFound(true);
            matches++;
            matchesLabel.setText(String.valueOf(matches));
        }
        else {
            clickedTiles.get(0).setFound(false);
            clickedTiles.get(1).setFound(false);
            showTilesForOneSec(imageViews.get(clickedTiles.get(0).getId()-1),imageViews.get(clickedTiles.get(1).getId()-1));
        }
        if (matches==8){
            win1.setImage(new Image("file:images/win.gif"));
            win2.setImage(new Image("file:images/win.gif"));
        }
        clickedTiles.get(0).setRevealed(false);
        clickedTiles.get(1).setRevealed(false);
        clickedTiles.clear();


    }

    void showTilesForOneSec(ImageView t1, ImageView t2) {
        for (ImageView tile : imageViews) {
            tile.setDisable(true);
        }
        Timer oneSecDelay = new Timer();
        oneSecDelay.schedule(new TimerTask() {
            @Override
            public void run() {
                t1.setImage(new Image("file:images/0.png"));
                t2.setImage(new Image("file:images/0.png"));
                for (ImageView tile : imageViews) {
                    tile.setDisable(disabledTiles.contains(tile));
                }
            }
        }, 1000);


    }

    @FXML
    void closeGame(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        System.exit(0);

    }

    @FXML
    void startNewGame() {

        if (timer!= null){
            timer.cancel();
            timer.purge();
        }
        gameEndedByUser = true;
        randomizedNumbers.clear();
        disabledTiles.clear();
        clickedTiles.clear();
        tiles.clear();
        generateNums();
        addTilesToArraylist();
        showTiles();
        second = 0;
        minute = 0;
        matches = 0;
        clickCounter = 0;
        attemptCounter = 0;
        timerHelper = 0;
        matchesLabel.setText(String.valueOf(matches));
        attemptsLabel.setText(String.valueOf(attemptCounter));
        timerLabel.setText("00:00");
        for (ImageView tile : imageViews) {
            tile.setDisable(false);
        }
        win1.setImage(null);
        win2.setImage(null);
    }

    void addTilesToArraylist() {
        for (int i = 1; i <= 16; i++) {
            Tile t = new Tile(i);
            t.setImage(new Image("file:images/" + randomizedNumbers.get(i-1) + ".png"));
            tiles.add(t);
        }
        imageViews = List.of(tile1,tile2,tile3,tile4,tile5,tile6,tile7,tile8,tile9,tile10,tile11,tile12,tile13,tile14,tile15,tile16);

    }
    void showTiles(){
        for (int i = 0; i < imageViews.size(); i++) {
            imageViews.get(i).setImage(tiles.get(i).getDefaultImage());
        }
    }

    private void memoryGameTimer() {
        gameEndedByUser = false;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (second == 60) {
                        minute++;
                        second = 0;
                    }
                    if (Integer.toString(minute).length() == 1 &&  Integer.toString(second).length() == 1){
                        timerLabel.setText("0"+minute + ":" +"0" +second++);
                    }else if (Integer.toString(minute).length() > 1 &&  Integer.toString(second).length() == 1){
                        timerLabel.setText(minute + ":" +"0" +second++);
                    }else if (Integer.toString(minute).length() == 1 &&  Integer.toString(second).length() > 1){
                        timerLabel.setText("0"+minute + ":" +second++);
                    }else{
                        timerLabel.setText(minute + ":" + second++);
                    }
                    if (matches == 8 || gameEndedByUser) {
                        timer.cancel();
                        timer.purge();
                    }
                });
            }
        }, 0, 1000);
    }

    void generateNums() {
        for (int i = 1; i <= 8; i++) {
            randomizedNumbers.add(i);
            randomizedNumbers.add(i);
        }
        Collections.shuffle(randomizedNumbers);
        showMatchesOnConsole(randomizedNumbers);
    }

    void showMatchesOnConsole(ArrayList<Integer> randomizedNumbers){
        for (int i = 0; i < randomizedNumbers.size(); i++) {
            if (i % Math.sqrt(randomizedNumbers.size()) == 0 && i != 0) {
                System.out.println();
            }
            System.out.print(randomizedNumbers.get(i) + "  ");
        }
        System.out.println("\n\n");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateNums();
        addTilesToArraylist();
        showTiles();

        mainStage.setOnCloseRequest(e -> System.exit(0));
    }
}