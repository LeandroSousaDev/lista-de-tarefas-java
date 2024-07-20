package br.com.leandrosousa.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {

    public static void copyNonNullProperties(Object source, Object targert) {
        BeanUtils.copyProperties(source, targert, getNullPropertyNames(source));
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);

        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> empityNames = new HashSet<>();

        for (PropertyDescriptor pd : pds) {
            Object srcValeu = src.getPropertyValue(pd.getName());
            if (srcValeu == null) {
                empityNames.add(pd.getName());
            }
        }

        String[] result = new String[empityNames.size()];
        return empityNames.toArray(result);
    }

}
