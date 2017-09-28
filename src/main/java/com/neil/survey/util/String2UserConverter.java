package com.neil.survey.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;

@Component
public class String2UserConverter implements Converter<String, PageEntity> {

    @Override
    public PageEntity convert(String source) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }

        Gson gson = new Gson();
        PageEntity res = gson.fromJson(source, PageEntity.class);
        return res;
    }

}