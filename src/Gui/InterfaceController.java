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

    private int penaltiesTeam1, penaltiesTeam2, Team1Goals, Team2Goals;
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

    private int getIndex(int teamID) {
        for (int i = 0; i < totalTeams.size(); i++)
            if (totalTeams.get(i).getID() == teamID)
                return i;

        return -1;
    }

    public void GenerateTeamList(ChoiceBox<Team> box) {
        box.getItems().clear();
        box.getItems().addAll(totalTeams);
    }

    private void activateButton() {
        if (team1ID > 0 && team2ID > 0)
            PauseButton.getStyleClass().add("pauseButtonActive");
        else
            PauseButton.getStyleClass().remove("pauseButtonActive");
    }



    public void AddGoalTeam1() {
        Team1Goals = game.goal(true, Team1Goals, Team1Score, team1ID);
    }

    public void AddGoalTeam2() {
        Team2Goals = game.goal(true, Team2Goals, Team2Score, team2ID);
    }

    public void RemoveGoalTeam1() {
        Team1Goals = game.goal(false, Team1Goals, Team1Score, team1ID);
    }

    public void RemoveGoalTeam2() {
        Team2Goals = game.goal(false, Team2Goals, Team2Score, team2ID);
    }



    public void AddPenaltiesTeam1() {
        penaltiesTeam1 = game.penalties(true, penaltiesTeam1, Team1PenaltyTextField, team1ID);
    }

    public void AddPenaltiesTeam2() {
        penaltiesTeam2 = game.penalties(true, penaltiesTeam2, Team2PenaltyTextField, team2ID);
    }

    public void RemovePenaltiesTeam1() {
        penaltiesTeam1 = game.penalties(false, penaltiesTeam1, Team1PenaltyTextField, team1ID);
    }

    public void RemovePenaltiesTeam2() {
        penaltiesTeam2 = game.penalties(false, penaltiesTeam2, Team2PenaltyTextField, team2ID);
    }



    public void UpdateTimer() {
        game.updateTime();
    }

}





