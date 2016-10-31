package com.amhzing.activities.ui.helper;

import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.apache.commons.lang3.Validate.notNull;

public class JsonLoader {

    public String getJson(final Resource source) {
        notNull(source);

        try {
            return getJson(source.getInputStream());
        }
        catch (IOException ex) {
            throw new IllegalStateException("Unable to load JSON from " + source, ex);
        }
    }

    public String getJson(final InputStream source) {
        notNull(source);

        try {
            return FileCopyUtils.copyToString(new InputStreamReader(source));
        }
        catch (IOException ex) {
            throw new IllegalStateException("Unable to load JSON from InputStream", ex);
        }
    }
}
