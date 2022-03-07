package demo.controller.usr

import akka.http.scaladsl.server.Directives._
import demo.dto.ErrorResponse

object Login {
    def controller(): Unit = {
        post {
            path("usr" / "login") {
                formField("usr", "pwd") { (usr, pwd) =>

                }
            }
        }
    }
}
