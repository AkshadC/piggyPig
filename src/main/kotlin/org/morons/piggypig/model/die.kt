package org.morons.piggypig.model
const val MAX_SIDES = 6
class die(Top :Int =0) {
    //Data Field
    var Top = Top
    set(value) {
        if(value in 1..MAX_SIDES) field= value
    }

    fun rollDie() {
        this.Top=(1..6).random()
    }
}