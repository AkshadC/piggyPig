package org.morons.piggypig.model

class player(val name:String) {

    var turnScore=0
    private set
    var totalScore=0
    private set

    fun resetTurnScore(){
        turnScore=0
    }
    fun updateTurn(roll:Int){
        turnScore+=roll
    }
    fun saveScore(){
        totalScore+=turnScore
        resetTurnScore()
    }
}