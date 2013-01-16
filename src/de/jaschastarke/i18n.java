package de.jaschastarke;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.IncompleteArgumentException;

public class i18n {
    private static final String DEFAULT_BUNDLE_NAME = "messages";
    protected ResourceBundle bundle;
    public i18n(String bundleName, Locale locale) {
        useBundle(bundleName, locale);
    }
    public i18n(String bundleName) {
        useBundle(bundleName, null);
    }
    public i18n(Locale locale) {
        useBundle(null, locale);
    }
    public i18n() {
        useBundle(null, null);
    }
    private void useBundle(String bundleName, Locale locale) {
        if (bundleName == null)
            bundleName = DEFAULT_BUNDLE_NAME;
        if (locale != null)
            bundle = ResourceBundle.getBundle(bundleName, locale);
        else
            bundle = ResourceBundle.getBundle(bundleName);
    }
    
    public ResourceBundle getResourceBundle() {
        return bundle;
    }
    
    public String trans(String msg, Object... objects) {
        msg = bundle.getString(msg);
        if (objects.length > 0)
            msg = MessageFormat.format(msg, objects);
        return msg;
    }
    
    public static Locale getLocaleFromString(String locale) {
        Matcher match = Pattern.compile("^([a-z]+)(?:[-_]([A-Z]+)(?:[-_].*))?)?$").matcher(locale);
        if (!match.matches())
            throw new IncompleteArgumentException("Locale-String could ne be interpreted: "+locale);
        if (match.group(2) != null)
            return new Locale(match.group(0), match.group(1), match.group(2));
        else if (match.group(1) != null)
            return new Locale(match.group(0), match.group(1));
        else
            return new Locale(match.group(0));
    }
}
