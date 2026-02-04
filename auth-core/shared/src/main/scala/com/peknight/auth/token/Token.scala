package com.peknight.auth.token

sealed trait Token:
  def token: String
  override def toString: String = "<Token>"
end Token
object Token:
  case class Bearer(token: String) extends com.peknight.auth.token.Token
  case class Token(token: String) extends com.peknight.auth.token.Token
  def apply(token: String): com.peknight.auth.token.Token = Token(token)
end Token
