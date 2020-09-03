package view

import controller.MainPanelController
import javafx.geometry.Orientation
import javafx.scene.control.TableView
import javafx.scene.control.TextArea
import tornadofx.*

typealias Row = MutableMap<String, String>

class MainPanelView : View("Dynamic Table View") {
    private val controller: MainPanelController by inject()
    var table : TableView<Row>
    var textArea :TextArea
    init {
        table= TableView<Row>()
        textArea = TextArea()
        textArea.text = "SE W6QE"
        textArea.text = "select * from CDCART00"
        textArea.apply {

        }
        shortcut("Meta+Enter") {
            runAsync { controller.loadQueryToTableView(textArea.text) }
        }
    }

    override val root = borderpane {

        top = label("File")

        center = splitpane{
            orientation = Orientation.VERTICAL
            add(textArea)
            add(table)
        }

        bottom = hbox {
            label(controller.statusToView)
        }
    }
}
