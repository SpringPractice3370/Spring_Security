package com.springpratice3370.kotlinWithSecurityJWT.security

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAccessDeniedHandler : AccessDeniedHandler {

    // 인증은 되었으나, 해당 자원 접근에 권한(Role)이 없을 경우 403
    @Throws(IOException::class)
    override fun handle(request: HttpServletRequest, response: HttpServletResponse, e: AccessDeniedException) {
        response.sendError(HttpServletResponse.SC_FORBIDDEN)
    }
}