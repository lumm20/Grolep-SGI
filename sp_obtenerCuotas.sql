-- -----------------------------------------------------------
delimiter $$
drop procedure if exists sp_monto_total_colegiaturas;
create procedure sp_monto_total_colegiaturas(in matricula varchar(15), in ciclo varchar(5),out monto_total double)
begin
declare meses int;
	set @fecha_actual = curdate();
	set @fecha_inicio = (select c.fecha_inicio from ciclos_escolares c where @fecha_actual between fecha_inicio and fecha_fin);

    if((select fecha_inicio from ciclos_escolares where id = ciclo) != @fecha_inicio) then
		set meses = 11;
	else
		set @primer_dia_mes_inicio = DATE_FORMAT(@fecha_inicio, '%Y-%m-01');
		set @primer_dia_mes_actual = DATE_FORMAT(@fecha_actual, '%Y-%m-01');
		set meses = (select timestampdiff(MONTH, @primer_dia_mes_inicio, @primer_dia_mes_actual) + 1);
    end if;
    set monto_total = meses * (select distinct c.monto_base from cuotas c where c.concepto = 'COLEGIATURA' and matricula_alumno = matricula and ciclo_escolar =ciclo);
end$$
delimiter ;
-- -----------------------------------------------------------
delimiter $$
drop procedure if exists sp_obtener_cuotas_alumnos;
create procedure sp_obtener_cuotas_alumno(in matricula varchar(20), in id_ciclo varchar(10))
begin
	call sp_monto_total_colegiaturas(matricula,id_ciclo,@monto_total);
	select 
		c.id, 
		c.matricula_alumno, 
		c.concepto,
		c.ciclo_escolar, 
		c.monto_base,
		if (c.concepto = 'COLEGIATURA',(sum(cm.adeudo)),(select c.monto_base-coalesce(sum(dp.monto_pagado),0)
        from detalles_pagos dp where dp.id_cuota = c.id)) as adeudo 
	from cuotas c
	left join cuotas_mensuales cm on c.id = cm.id
	where c.matricula_alumno = matricula and c.ciclo_escolar = id_ciclo
	group by c.concepto, c.matricula_alumno;
end$$
delimiter ;