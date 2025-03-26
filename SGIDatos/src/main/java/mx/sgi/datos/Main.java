package mx.sgi.datos;

import mx.sgi.datos.conexiones.Conexion;
import mx.sgi.datos.entidades.Alumno;
import mx.sgi.datos.entidades.CicloEscolar;
import mx.sgi.datos.excepciones.RepositoryException;
import mx.sgi.datos.interfaces.IConexion;
import mx.sgi.datos.repositorios.RepositorioAlumnos;
import mx.sgi.datos.repositorios.RepositorioCiclosEscolares;

import java.util.List;


public class Main {

    public static void main(String[] args) {
        try {
            // Suponiendo que tienes una implementación de IConexion que maneja la conexión con JPA
            IConexion conexion = Conexion.getInstance(); // Asegúrate de que esta clase existe y funciona correctamente
            RepositorioCiclosEscolares repositorio = new RepositorioCiclosEscolares(conexion);

            // Obtener los últimos dos ciclos escolares
            List<CicloEscolar> ciclos = repositorio.obtenerCiclosEscolares();

            // Imprimir los ciclos escolares obtenidos
            System.out.println("Últimos dos ciclos escolares:");
            for (CicloEscolar ciclo : ciclos) {
                System.out.println("ID: " + ciclo.getId() + " | Inicio: " + ciclo.getInicio() + " | Fin: " + ciclo.getFin());
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

}
