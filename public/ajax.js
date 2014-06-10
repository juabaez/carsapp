function traer(url, contenedor, formulario) {
    $.get(url, $("#" + formulario).serialize(), function(returnData) {
        escribir(returnData, contenedor);
    });
};

function postear(url, contenedor, formulario) {
    $.post(url, $("#" + formulario).serialize(), function(returnData) {
        escribir(returnData, contenedor);
    });
};

function escribir(dato, contenedor) {
    $("#" + contenedor).html(dato);
};