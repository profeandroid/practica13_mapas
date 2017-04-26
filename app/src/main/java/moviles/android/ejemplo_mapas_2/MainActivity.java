package moviles.android.ejemplo_mapas_2;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity {
    GoogleMap gm;
    LocationManager lm;
    Marker anterior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //obtener objeto LocationManager que representa
        //el servicio de localizaciÃ³n
        lm=(LocationManager)this.
                getSystemService(Context.LOCATION_SERVICE);
        FragmentManager fm=this.getSupportFragmentManager();
        SupportMapFragment smf=(SupportMapFragment)	fm.findFragmentById(R.id.map);
       /* gm=smf.getMap();
        gm.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        gm.getUiSettings().setZoomControlsEnabled(true);*/
        smf.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gm=googleMap;
                gm.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                gm.getUiSettings().setZoomControlsEnabled(true);
                gm.getUiSettings().setMapToolbarEnabled(true);
                gm.setMyLocationEnabled(true);
                //posición inicial centro Los Cármenes
                LatLng pos=new LatLng(40.3960965,-3.743638);
                gm.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 18));

                //añade un marcador a la posición
                anterior=gm.addMarker(new MarkerOptions().title("centro formación").position(pos));



            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        //elimina la asociación con el escuchador cuando
        //la actividad no está activa
        lm.removeUpdates(escuchadorPosicion);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //asociar el LocationManager al escuchador
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, escuchadorPosicion);

    }


    LocationListener escuchadorPosicion=new LocationListener(){

        @Override
        public void onLocationChanged(Location loc) {
            //elimina marcador anterior
            anterior.remove();
            LatLng pos=new LatLng(loc.getLatitude(), loc.getLongitude());
            gm.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 7));
            //añade un marcador a la posición
            anterior=gm.addMarker(new MarkerOptions().position(pos));
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };


}
