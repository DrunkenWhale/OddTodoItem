package demo.controller

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import demo.controller.todo.{AddTodoItem, DeleteTodoItem, GetTodoItem, UpdateTodoItem}
import demo.controller.usr.{Login, SignIn}

object IntegrateRouter {

  def register(): Route = {
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
          AddTodoItem.controller(),
          DeleteTodoItem.controller(),
          GetTodoItem.controller(),
          UpdateTodoItem.controller()
        )
      }
    )
  }
}
