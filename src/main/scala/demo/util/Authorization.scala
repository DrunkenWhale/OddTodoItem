package demo.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm

import java.security.MessageDigest
import scala.annotation.tailrec
import scala.util.Random

object Authorization {


  private val jwtSecretKey: String = "只有昏睡红茶可以吗"
  private val jwtIssuer: String = "野兽前辈"
  private val jwtSubject: String = "token"
  private val jwtEffectiveTime: Long = 7 * 24 * 60 * 60 * 1000

  private val randomPasswordSaltArray = "012346789.*-/+_=-/';.,abcdefghijklmnopqrstuvwxyz"

  @tailrec
  private def generatePasswordHashWithRecursion(salt: String, passwordString: String, deep: Int): String = {
    {
      deep match {
        case 0 => MessageDigest.getInstance("SHA-256").digest((salt + passwordString).getBytes).map(_.toChar).mkString
        case _ => generatePasswordHashWithRecursion(salt,
          MessageDigest.getInstance("SHA-256").digest((salt + passwordString).getBytes).map(_.toChar).mkString,
          deep - 1)
      }
    }
  }

  def generatePasswordHash(password: String, salt: String =
  (for (_ <- 0 to 7) yield randomPasswordSaltArray(Random.nextInt(randomPasswordSaltArray.length))).mkString): String = {
    salt + "$" + generatePasswordHashWithRecursion(salt, password, 27)
    // use hash to secret password
  }

  def checkPasswordHash(password: String, truePassword: String): Boolean = {
    generatePasswordHash(password, truePassword.split("\\$")(0)).equals(truePassword)
  }

  def generateJwtToken(userId: Long): String = {
    JWT.create()
        .withSubject(jwtSubject)
        .withIssuer(jwtIssuer)
        .withAudience(userId.toString)
        .withIssuedAt(new java.util.Date())
        .withExpiresAt(new java.util.Date(System.currentTimeMillis() + jwtEffectiveTime))
        .sign(Algorithm.HMAC512(jwtSecretKey))
  }

  /**
   * check jwt token is legal or not
   *
   * @return
   * legal => user ID
   * illegal => -1
   * */
  def checkJwtToken(tokenString: String): Long = {
    try {
      val decodeJwt = JWT.require(Algorithm.HMAC512(jwtSecretKey)).build().verify(tokenString)
      val decodeIssuer = decodeJwt.getIssuer
      val decodeSubject = decodeJwt.getSubject
      val decodeUserId = decodeJwt.getAudience.get(0).toLong
      if (decodeIssuer == jwtIssuer && decodeSubject == jwtSubject) decodeUserId
      else -1
    } catch {
      case _: Exception => -1
    }
  }


}
