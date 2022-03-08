package demo.controller.todo

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import demo.dto.ResponseJsonFormat.{errorResponseJsonFormat, successResponseJsonFormat}
import demo.dto.{ErrorResponse, SuccessResponse}
import demo.model.{DataBase, TodoItem}
import demo.util.Authorization.checkJwtToken
import org.squeryl.PrimitiveTypeMode.transaction
import spray.json.enrichAny

import java.sql.Timestamp

object AddTodoItem {
  def controller(): Route = {
    post {
      path("") {
        (headerValueByName("Auth-Token") & formField("content") & formField("done".as[Boolean]) & formField("startAt")) {
          (token, content, done, startAt) =>
            val userID = checkJwtToken(token)

            if (userID == -1) {
              return complete(StatusCodes.Unauthorized, HttpEntity(ContentTypes.`application/json`,
                ErrorResponse(StatusCode = "A003", body = Map("error" -> "IllegalToken")).toJson.toString
              ))
            }
            try {
              // throw exception about illegal string
              val dateTime = Timestamp.valueOf(startAt)
              val todoItemID = transaction {
                DataBase.todoItems.insert(new TodoItem(content = content, done = done, startAt = dateTime, userID = userID))
              }.id
              complete(StatusCodes.Accepted, HttpEntity(ContentTypes.`application/json`,
                SuccessResponse(body = Map("data" -> todoItemID.toString)).toJson.toString
              ))
            } catch {
              case _:Exception => complete(StatusCodes.Unauthorized, HttpEntity(ContentTypes.`application/json`,
                ErrorResponse(StatusCode = "A003", body = Map("error" -> "IllegalStartAt")).toJson.toString
              ))
            }

        }
      }
    }
  }
}
