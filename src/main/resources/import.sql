
INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('Luciano Rodrigo de Carvalho', 'luciano@alunova.com', '44-98816-7652', null);   
INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('Giovana Gonçalves','giovana@alunova.com', '44-99737-8851','44-1000-1001');
INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('Michele Pereira da Silva','michele@alunova.com.br', '11-95479-6733','44-1002-1003');
INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('Ecoalumi Aluminio S/A','ecoalumi@ecoalumi.com.br', '44-9902-4372','44-3031-2323')
INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('Recimax Ltda','recimax@reximax.com.br', '41-14244-1222','44-1442-2222');
INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('Metalurgica Reisam Industria e Comércio','reisam@reisam.com.br', '11-5199-6490','44-1442-2222');
INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('Natal da Silva Bueno','natal@gmail.com.br', '44-99987-8097','44-1442-2222');

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

INSERT INTO tb_partner (cnpj, ie, supplier, client, active, id) VALUES ('00.252.457/0001-45', '114.115.225', true, true, true, 4);
INSERT INTO tb_partner (cnpj, ie, supplier, client, active, id) VALUES ('00.111.157/0001-45', '114.100.225', true, true, true, 5);
INSERT INTO tb_partner (cnpj, ie, supplier, client, active, id) VALUES ('00.121.137/0002-52', '124.101.221', true, true, true, 6);


