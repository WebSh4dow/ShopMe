function showModalDialog(title,message){
    $("#modalitle").text(title);
    $("#modalBody").text(message);
    $("#modalDialog").modal();
}

function showErrorModal(message){
    showModalDialog("ERRO",message);
}

function showWarningModal(message){
    showModalDialog("ATENÇÃO",message);
}

