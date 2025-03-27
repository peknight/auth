package com.peknight.auth.http4s.syntax

import com.peknight.auth.token.Token
import org.http4s.headers.Authorization
import org.http4s.{AuthScheme, Credentials, Header}

trait TokenSyntax:
  extension (token: Token)
    def toHeader: Header.ToRaw = token match
      case Token.Bearer(token) => Authorization(Credentials.Token(AuthScheme.Bearer, token))
  end extension
end TokenSyntax
object TokenSyntax extends TokenSyntax