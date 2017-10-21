/* global google, PF */

var currentMarker = null;
var geocoder = null;

function handlePointClick(event) {

    if (currentMarker === null) {
        document.getElementById('lat').value = event.latLng.lat();
        document.getElementById('lng').value = event.latLng.lng();

        currentMarker = new google.maps.Marker({
            position: new google.maps.LatLng(event.latLng.lat(), event.latLng.lng()),
            draggable: true
        });

        PF('map').addOverlay(currentMarker);

        geocoder = new google.maps.Geocoder();

        geocoder.geocode({ 'latLng': currentMarker.getPosition() }, function (results, status) {
            if(status === google.maps.GeocoderStatus.OK) {
                if(results[0]) { 
                    document.getElementById('txtEndereco').value = results[0].formatted_address;
                    document.getElementById('endereco').value = results[0].formatted_address;
                    document.getElementById('lat').value = currentMarker.getPosition().lat();
                    document.getElementById('lng').value = currentMarker.getPosition().lng();
                }
            }
        });    
        
        PF('dlg').show();
    }
    else{
        currentMarker.setMap(null);
        currentMarker = null;
        handlePointClick(event);
    }
}

function adicionarMarcador() {
    var title = document.getElementById('title');
    currentMarker.setTitle(title.value);
    
    title.value = "";

    PF('dlg').hide();
}

function cancelar() {
    PF('dlg').hide();
    currentMarker.setMap(null);
    currentMarker = null;

    return false;
}

function mostrarConfirmacao(){
    PF('dlg2').show();
}

/*
$(document).ready(function () {

    geocoder = new google.maps.Geocoder();

    google.maps.event.addListener(currentMarker, 'drag', function () {
        geocoder.geocode({ 'latLng': currentMarker.getPosition() }, function (results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                    if (results[0]) { 
                    //$('#txtEndereco').val(results[0].formatted_address);
                    document.getElementById('txtEndereco').value = results[0].formatted_address;
                    document.getElementById('lat').value = currentMarker.getPosition().lat();
                    document.getElementById('lng').value = currentMarker.getPosition().lng();
                }
            }
        });
    });

});

*/