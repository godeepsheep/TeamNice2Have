package Gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;



public class InterfaceController {

    @FXML
    TextField Team1Name;
    @FXML
    TextField Team2Name;
    @FXML
    TextField Team1Score;
    @FXML
    TextField Team2Score;
    @FXML
    TextField Timer;
    @FXML
    Button Team1Goal;
    @FXML
    Button Team2Goal;
    @FXML
    Button Team1Penalty;
    @FXML
    Button Team2Penalty;
    @FXML
    ImageView PauseButton;

    public int Team1Goals = 0;
    public int Team2Goals = 0;
    public int penaltiesTeam1= 0;
    public int penaltiesTeam2= 0;

    public void AddGoalTeam1(String Team){
        Team1Goals += 1;
    }
    public void AddGoalTeam2(String Team){
        Team2Goals += 1;
    }

    public void AddPenaltiesTeam1(){
        penaltiesTeam1 +=1;
    }
    public void AddPenaltiesTeam2(){
        penaltiesTeam2 +=1;
    }

    public void AddPenalty(String Team){

    }



}
