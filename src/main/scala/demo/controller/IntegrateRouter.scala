package demo.controller

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import demo.controller.usr.{Login, SignIn}

object IntegrateRouter {

  val router: Route = pathPrefix("api") {
    concat(
      pathPrefix("usr") {
        concat(
          SignIn.controller(),
          Login.controller()
        ),
        pathPrefix("todo"){
          concat(

          )
        }
      }
    )
  }
}
