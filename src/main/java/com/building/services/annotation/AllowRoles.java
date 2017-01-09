package com.building.services.annotation;

import com.building.services.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ロールベースの許可情報の記載用アノテーション.
 *
 * メソッド(優先)またはクラス/インターフェースに宣言可能
 *
 * @author masahiro.takahashi
 *
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowRoles {
	/** 実行許可されるロール名のリスト(複数宣言時はどれかいずれかを含むこと[OR]) */
	Role[] roles();
}
