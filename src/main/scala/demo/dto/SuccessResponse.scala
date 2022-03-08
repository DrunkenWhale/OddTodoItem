package demo.dto

import scala.language.postfixOps

case class SuccessResponse(override val StatusCode: String = "0000",
                           body: Map[String, String]) extends Response()
