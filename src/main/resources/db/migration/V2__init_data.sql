INSERT
IGNORE INTO permissions (id, resource, action)
VALUES (1, 'ROLE', 'CREATE'),
       (2, 'ROLE', 'READ'),
       (3, 'ROLE', 'UPDATE'),
       (4, 'ROLE', 'DELETE'),
       (5, 'USER', 'CREATE'),
       (6, 'USER', 'READ'),
       (7, 'USER', 'UPDATE'),
       (8, 'USER', 'DELETE'),
       (9, 'POST', 'CREATE'),
       (10, 'POST', 'READ'),
       (11, 'POST', 'UPDATE'),
       (12, 'POST', 'DELETE'),
       (13, 'COMMENT', 'CREATE'),
       (14, 'COMMENT', 'READ'),
       (15, 'COMMENT', 'UPDATE'),
       (16, 'COMMENT', 'DELETE');

INSERT
IGNORE INTO roles (id, name)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER');

INSERT
IGNORE INTO role_permission (role_id, permission_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 9),
       (1, 10),
       (1, 11),
       (1, 12),
       (1, 13),
       (1, 14),
       (1, 15),
       (1, 16);

INSERT
IGNORE INTO users (id, first_name, last_name, email, password, enabled, account_locked)
VALUES (1, 'Admin', 'Admin', 'admin@gmail.com', '$2a$10$j3xWT2bic/.W2kA2yJS1XepBnFYFAy7NBZteVqr5BbzwmGn38FOO6', true, false); -- pwd => password

INSERT
IGNORE INTO role_user (user_id, role_id)
VALUES (1, 1);
