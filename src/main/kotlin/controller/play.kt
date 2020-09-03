package controller

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import tornadofx.observable


//from w  w  w . j  av  a2  s.c o m
class Main : Application() {
    override fun start(primaryStage: Stage) {
        val table: TableView<*> = TableView<Any?>()
        table.isEditable = true
        val firstNameCol: TableColumn<*, *> = TableColumn<Any?, Any?>("First Name")
        val lastNameCol: TableColumn<*, *> = TableColumn<Any?, Any?>("Last Name")
        val emailCol: TableColumn<*, *> = TableColumn<Any?, Any?>("Email")
        //table.columns.addAll(firstNameCol, lastNameCol, emailCol)
        //emailCol.isVisible = false
        val root = StackPane()
        root.children.add(table)
        primaryStage.scene = Scene(root, 200.00, 250.00)
        primaryStage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(*args)
        }
    }
}