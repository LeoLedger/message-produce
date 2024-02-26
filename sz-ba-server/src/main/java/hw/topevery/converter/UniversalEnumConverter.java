package hw.topevery.converter;

import hw.topevery.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author loner
 */
public class UniversalEnumConverter implements ConverterFactory<Integer, BaseEnum<Integer>> {

    private static final Map<Class<BaseEnum<Integer>>, Converter<Object, BaseEnum<Integer>>> converterMap = new WeakHashMap<>();
//
//    @Override
//    public <T extends BaseEnum<Integer>> Converter<S, T> getConverter(Class<T> targetType) {
//        return new StringToEnum(getEnumType(targetType));
//    }


    private static <T> Class<T> getEnumType(Class<T> targetType) {
        Class<T> enumType = targetType;
        while (enumType != null && !enumType.isEnum()) {
            enumType = (Class<T>) enumType.getSuperclass();
        }
        Assert.notNull(enumType, () -> "The target type " + targetType.getName() + " does not refer to an enum");
        return enumType;
    }

    @Override
    public <T extends BaseEnum<Integer>> Converter<Integer, T> getConverter(Class<T> targetType) {
        return new IntegerToEnum<>(getEnumType(targetType));
    }

//    @Override
//    public <T extends BaseEnum<Integer>> Converter<Integer, T> getConverter(Class<T> targetType) {
//        return new IntegerToEnum<T>(targetType);
//    }

//    @Override
//    public <T extends BaseEnum<Integer>> Converter<Integer, T> getConverter(Class<T> targetType) {
//        return new IntegerToEnum<BaseEnum<Integer>>(getEnumType(targetType));
//    }


    private static class IntegerToEnum<T extends BaseEnum<Integer>> implements Converter<Integer, T> {

        private final Class<T> enumType;

        public IntegerToEnum(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(Integer source) {
            for (T item : enumType.getEnumConstants()) {
                if (item.getValue().equals(source)) {
                    return item;
                }
            }
            throw new RuntimeException("No element matches " + source);
        }
    }

}
