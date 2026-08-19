/*
 * Autocompletado de direcciones con Google Places (Avance 3). El callback
 * lo invoca el script de Google Maps una vez cargado (ver
 * general/fragmentos.html :: googleMapsScript).
 */
function iniciarAutocompletadoDirecciones() {
    document.querySelectorAll('.js-autocomplete-direccion').forEach(function (input) {
        var autocomplete = new google.maps.places.Autocomplete(input, {
            componentRestrictions: {country: 'cr'},
            fields: ['formatted_address', 'geometry']
        });
        autocomplete.addListener('place_changed', function () {
            var place = autocomplete.getPlace();
            // Sin geometry el usuario escribio texto libre sin elegir sugerencia; se deja igual.
            if (!place.geometry) {
                return;
            }
            input.value = place.formatted_address;
            var contenedor = input.closest('form') || document;
            var lat = contenedor.querySelector('.js-latitud');
            var lng = contenedor.querySelector('.js-longitud');
            if (lat) {
                lat.value = place.geometry.location.lat();
            }
            if (lng) {
                lng.value = place.geometry.location.lng();
            }
        });
    });
}
window.iniciarAutocompletadoDirecciones = iniciarAutocompletadoDirecciones;

function usarDireccionGuardada(boton) {
    var datos = boton.dataset;
    var forma = document.getElementById('formEntrega');
    if (!forma) {
        return;
    }
    forma.querySelector('[name=nombreEntrega]').value = datos.nombre || '';
    forma.querySelector('[name=telefonoEntrega]').value = datos.telefono || '';
    forma.querySelector('[name=provincia]').value = datos.provincia || '';
    forma.querySelector('[name=canton]').value = datos.canton || '';
    forma.querySelector('[name=distrito]').value = datos.distrito || '';
    forma.querySelector('[name=direccionExacta]').value = datos.direccion || '';
    forma.querySelector('[name=notaEntrega]').value = datos.nota || '';
}
window.usarDireccionGuardada = usarDireccionGuardada;
