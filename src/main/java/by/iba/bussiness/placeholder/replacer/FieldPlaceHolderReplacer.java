package by.iba.bussiness.placeholder.replacer;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FieldPlaceHolderReplacer {
    public String replaceFieldPlaceHolders(Map<String, String> placeHolders, String field) {
        String modifiedField = field;
        for (Map.Entry<String, String> map : placeHolders.entrySet()) {
            String placeHolder = map.getKey();
            String placeHolderValue = map.getValue();
            modifiedField = modifiedField.replace(placeHolder, placeHolderValue);
        }
        return modifiedField;
    }
}
