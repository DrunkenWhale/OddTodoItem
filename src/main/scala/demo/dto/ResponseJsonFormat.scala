package demo.dto

import spray.json.DefaultJsonProtocol.{BooleanJsonFormat, LongJsonFormat, StringJsonFormat, jsonFormat2, jsonFormat3, jsonFormat4, listFormat, mapFormat}
import spray.json.RootJsonFormat

object ResponseJsonFormat {
  implicit val todoItemJsonFormat: RootJsonFormat[Item] = jsonFormat4(Item)

  implicit val successResponseJsonFormat: RootJsonFormat[SuccessResponse] = jsonFormat2(SuccessResponse)
  implicit val errorResponseJsonFormat: RootJsonFormat[ErrorResponse] = jsonFormat2(ErrorResponse)
  implicit val authTokeResponseJsonFormat: RootJsonFormat[AuthTokenResponse] = jsonFormat3(AuthTokenResponse)
  implicit val singleTodoItemResponseJsonFormat: RootJsonFormat[SingleTodoItemResponse] = jsonFormat2(SingleTodoItemResponse)
  implicit val todoItemListResponseJsonFormat: RootJsonFormat[TodoItemListResponse] = jsonFormat2(TodoItemListResponse)
}
