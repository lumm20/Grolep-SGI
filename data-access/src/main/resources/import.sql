INSERT INTO Usuarios ( nombre, contrasena, rol, correo) VALUES ('juan', 'admin123', 'admin', 'admin@escuela.edu');
INSERT INTO Usuarios ( nombre, contrasena, rol, correo) VALUES ('jose', 'sec456', 'cajero', 'cajero@escuela.edu');
INSERT INTO Usuarios ( nombre, contrasena, rol, correo) VALUES ('maria', 'dir789', 'admin', 'directora@escuela.edu');

INSERT INTO alumnos (matricula, nombre,  tipo_beca, telefono_padre) VALUES ('A20220001', 'Carlos Rodríguez Vega', 'NINGUNA', '5551234567');
INSERT INTO alumnos (matricula, nombre, tipo_beca, telefono_padre) VALUES('A20220002', 'Ana Martínez Soto', 'SEC', '5559876543');
INSERT INTO alumnos (matricula, nombre, tipo_beca, telefono_padre) VALUES('A20220003', 'Miguel López Jiménez', 'DEPORTIVA', '5552468013');


INSERT INTO ciclos_escolares (id, fecha_inicio, fecha_fin) VALUES ('23-24', '2023-08-15', '2024-07-15');
INSERT INTO ciclos_escolares (id, fecha_inicio, fecha_fin) VALUES ('24-25', '2024-08-15', '2025-07-15');

INSERT INTO cuotas (monto_base, ciclo_escolar, matricula_alumno, concepto) VALUES (2500.00, '23-24', 'A20220001', 'INSCRIPCION');
INSERT INTO cuotas (monto_base, ciclo_escolar, matricula_alumno, concepto) VALUES (1800.00, '23-24', 'A20220001', 'COLEGIATURA');
INSERT INTO cuotas (monto_base, ciclo_escolar, matricula_alumno, concepto) VALUES (2000,'23-24','A20220001','UNIFORMES');
INSERT INTO cuotas (monto_base, ciclo_escolar, matricula_alumno, concepto) VALUES (400,'23-24','A20220001','LIBROS');
INSERT INTO cuotas (monto_base, ciclo_escolar, matricula_alumno, concepto) VALUES (400,'23-24','A20220002','LIBROS');
INSERT INTO cuotas (monto_base, ciclo_escolar, matricula_alumno, concepto) VALUES (2000.00, '23-24', 'A20220002', 'UNIFORMES');
INSERT INTO cuotas (monto_base, ciclo_escolar, matricula_alumno, concepto) VALUES (1350.00, '23-24', 'A20220002', 'COLEGIATURA');
INSERT INTO cuotas (monto_base, ciclo_escolar, matricula_alumno, concepto) VALUES (400.00, '23-24', 'A20220003', 'LIBROS');
INSERT INTO cuotas (monto_base, ciclo_escolar, matricula_alumno, concepto) VALUES (1500.00, '23-24', 'A20220003', 'COLEGIATURA');
INSERT INTO cuotas (monto_base, ciclo_escolar, matricula_alumno, concepto) VALUES (2500,'23-24','A20220003','INSCRIPCION');

INSERT INTO pagos (folio, fecha_hora, monto_total, id_cajero,matricula_alumno, metodo_pago) VALUES ('P20230001', '2023-08-10 09:15:30', 4300.00, 2,'A20220001', 'EFECTIVO');
INSERT INTO pagos (folio, fecha_hora, monto_total, id_cajero, matricula_alumno,metodo_pago) VALUES('P20230002', '2023-08-11 10:25:45', 3350.00, 2,'A20220002', 'TARJETA');
INSERT INTO pagos (folio, fecha_hora, monto_total, id_cajero, matricula_alumno,metodo_pago) VALUES('P20230003', '2023-09-05 14:30:20', 1800.00, 2,'A20220003','TRANSFERENCIA');


INSERT INTO detalles_pagos ( monto_pagado, id_cuota, folio_pago) VALUES (2500.00, 1, 'P20230001');
INSERT INTO detalles_pagos ( monto_pagado, id_cuota, folio_pago) VALUES (1800.00, 2, 'P20230001');
INSERT INTO detalles_pagos ( monto_pagado, id_cuota, folio_pago) VALUES (2000.00, 6, 'P20230002');
INSERT INTO detalles_pagos ( monto_pagado, id_cuota, folio_pago) VALUES (1350.00, 7, 'P20230002');
INSERT INTO detalles_pagos ( monto_pagado, id_cuota, folio_pago) VALUES (1800.00, 10, 'P20230003');