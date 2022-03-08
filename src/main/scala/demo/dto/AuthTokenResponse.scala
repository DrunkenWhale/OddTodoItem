package demo.dto

case class AuthTokenResponse(override val StatusCode: String = "0000",
                             body: Map[String, String],
                             AuthToken: String) extends Response

