package com.peknight.auth

import cats.{Monad, Show}
import com.peknight.codec.config.given
import com.peknight.codec.cursor.Cursor
import com.peknight.codec.reader.{Key, Reader}
import com.peknight.codec.sum.{NullType, ObjectType, StringType}
import com.peknight.codec.{Codec, Decoder}

case class Client(id: Identifier, secret: Secret)
object Client:
  given codecClient[F[_]: Monad, S: {ObjectType, NullType, StringType, Show}]: Codec[F, S, Cursor[S], Client] =
    Codec.derived[F, S, Client]
  given keyDecodeClient[F[_]](using Monad[F], Reader[F, String]): Decoder[F, Key, Client] =
    Decoder.derivedByKey[F, Client]
end Client
