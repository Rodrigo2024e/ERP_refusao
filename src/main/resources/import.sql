
INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('Luciano Rodrigo de Carvalho', 'luciano@alunova.com', '44-98816-7652', null);   
INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('Giovana Gonçalves','giovana@alunova.com', '44-99737-8851','44-1000-1001');
INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('Michele Pereira da Silva','michele@alunova.com.br', '11-95479-6733','44-1002-1003');
INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('Ecoalumi','ecoalumi@ecoalumi.com.br', '44-9902-4372','44-3031-2323')
INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('Recimax Ltda','recimax@reximax.com.br', '41-14244-1222','44-1442-2222');
INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('Metalurgica Reisam Industria e Comércio ','reisam@reisam.com.br', '11-5199-6490','44-1442-2222');
INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('Natal da Silva Bueno','natal@gmail.com.br', '44-99987-8097','44-1442-2222');
--INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('Teste','luciano@gmail.com.br', '44-14244-1222','44-1442-2222');

INSERT INTO tb_city (name_city, uf_state) VALUES ('São Roque', 'SP');
INSERT INTO tb_city (name_city, uf_state) VALUES ('Maringá', 'PR');
INSERT INTO tb_city (name_city, uf_state) VALUES ('Paiçandu', 'PR');
INSERT INTO tb_city (name_city, uf_state) VALUES ('São José dos Pinhais', 'PR');
INSERT INTO tb_city (name_city, uf_state) VALUES ('Guararema', 'SP');
INSERT INTO tb_city (name_city, uf_state) VALUES ('Campo Largo', 'PR');

INSERT INTO tb_sector (name_sector, process) VALUES ('Produção', 'Recebimento e classificação de sucata');
INSERT INTO tb_sector (name_sector, process) VALUES ('Produção', 'Carregamento de fornos');
INSERT INTO tb_sector (name_sector, process) VALUES ('Produção', 'Vazamento');
INSERT INTO tb_sector (name_sector, process) VALUES ('Produção', 'Corte de tarugos');
INSERT INTO tb_sector (name_sector, process) VALUES ('Produção', 'Homogenização');
INSERT INTO tb_sector (name_sector, process) VALUES ('Produção', 'Resfriamento');
INSERT INTO tb_sector (name_sector, process) VALUES ('Produção', 'Expedição');
INSERT INTO tb_sector (name_sector, process) VALUES ('Administração', 'Qualidade');
INSERT INTO tb_sector (name_sector, process) VALUES ('Administração', 'Gerência Industrial');
INSERT INTO tb_sector (name_sector, process) VALUES ('Administração', 'Manutenção');
INSERT INTO tb_sector (name_sector, process) VALUES ('Administração', 'Balança Rodoviária');
INSERT INTO tb_sector (name_sector, process) VALUES ('Administração', 'Comercial');

INSERT INTO tb_employee (cpf, rg, sys_user, id, sector_id) VALUES ('100.125.255-49', '20.533.347.45', true, 1, 9);
INSERT INTO tb_employee (cpf, rg, sys_user, id, sector_id) VALUES ('520.123.255-49', '20.513.347.42', true, 2, 11);
INSERT INTO tb_employee (cpf, rg, sys_user, id, sector_id) VALUES ('111.121.225-49', '10.113.147.42', true, 3, 8);
INSERT INTO tb_employee (cpf, rg, sys_user, id, sector_id) VALUES ('111.000.111-49', '10.113.147.42', true, 7, 10);
--INSERT INTO tb_employee (cpf, rg, sys_user, id, sector_id) VALUES ('331.000.120-49', '10.113.147.42', true, 8, 8);

INSERT INTO tb_partner (cnpj, ie, supplier, client, active, id) VALUES ('00.252.457/0001-45', '114.115.225', true, true, true, 4);
INSERT INTO tb_partner (cnpj, ie, supplier, client, active, id) VALUES ('00.111.157/0001-45', '114.100.225', true, true, true, 5);
INSERT INTO tb_partner (cnpj, ie, supplier, client, active, id) VALUES ('00.121.137/0002-52', '124.101.221', true, true, true, 6);


INSERT INTO tb_user (email, password, employee_id) VALUES ('luciano@gmail.com','$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 7);
INSERT INTO tb_user (email, password, employee_id) VALUES ('ana@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', null);

INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO tb_role (authority) VALUES ('ROLE_USER');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);

