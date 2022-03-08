package demo.controller.todo

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import demo.dto.{ErrorResponse, Item, SingleTodoItemResponse, TodoItemListResponse}
import demo.dto.ResponseJsonFormat.{errorResponseJsonFormat, singleTodoItemResponseJsonFormat, todoItemListResponseJsonFormat}
import demo.model.{DataBase, TodoItem}
import demo.util.Authorization.checkJwtToken
import org.squeryl.PrimitiveTypeMode._
import spray.json.enrichAny

object GetTodoItem {
  def controller(): Route = {
    concat(
      get {
        path("all") {
          headerValueByName("Auth-Token") { token =>
            val userID = checkJwtToken(token)
            if (userID == -1) {
              return complete(StatusCodes.Unauthorized, HttpEntity(ContentTypes.`application/json`,
                ErrorResponse(StatusCode = "A003", body = Map("error" -> "IllegalToken")).toJson.toString
              ))
            }
            val itemList = transaction {
              DataBase.todoItems.where(item => item.userID === userID)
            }
            complete(StatusCodes.Accepted, HttpEntity(ContentTypes.`application/json`,
              TodoItemListResponse(
                body = Map("data" -> itemList.toList.map(x => Item(x.id, x.content, x.done, x.startAt.toString)))).toJson.toString
            ))
          }
        }
      },
      (get & path(LongNumber) & headerValueByName("Auth-Token")) { (id, token) =>
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
          complete(StatusCodes.Accepted, HttpEntity(ContentTypes.`application/json`,
            SingleTodoItemResponse(body = Map("data" -> Item(item.id, item.content, item.done, item.startAt.toString))).toJson.toString
          ))
        }
      }
    )
  }
}
