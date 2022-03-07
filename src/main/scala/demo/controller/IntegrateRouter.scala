package demo.controller

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{complete, get, path}

object IntegrateRouter {

    val router = path("hello") {
        get {
            complete(HttpEntity(ContentTypes.`application/json`, "<h1>Say hello to akka-http</h1>"))
        }
    }
}
