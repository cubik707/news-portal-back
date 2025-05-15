-- Добавляем пользователей
INSERT INTO users (username, email, password_hash, is_approved)
VALUES ('admin', 'admin@wiwiwi.com', '$2a$12$xyz123', 1),
       ('editor1', 'editor@wiwiwi.com', '$2a$12$abc456', 1),
       ('user1', 'user1@wiwiwi.com', '$2a$12$def789', 1),
       ('dev_anna', 'anna@wiwiwi.com', '$2a$12$ghi012', 1);

-- Связываем пользователей с ролями
INSERT INTO user_roles (user_id, role_id)
VALUES (1, 2), -- admin
       (2, 3), -- editor
       (3, 1), -- user
       (4, 1);
-- user

-- Добавляем информацию о пользователях
INSERT INTO users_info (user_id, last_name, first_name, surname, position, department)
VALUES (1, 'Иванов', 'Иван', 'Иванович', 'CEO', 'Management'),
       (2, 'Петрова', 'Мария', 'Сергеевна', 'Главный редактор', 'Media'),
       (4, 'Кузнецова', 'Анна', 'Алексеевна', 'Senior Developer', 'IT');

-- Категории новостей
INSERT INTO news_categories (name)
VALUES ('IT News'),
       ('Company Updates'),
       ('Tech Innovations'),
       ('Events');

-- Подписки пользователей
INSERT INTO user_subscriptions (user_id, category_id)
VALUES (3, 1),
       (3, 3),
       (4, 1),
       (4, 2);

-- Новости
INSERT INTO news (title, content, author_id, category_id, status, published_at)
VALUES ('Запуск нового продукта', 'Мы рады представить наш новый AI-фреймворк...', 2, 3, 'published', NOW()),
       ('Корпоративный тимбилдинг', 'Встречайте фотоотчёт с нашего ежегодного...', 2, 4, 'published', NOW()),
       ('Обновление политики безопасности', 'Важные изменения в системе доступа...', 1, 2, 'draft', NULL);

-- Теги
INSERT INTO tags (name)
VALUES ('Programming'),
       ('Security'),
       ('AI'),
       ('HR');

-- Связь новостей с тегами
INSERT INTO news_tags (news_id, tag_id)
VALUES (1, 3),
       (3, 2);

-- Уведомления
INSERT INTO notifications (news_id, message)
VALUES (1, 'Опубликована новая статья в категории Tech Innovations'),
       (NULL, 'Система будет недоступна 15.08 с 00:00 до 03:00');

-- Связь пользователей с уведомлениями
INSERT INTO user_notifications (user_id, notification_id)
VALUES (3, 1),
       (4, 1),
       (3, 2),
       (4, 2);

-- Процесс согласования новостей
INSERT INTO news_approval (news_id, editor_id, status, comment)
VALUES (3, 2, 'approved', 'Одобрено к публикации');