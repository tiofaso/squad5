INSERT INTO tb_users (email, name, password, username) VALUES ('admin.catalisa@zup.com.br', 'Ariel de Souza', '$2a$10$GcdGtxfc/5uujNYlKeiyieYTMRJaNjSElJJzDXZSaP6U5gF9lNBO.','admin');
INSERT INTO tb_role (role) VALUES ('ROLE_ADMIN');
INSERT INTO tb_users_roles (user_id, role_id) VALUES ('1', '1');