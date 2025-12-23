package com.peknight.auth.token

sealed trait Token:
  def token: String
end Token
object Token:
  case class Bearer(token: String) extends Token
end Token
