package Gui;

import Data.DBteam;
import Data.Team;
import Logic.Match;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class InterfaceController implements Initializable {

    private int team1ID, team2ID;

    @FXML private ChoiceBox<Team> Team1Name, Team2Name;
    @FXML private TextField Team1Score, Team2Score, Team1PenaltyTextField, Team2PenaltyTextField;
    @FXML private ImageView PauseButton;
    @FXML private Label timerLabelText;

    private final DBteam teamDB = new DBteam();
    private final ArrayList<Team> totalTeams = teamDB.getTeams();
    private final  Match game = new Match(58,0,60,58);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        game.setChoiceBox(Team1Name,Team2Name);
        game.setTextField(Team1Score,Team2Score,Team1PenaltyTextField,Team2PenaltyTextField);
        game.setMisc(PauseButton,timerLabelText);
        game.setStream(getClass());

        GenerateTeamList(Team1Name);
        GenerateTeamList(Team2Name);

        Team1Name.setOnAction(event -> {
            team1ID = getTeamID(Team1Name, Team2Name, team1ID);
            game.setTeamID(1,team1ID);
            activateButton();
        });

        Team2Name.setOnAction(event -> {
            team2ID = getTeamID(Team2Name, Team1Name, team2ID);
            game.setTeamID(2,team2ID);
            activateButton();
        });

    }

    private int getTeamID(ChoiceBox<Team> box1, ChoiceBox<Team> box2, int teamID) {
        //set the value for the choice box
        Team team = box1.getSelectionModel().getSelectedItem();

        if (team == null) 
            return teamID;

        teamID = team.getID();

        //delete the value in the other choice box
        Team selectedTeam = box2.getSelectionModel().getSelectedItem();
        GenerateTeamList(box2);
        box2.setValue(selectedTeam);
        box2.getItems().remove(getIndex(teamID));

        return teamID;
    }

    //get the index from the teams list to know which index to remove in the other choice box
    private int getIndex(int teamID) {
        for (int i = 0; i < totalTeams.size(); i++)
            if (totalTeams.get(i).getID() == teamID)
                return i;

        return -1;
    }

    //making the teamlist for both choice boxes
    public void GenerateTeamList(ChoiceBox<Team> box) {
        box.getItems().clear();
        box.getItems().addAll(totalTeams);
    }

    //show or hide the start button
    private void activateButton() {
        if (team1ID > 0 && team2ID > 0)
            PauseButton.getStyleClass().add("pauseButtonActive");
        else
            PauseButton.getStyleClass().remove("pauseButtonActive");
    }


    //goal operations
    public void AddGoalTeam1() {
        game.goal(1, Team1Score, 1, team1ID);
    }

    public void AddGoalTeam2() {
        game.goal(1, Team2Score, 2, team2ID);
    }

    public void RemoveGoalTeam1() {
        game.goal(-1, Team1Score, 1, team1ID);
    }

    public void RemoveGoalTeam2() {
        game.goal(-1, Team2Score, 2, team2ID);
    }


    //penalties operations
    public void AddPenaltiesTeam1() {
        game.penalties(1, Team1PenaltyTextField, 1, team1ID);
    }

    public void AddPenaltiesTeam2() {
        game.penalties(1, Team2PenaltyTextField, 2, team2ID);
    }

    public void RemovePenaltiesTeam1() {
        game.penalties(-1, Team1PenaltyTextField, 1, team1ID);
    }

    public void RemovePenaltiesTeam2() {
        game.penalties(-1, Team2PenaltyTextField, 2, team2ID);
    }


    //Begin Match
    public void UpdateTimer() {
        game.updateTime();
    }

}





