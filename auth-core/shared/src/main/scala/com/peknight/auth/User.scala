package com.peknight.auth

import cats.{Applicative, Show}
import com.peknight.auth
import com.peknight.codec.Codec
import com.peknight.codec.cursor.Cursor
import com.peknight.codec.sum.StringType

trait User:
  def value: String
  override def toString: String = value
end User
object User:
  private case class User(value: String) extends auth.User
  def apply(value: String): auth.User = User(value)
  given stringCodecUser[F[_]: Applicative]: Codec[F, String, String, auth.User] =
    Codec.map[F, String, String, auth.User](_.value)(apply)
  given codecUserS[F[_]: Applicative, S: {StringType, Show}]: Codec[F, S, Cursor[S], auth.User] =
    Codec.codecS[F, S, auth.User]
end User