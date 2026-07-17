package com.peknight.auth

import cats.{Applicative, Monad, Show}
import com.peknight.auth
import com.peknight.codec.cursor.Cursor
import com.peknight.codec.reader.{Key, Reader}
import com.peknight.codec.sum.StringType
import com.peknight.codec.{Codec, Decoder}

trait Identifier:
  def value: String
  override def toString: String = value
end Identifier
object Identifier:
  case class Identifier(value: String) extends auth.Identifier
  def apply(value: String): auth.Identifier = Identifier(value)
  given stringCodecIdentifier[F[_]: Applicative]: Codec[F, String, String, auth.Identifier] =
    Codec.map[F, String, String, auth.Identifier](_.value)(apply)
  given codecIdentifierS[F[_]: Applicative, S: {StringType, Show}]: Codec[F, S, Cursor[S], auth.Identifier] =
    Codec.codecS[F, S, auth.Identifier]
  given keyDecodeIdentifier[F[_], S](using Reader[F, String], Monad[F]): Decoder[F, Key, auth.Identifier] =
    Decoder.decodeK[F, auth.Identifier]
end Identifier