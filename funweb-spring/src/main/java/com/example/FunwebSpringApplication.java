package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
@SpringBootApplication 애노테이션이 수행하는 일
: 이 애노테이션이 달린 클래스의 패키지를 기준으로
  @Component 계열( @Configuration, @Controller, @RestController, @Service, @Repository 등 )
  애노테이션이 달린 클래스들을 하위 패키지까지 스캔해서
  스프링이 관리하는 객체(스프링 빈 또는 빈이라고 부름)로 등록함 -> 의존관계 주입(DI) 됨
*/
@SpringBootApplication  // 스프링 애노테이션
@MapperScan("com.example.mapper")  // 마이바티스 애노테이션
public class FunwebSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(FunwebSpringApplication.class, args);
	}

}




