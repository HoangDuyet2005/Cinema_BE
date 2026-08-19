package com.example.goldenticketnew.utils;

import com.example.goldenticketnew.dtos.UserDto;
import com.example.goldenticketnew.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModelMapperUtils {

    private static ModelMapper modelMapper;

    public static <T> T mapper(Object sourceData, Class<T> destinationClassType) {
        if (sourceData == null) {
            return null;
        }
        return getInstance().map(sourceData, destinationClassType);
    }

    public static <T> T mapper(Object sourceData, T targetData) {
        getInstance().map(sourceData, targetData);
        return targetData;
    }

    public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source.stream()
                .map(e -> getInstance().map(e, targetClass))
                .collect(Collectors.toList());
    }
    public static List<UserDto> mapListUser(List<User> source) {
        return source.stream()
            .map(UserDto::new)
            .collect(Collectors.toList());
    }
    public static  <S, T> Page<S> mapEntityPageIntoDtoPage(Page<T> entities, Class<S> dtoClass) {
        return entities.map(objectEntity -> modelMapper.map(objectEntity, dtoClass));
    }
    private static ModelMapper getInstance(){
        if(modelMapper == null){
            modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE);
            modelMapper.getConfiguration().setDestinationNameTokenizer(NameTokenizers.UNDERSCORE);
        }

        return modelMapper;
    }

    public static String removeAccentsWithApacheCommons(String input) {
        return StringUtils.stripAccents(input).replaceAll("\\s+", "-").toLowerCase();
    }

}
