package demo.controller

import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import demo.controller.todo.{AddTodoItem, DeleteTodoItem, GetTodoItem, UpdateTodoItem}
import demo.controller.usr.{Login, SignIn}

object IntegrateRouter {

  def register(): Route = {
    router
  }

  private val router: Route = respondWithDefaultHeaders(Seq(
    RawHeader("Access-Control-Allow-Origin", "*"),
    RawHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS"),
    RawHeader("Access-Control-Allow-Headers", "Accept, Origin, Content-type, X-Json, X-Prototype-Version, X-Requested-With"),
    RawHeader("Access-Control-Allow-Credentials", "true")
  )
  ){
    concat(
      pathPrefix("usr") {
        concat(
          SignIn.controller(),
          Login.controller()
        )
      },
      pathPrefix("todo") {
        concat(
          AddTodoItem.controller(),
          DeleteTodoItem.controller(),
          GetTodoItem.controller(),
          UpdateTodoItem.controller()
        )
      }
    )
  }
}
