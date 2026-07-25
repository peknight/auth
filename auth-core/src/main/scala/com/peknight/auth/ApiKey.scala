package com.peknight.auth

import cats.{Applicative, Show}
import com.peknight.auth
import com.peknight.codec.Codec
import com.peknight.codec.cursor.Cursor
import com.peknight.codec.sum.StringType

trait ApiKey:
  def value: String
  override def toString: String = "<ApiKey>"
end ApiKey
object ApiKey:
  case class ApiKey(value: String) extends auth.ApiKey
  def apply(value: String): auth.ApiKey = ApiKey(value)
  given stringCodecApiKey[F[_]: Applicative]: Codec[F, String, String, auth.ApiKey] =
    Codec.map[F, String, String, auth.ApiKey](_.value)(apply)
  given codecApiKeyS[F[_]: Applicative, S: {StringType, Show}]: Codec[F, S, Cursor[S], auth.ApiKey] =
    Codec.codecS[F, S, auth.ApiKey]
end ApiKey