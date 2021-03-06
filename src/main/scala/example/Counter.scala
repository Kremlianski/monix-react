package example

import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.html_<^._
import monix.execution.Cancelable
import monix.execution.Scheduler.Implicits.global
import org.scalajs.dom.html.{Button, Div}

case class CounterProps(d: Dispatcher[CounterState])

object Counter {


  case class ButtonProps(increment: Boolean, step: Int, props: CounterProps)


  class ButtonBackend($: BackendScope[ButtonProps, Unit]) {
    def click: Callback = {
      val props = $.props.runNow()
      val increment = props.increment match {
        case true => 1
        case _ => -1
      }
      Callback(props.props.d.dispatch(
        s => s.copy(count = s.count + increment)
          .copy(changeType = props.step)
      ))

    }

    def render(p: ButtonProps): VdomTagOf[Button] =
      <.button(^.onClick --> click, ^.className := "round", p.increment match {
        case true => "+"
        case _ => "-"
      })
  }

  private val Button =  ScalaComponent.builder[ButtonProps]("Button")
    .renderBackend[ButtonBackend]
    .build


  class ClearBackend($: BackendScope[CounterProps, Unit]) {
    def click: Callback = {

      Callback($.props.runNow().d.dispatch(
        s => s.copy(count = 0).copy(changeType = 3)
      )
      )

    }

    def render: VdomTagOf[Button] =
      <.button(^.onClick --> click, "clear")
  }

  private val Clear =  ScalaComponent.builder[CounterProps]("Clear")
    .renderBackend[ClearBackend]
    .build


  class Count0Backend($: BackendScope[CounterProps, Int]) {
    var end: Option[Cancelable] = None

    def render(s: Int): VdomTagOf[Div] =
      <.div(^.className := "red", s)
  }


  private val Count0 =  ScalaComponent.builder[CounterProps]("Count")
    .initialState(0)
    .renderBackend[Count0Backend]
    .componentDidMount(scope =>

      Callback {
        val disp = scope.props.d
        scope.backend.end = Option(disp.stream
          .subscribe(disp.observer(x => scope.modState(_ => x.count).runNow())))
      }
    )
    .componentWillUnmount(scope =>
      Callback(scope.backend.end.foreach(_.cancel)))
    .build


  class CountBackend($: BackendScope[CounterProps, Int]) {
    var end: Option[Cancelable] = None

    def render(s: Int): VdomTagOf[Div] =
      <.div(^.className := "green", s)
  }


  private val Count =  ScalaComponent.builder[CounterProps]("Count")
    .initialState(0)
    .renderBackend[CountBackend]
    .componentDidMount(scope =>

      Callback {
        val disp = scope.props.d
        scope.backend.end = Option(disp.stream
          .filter(_ match {
            case x if x.changeType == 1 => true
            case x if x.changeType == 3 => true
            case x if x.changeType == 2 & x.count % 2 == 0 => true
            case _ => false
          })
          .subscribe(disp.observer(
            x => scope.modState(_ => x.count).runNow())
          ))
      }
    )
    .componentWillUnmount(scope =>
      Callback(scope.backend.end.foreach(_.cancel)))
    .build


  class Count1Backend($: BackendScope[CounterProps, Int]) {
    var end: Option[Cancelable] = None

    def render(s: Int): VdomTagOf[Div] =
      <.div(^.className := "blue", s)
  }


  private val Count1 =  ScalaComponent.builder[CounterProps]("Count")
    .initialState(0)
    .renderBackend[Count1Backend]
    .componentDidMount(scope =>

      Callback {
        val disp = scope.props.d
        scope.backend.end = Option(disp.stream
          .filter(_ match {
            case x if x.changeType == 2 => true
            case x if x.changeType == 3 => true
            case _ => false
          })
          .subscribe(disp.observer(
            x => scope.modState(_ => x.count).runNow())
          ))
      }
    )
    .componentWillUnmount(scope =>
      Callback(scope.backend.end.foreach(_.cancel)))
    .build

  private val Counter =  ScalaComponent.builder[CounterProps]("Counter")
    .render_P { prop =>
      <.div(^.id := "counter-container",
        <.div(^.className := "counts",
          Count0(prop),
          Count(prop),
          Count1(prop)),
        <.div(
          Button(ButtonProps(false, 1, prop)),
          Button(ButtonProps(true, 1, prop))
        ),
        <.div(
          Button(ButtonProps(false, 2, prop)),
          Button(ButtonProps(true, 2, prop))
        ),
        <.div(Clear(prop))
      )
    }.build

  def apply(props: CounterProps): Unmounted[CounterProps, Unit, Unit] = Counter(props)
}


