
INSERT INTO tb_pessoa (nome_pessoa, email, celular, telefone) VALUES ('Luciano Rodrigo de Carvalho', 'luciano@gmail.com', '44-2523-25555','44-2552-2221');   
INSERT INTO tb_pessoa (nome_pessoa, email, celular, telefone) VALUES ('Fernando Liza Monteiro','fernando@gmail.com', '44-2500-25555','44-2511-2221');
INSERT INTO tb_pessoa (nome_pessoa, email, celular, telefone) VALUES ('Ecoalumi S/A','ecoalumi@ecoalumi.com.br', '44-244-1222','44-1442-2222');

INSERT INTO tb_cidade (cidade, cep, estado) VALUES ('São Roque', '18.120-000', 'SP');
INSERT INTO tb_cidade (cidade, cep, estado) VALUES ('Maringá', '87.080-127', 'PR');
INSERT INTO tb_cidade (cidade, cep, estado) VALUES ('Curitiba', '87.000-127', 'PR');

INSERT INTO tb_setor (setor_nome, processo) VALUES ('Produção', 'Recebimento e classificação de sucata');
INSERT INTO tb_setor (setor_nome, processo) VALUES ('Produção', 'Carregamento de fornos');
INSERT INTO tb_setor (setor_nome, processo) VALUES ('Produção', 'Vazamento');
INSERT INTO tb_setor (setor_nome, processo) VALUES ('Produção', 'Corte de tarugos');
INSERT INTO tb_setor (setor_nome, processo) VALUES ('Produção', 'Homogenização');
INSERT INTO tb_setor (setor_nome, processo) VALUES ('Produção', 'Resfriamento');
INSERT INTO tb_setor (setor_nome, processo) VALUES ('Produção', 'Expedição');

INSERT INTO tb_funcionario (cpf, rg, usuario_sistema, id, setor_id) VALUES ('100.125.255-49', '20.533.347.45', true, 1, 1);
INSERT INTO tb_funcionario (cpf, rg, usuario_sistema, id, setor_id) VALUES ('520.12.255-49', '20.513.347.42', true, 2, 1);
INSERT INTO tb_parceiro (cnpj, ie, fornecedor, cliente, ativo, id) VALUES ('00.252.457/000-45', '114.115.225', true, true, true, 3);

INSERT INTO tb_endereco (logradouro, numero, complemento, bairro, pessoa_id, cidade_id) VALUES ('Rua Governador Carvalho Pinto', 253, 'casa', 'Jardim Boa Vista', 1, 1);
INSERT INTO tb_endereco (logradouro, numero, complemento, bairro, pessoa_id, cidade_id) VALUES ('Rua Salomão Miguel Nasser', 731, 'AP23', 'Guatupê', 2, 3);
INSERT INTO tb_endereco (logradouro, numero, complemento, bairro, pessoa_id, cidade_id) VALUES ('Rua Argentino Moreschi', 62, 'casa B', 'Residencial Moreschi', 3, 2);
