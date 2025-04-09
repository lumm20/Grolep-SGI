INSERT INTO Usuarios ( nombre, contrasena, rol, correo) VALUES ('juan', 'admin123', 'admin', 'admin@escuela.edu');
INSERT INTO Usuarios ( nombre, contrasena, rol, correo) VALUES ('jose', 'sec456', 'cajero', 'cajero@escuela.edu');
INSERT INTO Usuarios ( nombre, contrasena, rol, correo) VALUES ('maria', 'dir789', 'admin', 'directora@escuela.edu');

INSERT INTO alumnos (matricula, nombre,  tipo_beca, telefono_padre) VALUES ('A20220001', 'Carlos Rodríguez Vega', 'NINGUNA', '5551234567');
INSERT INTO alumnos (matricula, nombre, tipo_beca, telefono_padre) VALUES('A20220002', 'Ana Martínez Soto', 'SEC', '5559876543');
INSERT INTO alumnos (matricula, nombre, tipo_beca, telefono_padre) VALUES('A20220003', 'Miguel López Jiménez', 'DEPORTIVA', '5552468013');


-- INSERT INTO ciclos_escolares (id, fecha_inicio, fecha_fin) VALUES ('23-24', '2023-08-15', '2024-07-15');
INSERT INTO ciclos_escolares (id, fecha_inicio, fecha_fin) VALUES ('24-25', '2024-08-15', '2025-07-15');

INSERT INTO cuotas (monto_base, ciclo_escolar, matricula_alumno, concepto) VALUES (2500.00, '24-25', 'A20220001', 'INSCRIPCION');
INSERT INTO cuotas (monto_base, ciclo_escolar, matricula_alumno, concepto) VALUES (1800.00, '24-25', 'A20220001', 'COLEGIATURA');
INSERT INTO cuotas (monto_base, ciclo_escolar, matricula_alumno, concepto) VALUES (2000,'24-25','A20220001','UNIFORMES');
INSERT INTO cuotas (monto_base, ciclo_escolar, matricula_alumno, concepto) VALUES (400,'24-25','A20220001','LIBROS');
INSERT INTO cuotas (monto_base, ciclo_escolar, matricula_alumno, concepto) VALUES (400,'24-25','A20220002','LIBROS');
INSERT INTO cuotas (monto_base, ciclo_escolar, matricula_alumno, concepto) VALUES (2000.00, '24-25', 'A20220002', 'UNIFORMES');
INSERT INTO cuotas (monto_base, ciclo_escolar, matricula_alumno, concepto) VALUES (1350.00, '24-25', 'A20220002', 'COLEGIATURA');
INSERT INTO cuotas (monto_base, ciclo_escolar, matricula_alumno, concepto) VALUES (400.00, '24-25', 'A20220003', 'LIBROS');
INSERT INTO cuotas (monto_base, ciclo_escolar, matricula_alumno, concepto) VALUES (1500.00, '24-25', 'A20220003', 'COLEGIATURA');
INSERT INTO cuotas (monto_base, ciclo_escolar, matricula_alumno, concepto) VALUES (2500,'24-25','A20220003','INSCRIPCION');

-- PAGOS DEL CICLO ESCOLAR 2023-2024

