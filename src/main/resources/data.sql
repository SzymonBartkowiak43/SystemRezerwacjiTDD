insert into
    users (email, name, password)
values
    ('user@wp.com','user', 'user');

insert into
    user_role (name, description)
values
    ('USER', 'podstawowe uprawnienia');

insert into
    user_roles (user_id, role_id)
values
    (1, 1);

INSERT INTO salon (salon_name, category, city, zip_code, street, number,user_id) VALUES
                                                                             ('Relaxing Touch', 'massage', 'Warsaw', '00-001', 'Main Street', '10',1),
                                                                             ('Glamour Nails', 'beauty', 'Krakow', '30-002', 'Flower Avenue', '15B',1);

INSERT INTO code (code, is_active, data_generated, data_consumption, user_id) VALUES
                                                                                  ('123e4567-e89b-12d3-a456-426614174000', TRUE, '2024-11-03 10:00:00', NULL, 1);