package com.peknight.auth

import cats.{Applicative, Show}
import com.peknight.auth
import com.peknight.codec.Codec
import com.peknight.codec.cursor.Cursor
import com.peknight.codec.sum.StringType

trait Password:
  def value: String
  override def toString: String = "<Password>"
end Password
object Password:
  private case class Password(value: String) extends auth.Password
  def apply(value: String): auth.Password = Password(value)
  given stringCodecPassword[F[_]: Applicative]: Codec[F, String, String, auth.Password] =
    Codec.map[F, String, String, auth.Password](_.value)(apply)
  given codecPasswordS[F[_]: Applicative, S: {StringType, Show}]: Codec[F, S, Cursor[S], auth.Password] =
    Codec.codecS[F, S, auth.Password]
end Password