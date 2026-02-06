package com.peknight.auth.token

import cats.{Applicative, Show}
import com.peknight.codec.Codec
import com.peknight.codec.cursor.Cursor
import com.peknight.codec.sum.StringType

sealed trait Token:
  def token: String
  override def toString: String = "<Token>"
end Token
object Token:
  case class Bearer(token: String) extends com.peknight.auth.token.Token
  object Bearer:
    given stringCodecBearer[F[_]: Applicative]: Codec[F, String, String, Bearer] =
      Codec.map[F, String, String, Bearer](_.token)(Bearer.apply)
    given codecBearerS[F[_]: Applicative, S: {StringType, Show}]: Codec[F, S, Cursor[S], Bearer] =
      Codec.codecS[F, S, Bearer]
  end Bearer
  case class Token(token: String) extends com.peknight.auth.token.Token
  object Token:
    given stringCodecToken[F[_]: Applicative]: Codec[F, String, String, Token] =
      Codec.map[F, String, String, Token](_.token)(Token.apply)
    given codecTokenS[F[_] : Applicative, S: {StringType, Show}]: Codec[F, S, Cursor[S], Token] =
      Codec.codecS[F, S, Token]
  end Token
  def apply(token: String): com.peknight.auth.token.Token = Token(token)
end Token
