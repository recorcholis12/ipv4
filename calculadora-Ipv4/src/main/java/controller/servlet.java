package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.calculadora;

import java.io.IOException;

/**
 * Servlet implementation class servlet
 */
@WebServlet("/servlet")
public class servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private calculadora calcu;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servlet() {
        super();
        calcu = new calculadora();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("ingreso al metodo get ");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // Obtener datos del formulario
        String ip = request.getParameter("ip");
        String mask = request.getParameter("mascara");

        // Validar entradas
        if (!calcu.recibirEntradas(ip, mask)) {
            request.setAttribute("error", "IP o máscara de subred no válida.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        // Procesar datos
        String claseIp = calcu.clase(ip);
        String tipoIp = calcu.esPublicaOPrivada(ip);
        String red = calcu.calcularIPRed(ip, mask);
        String broadcast = calcu.calcularIPBroadcast(ip, mask);
        String rango = calcu.rango(ip, mask);
        
        //parte de red y parte de host
        String ipBinario = calcu.ipBinario(ip);
        String maskBinario = calcu.ipBinario(mask);
        
        int numHosts = calcu.calcularHost(calcu.convertirIpABinario(mask));

        // Enviar resultados a la JSP
        request.setAttribute("ip", ip);
        request.setAttribute("mask", mask);
        request.setAttribute("claseIp", claseIp);
        request.setAttribute("tipoIp", tipoIp);
        request.setAttribute("red", red);
        request.setAttribute("broadcast", broadcast);
        request.setAttribute("rango", rango);
        request.setAttribute("numHosts", numHosts);
        request.setAttribute("maskbin", maskBinario);
        request.setAttribute("ipbin", ipBinario);
        // Redirigir a la página de resultados
        request.getRequestDispatcher("resultado.jsp").forward(request, response);
	}

}
