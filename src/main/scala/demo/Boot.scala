package demo

import akka.actor.typed.ActorSystem
import akka.actor.typed.javadsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import demo.controller.IntegrateRouter
import demo.model.DataBase

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object Boot {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "Master-Beast")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext
    val route:Route = IntegrateRouter.register()
    DataBase.register()
    val bindFuture = Http().newServerAt("localhost", 8080).bind(route)
    StdIn.readLine()
    bindFuture
        .flatMap(_.unbind())
        .onComplete(_ => system.terminate())
  }

}
