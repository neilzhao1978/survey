package com.neil.survey.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RequestBodyConverter implements GenericConverter {

	@Override
	public Set<ConvertiblePair> getConvertibleTypes() {
		Set<ConvertiblePair> pair = new HashSet<>();

		pair.add(new ConvertiblePair(String.class, PageEntity.class));

		return pair;
	}

	@Override
	public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
		// resolvableType，如果是泛型参数的话resolvableType，是rawType，Spring
		// 4.0以后的TypeDescriptor才提供getResolvableType(),提供rawType以及typeArguments
		// 所以我们的接口参数含有泛型就不能使用@RequestBody,Collection容器除外
		Class<?> resolvableType = targetType.getObjectType();
		Object obj = null;
		if (Collection.class.isAssignableFrom(resolvableType)) {
			Class<?> elementType = targetType.getElementTypeDescriptor().getObjectType();
			Type type = new MyParameterizedType(resolvableType, elementType);
			// obj = gson.fromJson((String) source, type);
		} else {
			// obj = gson.fromJson((String) source, resolvableType);
		}
		return obj;
	}

	static class MyParameterizedType implements ParameterizedType {
		private Type rawType;
		private Type[] typeArguments;

		private MyParameterizedType(Type rawType, Type... typeArguments) {
			super();
			this.rawType = rawType;
			this.typeArguments = typeArguments;
		}

		@Override
		public Type[] getActualTypeArguments() {
			return typeArguments;
		}

		@Override
		public Type getRawType() {
			return rawType;
		}

		@Override
		public Type getOwnerType() {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
