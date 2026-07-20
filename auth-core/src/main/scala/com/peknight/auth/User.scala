package com.peknight.auth

import cats.{Applicative, Monad, Show}
import com.peknight.auth
import com.peknight.codec.cursor.Cursor
import com.peknight.codec.reader.{Key, Reader}
import com.peknight.codec.sum.StringType
import com.peknight.codec.{Codec, Decoder}

trait User:
  def value: String
  override def toString: String = value
end User
object User:
  case class User(value: String) extends auth.User
  def apply(value: String): auth.User = User(value)
  given stringCodecUser[F[_]: Applicative]: Codec[F, String, String, auth.User] =
    Codec.map[F, String, String, auth.User](_.value)(apply)
  given codecUserS[F[_]: Applicative, S: {StringType, Show}]: Codec[F, S, Cursor[S], auth.User] =
    Codec.codecS[F, S, auth.User]
  given keyDecodeUser[F[_], S](using Reader[F, String])(using Monad[F]): Decoder[F, Key, auth.User] =
    Decoder.decodeK[F, auth.User]
end User