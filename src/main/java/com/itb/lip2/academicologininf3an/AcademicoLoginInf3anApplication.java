package com.itb.lip2.academicologininf3an;

import com.itb.lip2.academicologininf3an.model.Papel;
import com.itb.lip2.academicologininf3an.repository.PapelRepository;
import com.itb.lip2.academicologininf3an.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;



@SpringBootApplication
public class AcademicoLoginInf3anApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcademicoLoginInf3anApplication.class, args);

	}


	@Bean
	CommandLineRunner run(UsuarioService usuarioService, PapelRepository papelRepository){
		return args -> {
			//usuarioService.save(new Professor(null,"Rogerio","Caetano Santos", "nilcc@gmail.com", "1234", new ArrayList<>()));
			if(papelRepository.findAll().size() == 0) {
				usuarioService.savePapel(new Papel(null, "ROLE_PROFESSOR"));
				usuarioService.savePapel(new Papel(null, "ROLE_ALUNO"));
				usuarioService.savePapel(new Papel(null, "ROLE_FUNCIONARIO"));
				usuarioService.savePapel(new Papel(null, "ROLE_ADMIN"));
			}else {
				System.out.println("Papeis já criados no banco de dados!");
			}
		};
	}

}
