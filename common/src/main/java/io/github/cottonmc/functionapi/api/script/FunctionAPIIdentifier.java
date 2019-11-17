package io.github.cottonmc.functionapi.api.script;

import org.apache.commons.lang3.StringUtils;

public interface FunctionAPIIdentifier {
    String getNamespace();
    String getPath();

    static boolean isCharValid(char char_1) {
        return char_1 >= '0' && char_1 <= '9' || char_1 >= 'a' && char_1 <= 'z' || char_1 == '_' || char_1 == ':' || char_1 == '/' || char_1 == '.' || char_1 == '-';
    }

    static boolean isPathValid(String string_1) {
        return string_1.chars().allMatch((int_1) -> {
            return int_1 == 95 || int_1 == 45 || int_1 >= 97 && int_1 <= 122 || int_1 >= 48 && int_1 <= 57 || int_1 == 47 || int_1 == 46;
        });
    }

    static boolean isNamespaceValid(String string_1) {
        return string_1.chars().allMatch((int_1) -> {
            return int_1 == 95 || int_1 == 45 || int_1 >= 97 && int_1 <= 122 || int_1 >= 48 && int_1 <= 57 || int_1 == 46;
        });
    }

}
