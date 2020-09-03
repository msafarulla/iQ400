package controller

import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.TableColumn
import javafx.stage.FileChooser
import javafx.util.Callback
import tornadofx.*
import view.MainPanelView
import view.Row
import java.sql.ResultSet
import java.time.Instant
import java.time.format.DateTimeFormatter
import kotlin.collections.set

val db = DB()

class MainPanelController : Controller() {
    private val view: MainPanelView by inject()
    private val separator = SimpleStringProperty(",").value
    val linesProperty = SimpleLongProperty(0L)
    val statusToView = SimpleStringProperty("")
    var currentFilePath: String = ""

    fun openLoadingDialog() {
        val fileChooser = FileChooser()
        val file = fileChooser.showOpenDialog(view.currentWindow)
        if (file != null) currentFilePath = file.path
    }

    fun loadQueryToTableView(text: String) {
        var query = text
        if (query.toUpperCase().substring(0..2) == "SE ") {
            db.runCommand(query)
            var env = query.toUpperCase().substring(3..6)
            runLater { statusToView.value = "Environment is set to $env." }
            return
        }


        var header = listOf<String>()
        println(DateTimeFormatter.ISO_INSTANT.format(Instant.now()))
        val rs = db.fetchRows(query + " limit 500000")
        while (rs.next()) {

            if (linesProperty.value==0L) {
                val mapping = mutableMapOf<String, String>()
                header = headers(rs)
                for ((heading1, heading2) in header zip header) {
                    mapping[heading1] = heading2
                }
                runLater {
                        view.table.items.clear()
                        view.table.columns.clear()
                        view.table.columns.addAll(getColumns(mapping))
                }
            }

            val mapping = mutableMapOf<String, String>()
            for ((heading, lineElement) in header zip elements(rs)) {
                mapping[heading] = lineElement
            }
            runLater {
                populateToTable(mapping)
                statusToView.value = linesProperty.value++.toString() + " rows retrieved."
            }


        }
        println(DateTimeFormatter.ISO_INSTANT.format(Instant.now()))

    }

    fun headers(rs: ResultSet): List<String> {
        var header = listOf<String>()
        for (i in 1..rs.metaData.columnCount)
            header += rs.metaData.getColumnName(i)
        return header

    }

    fun elements(rs: ResultSet): List<String> {
        var columns = listOf<String>()
        for (i in 1..rs.metaData.columnCount) columns += rs.getString(rs.metaData.getColumnName(i))
        return columns
    }

    private fun populateToTable(mapping: MutableMap<String, String>) {
        view.table.items.addAll(mapping)//add column values
        view.table.enableCellEditing()
    }

    private fun getColumns(mapping: MutableMap<String, String>): ArrayList<TableColumn<Row, String>> {
        val columns = ArrayList<TableColumn<Row, String>>()
        val keys = mapping.keys
        for (key in keys) {
            val column = TableColumn<Row, String>(key)
            column.makeEditable()
            column.cellValueFactory = Callback { param -> SimpleObjectProperty(param.value[key]) }
            columns += column
        }
        return columns
    }
}


