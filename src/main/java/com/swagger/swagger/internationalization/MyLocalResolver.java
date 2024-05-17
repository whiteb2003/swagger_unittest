package com.swagger.swagger.internationalization;

import java.util.Locale;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class MyLocalResolver implements LocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String language = request.getHeader("Accept-Language");
        if (language == null || language.isEmpty()) {
            return Locale.forLanguageTag("en");
        }
        Locale locale = Locale.forLanguageTag(language);
        if (LanguageConfig.LOCALES.contains(locale)) {
            return locale;
        }
        return Locale.forLanguageTag("en");
    }

    @Override
    public void setLocale(HttpServletRequest arg0, HttpServletResponse arg1, Locale arg2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setLocale'");
    }

}
