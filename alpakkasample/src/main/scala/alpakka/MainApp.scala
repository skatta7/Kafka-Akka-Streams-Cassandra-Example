package alpakka

import scala.concurrent.Future

object MainApp extends App {
  val interpretedValue = Programs.act().interpret[Future]
}
