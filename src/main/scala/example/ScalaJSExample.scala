package example

import org.scalajs.dom

import scala.scalajs.js.annotation.JSExportTopLevel

case class CounterState(count: Int, changeType: Int)
object ScalaJSExample {

  @JSExportTopLevel("main")
  def main() {

  }


  @JSExportTopLevel("counter")
  def counter() {

    val state_0 = CounterState(0, 0)
    val dispatcher = new Dispatcher[CounterState](state_0)

    Counter(CounterProps(dispatcher)).renderIntoDOM(dom.document.getElementById("target"))


  }


}





