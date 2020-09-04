package view

import controller.MainPanelController
import javafx.concurrent.Task
import javafx.geometry.Orientation
import javafx.scene.control.TableView
import tornadofx.*

typealias Row = MutableMap<String, String>

class MainPanelView : View("iQ400") {
    private val controller: MainPanelController by inject()
    var table: TableView<Row> by singleAssign()
    var query = stringProperty("")
    var x: Task<Unit>? = null

    override val root = borderpane {
        top = label("File")

        center = splitpane(Orientation.VERTICAL) {
            textarea("select * from CDCART00") {
                //editableProperty()
                isUndoable
                //text = "select * from CDCART00"
                //query.value = text
                //textProperty().bind(query)
                shortcut("Meta+Enter") {
                    table.items.clear()
                    table.columns.clear()
                    if (x != null) x?.cancel()
                    x = runAsync {
                        controller.reset()
                        controller.loadQueryToTableView(text)
                    }
                }
            }
            table = tableview {
                onUserSelect(clickCount = 1) { persona ->
                    println(persona)
                }
            }
        }

        bottom = hbox {
            label(controller.statusToView) {
                padding = insets(horizontal = 10)
            }
        }
    }

    init {

    }
}