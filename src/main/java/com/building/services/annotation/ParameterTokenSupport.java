package com.building.services.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * このアノテーションの指定を行った場合、ヘッダー(こちら優先)に加えてパラメータからのトークン文字列の取得を行う.
 * @author masahiro.takahashi
 *
 */
@Target({ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ParameterTokenSupport {

}
