package org.morons.piggypig.about

import javafx.animation.*
import javafx.event.*
import javafx.geometry.*
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

const val DEV_NAME="Akshad C"
class AboutDev :Runnable {
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


    private inner class CustomDialog  constructor() : Stage() {
        private val scale1 = ScaleTransition()
        private val scale2 = ScaleTransition()
        private val anim = SequentialTransition(scale1, scale2)
        fun openDialog() {
            show()
            isResizable=true
            isAlwaysOnTop=true
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
            val aboutUsroot = Pane()
            scale1.fromX = 0.01
            scale1.fromY = 0.01
            scale1.toY = 1.0
            scale1.duration = Duration.seconds(0.2)
            scale1.interpolator = EXP_IN
            scale1.node = aboutUsroot
            scale2.fromX = 0.01
            scale2.toX = 1.0
            scale2.duration = Duration.seconds(0.2)
            scale2.interpolator = EXP_OUT
            scale2.node = aboutUsroot
            val space = Label()

            space.prefWidth = 120.0
            initStyle(StageStyle.TRANSPARENT) //Set the scene transparent

            //Label to store header text of about Us
            val aboutUsheadertxt = Label("About the Devs:\n$DEV_NAME\n\n\n")
            aboutUsheadertxt.id = "headerLabel"


            //Label to store content text
            val aboutUscontentText = Label(
                """This is a piggy dice game between two players 
just for fun 

                   Hope you all enjoy it!
"""
            )
            aboutUscontentText.id = "contentLabel"

            //Creating image view and label for it
            val aboutUsimageLabel = Label()
            aboutUsimageLabel.graphic =  ImageView(Image(piggyMainApplication::class.java.getResourceAsStream("DiceImages/aboutus_Pig.png")));
            aboutUsimageLabel.prefHeight = 50.0
            aboutUsimageLabel.prefWidth = 50.0
            aboutUsimageLabel.id = "aboutUsimageLabel"

            //Hbox to store header and image label
            val aboutUsheader_Image = HBox(5.0, aboutUsheadertxt, space, aboutUsimageLabel)

            //Vbox to store the labels
            val aboutUsVbox =
                VBox(11.0, aboutUsheader_Image,  Separator(Orientation.HORIZONTAL), aboutUscontentText)

            //Css for the Vbox
            aboutUsVbox.id = "dialogueVbox"
            aboutUsVbox.padding = Insets(10.0, 0.0, 0.0, 15.0)
            aboutUsVbox.prefHeight = 270.0
            aboutUsVbox.prefWidth = 350.0


            // Button to close the dialogue and its Css
            val aboutUsOkbtn = Button("OK")
            aboutUsOkbtn.prefHeight = 26.0
            aboutUsOkbtn.prefWidth = 55.0
            aboutUsOkbtn.id = "dialogueOkbutton"
            aboutUsOkbtn.translateX = 285.0
            aboutUsOkbtn.translateY = 233.0
            aboutUsOkbtn.setOnAction { closeDialog() }
            aboutUsroot.children.addAll(aboutUsVbox, aboutUsOkbtn) //Adding all the nodes to the root pane
            val scene = Scene(aboutUsroot, aboutUsVbox.prefWidth, aboutUsVbox.prefHeight) //Creating a scene
            scene.stylesheets.add(
                Objects.requireNonNull<URL>(piggyMainApplication::class.java.getResource("PiggyAboutStyle.css")).toExternalForm()
            )
            setScene(scene)
        }
    }
}