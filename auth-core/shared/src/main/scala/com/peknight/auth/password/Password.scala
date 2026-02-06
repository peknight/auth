package com.peknight.auth.password

import cats.{Applicative, Show}
import com.peknight.codec.Codec
import com.peknight.codec.cursor.Cursor
import com.peknight.codec.sum.StringType

trait Password:
  def value: String
  override def toString: String = "<Password>"
end Password
object Password:
  private case class Password(value: String) extends com.peknight.auth.password.Password
  def apply(value: String): com.peknight.auth.password.Password = Password(value)
  given stringCodecPassword[F[_]: Applicative]: Codec[F, String, String, com.peknight.auth.password.Password] =
    Codec.map[F, String, String, com.peknight.auth.password.Password](_.value)(apply)
  given codecPasswordS[F[_]: Applicative, S: {StringType, Show}]: Codec[F, S, Cursor[S], com.peknight.auth.password.Password] =
    Codec.codecS[F, S, com.peknight.auth.password.Password]
end Password