package org.morons.piggypig.model
const val MAX_SCORE=100


class game (private val p1Name:String, private val p2Name:String) {
      var d :die = die()
      private set
      private val p1:player = player(p1Name)
      private val p2:player = player(p2Name)
      private  var current:player =p1

      fun getCurrent(): player {
            return current
      }

      fun getP1(): player {
            return p1
      }

      fun getP2(): player {
            return p2
      }
      fun gameOver():Boolean{
            return current.totalScore>= MAX_SCORE
      }
      fun p1Turn():Boolean{
            return current==p1
      }
      fun switchTurn(){
            current = if(p1Turn()){p2}
            else {p1}
      }
      fun roll(){
            d.rollDie()
            val t :Int = d.Top
            current.updateTurn(t)
            if(t==1){
                  current.resetTurnScore()
                  switchTurn()
            }
      }
      fun hold(){
            current.saveScore()
            if(!gameOver()){
                  switchTurn()
                  d.Top=0
            }
      }
      fun getPlayersScores():Pair< Pair <Int,Int> , Pair <Int,Int>  >{
            val p1Score = Pair (getP1().turnScore,getP1().totalScore)
            val p2Score = Pair (getP2().turnScore,getP2().totalScore)
            return Pair (p1Score,p2Score)
      }


}