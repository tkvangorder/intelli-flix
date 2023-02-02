package org.tkv.intelliflix;

import com.intellij.DynamicBundle;
import org.jetbrains.annotations.PropertyKey;

import java.util.function.Supplier;

public class IntelliFlixBundle extends DynamicBundle {

    private static final String BUNDLE = "messages.IntelliFlixBundle";

    public IntelliFlixBundle() {
        super(BUNDLE);
    }

    public static String message(@PropertyKey(resourceBundle=BUNDLE) String key, Object... params) {
        return INSTANCE.getMessage(key, params);
    }

    public static Supplier<String> messagePointer(@PropertyKey(resourceBundle=BUNDLE) String key, Object... params) {
        return INSTANCE.getLazyMessage(key, params);
    }
}
