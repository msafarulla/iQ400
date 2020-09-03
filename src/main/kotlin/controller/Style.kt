package controller

import com.sun.javafx.scene.control.skin.TableColumnHeader
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.Stylesheet
import tornadofx.c
import tornadofx.cssclass

class Style : Stylesheet() {
    companion object {
        val pass by cssclass()
        val fail by cssclass()
    }
    init {
        pass{
            textFill = c("#7CFC00", 1.0)
            fontFamily = "JetBrains Mono"
            fontWeight = FontWeight.SEMI_BOLD
            backgroundColor += c("black", 0.8)
            and(selected){
                backgroundColor += c("grey", 1.0)
            }
        }
        fail{
            backgroundColor += c("#FF5722", .5)
            and(selected){
                backgroundColor += c("#0096C9", .5)
            }
        }
    }
}