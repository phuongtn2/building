package com.building.services.annotation;

import javax.ws.rs.NameBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * AuthorizedPreProcessFilterの処理を行う対象の宣言.
 *
 * ExcelやCSVなどのケースもあるのでGlobal対象とするかは要検討
 *
 * @author masahiro.takahashi
 *
 */
@NameBinding
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionBinding {

}
