package demo.controller

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import demo.controller.usr.{Login, SignIn}

object IntegrateRouter {

  def register(): Route ={
    router
  }

  private val router: Route = pathPrefix("api") {
    concat(
      pathPrefix("usr") {
        concat(
          SignIn.controller(),
          Login.controller()
        )
      },
      pathPrefix("todo") {
        concat(

        )
      }
    )
  }
}
