<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Resultados - Calculadora de IP</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <style>
        .red { color: red; font-weight: bold; }
        .host { color: green; font-weight: bold; }
        body {
            background-color: black; 
            color: white; 
        }
        .resultado-box {
            background: rgba(0, 123, 255, 0.3); 
            border-radius: 10px;
            padding: 20px;
            border: 1px solid white;
        }
           canvas {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            z-index: 0;
        }

        .container {
            position: relative;
            z-index: 2;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
    </style>
</head>
<body>
 <canvas id="backgroundCanvas"></canvas>

        <div class="container">
            <div class="resultado-box p-4">
                <h2 class="text-center">Resultados de la Calculadora de Subred</h2>

                <% if (request.getAttribute("error") != null) { %>
                    <p class="text-danger"><%= request.getAttribute("error") %></p>
                <% } else { %>
                    <p><strong>Dirección IP:</strong> <%= request.getAttribute("ip") %></p>
                    <p><strong>Máscara de Subred:</strong> <%= request.getAttribute("mask") %></p>
                    <p><strong>Clase de IP:</strong> <%= request.getAttribute("claseIp") %></p>
                    <p><strong>Tipo de IP:</strong> <%= request.getAttribute("tipoIp") %></p>
                    <p><strong>Dirección de Red:</strong> <%= request.getAttribute("red") %></p>
                    <p><strong>Dirección de Broadcast:</strong> <%= request.getAttribute("broadcast") %></p>
                    <p><strong>Rango de IPs:</strong> <%= request.getAttribute("rango") %></p>
                    <p><strong>Número de Hosts Utilizables:</strong> <%= request.getAttribute("numHosts") %></p>

                    
                    <div>
                        <%
                            for (int i = 0; i < 32; i++) {
                                if (request.getAttribute("maskbin").toString().charAt(i) == '1') {
                        %>
                                    <span class="red"><%= request.getAttribute("ipbin").toString().charAt(i) %></span>
                        <%
                                } else {
                        %>
                                    <span class="host"><%= request.getAttribute("ipbin").toString().charAt(i) %></span>
                        <%
                                }
                                if ((i + 1) % 8 == 0 && i != 31) {
                        %>
                                    .
                        <%
                                }
                            }
                        %>
                    </div>

                    
                <% } %>
                <br>
 <style>
    .contenedor-titulos {
        display: flex;
        justify-content: center;
        gap: 20px; /* Espacio entre los títulos */
    }
    .titulo-red { color: red; font-weight: bold; }
    .titulo-host { color: green; font-weight: bold; }
</style>

<div class="contenedor-titulos">
    <h3 class="titulo-red">RED</h3>
    <h3 class="titulo-host">HOST</h3>
</div>

                
                <a href="index.jsp" class="btn btn-light">Volver</a>
            </div>
        </div>
    </div>
</div>

 <script>
        const canvas = document.getElementById("backgroundCanvas");
        const ctx = canvas.getContext("2d");

        canvas.width = window.innerWidth;
        canvas.height = window.innerHeight;

        const numbers = [];
        const numCount = 50;

        for (let i = 0; i < numCount; i++) {
            numbers.push({
                x: Math.random() * canvas.width,
                y: Math.random() * canvas.height,
                speedY: Math.random() * 2 + 1,
                text: Math.floor(Math.random() * 256) 
            });
        }

        function animate() {
            ctx.clearRect(0, 0, canvas.width, canvas.height);

            ctx.fillStyle = "rgba(0, 255, 0, 0.7)"; 
            ctx.font = "50px 'Press Start 2P'";

            numbers.forEach((num) => {
                ctx.fillText(num.text, num.x, num.y);
                num.y += num.speedY;
                if (num.y > canvas.height) {
                    num.y = 0;
                    num.x = Math.random() * canvas.width;
                    num.text = Math.floor(Math.random() * 256);
                }
            });

            requestAnimationFrame(animate);
        }

        animate();

        window.addEventListener("resize", () => {
            canvas.width = window.innerWidth;
            canvas.height = window.innerHeight;
        });
    </script>
</body>
</html>
