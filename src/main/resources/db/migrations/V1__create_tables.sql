-- 1. Таблица roles (Роли пользователей)
CREATE TABLE roles
(
    id   INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

-- 2. Таблица users (Пользователи)
CREATE TABLE users
(
    id            INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username      VARCHAR(50)  NOT NULL,
    email         VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    is_approved   BOOLEAN      NOT NULL DEFAULT false,
    created_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 3. Таблица user_roles (Связь пользователей и ролей, многие ко многим)
CREATE TABLE user_roles
(
    user_id INT UNSIGNED NOT NULL,
    role_id INT UNSIGNED NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

-- 4. Таблица users_info (Доп. информация о пользователе)
CREATE TABLE users_info
(
    user_id    INT UNSIGNED NOT NULL PRIMARY KEY,
    last_name  VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    surname    VARCHAR(100) NOT NULL,
    avatar_url VARCHAR(255),
    position   VARCHAR(100) NOT NULL,
    department VARCHAR(100) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- 5. Таблица news_categories (Категории новостей)
CREATE TABLE news_categories
(
    id   INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- 6. Таблица user_subscriptions (Подписки на категории новостей)
CREATE TABLE user_subscriptions
(
    user_id     INT UNSIGNED NOT NULL,
    category_id INT UNSIGNED NOT NULL,
    PRIMARY KEY (user_id, category_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES news_categories (id) ON DELETE CASCADE
);

-- 7. Таблица news (Новости)
CREATE TABLE news
(
    id           INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title        VARCHAR(255) NOT NULL,
    content      TEXT         NOT NULL,
    image        VARCHAR(255),
    author_id    INT UNSIGNED NOT NULL,
    category_id  INT UNSIGNED NOT NULL,
    status       ENUM('draft', 'published', 'archived') NOT NULL DEFAULT 'draft',
    published_at TIMESTAMP NULL DEFAULT NULL,
    scheduled_at TIMESTAMP NULL DEFAULT NULL,
    created_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP NULL DEFAULT NULL,
    FOREIGN KEY (author_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES news_categories (id) ON DELETE CASCADE
);

-- 8. Таблица comments (Комментарии)
CREATE TABLE comments
(
    id         INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    news_id    INT UNSIGNED NOT NULL,
    user_id    INT UNSIGNED NOT NULL,
    content    TEXT      NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (news_id) REFERENCES news (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- 9. Таблица likes (Лайки)
CREATE TABLE likes
(
    id         INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    news_id    INT UNSIGNED NOT NULL,
    user_id    INT UNSIGNED NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (news_id) REFERENCES news (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);


-- 11. Таблица news_tags (Связь новостей и тегов)
CREATE TABLE news_tags
(
    news_id INT UNSIGNED NOT NULL,
    tag_id  INT UNSIGNED NOT NULL,
    PRIMARY KEY (news_id, tag_id),
    FOREIGN KEY (news_id) REFERENCES news (id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags (id) ON DELETE CASCADE
);

-- 12. Таблица notifications (Уведомления)
CREATE TABLE notifications
(
    id         INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    news_id    INT UNSIGNED NULL,
    message    TEXT      NOT NULL,
    is_read    BOOLEAN   NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (news_id) REFERENCES news (id) ON DELETE SET NULL
);

-- 13. Таблица user_notifications (Связь пользователей и уведомлений)
CREATE TABLE user_notifications
(
    user_id         INT UNSIGNED NOT NULL,
    notification_id INT UNSIGNED NOT NULL,
    is_read         BOOLEAN   NOT NULL DEFAULT false,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, notification_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (notification_id) REFERENCES notifications (id) ON DELETE CASCADE
);

-- 14. Таблица news_approval (Согласование новостей)
CREATE TABLE news_approval
(
    id          INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    news_id     INT UNSIGNED NOT NULL,
    editor_id   INT UNSIGNED NOT NULL,
    status      ENUM('pending', 'approved', 'rejected') NOT NULL DEFAULT 'pending',
    comment     TEXT,
    reviewed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (news_id) REFERENCES news (id) ON DELETE CASCADE,
    FOREIGN KEY (editor_id) REFERENCES users (id) ON DELETE CASCADE
);
