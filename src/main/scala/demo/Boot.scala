package demo

import akka.actor.typed.ActorSystem
import akka.actor.typed.javadsl.Behaviors
import akka.http.scaladsl.Http

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object Boot {
    def main(args: Array[String]): Unit = {
        implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "下北泽")
        implicit val executionContext: ExecutionContextExecutor = system.executionContext
        val route = demo.controller.IntegrateRouter.router
        val bindFuture = Http().newServerAt("localhost", 8080).bind(route)
        StdIn.readLine()
        bindFuture
            .flatMap(_.unbind())
            .onComplete(_ => system.terminate())
    }

}
