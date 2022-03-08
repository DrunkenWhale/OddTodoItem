package demo.dto


case class ErrorResponse(override val StatusCode: String,
                         body: Map[String, String]) extends Response

