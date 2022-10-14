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
    ArrayList<Integer> clickedTilesValue = new ArrayList<>();
    ArrayList<Integer> clickedTiles = new ArrayList<>();
    List<ImageView> tiles = new ArrayList<>();
    List<ImageView> disabledTiles = new ArrayList<>();
    boolean gameEndedByUser = false;
    Timer timer;

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
        clickFunction(tileNumber);
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
            clickedTilesValue.clear();
            clickedTiles.clear();
            generateNums();
            setDefaultImg();
            second = 0;
            minute = 0;
            matches = 0;
            clickCounter = 0;
            attemptCounter = 0;
            matchesLabel.setText(String.valueOf(matches));
            attemptsLabel.setText(String.valueOf(attemptCounter));
            timerLabel.setText("00:00");
            for (ImageView tile : tiles) {
                tile.setDisable(false);
            }
            win1.setImage(null);
            win2.setImage(null);
    }

    void clickFunction(int tileNumber) {
        clickCounter++;
        if (clickCounter == 1){
            memoryGameTimer();
        }
        tiles.get(tileNumber - 1).setImage(new Image("file:images/" + randomizedNumbers.get(tileNumber - 1) + ".png"));
        clickedTilesValue.add(randomizedNumbers.get(tileNumber - 1));
        clickedTiles.add(tileNumber);
        tiles.get(tileNumber - 1).setDisable(true);
        checkMatches();
    }

    void addTilesToArraylist() {
        tiles = List.of(tile1,tile2,tile3,tile4,tile5,tile6,tile7,tile8,tile9,tile10,tile11,tile12,tile13,tile14,tile15,tile16);
    }

    void checkMatches() {
        int tileNum1;
        int tileNum2;
        if (clickedTilesValue.size() == 2) {
            attemptCounter++;
            attemptsLabel.setText(String.valueOf(attemptCounter));
            tileNum1 = clickedTilesValue.get(0);
            tileNum2 = clickedTilesValue.get(1);
            if (tileNum1 != tileNum2) {
                showTilesForOneSec(tiles.get(clickedTiles.get(0) - 1), tiles.get(clickedTiles.get(1) - 1));
            } else if ((!Objects.equals(clickedTiles.get(0), clickedTiles.get(1)))) {
                matches++;
                matchesLabel.setText(String.valueOf(matches));
                tiles.get(clickedTiles.get(0) - 1).setDisable(true);
                tiles.get(clickedTiles.get(1) - 1).setDisable(true);
                disabledTiles.add(tiles.get(clickedTiles.get(0) - 1));
                disabledTiles.add(tiles.get(clickedTiles.get(1) - 1));
            }else {
                tiles.get(clickedTiles.get(0) - 1).setImage(new Image("file:images/0.png"));
                tiles.get(clickedTiles.get(1) - 1).setImage(new Image("file:images/0.png"));
            }
            clickedTilesValue.clear();
            clickedTiles.clear();
        }
        if (matches==8){
            win1.setImage(new Image("file:images/win.gif"));
            win2.setImage(new Image("file:images/win.gif"));
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

    void showTilesForOneSec(ImageView t1, ImageView t2) {
        for (ImageView tile : tiles) {
            tile.setDisable(true);
        }
        Timer oneSecDelay = new Timer();
        oneSecDelay.schedule(new TimerTask() {
            @Override
            public void run() {
                t1.setImage(new Image("file:images/0.png"));
                t2.setImage(new Image("file:images/0.png"));
                for (ImageView tile : tiles) {
                    tile.setDisable(disabledTiles.contains(tile));
                }
            }
        }, 1000);

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

    void setDefaultImg() {
        for (ImageView tile : tiles) {
            tile.setImage(new Image("file:images/0.png"));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateNums();
        addTilesToArraylist();
        setDefaultImg();
        mainStage.setOnCloseRequest(e -> System.exit(0));
    }
}