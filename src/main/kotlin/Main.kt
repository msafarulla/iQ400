import controller.Style
import tornadofx.App
import tornadofx.importStylesheet
import tornadofx.launch

import view.MainPanelView

class BigMommy : App(MainPanelView::class, Style::class)
{
     init {
          importStylesheet(resources.get("bootstrap3.css"))
     }
}

fun main(args: Array<String>) {
    launch<BigMommy>(args)
}