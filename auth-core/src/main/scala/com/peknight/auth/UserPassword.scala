package com.peknight.auth

import cats.{Monad, Show}
import com.peknight.codec.config.given
import com.peknight.codec.cursor.Cursor
import com.peknight.codec.reader.{Key, Reader}
import com.peknight.codec.sum.{NullType, ObjectType, StringType}
import com.peknight.codec.{Codec, Decoder}

case class UserPassword(user: User, password: Password)
object UserPassword:
  given codecUserPassword[F[_]: Monad, S: {ObjectType, NullType, StringType, Show}]: Codec[F, S, Cursor[S], UserPassword] =
    Codec.derived[F, S, UserPassword]
  given keyDecodeUserPassword[F[_]](using Reader[F, String])(using Monad[F]): Decoder[F, Key, UserPassword] =
    Decoder.derivedByKey[F, UserPassword]
end UserPassword
