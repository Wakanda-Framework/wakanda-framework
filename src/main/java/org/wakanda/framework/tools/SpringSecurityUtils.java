/* (C)2022 */
package org.wakanda.framework.tools;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * Spring security utils.
 *
 * @author Vipul Meehnia
 * @date 8/16/21
 * @since JDK1.8
 */
public class SpringSecurityUtils {

  private SpringSecurityUtils() {}

  /**
   * Get current user's IP address.
   *
   * @return IP
   */
  public static String getCurrentUserIp() {
    Authentication authentication = getAuthentication();
    if (authentication == null) {
      return "";
    }
    Object details = authentication.getDetails();
    if (details instanceof WebAuthenticationDetails webDetails) {
      return webDetails.getRemoteAddress();
    }
    return "";
  }

  /**
   * Get current username.
   *
   * @return current username
   */
  public static String getCurrentUsername() {
    Authentication authentication = getAuthentication();
    if ((authentication == null) || (authentication.getPrincipal() == null)) {
      return "";
    }
    return authentication.getName();
  }

  /**
   * Save user details to security context.
   *
   * @param userDetails user details
   * @param request request
   */
  public static void saveUserDetailsToContext(UserDetails userDetails, HttpServletRequest request) {
    PreAuthenticatedAuthenticationToken authentication =
        new PreAuthenticatedAuthenticationToken(
            userDetails, userDetails.getPassword(), userDetails.getAuthorities());

    if (request != null) {
      authentication.setDetails(new WebAuthenticationDetails(request));
    }

    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  /**
   * Get Authentication
   *
   * @return authentication
   */
  private static Authentication getAuthentication() {
    SecurityContext context = SecurityContextHolder.getContext();
    if (context == null) {
      return null;
    }
    return context.getAuthentication();
  }
}