-- A20220001 - INSCRIPCIÓN
INSERT INTO pagos VALUES ('P202300004', '2023-08-15 09:15:30', 2300.00, 1, 'A20220001', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (2300.00, 1, 'P202300004');

-- A20220001 - COLEGIATURA AGOSTO 2023
INSERT INTO pagos VALUES ('P202300006', '2023-08-20 10:00:00', 1600.00, 1, 'A20220001', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1600.00, 2, 'P202300006');

-- A20220001 - UNIFORMES + LIBROS
INSERT INTO pagos VALUES ('P202300003', '2023-08-13 10:15:00', 2350.00, 1, 'A20220001', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (2000.00, 3, 'P202300003');
INSERT INTO detalles_pagos VALUES (350.00, 4, 'P202300003');

-- A20220001 - COLEGIATURA SEPTIEMBRE
INSERT INTO pagos VALUES ('P202300008', '2023-09-10 09:50:00', 1600.00, 1, 'A20220001', 'TRANSFERENCIA', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1600.00, 2, 'P202300008');

-- A20220001 - COLEGIATURA OCTUBRE + NOVIEMBRE (adeudo)
INSERT INTO pagos VALUES ('P202300010', '2023-11-15 08:30:00', 3200.00, 1, 'A20220001', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (3200.00, 2, 'P202300010'); -- 2 x 1600.00

-- A20220001 - COLEGIATURA DICIEMBRE
INSERT INTO pagos VALUES ('P202300012', '2023-12-05 12:00:00', 1600.00, 1, 'A20220001', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1600.00, 2, 'P202300012');

-- A20220001 - COLEGIATURA ENERO Y FEBRERO (adeudo enero)
INSERT INTO pagos VALUES ('P202400002', '2024-02-10 10:10:00', 3200.00, 1, 'A20220001', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (3200.00, 2, 'P202400002'); -- 2 x 1600.00

-- A20220001 - COLEGIATURA MARZO
INSERT INTO pagos VALUES ('P202400003', '2024-03-08 11:20:00', 1600.00, 1, 'A20220001', 'TRANSFERENCIA', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1600.00, 2, 'P202400003');

-- A20220001 - COLEGIATURA ABRIL + MAYO 
INSERT INTO pagos VALUES ('P202400007', '2024-05-30 08:45:00', 3200.00, 1, 'A20220001', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (3200.00, 2, 'P202400007'); -- 2 x 1600.00

-- A20220001 - COLEGIATURA JUNIO
INSERT INTO pagos VALUES ('P202400009', '2024-06-05 10:30:00', 1600.00, 1, 'A20220001', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1600.00, 2, 'P202400009');

----------------------------------------------
-- A20220002 - LIBROS + UNIFORMES
INSERT INTO pagos VALUES ('P202300002', '2023-08-10 11:00:00', 2350.00, 1, 'A20220002', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (2000.00, 6, 'P202300002');
INSERT INTO detalles_pagos VALUES (350.00, 5, 'P202300002');

-- A20220002 - COLEGIATURAS con adeudos (verificado previamente)
INSERT INTO pagos VALUES ('P202300007', '2023-08-20 11:00:00', 1200.00, 1, 'A20220002', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1200.00, 7, 'P202300007'); -- agosto

INSERT INTO pagos VALUES ('P202300009', '2023-11-05 09:20:00', 3600.00, 1, 'A20220002', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (3600.00, 7, 'P202300009'); -- 3 x 1200.00 (septiembre, octubre y noviembre)

INSERT INTO pagos VALUES ('P202400001', '2024-01-15 10:40:00', 2400.00, 1, 'A20220002', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (2400.00, 7, 'P202400001'); -- 2 x 1200.00 (diciembre y enero)

INSERT INTO pagos VALUES ('P202400004', '2024-03-15 09:00:00', 2400.00, 1, 'A20220002', 'TRANSFERENCIA', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (2400.00, 7, 'P202400004'); -- 2 x 1200.00 (febrero y marzo)

INSERT INTO pagos VALUES ('P202400006', '2024-04-15 11:00:00', 1200.00, 1, 'A20220002', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1200.00, 7, 'P202400006'); -- abril

INSERT INTO pagos VALUES ('P202400010', '2024-06-07 10:30:00', 2400.00, 1, 'A20220002', 'TRANSFERENCIA', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (2400.00, 7, 'P202400010'); -- 2 x 1200.00 (mayo y junio)

--------------------------------------------------- 
-- A20220003 - LIBROS + EVENTOS + INSCRIPCIÓN
INSERT INTO pagos VALUES ('P202300001', '2023-08-05 10:00:00', 3450.00, 1, 'A20220003', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (2300.00, 11, 'P202300001'); -- inscripción
INSERT INTO detalles_pagos VALUES (800.00, 10, 'P202300001'); -- eventos
INSERT INTO detalles_pagos VALUES (350.00, 8, 'P202300001'); -- libros

-- A20220003 - COLEGIATURAS
INSERT INTO pagos VALUES ('P202300005', '2023-08-18 09:30:00', 1300.00, 1, 'A20220003', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1300.00, 9, 'P202300005'); -- agosto

INSERT INTO pagos VALUES ('P202300009', '2023-09-18 09:30:00', 1300.00, 1, 'A20220003', 'TRANSFERENCIA', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1300.00, 9, 'P202300009'); -- septiembre

INSERT INTO pagos VALUES ('P202300011', '2023-12-01 11:00:00', 3900.00, 1, 'A20220003', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (3900.00, 9, 'P202300011'); -- 2 x 1300.00 (octubre, noviembre y diciembre)

INSERT INTO pagos VALUES ('P202400005', '2024-04-10 09:15:00', 5200.00, 1, 'A20220003', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (5200.00, 9, 'P202400005'); -- 4 x 1300.00 (enero, febrero, marzo y abril)

INSERT INTO pagos VALUES ('P202400008', '2024-06-02 10:20:00', 2600.00, 1, 'A20220003', 'TRANSFERENCIA', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (2600.00, 9, 'P202400008'); -- 2 x 1300.00 (mayo y junio)

-----------------------------------------------------
-----------------------------------------------------
-- Pagos del ciclo 24-25
-- A20220001 

-- Pago único por INSCRIPCION, UNIFORMES, LIBROS
INSERT INTO pagos VALUES ('P202400011', '2024-08-01 10:00:00', 4900.00, 1, 'A20220001', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (2500.00, 12, 'P202400011'); -- INSCRIPCION
INSERT INTO detalles_pagos VALUES (2000.00, 14, 'P202400011'); -- UNIFORMES
INSERT INTO detalles_pagos VALUES (400.00, 15, 'P202400011'); -- LIBROS

-- Pagos mensuales de COLEGIATURA (ID 13, monto 1800.00)
-- AGOSTO
INSERT INTO pagos VALUES ('P202400013', '2024-08-15 10:00:00', 1800.00, 1, 'A20220001', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1800.00, 13, 'P202400013');

-- SEPTIEMBRE
INSERT INTO pagos VALUES ('P202400015', '2024-09-15 10:00:00', 1800.00, 1, 'A20220001', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1800.00, 13, 'P202400015');

-- OCTUBRE
INSERT INTO pagos VALUES ('P202400017', '2024-10-15 9:00:00', 1800.00, 1, 'A20220001', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1800.00, 13, 'P202400017');

-- NOVIEMBRE
INSERT INTO pagos VALUES ('P202400019', '2024-11-15 10:00:00', 1800.00, 1, 'A20220001', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1800.00, 13, 'P202400019');

-- DICIEMBRE
INSERT INTO pagos VALUES ('P202400024', '2024-12-16 10:00:00', 1800.00, 1, 'A20220001', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1800.00, 13, 'P202400024');

-- ENERO
INSERT INTO pagos VALUES ('P202500002', '2025-01-15 10:00:00', 1800.00, 1, 'A20220001', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1800.00, 13, 'P202500002');

-- FEBRERO y MARZO: NO SE PAGAN (generan adeudo)

-- ABRIL: Pago de 2 adeudos (FEB + MAR) + ABRIL = 3 x 1800
INSERT INTO pagos VALUES ('P202500008', '2025-04-2 10:00:00', 5400.00, 1, 'A20220001', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1800.00, 13, 'P202500008'); -- FEB
INSERT INTO detalles_pagos VALUES (1800.00, 13, 'P202500008'); -- MAR
INSERT INTO detalles_pagos VALUES (1800.00, 13, 'P202500008'); -- ABR

--------------------------------------------------------

-- A20220003 - Pagos especiales
-- Inscripción, uniformes, libros
INSERT INTO pagos VALUES ('P202400012', '2024-08-03 10:00:00', 5400.00, 1, 'A20220003', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (2500.00, 21, 'P202400012'); -- INSCRIPCION
INSERT INTO detalles_pagos VALUES (400.00, 19, 'P202400012'); -- LIBROS
-- No tiene UNIFORMES en 24-25

-- COLEGIATURA id 20, $1500
-- SEPTIEMBRE: NO PAGA

-- OCTUBRE: Paga septiembre + octubre
INSERT INTO pagos VALUES ('P202400018', '2024-10-15 10:00:00', 3000.00, 1, 'A20220003', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (3000.00, 20, 'P202400018'); -- 2 x 1500.00

-- NOVIEMBRE
INSERT INTO pagos VALUES ('P202400020', '2024-11-15 13:00:00', 1500.00, 1, 'A20220003', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1500.00, 20, 'P202400020');

-- DICIEMBRE
INSERT INTO pagos VALUES ('P202400023', '2024-12-15 10:00:00', 1500.00, 1, 'A20220003', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1500.00, 20, 'P202400023');

-- A20220003 - COLEGIATURA (ID cuota 20, $1500.00)
-- ENERO 2025
INSERT INTO pagos VALUES ('P202500001', '2025-01-10 10:00:00', 1500.00, 1, 'A20220003', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1500.00, 20, 'P202500001');

-- FEBRERO 2025
INSERT INTO pagos VALUES ('P202500005', '2025-02-10 12:00:00', 1500.00, 1, 'A20220003', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1500.00, 20, 'P202500005');

-- MARZO 2025
INSERT INTO pagos VALUES ('P202500006', '2025-03-10 10:00:00', 1500.00, 1, 'A20220003', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1500.00, 20, 'P202500006');

-- ABRIL 2025
INSERT INTO pagos VALUES ('P202500009', '2025-04-7 10:00:00', 1500.00, 1, 'A20220003', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1500.00, 20, 'P202500009');

---------------------------------------------------------

-- A20220002 - COLEGIATURA (ID cuota 17, $1350.00)
-- AGOSTO 2024
INSERT INTO pagos VALUES ('P202400017', '2024-08-10 10:00:00', 1350.00, 1, 'A20220002', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1350.00, 17, 'P202400017');

-- SEPTIEMBRE 2024
INSERT INTO pagos VALUES ('P202400014', '2024-09-10 10:00:00', 1350.00, 1, 'A20220002', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1350.00, 17, 'P202400014');

-- OCTUBRE 2024
INSERT INTO pagos VALUES ('P202400016', '2024-10-10 10:00:00', 1350.00, 1, 'A20220002', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1350.00, 17, 'P202400016');

-- NOVIEMBRE 2024
INSERT INTO pagos VALUES ('P202400021', '2024-11-18 10:00:00', 1350.00, 1, 'A20220002', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1350.00, 17, 'P202400021');

-- DICIEMBRE 2024
INSERT INTO pagos VALUES ('P202400022', '2024-12-10 10:00:00', 1350.00, 1, 'A20220002', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1350.00, 17, 'P202400022');

-- ENERO 2025
INSERT INTO pagos VALUES ('P202500003', '2025-01-16 10:00:00', 1350.00, 1, 'A20220002', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1350.00, 17, 'P202500003');

-- FEBRERO 2025
INSERT INTO pagos VALUES ('P202500004', '2025-02-10 10:00:00', 1350.00, 1, 'A20220002', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1350.00, 17, 'P202500004');

-- MARZO 2025
INSERT INTO pagos VALUES ('P202500007', '2025-03-20 10:00:00', 1350.00, 1, 'A20220002', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1350.00, 17, 'P202500007');

-- ABRIL 2025
INSERT INTO pagos VALUES ('P202500010', '2025-04-9 10:00:00', 1350.00, 1, 'A20220002', 'EFECTIVO', 0.0, 'No aplica');
INSERT INTO detalles_pagos VALUES (1350.00, 17, 'P202500010');
