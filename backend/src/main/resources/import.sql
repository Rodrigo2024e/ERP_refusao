INSERT INTO tb_pais (nome) VALUES ('Brasil');
INSERT INTO tb_pais (nome) VALUES ('ARGENTINA');
INSERT INTO tb_pais (nome) VALUES ('EUA');

INSERT INTO tb_estado (nome, uf, pais_id) VALUES ('São Paulo', 'SP', 1);
INSERT INTO tb_estado (nome, uf, pais_id) VALUES ('Paraná', 'PR', 1);
INSERT INTO tb_estado (nome, uf, pais_id) VALUES ('Buenos Aires', 'CABA', 2);
INSERT INTO tb_estado (nome, uf, pais_id) VALUES ('Nova York', 'NY', 3);

iNSERT INTO tb_cidade (nome, cep, estado_id) VALUES ('São Roque', '18.130-000', 1);
INSERT INTO tb_cidade (nome, cep, estado_id) VALUES ('São José dos Pinhais', '86.230-000', 2);
INSERT INTO tb_cidade (nome, cep, estado_id) VALUES ('Maringá', '87.080-127', 2);


INSERT INTO tb_endereco (logradouro, numero, complemento, bairro, cidade_id) VALUES ('Rua Governador Carvalho Pinto', 253, 'casa', 'Jardim Boa Vista', 1);
INSERT INTO tb_endereco (logradouro, numero, complemento, bairro, cidade_id) VALUES ('Rua Salomão Miguel Nasser', 731, 'AP23', 'Guatupê', 2);
INSERT INTO tb_endereco (logradouro, numero, complemento, bairro, cidade_id) VALUES ('Rua Argentino Moreschi', 62, 'casa B', 'Residencial Moreschi', 3);
INSERT INTO tb_endereco (logradouro, numero, complemento, bairro, cidade_id) VALUES ('Rua Aluizio Nunes Costa', 540, 'Barração B', 'Distrito Industrial', 3);