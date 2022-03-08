package demo.dto

case class AuthTokenResponse(override val StatusCode: String="0000",
                             override val body: Map[String, String],
                             AuthToken: String)
    extends SuccessResponse(StatusCode, body)
