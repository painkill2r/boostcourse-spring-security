package kr.co.connect.springsecurity;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Spring 공통 설정 파일
 */
@Configuration
@ComponentScan(basePackages = {"kr.co.connect.springsecurity.service", "kr.co.connect.springsecurity.dao"})
@Import({DBConfig.class})
public class ApplicationConfig {
}
