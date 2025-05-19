package reportes;

import mx.itson.sgi.dto.PagoDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GeneradorReportes {

    public void generarReportes(List<PagoDTO> pagos, String destino) throws FileNotFoundException, JRException {
        List<ReporteDTO> entradasReporte = pagos.stream().map(GeneradorReportes::convertirPago).toList();

        File archivo = ResourceUtils.getFile("classpath:ReportePagos.jrxml");
        System.out.println(archivo.getAbsolutePath());
        JasperReport reporte = JasperCompileManager.compileReport(archivo.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(entradasReporte);
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("createdBy", "GROLEP");
        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, dataSource);

        JasperExportManager.exportReportToPdfFile(jasperPrint, destino + "\\employees.pdf");
    }

    private static ReporteDTO convertirPago(PagoDTO pago) {
        ReporteDTO reporte = new ReporteDTO();
        reporte.setMatricula(pago.getAlumno().getMatricula());
        reporte.setFolio(pago.getFolio());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd / MM / yyyy");
        reporte.setFecha(pago.getFecha().format(formatter));
        reporte.setMetodoPago(pago.getMetodoPago());
        reporte.setNombre(pago.getAlumno().getNombre());
        reporte.setMontoTotal(pago.getMontoTotal());
        reporte.setTipoDescuento((pago.getTipoDescuento() != null && !pago.getTipoDescuento().isEmpty())?
                pago.getTipoDescuento() : "N/A");
        reporte.setMontoDescuento((pago.getMontoDescuento() != null)? pago.getMontoDescuento(): 0.0);

        return reporte;
    }
}
