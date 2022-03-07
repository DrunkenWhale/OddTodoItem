package demo.dto

import spray.json.DefaultJsonProtocol.{IntJsonFormat, StringJsonFormat, jsonFormat3, mapFormat}
import spray.json.RootJsonFormat

object ResponseJsonFormat {
    implicit val successResponseJsonFormat: RootJsonFormat[SuccessResponse] = jsonFormat3(SuccessResponse)
    implicit val errorResponseJsonFormat: RootJsonFormat[ErrorResponse] = jsonFormat3(ErrorResponse)
}
