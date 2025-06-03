
INSERT INTO tb_user (email, password) VALUES ('luciano@gmail.com','$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (email, password) VALUES ('ana@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');

INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO tb_role (authority) VALUES ('ROLE_CLIENT');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);

INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('Zickey Mouse', 'micky@gmail.com', '44-12523-2555','44-2552-2221');   
INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('Pateta Souza','pateta@gmail.com', '44-12500-2555','44-2511-2221');
INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('Michele Souza','michele@alunova.com.br', '44-14114-1222','44-1442-2222');
INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('Apple S/A','aplle@ecoalumi.com.br', '44-12244-1222','44-1442-2222')
INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('TSS S/A','aplle@ecoalumi.com.br', '44-14244-1222','44-1442-2222');
INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('João Carlos','jo@ecoalumi.com.br', '44-14244-1222','44-1442-2222');
INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('Luciano R Carvalho','luciano@gmail.com.br', '44-14244-1222','44-1442-2222');

INSERT INTO tb_city (name_city, uf_state) VALUES ('São Roque', 'SP');
INSERT INTO tb_city (name_city, uf_state) VALUES ('Maringá', 'PR');
INSERT INTO tb_city (name_city, uf_state) VALUES ('São José dos Pinhais', 'PR');

INSERT INTO tb_sector (name_sector, process) VALUES ('Produção', 'Recebimento e classificação de sucata');
INSERT INTO tb_sector (name_sector, process) VALUES ('Produção', 'Carregamento de fornos');
INSERT INTO tb_sector (name_sector, process) VALUES ('Produção', 'Vazamento');
INSERT INTO tb_sector (name_sector, process) VALUES ('Produção', 'Corte de tarugos');
INSERT INTO tb_sector (name_sector, process) VALUES ('Produção', 'Homogenização');
INSERT INTO tb_sector (name_sector, process) VALUES ('Produção', 'Resfriamento');
INSERT INTO tb_sector (name_sector, process) VALUES ('Produção', 'Expedição');
INSERT INTO tb_sector (name_sector, process) VALUES ('Administração', 'Qualidade');

INSERT INTO tb_employee (cpf, rg, sys_user, id, sector_id) VALUES ('100.125.255-49', '20.533.347.45', true, 1, 1);
INSERT INTO tb_employee (cpf, rg, sys_user, id, sector_id) VALUES ('520.123.255-49', '20.513.347.42', true, 2, 1);
INSERT INTO tb_employee (cpf, rg, sys_user, id, sector_id) VALUES ('111.121.225-49', '10.113.147.42', true, 3, 8);
INSERT INTO tb_employee (cpf, rg, sys_user, id, sector_id) VALUES ('111.000.111-49', '10.113.147.42', true, 7, 8);

INSERT INTO tb_partner (cnpj, ie, supplier, client, active, id) VALUES ('00.252.457/0001-45', '114.115.225', true, true, true, 4);
INSERT INTO tb_partner (cnpj, ie, supplier, client, active, id) VALUES ('00.111.157/0001-45', '114.100.225', true, true, true, 5);

INSERT INTO tb_address (street, number_address, complement, neighborhood, zip_code, city_id, people_id) VALUES ('Rua Governador Carvalho Pinto', 253, 'casa', 'Jardim Boa Vista', '18.120-000', 1, 1);
INSERT INTO tb_address (street, number_address, complement, neighborhood, zip_code, city_id, people_id) VALUES ('Rua Salomão Miguel Nasser', 731, 'AP23', 'Guatupê', '87.080-127', 3, 2);
INSERT INTO tb_address (street, number_address, complement, neighborhood, zip_code, city_id, people_id) VALUES ('Rua Argentino Moreschi', 62, 'casa B', 'Residencial Moreschi', '87.000-127', 2, 3);
INSERT INTO tb_address (street, number_address, complement, neighborhood, zip_code, city_id, people_id) VALUES ('Rua Central', 62, 'casa B', 'Residencial ', '87.000-127', 2, 4);
INSERT INTO tb_address (street, number_address, complement, neighborhood, zip_code, city_id, people_id) VALUES ('Avenida Brasil', 12, 'casa B', 'Centro ', '87.000-127', 2, 5);

INSERT INTO tb_ticket (data_ticket, num_ticket, peso_bruto, peso_desconto, peso_liquido) VALUES ('2025-04-18', 30110, 10.000, 500, 9500);

INSERT INTO tb_operacao (data_atual, entrada, placa, parceiro, material, preco, valor_total, tipo_operacao, tipo_gasto, num_ticket) VALUES ('2025-04-18', 'false', 'BAX-6B53', 'Ecoalumi', 'Tarugo de alumínio', 21.00, 100.200, 'Arrendamento', 1, 30110);
INSERT INTO tb_operacao (data_atual, entrada, placa, parceiro, material, preco, valor_total, tipo_operacao, tipo_gasto, num_ticket) VALUES ('2025-04-18', 'false', 'BAX-6B53', 'Ecoalumi', 'Tarugo de alumínio', 21.00, 100.200, 'Mão de obra', 1, 30110);



