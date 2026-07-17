package com.peknight.auth

import cats.{Monad, Show}
import com.peknight.codec.config.given
import com.peknight.codec.cursor.Cursor
import com.peknight.codec.reader.{Key, Reader}
import com.peknight.codec.sum.{NullType, ObjectType, StringType}
import com.peknight.codec.{Codec, Decoder}

case class AccessKey(id: Identifier, secret: Secret)
object AccessKey:
  given codecAccessKey[F[_]: Monad, S: {ObjectType, NullType, StringType, Show}]: Codec[F, S, Cursor[S], AccessKey] =
    Codec.derived[F, S, AccessKey]
  given keyDecodeAccessKey[F[_]](using Monad[F], Reader[F, String]): Decoder[F, Key, AccessKey] =
    Decoder.derivedByKey[F, AccessKey]
end AccessKey
