// Rutinas generales de AutoCR Pro (patron visto en Semana 4/5: rutinas.js en static/js)

document.addEventListener("DOMContentLoaded", function () {
    // Los mensajes flash (fragmento "mensajes") se ocultan solos despues de 4 segundos.
    document.querySelectorAll(".toast.show").forEach(function (toast) {
        setTimeout(function () {
            toast.classList.remove("show");
        }, 4000);
    });
});
