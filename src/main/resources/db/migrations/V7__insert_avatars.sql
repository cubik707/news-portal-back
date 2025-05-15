-- Обновляем аватары пользователей
UPDATE users_info
SET avatar_url = '/uploads/avatars/editor2_avatar.png'
WHERE user_id = 6;

UPDATE users_info
SET avatar_url = '/uploads/avatars/dev_igor_avatar.png'
WHERE user_id = 7;

UPDATE users_info
SET avatar_url = '/uploads/avatars/user2_avatar.png'
WHERE user_id = 8;

UPDATE users_info
SET avatar_url = '/uploads/avatars/user3_avatar.png'
WHERE user_id = 9;