INSERT INTO tb_address (street, number_address, complement, neighborhood, zip_code, city_id, people_id) VALUES ('Rua Argentino Moreschi', 62, 'Casa B', 'Residencial Moreschi', '87.080-127', 2, 1);
INSERT INTO tb_address (street, number_address, complement, neighborhood, zip_code, city_id, people_id) VALUES ('Rua Salomão Miguel Nasser', 731, 'AP23', 'Centro', '87.080-127', 3, 2);
INSERT INTO tb_address (street, number_address, complement, neighborhood, zip_code, city_id, people_id) VALUES ('Avenida Central', 1001, null, 'Centro', '87.000-127', 3, 3);
INSERT INTO tb_address (street, number_address, complement, neighborhood, zip_code, city_id, people_id) VALUES ('Rua Aluízio Nunes Costa', 540, 'Barracão 54A', 'Distrito Industrial ', '87.000-127', 2, 4);
INSERT INTO tb_address (street, number_address, complement, neighborhood, zip_code, city_id, people_id) VALUES ('Rua do Alumínio', 12, null, 'Distrito Industrial ', '87.000-127', 2, 5);
INSERT INTO tb_address (street, number_address, complement, neighborhood, zip_code, city_id, people_id) VALUES ('Rua Regional', 625, 'Centro', 'Residencial Central ', '87.000-127', 6, 6);
INSERT INTO tb_address (street, number_address, complement, neighborhood, zip_code, city_id, people_id) VALUES ('Rua Luis Inácio', 24, 'casa B', 'Mandacaru ', '87.000-127', 2, 7);

INSERT INTO tb_uom (description, acronym) VALUES ('Kilograma', 'kg');
INSERT INTO tb_uom (description, acronym) VALUES ('Metro', 'm');
INSERT INTO tb_uom (description, acronym) VALUES ('Polegada', 'pol');

INSERT INTO tb_tax_classification (description, number) VALUES ('Sucata de alumínio', '7602000');
INSERT INTO tb_tax_classification (description, number) VALUES ('Tarugo de alumínio', '7604000');

INSERT INTO tb_product_group (description) VALUES ('Sucata de alumínio');
INSERT INTO tb_product_group (description) VALUES ('Produto acabado');

INSERT INTO tb_material (description, uom_id, taxclass_id, prod_group_id) VALUES ('Perfil de processo', 1, 1, 1);
INSERT INTO tb_material (description, uom_id, taxclass_id, prod_group_id) VALUES ('Perfil de natural', 1, 1, 1);
INSERT INTO tb_material (description, uom_id, taxclass_id, prod_group_id) VALUES ('Perfil de pintado', 1, 1, 1);
INSERT INTO tb_material (description, uom_id, taxclass_id, prod_group_id) VALUES ('Perfil de misto', 1, 1, 1);
INSERT INTO tb_material (description, uom_id, taxclass_id, prod_group_id) VALUES ('Perfil de anodizado', 1, 1, 1);
INSERT INTO tb_material (description, uom_id, taxclass_id, prod_group_id) VALUES ('Perfil picotado', 1, 1, 1);
INSERT INTO tb_material (description, uom_id, taxclass_id, prod_group_id) VALUES ('Cabo de alumínio prensado', 1, 1, 1);

INSERT INTO tb_product (description, alloy, inch, length, uom_id, taxclass_id, prod_group_id) VALUES ('Tarugo de alumínio', '6060', 4, 6, 1, 2, 2);
INSERT INTO tb_product (description, alloy, inch, length, uom_id, taxclass_id, prod_group_id) VALUES ('Tarugo de alumínio', '6063', 4, 6, 1, 2, 2);
INSERT INTO tb_product (description, alloy, inch, length, uom_id, taxclass_id, prod_group_id) VALUES ('Tarugo de alumínio', '6060', 5, 6, 1, 2, 2);
INSERT INTO tb_product (description, alloy, inch, length, uom_id, taxclass_id, prod_group_id) VALUES ('Tarugo de alumínio', '6063', 5, 6, 1, 2, 2);
INSERT INTO tb_product (description, alloy, inch, length, uom_id, taxclass_id, prod_group_id) VALUES ('Tarugo de alumínio', '6060', 6, 6, 1, 2, 2);
INSERT INTO tb_product (description, alloy, inch, length, uom_id, taxclass_id, prod_group_id) VALUES ('Tarugo de alumínio', '6063', 6, 6, 1, 2, 2);
INSERT INTO tb_product (description, alloy, inch, length, uom_id, taxclass_id, prod_group_id) VALUES ('Tarugo de alumínio', '6060', 7, 6, 1, 2, 2);
INSERT INTO tb_product (description, alloy, inch, length, uom_id, taxclass_id, prod_group_id) VALUES ('Tarugo de alumínio', '6063', 7, 6, 1, 2, 2);
