package de.jaschastarke;

import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MultipleResourceBundle extends ResourceBundle {
    private Locale locale;
    private List<ResourceBundle> bundles = new ArrayList<ResourceBundle>();
    private ClassLoader loader = null;

    public MultipleResourceBundle(Locale locale, Collection<String> bundleNames) {
        this.locale = locale == null ? Locale.getDefault() : locale;
        for (String bundleName : bundleNames) {
            addResourceBundle(bundleName);
        }
    }
    public MultipleResourceBundle(Locale locale, String[] bundleNames) {
        this.locale = locale == null ? Locale.getDefault() : locale;
        for (String bundleName : bundleNames) {
            addResourceBundle(bundleName);
        }
    }
    public MultipleResourceBundle(Locale locale, String bundleName) {
        this.locale = locale == null ? Locale.getDefault() : locale;
        addResourceBundle(bundleName);
    }
    public MultipleResourceBundle(Locale locale, String[] bundleNames, URLClassLoader loader) {
        this.locale = locale == null ? Locale.getDefault() : locale;
        this.loader = loader;
        for (String bundleName : bundleNames) {
            addResourceBundle(bundleName);
        }
    }
    public void addResourceBundle(String bundleName) {
        if (loader != null)
            bundles.add(ResourceBundle.getBundle(bundleName, locale, loader));
        else
            bundles.add(ResourceBundle.getBundle(bundleName, locale));
    }
    
    @Override
    public Enumeration<String> getKeys() {
        return null;
    }

    @Override
    protected Object handleGetObject(String key) {
        Object result = null;
        MissingResourceException nfe = null;
        for (ResourceBundle bundle : bundles) {
            try {
                result = bundle.getObject(key);
                if (result != null)
                    break;
            } catch (MissingResourceException exc) {
                nfe = exc;
            }
        }
        if (result == null) {
            throw nfe;
        }
        return result;
    }
}
