let webSocket;

document.addEventListener("DOMContentLoaded", function(){
    connect();
})

function connect() {

    // if (document.querySelector('#user')) {
        webSocket = new WebSocket("ws://localhost:7070/shoppingCart");

        webSocket.onopen = function () {
            console.log("conectado a web socket");
            if (document.querySelector("#myChart")) {
                webSocket.send(JSON.stringify({
                    type:"InitProductsSold"
                }));
            }
            if (document.querySelector("#user")) {
                webSocket.send(JSON.stringify({
                    type:"usuarioLogueado"
                }));
            }
        }

        webSocket.onclose = function () {
            console.log("desconectado")
        }

        webSocket.addEventListener("message", (event) => {
            var data = JSON.parse(event.data);
            if (data.type == "cantidadUsuarios") {
                updateCount(data.cantidad);
            } else if (data.type == "getProductsSold") {
                productsSold(data.products);
            } else if (data.type == "deleteComment") {
                const comment = document.getElementById(data.idComment);
                comment.style.display = "none";
                console.log(data.idComment);
            }
        });
    // }
}

function updateCount(msg) {
    document.getElementById("quantity").innerHTML = msg;
}

function productsSold(msg) {
    var valor = [];
    var label = [];
    var colores = [];

    if (myChart != null) {
        Object.keys(msg).forEach(key => {
            console.log(key, msg[key]);
            valor.push(msg[key]);
            label.push(key);
            colores.push('#'+Math.floor(Math.random()*16777215).toString(16));
        });
        myChart.data.datasets[0].data = valor;
        myChart.data.labels = label;
        myChart.data.datasets[0].backgroundColor = colores;
        myChart.update();
    }
}