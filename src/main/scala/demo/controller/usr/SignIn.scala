package demo.controller.usr

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import demo.dto.{ErrorResponse, SuccessResponse}
import demo.dto.ResponseJsonFormat.{errorResponseJsonFormat, successResponseJsonFormat}
import demo.model.{DataBase, User}
import org.squeryl.PrimitiveTypeMode._
import spray.json.enrichAny
import demo.util.Authorization.generatePasswordHash

object SignIn {
  def controller(): Route = {
    post {
      path("sign-in") {
        formFields("usr", "pwd") { (userIdentity, password) =>
          transaction {
            if (DataBase.users.where(user => user.uniqueIdentity === userIdentity).isEmpty) {
              DataBase.users.insert(new User(userIdentity, generatePasswordHash(password)))
              complete(StatusCodes.Accepted, HttpEntity(ContentTypes.`application/json`,
                SuccessResponse(body = Map("data" -> "")).toJson.toString
              ))
            } else {
              complete(StatusCodes.BadRequest, HttpEntity(ContentTypes.`application/json`,
                ErrorResponse(StatusCode = "A002", body = Map("error" -> "UserExist")).toJson.toString
              ))
            }
          }
        }
      }
    }
  }
}
