IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='cursos' and xtype='U')

create table cursos (
id BIGINT NOT NULL IDENTITY(1,1) PRIMARY KEY,
nome_curso VARCHAR(45) NOT NULL,
descricao_curso VARCHAR(45) NULL,
usuario_id BIGINT NOT NULL,
CONSTRAINT fk_cursos_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
)

