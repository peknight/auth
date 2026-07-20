package com.peknight.auth

import cats.{Applicative, Monad, Show}
import com.peknight.auth
import com.peknight.codec.cursor.Cursor
import com.peknight.codec.reader.{Key, Reader}
import com.peknight.codec.sum.StringType
import com.peknight.codec.{Codec, Decoder}

trait Password:
  def value: String
  override def toString: String = "<Password>"
end Password
object Password:
  case class Password(value: String) extends auth.Password
  def apply(value: String): auth.Password = Password(value)
  given stringCodecPassword[F[_]: Applicative]: Codec[F, String, String, auth.Password] =
    Codec.map[F, String, String, auth.Password](_.value)(apply)
  given codecPasswordS[F[_]: Applicative, S: {StringType, Show}]: Codec[F, S, Cursor[S], auth.Password] =
    Codec.codecS[F, S, auth.Password]
  given keyDecodePassword[F[_], S](using Reader[F, String])(using Monad[F]): Decoder[F, Key, auth.Password] =
    Decoder.decodeK[F, auth.Password]
end Password