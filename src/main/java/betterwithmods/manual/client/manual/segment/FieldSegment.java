package betterwithmods.manual.client.manual.segment;

import net.minecraftforge.fml.relauncher.ReflectionHelper;

import javax.annotation.Nullable;
import java.lang.reflect.Field;

/**
 * Created by primetoxinz on 6/19/17.
 */
public class FieldSegment extends TextSegment {
    public FieldSegment(@Nullable Segment parent, String className, String fieldNames) {
        super(parent, getFieldValue(className, fieldNames));
    }

    public static String getFieldValue(String className, String fieldNames) {

        String[] fields = new String[]{fieldNames};
        if (fieldNames.contains("|"))
            fields = fieldNames.split("|");

        try {
            Class clazz = Class.forName(className);
            Field field = ReflectionHelper.findField(clazz, fields);
            return field.get(null).toString();
        } catch (ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return "";

    }
}
