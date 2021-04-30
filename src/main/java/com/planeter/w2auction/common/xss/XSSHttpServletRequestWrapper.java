package com.planeter.w2auction.common.xss;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Planeter
 * @description: TODO
 * @date 2021/4/30 10:25
 * @status dev
 */
public class XSSHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XSSHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getQueryString() {
        return HtmlUtils.htmlEscape(super.getQueryString());
    }

    @Override
    public String getParameter(String name) {
        return HtmlUtils.htmlEscape(super.getParameter(name));
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (ArrayUtils.isEmpty(values)) {
            return values;
        }
        int length = values.length;
        String[] escapeValues = new String[length];
        for (int i = 0; i < length; i++) {
            escapeValues[i] = HtmlUtils.htmlEscape(values[i]);
        }
        return escapeValues;
    }
}
