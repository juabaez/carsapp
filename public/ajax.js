function httpreq(params) {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState===4 && xmlhttp.status===200) {
                document.getElementById(params.container).innerHTML=xmlhttp.responseText;
            }
        }
    ;  
    xmlhttp.open(params.method, params.url, params.async);
    switch(params.method) {
        case 'GET': 
            xmlhttp.send();
            break;
        case 'POST':
            //TODO: Implement this method
            break;
    } 
}
