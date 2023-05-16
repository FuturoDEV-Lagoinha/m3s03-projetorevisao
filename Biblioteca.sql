--------------------------
-- Criar Banco de Dados --
--------------------------

CREATE DATABASE biblioteca_revisao;


-------------------
-- Criar Tabelas --
-------------------

CREATE TABLE autor (
  autor_id bigserial PRIMARY KEY,
  nome varchar(20) NOT NULL,
  sobrenome varchar(50) NOT NULL
);

CREATE TABLE usuario (
  usuario_id bigserial PRIMARY KEY,
  nome varchar(120) NOT NULL,
  documento varchar(20) NOT NULL,
  email varchar(120) NOT NULL,
  telefone varchar(16),
  bloqueado boolean NOT NULL DEFAULT false
);

CREATE TABLE livro (
  livro_id bigserial PRIMARY KEY,
  titulo varchar(50) NOT NULL,
  subtitulo varchar(150),
  autor_id bigint NOT NULL REFERENCES autor(autor_id),
  categoria varchar(20) NOT NULL,
  editora varchar(20) NOT NULL,
  exemplares int NOT NULL DEFAULT 0,
  disponibilidade boolean NOT NULL DEFAULT false
);

CREATE TABLE emprestimo (
  emprestimo_id bigserial PRIMARY KEY,
  usuario_id bigint NOT NULL REFERENCES usuario(usuario_id),
  data_emprestimo timestamp NOT NULL DEFAULT now()
);

CREATE TABLE emprestimo_livro (
  emprestimo_livro_id bigserial PRIMARY KEY,
  emprestimo_id bigint NOT NULL REFERENCES emprestimo(emprestimo_id),
  livro_id bigint NOT NULL REFERENCES livro(livro_id),
  data_prevista timestamp NOT NULL,
  data_devolucao timestamp
);
