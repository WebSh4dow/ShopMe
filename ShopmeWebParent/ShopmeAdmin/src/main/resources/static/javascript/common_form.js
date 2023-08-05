function showModalDialog(title, message) {

    document.getElementById("modalTitle").textContent = title;
    document.getElementById("modalBody").textContent = message;

    const modal = UIkit.modal("#modalDialog");
    modal.show();
}

function showErrorModal(message) {
     showModalDialog("Erro", message);
}

function showWarningModal(message) {
    showModalDialog("Atenção", message);
}