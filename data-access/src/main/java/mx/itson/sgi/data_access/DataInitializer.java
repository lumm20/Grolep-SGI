package mx.itson.sgi.data_access;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.itson.sgi.data_access.entities.Alumno;
import mx.itson.sgi.data_access.entities.CicloEscolar;
import mx.itson.sgi.data_access.entities.Usuario;


@Component
public class DataInitializer {
    @Autowired
    private UserDataInitializer userInit;
    @Autowired
    private CycleDataInitializer cycleInit;
    @Autowired
    private StudentDataInitializer studentInit;
    @Autowired
    private FeeDataInitializer feeInit;
    @Autowired
    private PaymentDataInitializer paymentInit;


    protected void initData()throws Exception{
        Usuario cajero = userInit.cargarUsuarios();//guardamos a los usuarios
		List<Alumno> alumnos = studentInit.cargarAlumnos();//guardamos a los alumnos
		Alumno alumno1 = alumnos.get(0);
		Alumno alumno2 = alumnos.get(1);
		Alumno alumno3 = alumnos.get(2);
        if (alumnos != null){
            System.out.println("si hay alumnos persistidos");
        }
		List<CicloEscolar> ciclos = cycleInit.cargarCiclosEscolares();//guardamos los ciclos escolares
		feeInit.cargarCuotas(ciclos.getFirst(), ciclos.getLast(), alumno1, alumno2, alumno3);//guardamos las cuotas
		paymentInit.cargarPagos2324(cajero, alumno1, alumno2, alumno3);//guardamos los pagos del 23-24
		paymentInit.cargarPagos2425(cajero, alumno1, alumno2, alumno3);//guardamos los pagos del 24-25
    }
}
