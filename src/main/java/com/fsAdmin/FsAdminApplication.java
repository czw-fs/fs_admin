package com.fsAdmin;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan({
		"com.fsAdmin.modules.System.user.mapper",
		"com.fsAdmin.modules.System.dict.mapper",
		"com.fsAdmin.modules.System.menu.mapper",
		"com.fsAdmin.modules.System.role.mapper",
})
@SpringBootApplication
@Slf4j
public class FsAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(FsAdminApplication.class, args);
	}

}
