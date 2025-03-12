INSERT INTO Usuarios ( nombre, contra, rol, correo) VALUES
('juan', 'admin123', 'admin', 'admin@escuela.edu'),
('jose', 'sec456', 'cajero', 'cajero@escuela.edu'),
('maria', 'dir789', 'admin', 'directora@escuela.edu');

INSERT INTO alumnos (matricula, nombre, apellidos, tipo_beca, telefono_padre, fecha_nacimiento)
VALUES 
('A20220001', 'Carlos', 'Rodríguez Vega', 'NINGUNA', '5551234567', '2010-05-15'),
('A20220002', 'Ana', 'Martínez Soto', 'SEC', '5559876543', '2011-03-22'),
('A20220003', 'Miguel', 'López Jiménez', 'DEPORTIVA', '5552468013', '2009-11-10');


INSERT INTO ciclos_escolares (id, fecha_inicio, fecha_fin)
VALUES 
('23-24', '2023-08-15', '2024-07-15'),
('24-25', '2024-08-15', '2025-07-15');

INSERT INTO cuotas (monto_base, ciclo_escolar, matricula_alumno, concepto)
VALUES 
(2500.00, '23-24', 'A20220001', 'INSCRIPCION'),
(1800.00, '23-24', 'A20220001', 'COLEGIATURA'),
(2000,'23-24','A20220001','UNIFORMES'),
(400,'23-24','A20220001','LIBROS'),
(400,'23-24','A20220002','LIBROS'),
(2000.00, '23-24', 'A20220002', 'UNIFORMES'),
(1350.00, '23-24', 'A20220002', 'COLEGIATURA'),
(400.00, '23-24', 'A20220003', 'LIBROS'),
(1500.00, '23-24', 'A20220003', 'COLEGIATURA'), 
(2500,'23-24','A20220003','INSCRIPCION')
;

INSERT INTO pagos (folio, fecha_hora, monto_total, id_cajero, metodo_pago)
VALUES 
('P20230001', '2023-08-10 09:15:30', 4300.00, 2, 'EFECTIVO'),
('P20230002', '2023-08-11 10:25:45', 3350.00, 2, 'TARJETA'),
('P20230003', '2023-09-05 14:30:20', 1800.00, 2,'TRANSFERENCIA');


INSERT INTO detalles_pagos ( monto_pagado, id_cuota, folio_pago)
VALUES 
(2500.00, 1, 'P20230001'),
(1800.00, 2, 'P20230001'),
(2000.00, 6, 'P20230002'),
(1350.00, 7, 'P20230002'),
(1800.00, 10, 'P20230003');