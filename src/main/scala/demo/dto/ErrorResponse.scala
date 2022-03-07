package demo.dto


case class ErrorResponse(override val httpCode: Int,
                         override val statusCode: String,
                         body: Map[String, String]) extends Response

