package demo.dto

import spray.json.DefaultJsonProtocol.{StringJsonFormat, jsonFormat2, jsonFormat3, mapFormat}
import spray.json.RootJsonFormat

object ResponseJsonFormat {
    implicit val successResponseJsonFormat: RootJsonFormat[SuccessResponse] = jsonFormat2(SuccessResponse)
    implicit val errorResponseJsonFormat: RootJsonFormat[ErrorResponse] = jsonFormat2(ErrorResponse)
    implicit val authTokeResponseJsonFormat: RootJsonFormat[AuthTokenResponse] = jsonFormat3(AuthTokenResponse)
}
