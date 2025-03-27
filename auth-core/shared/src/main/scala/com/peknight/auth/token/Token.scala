package com.peknight.auth.token

sealed trait Token
object Token:
  case class Bearer(token: String) extends Token
end Token
