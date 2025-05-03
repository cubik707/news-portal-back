-- Обновляем аватары пользователей
UPDATE users_info
SET avatar_url = '/uploads/avatars/admin_avatar.jpg'
WHERE user_id = 1;

UPDATE users_info
SET avatar_url = '/uploads/avatars/editor_avatar.jpg'
WHERE user_id = 2;

UPDATE users_info
SET avatar_url = '/uploads/avatars/dev_avatar.jpg'
WHERE user_id = 4;

-- Добавляем изображения к новостям
UPDATE news
SET image = '/uploads/news/ai_framework.jpg'
WHERE id = 1;

UPDATE news
SET image = '/uploads/news/team_building.jpg'
WHERE id = 2;

UPDATE news
SET image = '/uploads/news/security_update.jpg'
WHERE id = 3;