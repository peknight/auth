package com.peknight.auth

import cats.{Applicative, Monad, Show}
import com.peknight.auth
import com.peknight.codec.cursor.Cursor
import com.peknight.codec.reader.{Key, Reader}
import com.peknight.codec.sum.StringType
import com.peknight.codec.{Codec, Decoder}

trait Secret:
  def value: String
  override def toString: String = "<Secret>"
end Secret
object Secret:
  case class Secret(value: String) extends auth.Secret
  def apply(value: String): auth.Secret = Secret(value)
  given stringCodecSecret[F[_]: Applicative]: Codec[F, String, String, auth.Secret] =
    Codec.map[F, String, String, auth.Secret](_.value)(apply)
  given codecSecretS[F[_]: Applicative, S: {StringType, Show}]: Codec[F, S, Cursor[S], auth.Secret] =
    Codec.codecS[F, S, auth.Secret]
  given keyDecodeSecret[F[_]](using Reader[F, String])(using Monad[F]): Decoder[F, Key, auth.Secret] =
    Decoder.decodeK[F, auth.Secret]
end Secret