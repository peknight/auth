package com.peknight.auth.token

sealed trait Token
object Token:
  case class BearerAuth(token: String) extends Token
end Token
