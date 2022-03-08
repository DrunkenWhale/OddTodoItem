package demo.controller.todo

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import demo.dto.ResponseJsonFormat.{errorResponseJsonFormat, successResponseJsonFormat}
import demo.dto.{ErrorResponse, SuccessResponse}
import demo.model.{DataBase, TodoItem}
import demo.util.Authorization.checkJwtToken
import org.squeryl.PrimitiveTypeMode._
import spray.json.enrichAny

import java.sql.Timestamp

object UpdateTodoItem {
  def controller(): Route = {
    (put & path(LongNumber) & headerValueByName("Auth-Token") & formFields("content", "done".as[Boolean], "startAt")) {
      (id, token, content, done, startAt) =>
        val userID = checkJwtToken(token)
        if (userID == -1) {
          return complete(StatusCodes.Unauthorized, HttpEntity(ContentTypes.`application/json`,
            ErrorResponse(StatusCode = "A003", body = Map("error" -> "IllegalToken")).toJson.toString
          ))
        }
        val itemOpt: Option[TodoItem] = transaction {
          DataBase.todoItems.where(item => item.id === id).headOption
        }
        if (itemOpt.isEmpty) {
          return complete(StatusCodes.NotFound, HttpEntity(ContentTypes.`application/json`,
            ErrorResponse(StatusCode = "A003", body = Map("error" -> s"can't find TodoItem id = $id")).toJson.toString
          ))
        }
        val item = itemOpt.get
        if (item.userID != userID) {
          complete(StatusCodes.Unauthorized, HttpEntity(ContentTypes.`application/json`,
            ErrorResponse(StatusCode = "A004", body = Map("error" -> s"This Item is not belong to you!")).toJson.toString
          ))
        } else {
          val dateTime = Timestamp.valueOf(startAt) // throw exception...
          transaction {
            update(DataBase.todoItems)(item =>
              where(item.id === id)
                  set(item.content := content, item.startAt := dateTime, item.done := done)
            )
          }
          complete(StatusCodes.Accepted, HttpEntity(ContentTypes.`application/json`,
            SuccessResponse(body = Map("data" -> "")).toJson.toString
          ))
        }
    }
  }
}
