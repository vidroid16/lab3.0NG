var submit;

function onStartPageLoaded(){
    updateClock();
    setInterval(updateClock, 12000);
}

function updateClock() {
    let curDate = new Date();
    let weekDays = ["Пн","Вт","Ср","Чт","Пт","Сб","Вс"];
    let monthes = ["Янв.","Фев.","Мар.","Апр.","Май","Июн.","Июл.","Авг.","Сен.","Окт.","Ноя.","Дек."];
    let hours = curDate.getHours().toString();
    let minutes = curDate.getMinutes().toString();
    if(hours.length === 1){
        hours = '0' + hours;
    }
    if(minutes.length === 1){
        minutes = '0' + minutes;
    }
    document.getElementById("clock-time").innerText = hours + ":" + minutes;
    document.getElementById("clock-date").innerText = weekDays[6-curDate.getDay()] + ", " + curDate.getDate() + " " + monthes[curDate.getMonth()];
}
function docLoad(){
    submit = document.getElementById("go");
    let data = document.getElementById("h-rez").value;
    document.getElementById("loadButton").click();
    console.log(data);
    if(data[0]!=="u"){
        redraw();
    }
}
function check(x, y, r) {
    var vars =[];
    if (x === undefined) vars.push("X");
    if (y === undefined || y === "") vars.push("Y");
    if (r === undefined) vars.push("R");
    if (/^(4.9+)$/.test(y)) {
        y = 4.9999;
    } else if (/^-4.9+$/.test(y)) {
        y = -4.9999;
    }
    if (vars.length !== 0) return 'Значения ' + vars.join(', ') + ' не инициализированы';
    if (isNaN(Number(y))) return 'Значение Y должно быть числом';
    if ((y >= 5 || y <= -5)) return 'Значение Y должно быть в промежутке (-5; 5)';
    return 'ok';
}
function submitForm(){
    var x=document.getElementById("x-menu").value;
    var y=document.getElementById("Y").value;
    var r=document.getElementById("R-select").value;
    y=y.replace(",",".");
    var isOk = check(x,y,r);
    if(isOk=="ok"){
        var form = document.createElement("form");
        form.method = "POST";
        form.type = "hidden";
        form.action = jcp+"/controller";
        var str = getCheckedBoxes();
        form.innerHTML = str +
            "<input type='hidden' name='y' value=" + y + ">" +
            "<input type='hidden' name='r' value=" + r + ">" +
            document.body.appendChild(form);
        console.log(str);
        form.submit();
    }else{
        let output = document.getElementById("invalid_data");
        output.innerHTML = isOk;
    }

}
function checkCheckboxes() {
    let checkboxes = document.getElementsByName("x");
    let checkboxesChecked = [];
    let htmlStr="";
    if(checkboxes.length>0){
        for (let i=0; i<checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                checkboxesChecked.push(checkboxes[i]);
                return checkboxesChecked.length;
            }
        }
    }
}
function getCheckedBoxes() {
    let checkboxes = document.getElementsByName("x");
    let checkboxesChecked = [];
    let htmlStr="";
    if(checkboxes.length>0){
        for (let i=0; i<checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                checkboxesChecked.push(checkboxes[i]);
                htmlStr = htmlStr + "<input type='hidden' name='x' value=" + $(checkboxes[i]).val() + ">";
            }
        }
    }
    console.log(htmlStr);
    return htmlStr;
}
function updateX() {
    let x_val = document.getElementById("buttons:x-menu").value;
    document.getElementById("buttons:xJS").value = x_val;
    console.log(x_val+" "+document.getElementById("buttons:xJS").value)
}
function setR(x) {
    document.getElementById("buttons:rJS").value = x;
}
function updateRadiusHidden(num){

}


