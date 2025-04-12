

INSERT INTO tb_cidade (nome_cidade, cep, estado) VALUES ('São Roque', '18.130-000', 'SP');
INSERT INTO tb_cidade (nome_cidade, cep, estado) VALUES ('São José dos Pinhais', '86.230-000', 'PR');
INSERT INTO tb_cidade (nome_cidade, cep, estado) VALUES ('Maringá', '87.080-127', 'PR');

INSERT INTO tb_endereco (logradouro, numero, complemento, bairro, cidade_id) VALUES ('Rua Governador Carvalho Pinto', 253, 'casa', 'Jardim Boa Vista', 1);
INSERT INTO tb_endereco (logradouro, numero, complemento, bairro, cidade_id) VALUES ('Rua Salomão Miguel Nasser', 731, 'AP23', 'Guatupê', 2);
INSERT INTO tb_endereco (logradouro, numero, complemento, bairro, cidade_id) VALUES ('Rua Argentino Moreschi', 62, 'casa B', 'Residencial Moreschi', 3);
--INSERT INTO tb_endereco (logradouro, numero, complemento, bairro, cidade_id) VALUES ('Rua Aluizio Nunes Costa', 540, 'Barração B', 'Distrito Industrial', 3);

INSERT INTO tb_setor (setor_nome, processo) VALUES ('Produção', 'Recebimento e classificação de sucata');
INSERT INTO tb_setor (setor_nome, processo) VALUES ('Produção', 'Carregamento de fornos');
INSERT INTO tb_setor (setor_nome, processo) VALUES ('Produção', 'Vazamento');
INSERT INTO tb_setor (setor_nome, processo) VALUES ('Produção', 'Corte de tarugos');
INSERT INTO tb_setor (setor_nome, processo) VALUES ('Produção', 'Homogenização');
INSERT INTO tb_setor (setor_nome, processo) VALUES ('Produção', 'Resfriamento');
INSERT INTO tb_setor (setor_nome, processo) VALUES ('Produção', 'Expedição');

INSERT INTO tb_pessoa (nome_pessoa, email, celular, telefone, endereco_id) VALUES ('Luciano Rodrigo de Carvalho', 'luciano@gmail.com', '44-2523-25555','44-2552-2221', 3);   
INSERT INTO tb_pessoa (nome_pessoa, email, celular, telefone, endereco_id) VALUES ('João da Silva','João@gmail.com', '44-2500-25555','44-2511-2221', 1);
INSERT INTO tb_pessoa (nome_pessoa, email, celular, telefone, endereco_id) VALUES ('Michele Andrade','Michele@gmail.com', '44-211-1222','44-122-2221', 2);
--INSERT INTO tb_pessoa (nome_pessoa, email, celular, telefone, endereco_id) VALUES ('Ecoalumi S/A','ecoalumi@ecoalumi.com.br', '44-244-1222','44-1442-2222', 4);

INSERT INTO tb_funcionario (cpf, rg, usuario_sistema, id, setor_id) VALUES ('100.125.255-49', '20.533.347.45', true, 1, 1);
INSERT INTO tb_funcionario (cpf, rg, usuario_sistema, id, setor_id) VALUES ('520.12.255-49', '20.513.347.42', true, 2, 1);
--INSERT INTO tb_parceiro (cnpj, ie, fornecedor, cliente, ativo, id) VALUES ('00.252.457/000-45', '114.115.225', true, true, true, 4);
