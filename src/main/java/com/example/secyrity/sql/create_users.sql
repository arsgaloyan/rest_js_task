insert into roles(name) values ('ROLE_ADMIN');
insert into roles(name) values ('ROLE_USER');

INSERT INTO users(age, email, last_name, password, username)
VALUES (20, 'admin@mail.ru', 'admin', '$2a$12$b7s.hqy5gH/DI613WUbX.eddL1ZpXPLKh/E20Kc3YqWLUoylpFhJi', 'admin');
INSERT INTO users(age, email, last_name, password, username)
VALUES (20, 'user@mail.ru', 'user', '$2a$12$b7s.hqy5gH/DI613WUbX.eddL1ZpXPLKh/E20Kc3YqWLUoylpFhJi', 'user');

insert into users_roles(user_id, role_id) VALUES (1, 1);
insert into users_roles(user_id, role_id) VALUES (2, 2);