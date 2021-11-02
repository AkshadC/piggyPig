package org.morons.piggypig.about

import javafx.animation.Interpolator
import javafx.animation.ScaleTransition
import javafx.animation.SequentialTransition
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.stage.*
import javafx.util.Duration

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
            space.prefWidth = 15.0
            initStyle(StageStyle.TRANSPARENT) //Set the scene transparent

            //Label to store header text  of about app
            val aboutAppheadertxt = Label("\n\nAbout Piggy Dice Game\n")
            aboutAppheadertxt.id = "headerLabel"

            //Label header text one of about app




            //Label to store content text
            val aboutAppcontentText =
                Label("Piggy Dice Game is 2 player dice game\n" +
                        "The aim of the game is to 'roll' a die \nand 'hold' points to your score\n" +
                        "but, beware of the number '1' \nas rolling it will set your current score to 0.")
            aboutAppcontentText.id = "contentLabel"

            //Creating image view and label for it
            val aboutAppimageLabel = Label()
            aboutAppimageLabel.prefHeight = 50.0
            aboutAppimageLabel.prefWidth = 50.0
            aboutAppimageLabel.id = "aboutAppimageLabel"

            //Hbox to store header and image label
            val aboutAppheader_Image = HBox(5.0,  space, aboutAppimageLabel)

            //Vbox to store the labels
            val aboutAppbox = VBox(
                aboutAppheadertxt,
                aboutAppheader_Image,
                space1,
                Separator(Orientation.HORIZONTAL),
                aboutAppcontentText
            )

            //Css for the Vbox
            aboutAppbox.id = "dialogueVbox"
            aboutAppbox.padding = Insets(10.0, 0.0, 0.0, 15.0)
            aboutAppbox.prefHeight =275.0
            aboutAppbox.prefWidth = 350.0

            // Button to close the dialogue and its Css
            val aboutAppbtn = Button("OK")
            aboutAppbtn.prefHeight = 26.0
            aboutAppbtn.prefWidth=55.0
            aboutAppbtn.id = "dialogueOkbutton"
            aboutAppbtn.translateX = 285.0
            aboutAppbtn.translateY = 245.0
            aboutAppbtn.setOnAction { closeDialog() }
            aboutApproot.children.addAll(aboutAppbox, aboutAppbtn) //Adding all the nodes to the root pane
            val scene = Scene(aboutApproot, aboutAppbox.prefWidth,aboutAppbox.prefHeight) //Creating a scene

            //scene.stylesheets.add(
              //  Objects.requireNonNull<URL>(aboutapp::class.java.getResource("Calculator.css")).toExternalForm()
            //) //Importing the stylesheet
            setScene(scene)
        }
    }
}