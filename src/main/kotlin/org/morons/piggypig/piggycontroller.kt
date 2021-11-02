package org.morons.piggypig

import javafx.animation.AnimationTimer
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox
import org.morons.piggypig.about.AboutDev
import org.morons.piggypig.about.AboutGame
import org.morons.piggypig.model.game
import java.net.URL
import java.util.*

const val FRAMES_PER_SEC = 24L
const val INTERVAL = 1000000000L / FRAMES_PER_SEC
const val MAX_ROLLS = 12

const val FOCUS_COLOUR="-fx-background-color:\n" +
        "            rgba(0,0,0,0.08),\n" +
        "            linear-gradient(#5a61af, #51536d),\n" +
        "            linear-gradient(#e4fbff 0%,#cee6fb 10%, #a5d3fb 50%, #88c6fb 51%, #d5faff 100%);\n" +
        "            -fx-border-color: \n" +
        "             rgb(212,212,221);\n" +
        "             -fx-background-radius: 6 6 6 6 , 5 5 5 5 ,4 4 4 4;"

class piggycontroller : Initializable {
    private lateinit var game: game
    @FXML
    lateinit var gameTitleLabel:Label
    @FXML
    lateinit var newGameMenuItem: MenuItem
    @FXML
    lateinit var quitMenuItem: MenuItem
    @FXML
    lateinit var aboutGameMenuItem: MenuItem
    @FXML
    lateinit var aboutUsMenuItem: MenuItem
    @FXML
    lateinit var pigImageView: ImageView
    @FXML
    lateinit var player1TurnField: TextField
    @FXML
    lateinit var player1ScoreField: TextField
    @FXML
    lateinit var dieImageView: ImageView
    @FXML
    lateinit var player2TurnField: TextField
    @FXML
    lateinit var player2ScoreField: TextField
    @FXML
    lateinit var rollBtn: Button
    @FXML
    lateinit var holdBtn: Button
    @FXML
    lateinit var p1VBox: VBox
    @FXML
    lateinit var p2VBox: VBox

    private lateinit var clock:Roller
    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {
        pigImageView.image= diceImages[7]
        newGameMenuItem.setOnAction { resetField() }
        quitMenuItem.setOnAction { piggyMainApplication.quitAction() }
        game = game("Player 1","Player 2")
        clock=Roller()
        rollBtn.setOnAction { rollAnimation() }
        holdBtn.setOnAction { holdAction() }
        aboutGameMenuItem.setOnAction { Platform.runLater(AboutGame()) }
        aboutUsMenuItem.setOnAction { Platform.runLater(AboutDev()) }
        dieImageView.image= diceImages[6]
    }

    private fun rollAction(){
        game.roll()
        updateView()
    }
    private fun holdAction(){
        game.hold()
        updateView()
    }
    private fun setDieImage(top:Int){
        dieImageView.image= diceImages[top-1]
    }

    private fun updateView(){

        setDieImage(game.d.Top)
        if (game.p1Turn()) {
            p1VBox.style = FOCUS_COLOUR
            p2VBox.style = null
        } else {
            p2VBox.style= FOCUS_COLOUR

            p1VBox.style = null
        }
        val (p1Scores,p2Scores) = game.getPlayersScores()

        player1TurnField.text  = p1Scores.first.toString()
        player1ScoreField.text = p1Scores.second.toString()

        player2TurnField.text  = p2Scores.first.toString()
        player2ScoreField.text = p2Scores.second.toString()

        if(game.gameOver()) {
            disableButtons(true)
            gameTitleLabel.text="Game Over ${game.getCurrent().name} wins!!"
        }

    }
    private fun disableButtons(status:Boolean){
        rollBtn.isDisable=status
        holdBtn.isDisable=status
    }

    private fun resetField(){
        dieImageView.image= diceImages[6]
        player1TurnField.text="0"
        player2TurnField.text="0"
        player1ScoreField.text="0"
        player2ScoreField.text="0"
        disableButtons(false)
    }

    private inner  class Roller :AnimationTimer(){
        private var last =0L
        private var count =0L

        override fun handle(now: Long) {
            if(now-last> INTERVAL){
                val r:Int = (1..6).random()
                setDieImage(r)
                last=now
                count++
                if(count> MAX_ROLLS){
                    clock.stop()
                    disableButtons(false)
                    rollAction()
                    count=0L
                }
            }
        }

    }

    private fun rollAnimation() {
        clock.start()
        disableButtons(true)
    }
}