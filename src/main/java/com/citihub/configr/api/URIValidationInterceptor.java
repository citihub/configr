package com.citihub.configr.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import com.citihub.configr.exception.BadURIException;

public class URIValidationInterceptor implements HandlerInterceptor {

  /**
   * Allows the base routes plus swagger/openapi endpoints
   */
  private final String VALID_URI_REGEX =
      "/{1}(configuration/.+|metadata/.+|version/.+|v3/.*|swagger-ui/.*|swagger-ui.html)";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    if (!isValidURI(request.getRequestURI()))
      throw new BadURIException();

    return true;
  }

  boolean isValidURI(String uri) {
    return uri.matches(VALID_URI_REGEX);
  }
}
