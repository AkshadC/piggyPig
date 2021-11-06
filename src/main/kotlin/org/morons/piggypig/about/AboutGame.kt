package org.morons.piggypig.about

import javafx.animation.Interpolator
import javafx.animation.ScaleTransition
import javafx.animation.SequentialTransition
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.stage.*
import javafx.util.Duration
import org.morons.piggypig.piggyMainApplication
import java.net.URL
import java.util.*

import kotlin.math.pow


class AboutGame : Runnable{
    override fun run() {
        val dialog = CustomDialog()
        dialog.openDialog()
    }

    //Function to set the in ExpIn interpolator
    private val EXP_IN: Interpolator = object : Interpolator() {
        override fun curve(t: Double): Double {
            return if (t == 1.0) 1.0 else 1 - 2.0.pow(-10 * t)
        }
    }

    //Function to set the ExpOut interpolator
    private val EXP_OUT: Interpolator = object : Interpolator() {
        override fun curve(t: Double): Double {
            return if (t == 0.0) 0.0 else 2.0.pow(10 * (t - 1))
        }
    }



    private inner class CustomDialog() : Stage() {
        private val scale1 = ScaleTransition()
        private val scale2 = ScaleTransition()
        private val anim = SequentialTransition(scale1, scale2)
        fun openDialog() {
            isResizable=true
            isAlwaysOnTop=true
            show()
            anim.play()
        }

        fun closeDialog() {
            anim.onFinished = EventHandler { close() }
            anim.isAutoReverse = true
            anim.cycleCount = 2
            anim.playFrom(Duration.seconds(0.66))
        }

        //Constructor
        init {
            val aboutApproot = Pane()
            scale1.fromX = 0.01
            scale1.fromY = 0.01
            scale1.toY = 1.0
            scale1.duration = Duration.seconds(0.2)
            scale1.interpolator = EXP_IN
            scale1.node = aboutApproot
            scale2.fromX = 0.01
            scale2.toX = 1.0
            scale2.duration = Duration.seconds(0.2)
            scale2.interpolator = EXP_OUT
            scale2.node = aboutApproot
            val space1 = Label()
            space1.prefHeight = 10.0
            space1.prefWidth = 25.0
            val space = Label()
            space.prefHeight = 50.0
            space.prefWidth = 80.0
            initStyle(StageStyle.TRANSPARENT) //Set the scene transparent

            //Label to store header text  of about app
            val aboutAppheadertxt = Label("About the piggy dice app:\n\n\n")
            aboutAppheadertxt.id = "headerLabel"
            aboutAppheadertxt.prefWidth(150.0)

            //Label to store content text
            val aboutAppcontext = Label(
                """Piggy Dice Game is 2 player dice game 
The aim of the game is to 'roll' a die 
and 'hold' points to your score

but, beware of the number '1' as rolling 
it will set your current score to 0.
"""
            )
            aboutAppcontext.id = "contentLabel"

            //Creating image view and label for it
            val aboutAppImageLabel = Label()
            aboutAppImageLabel.graphic =  ImageView(Image(piggyMainApplication::class.java.getResourceAsStream("DiceImages/0.png")));
            aboutAppImageLabel.prefHeight = 50.0
            aboutAppImageLabel.prefWidth = 50.0
            aboutAppImageLabel.id = "aboutAppImageLabel"

            //Hbox to store header and image label
            val aboutAppheader_Image = HBox(5.0, aboutAppheadertxt, space, aboutAppImageLabel)

            //Vbox to store the labels
            val aboutAppVBox =
                VBox(11.0, aboutAppheader_Image,  Separator(Orientation.HORIZONTAL), aboutAppcontext)

            //Css for the Vbox
            aboutAppVBox.id = "dialogueVbox"
            aboutAppVBox.padding = Insets(10.0, 0.0, 0.0, 15.0)
            aboutAppVBox.prefHeight = 270.0
            aboutAppVBox.prefWidth = 350.0


            // Button to close the dialogue and its Css
            val aboutappokbtn = Button("OK")
            aboutappokbtn.prefHeight = 26.0
            aboutappokbtn.prefWidth = 55.0
            aboutappokbtn.id = "dialogueOkbutton"
            aboutappokbtn.translateX = 285.0
            aboutappokbtn.translateY = 233.0
            aboutappokbtn.setOnAction { closeDialog() }
            aboutApproot.children.addAll(aboutAppVBox, aboutappokbtn) //Adding all the nodes to the root pane
            val scene = Scene(aboutApproot, aboutAppVBox.prefWidth, aboutAppVBox.prefHeight) //Creating a scene
            scene.stylesheets.add(
                Objects.requireNonNull<URL>(piggyMainApplication::class.java.getResource("PiggyAboutStyle.css")).toExternalForm()
            )
            setScene(scene)
        }
    }
}