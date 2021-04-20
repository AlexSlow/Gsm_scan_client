var map;
var layerMarkers;

function GetMap() {
    var markerAction = false;
    var maxExtent = new OpenLayers.Bounds(-20037508, -20037508, 20037508, 20037508);
    var restrictedExtent = maxExtent.clone();
    var maxResolution = 156543.0339;
    var options = {
        projection: new OpenLayers.Projection("EPSG:900913"),
        displayProjection: new OpenLayers.Projection("EPSG:4326"),
        numZoomLevels: 18,
        maxResolution: maxResolution,
        maxExtent: maxExtent,
        restrictedExtent: restrictedExtent
    };
    map = new OpenLayers.Map("OSMap", options);
    var mapnik = new OpenLayers.Layer.OSM();
    map.addLayer(mapnik);
    map.setCenter(new OpenLayers.LonLat(30.37681, 60.01196)
          .transform(
            new OpenLayers.Projection("EPSG:4326"),
            new OpenLayers.Projection("EPSG:900913")
          ), 15
        );
    layerMarkers = new OpenLayers.Layer.Markers("Markers");
	layerMarkers.events.register('mousedown', layerMarkers, function(e) { 
        console.log(layerMarkers);
        var m = e.object;
        var id = m.id.toString();
        var popup = new OpenLayers.Popup("object" + id, m.lonlat, new OpenLayers.Size(200,200), Date.now(), true);
        map.addPopup(popup);
        markerAction = true;
	});
    map.addLayer(layerMarkers);//добавляем этот слой к карте
    map.events.register('click', map, function (e) {   
        layerMarkers.addMarker(
            new OpenLayers.Marker(
                map.getLonLatFromViewPortPx(e.xy), 
                GetIcon(),
                {
                    name: Date.now()
                }
            )
        );
    });
    SetMarker(30.37681, 60.01196);
}

function SetMarker(x, y){
    layerMarkers.addMarker(
        new OpenLayers.Marker(
            new OpenLayers.LonLat(x,y).transform(
                new OpenLayers.Projection("EPSG:4326"), // переобразование в WGS 1984
                new OpenLayers.Projection("EPSG:900913") // переобразование проекции
              ),
            GetIcon()
        )
    );
}

function GetIcon(){
    var size = new OpenLayers.Size(25, 25);//размер картинки для маркера
    var offset = new OpenLayers.Pixel(-(size.w / 2), -size.h); //смещение картинки для маркера
    return new OpenLayers.Icon('img/station.png', size, offset);
}

function ClosePopup(){
    map.popups.forEach(function (poppup){
        map.removePopup(poppup);
    })
}