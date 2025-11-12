package model;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class calculadora {
	

	
	public boolean recibirEntradas (String ip, String mask) {
		//validar ip
		String formatoIp = "^((25[0-5]|2[0-4][0-9]|1?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|1?[0-9][0-9]?)$";
		
        Pattern pattern = Pattern.compile(formatoIp);
        Matcher matcher = pattern.matcher(ip);
        
        //validar mascara
        
        String formatoMask = "^(255\\.(255|0)\\.(255|0|128|192|224|240|248|252|254|255)\\.(0|128|192|224|240|248|252|254|255))$";

        Pattern pattern2 = Pattern.compile(formatoMask);
        Matcher matcher2 = pattern2.matcher(mask);
        
        
        if (matcher.matches() && matcher2.matches()) {
            return true;
        } 
        
        return false;
        
	}

	public int calcularHost(String binario ) {
		int contador = 0;
		for (int i = 0; i < binario.length(); i++) {
			if(binario.charAt(i) == '0') {
				contador++;
			}
		}
		return (int) ((Math.pow(2, contador))-2);
	}
	

	// Convierte una dirección IP a binario
    public  String convertirIpABinario(String ip) {
        try {
            String[] octetos = ip.split("\\.");
            StringBuilder binario = new StringBuilder();

            for (String octeto : octetos) {
                int num = Integer.parseInt(octeto);
                String octetoBinario = String.format("%8s", Integer.toBinaryString(num)).replace(' ', '0');
                binario.append(octetoBinario).append(".");
            }
            return binario.substring(0, binario.length() - 1);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Error: Formato de IP inválido - " + e.getMessage());
            return null;
        }
    }

    // Convierte una IP en binario a decimal
    public  String convertirBinarioADecimal(String binario) {
        try {
            String[] octetos = binario.split("\\.");
            StringBuilder decimal = new StringBuilder();

            for (String octeto : octetos) {
                int num = Integer.parseInt(octeto, 2);
                decimal.append(num).append(".");
            }
            return decimal.substring(0, decimal.length() - 1);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Error: Formato binario inválido - " + e.getMessage());
            return null;
        }
    }

    // Calcula la IP de red haciendo la operación AND bit a bit
    public  String calcularIPRed(String ip, String mascara) {
        try {
            String[] ipBin = convertirIpABinario(ip).split("\\.");
            String[] maskBin = convertirIpABinario(mascara).split("\\.");
            StringBuilder redBinaria = new StringBuilder();

            for (int i = 0; i < 4; i++) {
                int octetoRed = Integer.parseInt(ipBin[i], 2) & Integer.parseInt(maskBin[i], 2); 
                String octetoRedBin = String.format("%8s", Integer.toBinaryString(octetoRed)).replace(' ', '0');
                redBinaria.append(octetoRedBin).append(".");
            }

            return convertirBinarioADecimal(redBinaria.substring(0, redBinaria.length() - 1)); 
        } catch (Exception e) {
            System.err.println("Error al calcular IP de red: " + e.getMessage());
            return null;
        }
    }

    // Calcula la IP de Broadcast haciendo la operación OR entre la IP de red y el complemento de la máscara
    public  String calcularIPBroadcast(String ip, String mascara) {
        try {
            String ipRed = calcularIPRed(ip, mascara);
            if (ipRed == null) return null; // Si la IP de red es nula, retornar

            String[] redBin = convertirIpABinario(ipRed).split("\\.");
            String[] maskBin = convertirIpABinario(mascara).split("\\.");
            StringBuilder broadcastBinario = new StringBuilder();

            for (int i = 0; i < 4; i++) {
                int complementoMascara = ~Integer.parseInt(maskBin[i], 2) & 0xFF; // Complemento de la máscara
                int octetoBroadcast = Integer.parseInt(redBin[i], 2) | complementoMascara; 
                String octetoBroadcastBin = String.format("%8s", Integer.toBinaryString(octetoBroadcast)).replace(' ', '0');
                broadcastBinario.append(octetoBroadcastBin).append(".");
            }

            return convertirBinarioADecimal(broadcastBinario.substring(0, broadcastBinario.length() - 1)); 
        } catch (Exception e) {
            System.err.println("Error al calcular IP de Broadcast: " + e.getMessage());
            return null;
        }
    }
	
    public String rango(String ip, String mask) {
        String[] red = calcularIPRed(ip, mask).split("\\.");
        String[] broadcast = calcularIPBroadcast(ip, mask).split("\\.");

        try {
            int primeraIP = Integer.parseInt(red[3]) + 1;
            int ultimaIP = Integer.parseInt(broadcast[3]) - 1;

            // Asegurar que los valores sean válidos
            if (primeraIP > 254) primeraIP = 254;  
            if (ultimaIP < 0) ultimaIP = 0;

            return red[0] + "." + red[1] + "." + red[2] + "." + primeraIP + 
                   " - " + 
                   broadcast[0] + "." + broadcast[1] + "." + broadcast[2] + "." + ultimaIP;
        } catch (NumberFormatException e) {
            return "Error en el cálculo del rango de IPs";
        }
    }


    public String clase (String ip) {
    	  int primerOcteto = Integer.parseInt(ip.split("\\.")[0]);
    	    if (primerOcteto >= 1 && primerOcteto <= 126) {
    	        return "Clase A";
    	    } else if (primerOcteto >= 127 && primerOcteto <= 191) {
    	        return "Clase B";
    	    } else if (primerOcteto >= 192 && primerOcteto <= 223) {
    	        return "Clase C";
    	    } else if (primerOcteto >= 224 && primerOcteto <= 239) {
    	        return "Clase D (Multicast)";
    	    } else if (primerOcteto >= 240 && primerOcteto <= 255) {
    	        return "Clase E (Experimental)";
    	    } else {
    	        return "IP fuera de rango";
    	    }
	}
	
    public String esPublicaOPrivada(String ip) {
        String[] octetos = ip.split("\\.");
        int primerOcteto = Integer.parseInt(octetos[0]);
        int segundoOcteto = Integer.parseInt(octetos[1]);

        if ((primerOcteto == 10) ||
            (primerOcteto == 172 && segundoOcteto >= 16 && segundoOcteto <= 31) ||
            (primerOcteto == 192 && segundoOcteto == 168)) {
            return "IP Privada";
        } else {
            return "IP Pública";
        }
    }

    public String ipBinario(String ip) {
        String[] octetos = ip.split("\\.");
        StringBuilder binario = new StringBuilder();
        for (String oct : octetos) {
            int num = Integer.parseInt(oct);
            binario.append(String.format("%8s", Integer.toBinaryString(num)).replace(' ', '0'));
        }
        return binario.toString();
    }
    
}
