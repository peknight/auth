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
  case class User(value: String) extends com.peknight.auth.user.User
  object User:
    given stringCodecUser[F[_]: Applicative]: Codec[F, String, String, User] =
      Codec.map[F, String, String, User](_.value)(User.apply)
    given codecUserS[F[_]: Applicative, S: {StringType, Show}]: Codec[F, S, Cursor[S], User] =
      Codec.codecS[F, S, User]
  end User
  def apply(value: String): com.peknight.auth.user.User = User(value)
end User