package com.peknight.auth.user

import cats.{Applicative, Show}
import com.peknight.codec.Codec
import com.peknight.codec.cursor.Cursor
import com.peknight.codec.sum.StringType

trait User:
  def value: String
  override def toString: String = value
end User
object User:
  private case class User(value: String) extends com.peknight.auth.user.User
  def apply(value: String): com.peknight.auth.user.User = User(value)
  given stringCodecUser[F[_]: Applicative]: Codec[F, String, String, com.peknight.auth.user.User] =
    Codec.map[F, String, String, com.peknight.auth.user.User](_.value)(apply)
  given codecUserS[F[_]: Applicative, S: {StringType, Show}]: Codec[F, S, Cursor[S], com.peknight.auth.user.User] =
    Codec.codecS[F, S, com.peknight.auth.user.User]
end User