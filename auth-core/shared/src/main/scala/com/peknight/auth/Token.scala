package com.peknight.auth

import cats.{Applicative, Show}
import com.peknight.auth
import com.peknight.codec.Codec
import com.peknight.codec.cursor.Cursor
import com.peknight.codec.sum.StringType

sealed trait Token:
  def token: String
  override def toString: String = "<Token>"
end Token
object Token:
  private case class Token(token: String) extends auth.Token
  case class Bearer(token: String) extends auth.Token
  object Bearer:
    given stringCodecBearer[F[_]: Applicative]: Codec[F, String, String, Bearer] =
      Codec.map[F, String, String, Bearer](_.token)(Bearer.apply)
    given codecBearerS[F[_]: Applicative, S: {StringType, Show}]: Codec[F, S, Cursor[S], Bearer] =
      Codec.codecS[F, S, Bearer]
  end Bearer
  def apply(token: String): auth.Token = Token(token)
  given stringCodecToken[F[_]: Applicative]: Codec[F, String, String, auth.Token] =
    Codec.map[F, String, String, auth.Token](_.token)(apply)
  given codecTokenS[F[_] : Applicative, S: {StringType, Show}]: Codec[F, S, Cursor[S], auth.Token] =
    Codec.codecS[F, S, auth.Token]
end Token
