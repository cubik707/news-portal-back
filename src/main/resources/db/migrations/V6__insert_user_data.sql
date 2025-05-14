-- Добавляем новых пользователей
INSERT INTO users (username, email, password_hash, is_approved)
VALUES ('editor2', 'editor2@wiwiwi.com', '$2a$12$t3rYbi4CPfn6F/z9BgYziuWszw1f.aDjKpIRNdCHD/TypkOYVePr6', 1),
       ('dev_igor', 'igor@wiwiwi.com', '$2a$12$o3XXWxlCh4Pn27s2HMdw6.voSxI1lerE8otPTiJNQv2Ia40/tSqKi', 1),
       ('user2', 'user2@wiwiwi.com', '$2a$12$4eeoTznurH93y/e7cP5VmuiaW58SvngNIuvP5CXow27kvjkAprdA2', 1),
       ('user3', 'user3@wiwiwi.com', '$2a$12$yy4aYV3Ee6Ao25vR8ZbNN.r/kx2TfTdfZqiMCZzZZwE4fhFWsxsyy', 1);

-- Связываем новых пользователей с ролями
INSERT INTO user_roles (user_id, role_id)
VALUES (6, 3), -- editor
       (7, 1), -- user
       (8, 1), -- user
       (9, 1); -- user

-- Добавляем информацию о новых пользователях
INSERT INTO users_info (user_id, last_name, first_name, surname, position, department)
VALUES (6, 'Смирнов', 'Алексей', 'Игоревич', 'Редактор отдела новостей', 'Media'),
       (7, 'Лебедев', 'Игорь', 'Николаевич', 'Backend Developer', 'IT'),
       (8, 'Сидорова', 'Ольга', 'Владимировна', 'Аналитик', 'Analytics'),
       (9, 'Тихонов', 'Дмитрий', 'Андреевич', 'Контент-менеджер', 'Content');
