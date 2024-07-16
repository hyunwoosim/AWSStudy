package com.woo.AWS.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
// 이 어노테이션이 생성될 수 있는 위치를 지정한다.
// PRAMETER로 지정 했으니 메서드의 파라미터로 선언된 객체에만 사용할 수 있다.
@Retention(RetentionPolicy.RUNTIME)
// 에노테이션의 유지 정책이다. 언제까지 유지될지를 지정한다.
// 컴파일된 클래스 파일에 포함되고, 런타임에서도 리플렉션을 통해 사용할 수 있습니다.
public @interface LoginUser {
    // 이 파일을 어노테이션 클래스로 지정한다.


}
