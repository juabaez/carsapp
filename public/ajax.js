/* Una function que recibe como parametros una url, un div container
 * y ejecuta un HTTP GET sobre la url e inserta el cuerpo de la respuesta
 * en el container pasado por parametro.
 * Uso: 
 * <div id=usuarios>
 * <input type=button value="Probar" onclick="traer('users','usuarios')">
 * </div>
 */
function traer(url, container)
{
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange=function() {
        if (xmlhttp.readyState==4 && xmlhttp.status==200) {
            document.getElementById(container).innerHTML=xmlhttp.responseText;
        }
    }
    xmlhttp.open('GET', url, true);
    xmlhttp.send();
    xmlhttp.close();
}

function lucho(params)
{
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange=function() {
        if (xmlhttp.readyState==4 && xmlhttp.status==200) {
            document.getElementById(params.container).innerHTML=xmlhttp.responseText;
        }
    }
    xmlhttp.open(params.method, params.url, params.async);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    switch(params.method) {
        case 'GET': 
            xmlhttp.send();
            break;
        case 'POST':
            xmlhttp.send(params.postattrb);
            break;
    } 
}


