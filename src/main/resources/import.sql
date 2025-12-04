--TB_PEOPLE
INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('LUCIANO RODRIGO DE CARVALHO', 'luciano@alunova.com', '44-98816-7652', '44-3000-3001');   
INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('GIOVANA GONÇALVES','giovana@alunova.com', '44-99737-8851','44-1000-1001');
INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('MICHELE PEREIRA DA SILVA','michele@alunova.com', '11-95479-6733','44-1002-1003');
INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('ECOALUMI ALUMINIO S/A','ecoalumi@ecoalumi.com.br', '44-9902-4372','44-3031-2323')
INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('RECIMAX LTDA','recimax@reximax.com.br', '41-14244-1222','44-1442-2222');
INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('METALURGICA REISAM INDUSTRIA E COMÉRCIO','reisam@reisam.com.br', '11-5199-6490','44-1442-2222');
INSERT INTO tb_people (name, email, cell_phone, telephone) VALUES ('NATAL DA SILVA BUENO','natal@gmail.com.br', '44-99987-8097','44-1442-2222');

--TB_CITY
INSERT INTO tb_city (name, state) VALUES ('SÃO ROQUE', 'SP');
INSERT INTO tb_city (name, state) VALUES ('MARINGÁ', 'PR');
INSERT INTO tb_city (name, state) VALUES ('PAIÇANDU', 'PR');
INSERT INTO tb_city (name, state) VALUES ('SÃO JOSÉ DOS PINHAIS', 'PR');
INSERT INTO tb_city (name, state) VALUES ('GUARAREMA', 'SP');
INSERT INTO tb_city (name, state) VALUES ('CAMPO LARGO', 'PR');

--TB_DEPARTAMENT
INSERT INTO tb_departament (name, process) VALUES ('PRODUÇÃO', 'RECEBIMENTO E CLASSIFICAÇÃO DE SUCATAS');
INSERT INTO tb_departament (name, process) VALUES ('PRODUÇÃO', 'CARREGAMENTOS DE FORNOS');
INSERT INTO tb_departament (name, process) VALUES ('PRODUÇÃO', 'VAZAMENTO');
INSERT INTO tb_departament (name, process) VALUES ('PRODUÇÃO', 'CORTE DE TARUGOS');
INSERT INTO tb_departament (name, process) VALUES ('PRODUÇÃO', 'HOMOGENIZAÇÃO');
INSERT INTO tb_departament (name, process) VALUES ('PRODUÇÃO', 'RESFRIAMENTO');
INSERT INTO tb_departament (name, process) VALUES ('PRODUÇÃO', 'EMBALAGEM');
INSERT INTO tb_departament (name, process) VALUES ('PRODUÇÃO', 'EXPEDIÇÃO');
INSERT INTO tb_departament (name, process) VALUES ('ADMINISTRAÇÃO', 'QUALIDADE');
INSERT INTO tb_departament (name, process) VALUES ('ADMINISTRAÇÃO', 'GERÊNCIA INDUSTRIAL');
INSERT INTO tb_departament (name, process) VALUES ('ADMINISTRAÇÃO', 'MANUTENÇÃO');
INSERT INTO tb_departament (name, process) VALUES ('ADMINISTRAÇÃO', 'BALANÇA RODOVIÁRIA');
INSERT INTO tb_departament (name, process) VALUES ('ADMINISTRAÇÃO', 'COMERCIAL');

--TB_EMPLOYEE
INSERT INTO tb_employee (cpf, id, departament_id) VALUES ('100.125.255-49',  1, 9);
INSERT INTO tb_employee (cpf, id, departament_id) VALUES ('520.123.255-49',  2, 11);
INSERT INTO tb_employee (cpf, id, departament_id) VALUES ('111.121.225-49',  3, 8);
INSERT INTO tb_employee (cpf, id, departament_id) VALUES ('111.000.111-49',  7, 10);

--TB_PARTNER
INSERT INTO tb_partner (cnpj, ie, supplier, client, active, id) VALUES ('00.252.457/0001-45', '114.115.225', true, true, true, 4);
INSERT INTO tb_partner (cnpj, ie, supplier, client, active, id) VALUES ('00.111.157/0001-45', '114.100.225', true, true, true, 5);
INSERT INTO tb_partner (cnpj, ie, supplier, client, active, id) VALUES ('00.121.137/0002-52', '124.101.221', true, true, true, 6);

