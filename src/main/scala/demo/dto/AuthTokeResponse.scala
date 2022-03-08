package demo.dto

case class AuthTokeResponse(override val StatusCode: String,
                            override val body: Map[String, String],
                            AuthToken: String)
    extends SuccessResponse(StatusCode, body)
