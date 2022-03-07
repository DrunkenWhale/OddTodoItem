package demo.dto

case class SuccessResponse(override val httpCode: Int,
                           override val statusCode: String,
                           body: Map[String, String]) extends Response

object SuccessResponse{

}
