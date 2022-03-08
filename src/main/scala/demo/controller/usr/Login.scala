package demo.controller.usr

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import demo.dto.ResponseJsonFormat.{authTokeResponseJsonFormat, errorResponseJsonFormat, successResponseJsonFormat}
import demo.dto.{AuthTokenResponse, ErrorResponse}
import demo.model.DataBase
import org.squeryl.PrimitiveTypeMode._
import spray.json.enrichAny
import demo.util.Authorization.{checkPasswordHash, generateJwtToken}
object Login {
  def controller(): Route = {
    post {
      path("login") {
        formFields("usr", "pwd") { (userIdentity, password) =>
          transaction {
            val userOpt = DataBase.users.where(usr => usr.uniqueIdentity === userIdentity).headOption
            if(userOpt.isEmpty){
              return complete(StatusCodes.BadRequest, HttpEntity(ContentTypes.`application/json`,
                ErrorResponse(StatusCode="A001",body = Map("error" -> "UserUnExist")).toJson.toString
              ))
            }
            val user = userOpt.get
            if(checkPasswordHash(password,user.password)){
              complete(StatusCodes.Accepted, HttpEntity(ContentTypes.`application/json`,
                AuthTokenResponse(body = Map("data" -> ""),AuthToken = generateJwtToken(user.id)).toJson.toString
              ))
            }else{
              complete(StatusCodes.BadRequest, HttpEntity(ContentTypes.`application/json`,
                ErrorResponse(StatusCode="A001",body = Map("error" -> "PasswordUnmatched")).toJson.toString
              ))
            }
          }
        }
      }
    }
  }
}
