<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Fondo Interactivo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <style>
        body, html {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
            background-color: black;
            color: white;
            overflow: hidden;
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

        .form-container {
            background-color: rgba(0, 0, 255, 0.3); 
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0px 0px 10px rgba(255, 255, 255, 0.2);
            backdrop-filter: blur(5px);
        }
    </style>
</head>
<body>

    <canvas id="backgroundCanvas"></canvas>

    <div class="container">
        <div class="form-container text-white">
            <h2 class="text-center">Calculadora de Subred</h2>
            <form action="servlet" method="POST">
                <div class="mb-3">
                    <label for="ip" class="form-label">Dirección IP</label>
                    <input type="text" class="form-control" id="ip" name="ip" required placeholder="192.168.1.1">
                </div>
                <div class="mb-3">
                    <label for="mascara" class="form-label">Máscara de subred</label>
                    <input type="text" class="form-control" id="mascara" name="mascara" required placeholder="255.255.255.0">
                </div>
                <button type="submit" class="btn btn-primary w-100">Calcular</button>
            </form>
        </div>
    </div>


    <div class="modal fade" id="errorModal" tabindex="-1" aria-labelledby="errorModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title text-danger" id="errorModalLabel">Error</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p style="color: black;"id="errorMessage"><%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Cerrar</button>
                </div>
            </div>
        </div>
    </div>

  
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            let error = "<%= request.getAttribute("error") %>";
            if (error && error.trim() !== "null") {
                let errorModal = new bootstrap.Modal(document.getElementById("errorModal"));
                errorModal.show();
            }
        });
    </script>


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

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