INSERT INTO tb_user (email, password, employee_id) VALUES ('luciano@alunova.com','$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 1);
INSERT INTO tb_user (email, password, employee_id) VALUES ('michele@alunova.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 3);

INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO tb_role (authority) VALUES ('ROLE_CLIENT');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);

INSERT INTO tb_address (street, number_address, complement, neighborhood, zip_code, city_id, people_id) VALUES ('Rua Argentino Moreschi', 62, 'Casa B', 'Residencial Moreschi', '87.080-127', 2, 1);
INSERT INTO tb_address (street, number_address, complement, neighborhood, zip_code, city_id, people_id) VALUES ('Rua Salomão Miguel Nasser', 731, 'AP23', 'Centro', '87.080-127', 3, 2);
INSERT INTO tb_address (street, number_address, complement, neighborhood, zip_code, city_id, people_id) VALUES ('Avenida Central', 1001, null, 'Centro', '87.000-127', 3, 3);
INSERT INTO tb_address (street, number_address, complement, neighborhood, zip_code, city_id, people_id) VALUES ('Rua Aluízio Nunes Costa', 540, 'Barracão 54A', 'Distrito Industrial', '87.000-127', 2, 4);
INSERT INTO tb_address (street, number_address, complement, neighborhood, zip_code, city_id, people_id) VALUES ('Rua do Alumínio', 12, null, 'Distrito Industrial', '87.000-127', 2, 5);
INSERT INTO tb_address (street, number_address, complement, neighborhood, zip_code, city_id, people_id) VALUES ('Rua Regional', 625, 'Centro', 'Residencial Central', '87.000-127', 6, 6);
INSERT INTO tb_address (street, number_address, complement, neighborhood, zip_code, city_id, people_id) VALUES ('Rua Luis Inácio', 24, 'casa B', 'Mandacaru', '87.000-127', 2, 7);

INSERT INTO tb_uom (description, acronym) VALUES ('Kilograma', 'kg');
INSERT INTO tb_uom (description, acronym) VALUES ('Metro', 'm');
INSERT INTO tb_uom (description, acronym) VALUES ('Polegada', 'pol');

INSERT INTO tb_tax_classification (description, number) VALUES ('Sucata de alumínio', '7602000');
INSERT INTO tb_tax_classification (description, number) VALUES ('Tarugo de alumínio', '7604000');

INSERT INTO tb_product_group (description) VALUES ('Sucata de alumínio');
INSERT INTO tb_product_group (description) VALUES ('Produto acabado');

INSERT INTO tb_material (description, uom_id, tax_class_id, prod_group_id) VALUES ('Perfil de Processo', 1, 1, 1);
INSERT INTO tb_material (description, uom_id, tax_class_id, prod_group_id) VALUES ('Perfil Natural', 1, 1, 1);
INSERT INTO tb_material (description, uom_id, tax_class_id, prod_group_id) VALUES ('Perfil Pintado', 1, 1, 1);
INSERT INTO tb_material (description, uom_id, tax_class_id, prod_group_id) VALUES ('Perfil Misto', 1, 1, 1);
INSERT INTO tb_material (description, uom_id, tax_class_id, prod_group_id) VALUES ('Perfil Anodizado', 1, 1, 1);
INSERT INTO tb_material (description, uom_id, tax_class_id, prod_group_id) VALUES ('Perfil Picotado', 1, 1, 1);
INSERT INTO tb_material (description, uom_id, tax_class_id, prod_group_id) VALUES ('Cabo de Alumínio Prensado', 1, 1, 1);

INSERT INTO tb_product (description, alloy, inch, length, uom_id, taxclass_id, prod_group_id) VALUES ('Tarugo de alumínio', '6060', 4, 6, 1, 2, 2);
INSERT INTO tb_product (description, alloy, inch, length, uom_id, taxclass_id, prod_group_id) VALUES ('Tarugo de alumínio', '6063', 4, 6, 1, 2, 2);
INSERT INTO tb_product (description, alloy, inch, length, uom_id, taxclass_id, prod_group_id) VALUES ('Tarugo de alumínio', '6060', 5, 6, 1, 2, 2);
INSERT INTO tb_product (description, alloy, inch, length, uom_id, taxclass_id, prod_group_id) VALUES ('Tarugo de alumínio', '6063', 5, 6, 1, 2, 2);
INSERT INTO tb_product (description, alloy, inch, length, uom_id, taxclass_id, prod_group_id) VALUES ('Tarugo de alumínio', '6060', 6, 6, 1, 2, 2);
INSERT INTO tb_product (description, alloy, inch, length, uom_id, taxclass_id, prod_group_id) VALUES ('Tarugo de alumínio', '6063', 6, 6, 1, 2, 2);
INSERT INTO tb_product (description, alloy, inch, length, uom_id, taxclass_id, prod_group_id) VALUES ('Tarugo de alumínio', '6060', 7, 6, 1, 2, 2);
INSERT INTO tb_product (description, alloy, inch, length, uom_id, taxclass_id, prod_group_id) VALUES ('Tarugo de alumínio', '6063', 7, 6, 1, 2, 2);

INSERT INTO tb_expenses (expenses, direct_costs, indirect_costs, investiment) VALUES (true, false, false, false);
INSERT INTO tb_expenses (expenses, direct_costs, indirect_costs, investiment) VALUES (false, true, false, false);
INSERT INTO tb_expenses (expenses, direct_costs, indirect_costs, investiment) VALUES (false, false, true, false);
INSERT INTO tb_expenses (expenses, direct_costs, indirect_costs, investiment) VALUES (false, false, false, true);

INSERT INTO tb_type_transaction (description) VALUES ('Compra');
INSERT INTO tb_type_transaction (description) VALUES ('Industrialização');
INSERT INTO tb_type_transaction (description) VALUES ('Retorno de Industrialização');
INSERT INTO tb_type_transaction (description) VALUES ('Arrendamento');

INSERT INTO tb_ticket (num_ticket, date_ticket, number_plate, net_weight) VALUES (34950,'2022-07-25', 'ABC-1245', 1000);
INSERT INTO tb_movement (amount_material, unit_value, total_value, metal_yield, metal_weight, slag, num_ticket_id) VALUES (200, 2.0, 400.0, 0.88, 176.0, 24.0, 34950);
INSERT INTO tb_movement (amount_material, unit_value, total_value, metal_yield, metal_weight, slag, num_ticket_id) VALUES (300, 2.0, 600.0, 0.90, 270.0, 30.0, 34950);
INSERT INTO tb_movement (amount_material, unit_value, total_value, metal_yield, metal_weight, slag, num_ticket_id) VALUES (500, 2.0, 1000.0, 0.97, 485.0, 15.0, 34950);

INSERT INTO tb_movement_material (material_id, movement_mat_id) VALUES (1, 1);
INSERT INTO tb_movement_material (material_id, movement_mat_id) VALUES (2, 2);
INSERT INTO tb_movement_material (material_id, movement_mat_id) VALUES (3, 3);

INSERT INTO tb_movement_partner (partner_id, movement_par_id) VALUES (4, 1);
INSERT INTO tb_movement_partner (partner_id, movement_par_id) VALUES (5, 2);
INSERT INTO tb_movement_partner (partner_id, movement_par_id) VALUES (6, 3);

INSERT INTO tb_movement_expense (expense_id, movement_exp_id) VALUES (2, 1);
INSERT INTO tb_movement_expense (expense_id, movement_exp_id) VALUES (2, 2);
INSERT INTO tb_movement_expense (expense_id, movement_exp_id) VALUES (2, 3);

INSERT INTO tb_movement_transaction (transaction_id, movement_tra_id) VALUES (1, 1);
INSERT INTO tb_movement_transaction (transaction_id, movement_tra_id) VALUES (2, 2);
INSERT INTO tb_movement_transaction (transaction_id, movement_tra_id) VALUES (2, 3);