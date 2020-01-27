const A = 222;
const R = 80;

var pointsHistoryJson = "";

$(document).ready(function() {
    $("#my-canvas").on("click",function (event) {
        let h = document.getElementById("my-canvas").offsetHeight;
        let w = document.getElementById("my-canvas").offsetWidth;
        let x = event.pageX - this.offsetLeft;
        let y = event.pageY - this.offsetTop;
        let r = document.getElementById("buttons:rJS").value;

        let x_server = getServerX(x, h, r);
        let y_server = getServerY(y, h, r);
        if(y_server>-5 && y_server<5){
            document.getElementById("buttons:xJS").value = x_server;
            document.getElementById("buttons:Y").value = y_server;
            document.getElementById("go").click();
        }else{
            let output = document.getElementById("invalid_data");
            output.innerHTML = "Y должен быть в диапазоне (-5;5)";
        }
    });
});

function getServerX(x, a, r){
    //let x_server = (A/a)*((x-0.5*a)/(R/r));
    let x_server = ((x-0.5*a)/(R/r));
    x_server = x_server.toFixed(4);
    //console.log("x-server: " + x_server);
    return x_server;
}
function getServerY(y, a, r){
    let y_server = -(A/a)*((y-0.5*a)/(R/r));
    y_server = y_server.toFixed(4);
    //console.log("y_server: " + y_server);
    return y_server;
}

function getClientX(x, a, r){
    let x_client = ((x* (R/r)+0.5 * A));
    x_client = x_client.toFixed(4);
    //console.log("x_client: " + x_client);
    return x_client;
}
function getClientY(y, a, r){
    let y_client = -((y* (R/r)-0.5 * A));
    y_client = y_client.toFixed(4);
    //console.log("y_client: " + y_client);
    return y_client;
}

function getHistory() {
    let y_check = document.getElementById("buttons:Y").value;
    y_check=y_check.replace(",",".");
    console.log((/^[+-]?\d+(\.\d+)?$/.test(y_check))+"________"+y_check);
    if(y_check.match(/^[+-]?\d+(\.\d+)?$/) && y_check<5 && y_check >-5){
        document.getElementById('xyi').innerHTML = "";
        let data = document.getElementById("h-rez").value;
        console.log(data);
        JSON.parse(data).forEach((p)=>{
            let mdiv = document.createElement('div');
            let subdiv = document.createElement('div');
            mdiv.className = 'result_h';
            mdiv.textContent = "X= " + p.x + ", Y= " + p.y+ ", R=" + p.r +"\n";
            subdiv.textContent = "Результат - " + (p.isIn ? "Входит" : "Не входит") + "\n";

            mdiv.appendChild(subdiv);
            $('#xyi').prepend(mdiv);
        });
        redraw();
    }else{
        //document.getElementById("buttons:Y").value = 0.0;
        //console.log(document.getElementById("h-rez").value);
        //redraw();
    }

}

function getR() {
    return document.getElementById("buttons:rJS").value;
}

function redraw(){
    let data = document.getElementById("h-rez").value;
    //let r = $("#R-select").val();
    let r = getR();
    document.getElementById("my-canvas").getContext("2d").clearRect(0, 0, 222, 222);
    JSON.parse(data).forEach((p, i)=>{
        let h = document.getElementById("my-canvas").offsetHeight;
        let xClient = getClientX(parseFloat(p.x), h, r);
        let yClient = getClientY(parseFloat(p.y), h, r);
        let canvas = document.getElementById("my-canvas");
        let context = canvas.getContext("2d");
        console.log("X= " + p.x + ", Y= " + p.y+ ", R=" + p.r +"\n");
        context.beginPath();
        if(parseFloat(getR()) != parseFloat(p.r)){
            context.strokeStyle = 'black';
            context.fillStyle = 'black';
        }else{
            if(p.isIn){
                context.strokeStyle = 'green';
                context.fillStyle = 'green';
            }else{
                context.strokeStyle = 'red';
                context.fillStyle = 'red';
            }
        }


        context.arc(xClient, yClient, 1.5, 0, 2 * Math.PI);
        context.closePath();
        context.fill();
        context.stroke();
    });
}
function bind() {
    let r = document.getElementById("buttons:rJS").value;
    let y = document.getElementById("buttons:Y").value;
    if (isNaN(Number(y)) && y<5 && y>5){

    }else{
        document.getElementById("buttons:Y").value = 0;
        console.log("New Y is " + document.getElementById("buttons:Y").value);
    }
}