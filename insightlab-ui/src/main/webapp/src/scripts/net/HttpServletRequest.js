
/**
 * @fileoverview Simple implementation of javax.servlet.http.HttpServletRequest.
 * @link http://google-styleguide.googlecode.com/svn/trunk/javascriptguide.xml
 */



/**
 * Simple implementation of javax.servlet.http.HttpServletRequest.
 * @constructor
 * @extends {ServletRequest}
 */
function HttpServletRequest() {
  ServletRequest.apply(this, arguments);

  /**
   * Returns the query string that is contained in the request URL after the
   *     path.
   * @return {string} Returns the query string that is contained in the request
   *     URL after the path.
   */
  this.getQueryString = function() {
    return location.search && location.search.substr(1);
  };

  // Export for closure compiler.
  this['getQueryString'] = this.getQueryString;

  /**
   * Returns the portion of the request URI that indicates the context of the
   * request.
   * @return {string} Returns current context path.
   */
  this.getContextPath = function() {
    if (!HttpServletRequest.contextPath_) {  
      HttpServletRequest.contextPath_ = window.CONTEXT_PATH || "";
    }
    return HttpServletRequest.contextPath_;
  };

  // Export for closure compiler.
  this['getContextPath'] = this.getContextPath;

}

// Export for closure compiler.
window['HttpServletRequest'] = HttpServletRequest;