--TB_USER
INSERT INTO tb_user (username, password, employee_id) VALUES ('luciano@alunova.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 1);
INSERT INTO tb_user (username, password, employee_id) VALUES ('michele@alunova.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 3);


--TB_ROLE
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO tb_role (authority) VALUES ('ROLE_CLIENT');


--TB_USER_ROLE
INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);


--TB_ADDRESS
INSERT INTO tb_address (street, number, complement, neighborhood, zip_code, city_id, people_id) VALUES ('RUA ARGENTINO MORESCHI', 62, 'CASA B', 'ZONA 7', '87.080-127', 2, 1);
INSERT INTO tb_address (street, number, complement, neighborhood, zip_code, city_id, people_id) VALUES ('RUA SALOMÃO MIGUEL NASSER', 731, 'AP23', 'CENTRO', '87.080-127', 3, 2);
INSERT INTO tb_address (street, number, complement, neighborhood, zip_code, city_id, people_id) VALUES ('AVENIDA CENTRAL', 1001, null, 'CENTRO', '87.000-127', 3, 3);
INSERT INTO tb_address (street, number, complement, neighborhood, zip_code, city_id, people_id) VALUES ('RUA ALUÍZIO NUNES COSTA', 540, 'BARACÃO 54A', 'DISTRITO INDUSTRIAL', '87.000-127', 2, 4);
INSERT INTO tb_address (street, number, complement, neighborhood, zip_code, city_id, people_id) VALUES ('RUA DO ALUMÍNIO', 12, null, 'DISTRITO INDUSTRIAL', '87.000-127', 2, 5);
INSERT INTO tb_address (street, number, complement, neighborhood, zip_code, city_id, people_id) VALUES ('RUA REGIONAL', 625, 'CENTRO', 'RESIDENCIAL CENTRAL', '87.000-127', 6, 6);
INSERT INTO tb_address (street, number, complement, neighborhood, zip_code, city_id, people_id) VALUES ('RUA LUIS INÁCIO', 24, 'CASA B', 'MANDACARU', '87.000-127', 2, 7);


--TB_UNIT
INSERT INTO tb_unit (description, acronym) VALUES ('KILOGRAMA', 'kg');
INSERT INTO tb_unit (description, acronym) VALUES ('METRO', 'm');
INSERT INTO tb_unit (description, acronym) VALUES ('POLEGADA', 'pol');


--TB_TAX_CLASSIFICATION
INSERT INTO tb_tax_classification (description, ncm_code) VALUES ('MATÉRIA-PRIMA', '7602000');
INSERT INTO tb_tax_classification (description, ncm_code) VALUES ('INSUMOS', '7601000');
INSERT INTO tb_tax_classification (description, ncm_code) VALUES ('TARUGO DE ALUMÍNIO', '7604000');
INSERT INTO tb_tax_classification (description, ncm_code) VALUES ('DIVERSOS', '7606000');


--TB_MATERIAL_GROUP
INSERT INTO tb_material_group (description) VALUES ('MATÉRIA-PRIMA');
INSERT INTO tb_material_group (description) VALUES ('INSUMOS');
INSERT INTO tb_material_group (description) VALUES ('PRODUTO ACABADO');
INSERT INTO tb_material_group (description) VALUES ('DIVERSOS');


--TB_MATERIAL
INSERT INTO tb_material (code, description,  type, unit_id, tax_class_id, material_group_id) VALUES (1001, 'PERFIL DE PROCESSO', 'SCRAP', 1, 1, 1);
INSERT INTO tb_material (code, description,  type, unit_id, tax_class_id, material_group_id) VALUES (1002, 'PERFIL NATURAL',  'SCRAP', 1, 1, 1);
INSERT INTO tb_material (code, description,  type, unit_id, tax_class_id, material_group_id) VALUES (1003, 'PERFIL PINTADO', 'SCRAP', 1, 1, 1);
INSERT INTO tb_material (code, description,  type, unit_id, tax_class_id, material_group_id) VALUES (2001, 'GLP - GAS LIQUEFEITO',  'SUPPLIES', 1, 2, 2);
INSERT INTO tb_material (code, description,  type, unit_id, tax_class_id, material_group_id) VALUES (2002, 'FILTRO - CAIXA FILTRO', 'SUPPLIES', 1, 2, 2);
INSERT INTO tb_material (code, description,  type, unit_id, tax_class_id, material_group_id) VALUES (3001, 'TARUGO DE ALUMINIO', 'FINISHED_PRODUCTS', 1, 3, 3);
INSERT INTO tb_material (code, description,  type, unit_id, tax_class_id, material_group_id) VALUES (3002, 'TARUGO DE ALUMINIO',  'FINISHED_PRODUCTS', 1, 3, 3);
INSERT INTO tb_material (code, description,  type, unit_id, tax_class_id, material_group_id) VALUES (3003, 'TARUGO DE ALUMINIO',  'FINISHED_PRODUCTS', 1, 3, 3);

--TB_TICKET
INSERT INTO tb_ticket (num_ticket, date_ticket, number_plate, net_weight) VALUES (34950,'2022-07-25', 'ABC-1245', 15000);
INSERT INTO tb_ticket (num_ticket, date_ticket, number_plate, net_weight) VALUES (36425,'2025-11-07', 'ALU-2025', 17475);

--TB_RECEIPT
INSERT INTO tb_receipt (id) VALUES (1);

--TB_RECEIPT_ITEM
--INSERT INTO tb_receipt_item (receipt_id, item_sequence, partner_id, material_id, document_number, quantity, price, total_value, observation, type_receipt, type_costs) VALUES (1, 1, 4, 1, '125.147', 10000.00, 1.00, 10000.00, 'Material para seleção', 'PURCHASE', 'DIRECT_COSTS');

--TB_DISPATCH
INSERT INTO tb_dispatch (id) VALUES (2);

--TB_DISPATCH_ITEM
INSERT INTO tb_dispatch_item (dispatch_id, item_sequence, partner_id, material_id, document_number, type_dispatch, alloy, alloy_pol, alloy_footage, quantity, price, total_value, observation) VALUES (2, 1, 4, 6, '174.254', 'JOB_RETURN_TO_CUSTOMER', 'AL6060', 'POLEGADA_6', 'METRAGEM_6', 6124.96, 3, 18374.88, 'MO/OUT/25 ECO/MATRIZ');
INSERT INTO tb_dispatch_item (dispatch_id, item_sequence, partner_id, material_id, document_number, type_dispatch, alloy, alloy_pol, alloy_footage, quantity, price, total_value, observation) VALUES (2, 2, 4, 6, '174.254', 'JOB_RETURN_TO_CUSTOMER', 'AL6060', 'POLEGADA_6', 'METRAGEM_6', 11350.04, 3, 34050.12, 'MO/OUT/25 ECO/FILIAL');
