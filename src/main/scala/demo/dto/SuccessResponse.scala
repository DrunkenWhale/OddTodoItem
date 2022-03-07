package demo.dto

import scala.language.postfixOps

case class SuccessResponse(override val httpCode: Int,
                           override val statusCode: String,
                           body: Map[String, String]) extends Response()
