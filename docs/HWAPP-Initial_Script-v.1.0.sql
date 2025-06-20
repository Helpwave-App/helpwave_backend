INSERT INTO role (role) VALUES 
('volunteer'),
('requester');

INSERT INTO skill (skill_desc) values
('Asistencia general'),
('Soporte tecnológico'),
('Acompañamiento emocional'),
('Lectura de documentos'),
('Uso de electrodomésticos'),
('Identificación de objetos o lugares'),
('Trámites digitales'),
('Consejos prácticos o tutoriales');

INSERT INTO level (max_request, min_request, name_level, photo_url) values
(4, 0, 'Gota Solidaria', 'levels%2Flevel1.png?alt=media&token=76c11cdb-9223-453c-9605-ac7c41a8f7e8'),
(9, 5, 'Remador Digital', 'levels%2Flevel2.png?alt=media&token=0a836a82-a40c-45e1-8b84-849b04084044'),
(14, 10, 'Ola Confiable', 'levels%2Flevel3.png?alt=media&token=57404a4b-e0af-4985-8af8-9b587125b5b5'),
(19, 15, 'Faro Solidario', 'levels%2Flevel4.png?alt=media&token=117d10c7-c26c-43d0-92a1-685e3dbd7194'),
(25, 20, 'Maestro del Oleaje', 'levels%2Flevel5.png?alt=media&token=f4532c74-a1c0-4ad8-8649-0ff24fd2c270');

INSERT INTO public."language" (name_language) values
('Español'),
('Inglés');

INSERT INTO public.type_report (type_desc) values
('Conducta inapropiada'),
('Solicitud no relacionada'),
('Falta de respeto'),
('Incumplimiento de reglas'),
('Problemas técnicos graves'),
('Desconexión sin aviso'),
('Actitud poco colaborativa'),
('Otro (especificar)');

INSERT INTO public.state_report (description_state) values
('Pendiente'),
('En Revisión'),
('Completado');